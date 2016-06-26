package jgame.networking;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

import jgame.util.Delay;
import jgame.util.ErrorManager;

public abstract class JClient {

	private int port;

	private Socket tcpSocket;
	private DatagramSocket udpSocket;

	private ObjectOutputStream oos;
	private ObjectInputStream ois;

	private InetAddress serverAddress;
	private InetSocketAddress serverSocketAddress;
	
	private boolean tcpListening = false;
	private boolean udpListening = false;

	public static int pingDelay = 5000;

	private long pingStartTime;
	private int pingAttempt = 0;

	public static int maxPingAttempts = 5;

	private int reconnectAttempt = 1;

	public static int maxReconnectAttempts = 5;
	private boolean pingRunning = false;

	private Thread udpListenToServer;
	private Thread pingThread;
	
	public JClient(String address, int port) {
		try {
			serverAddress = InetAddress.getByName(address);
			this.port = port;
			
			serverSocketAddress = new InetSocketAddress(address, port);

			tcpSocket = new Socket();
			tcpSocket.connect(serverSocketAddress);
			
			startClient();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void startClient() {
		try {
			udpSocket = new DatagramSocket();
			
			oos = new ObjectOutputStream(tcpSocket.getOutputStream());
			ois = new ObjectInputStream(tcpSocket.getInputStream());

			sendHandshake();
			if (completeHandshake()) {
				startTcpListeningToServer();
				startUdpListeningToServer();
				onConnection();
				ping();
			} else {
				close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public abstract void processTcpPacket(Serializable data);

	public abstract void processUdpPacket(Serializable data);

	public abstract void onConnection();

	public abstract void onPingResponse(long delay);
	
	public abstract void onAttemptReconnect(int reconnectAttempt);
	
	public abstract void onReconnectSucceeded();
	
	public abstract void onReconnectFailed();

	private void sendHandshake() {
		Packet handshake = new Packet(PacketType.HANDSHAKE, udpSocket.getLocalPort());
		sendTcp(handshake);
	}

	private synchronized void startTcpListeningToServer() {
		Thread tcpListenToServer = new Thread("Tcp connection: " + serverAddress + ":" + port) {
			public void run() {
				try {
					tcpListening = true;
					while (tcpListening) {
						try {
							Packet data = (Packet) ois.readObject();
							processTcpPacket(data);
						} catch (ClassNotFoundException e) {
							ErrorManager.appendToLog(e.getMessage());
						}
					}
				} catch (IOException e) {
					ErrorManager.write(e.getMessage());
					udpListening = false;
					udpSocket.close();
					startAttemptingReconnect();
				}
			}
		};
		tcpListenToServer.start();
	}

	private synchronized void startUdpListeningToServer() {
		udpListenToServer = new Thread("Udp connection: " + serverAddress + ":" + udpSocket.getPort()) {
			public void run() {
				udpListening = true;
				while (udpListening) {
					try {
						byte[] data = new byte[Packet.Max_Size];
						DatagramPacket packet = new DatagramPacket(data, data.length);
						udpSocket.receive(packet);
						ByteArrayInputStream bais = new ByteArrayInputStream(packet.getData());
						ObjectInputStream ois;
						ois = new ObjectInputStream(bais);
						PacketType type = (PacketType) ois.readObject();
						Serializable contents = (Serializable) ois.readObject();

						processUdpPacket(type, contents);
						
						ois.close();
						bais.close();
					} catch (ClassNotFoundException | IOException e) {
						ErrorManager.appendToLog(e.getMessage());
					}
				}
			}
			
			@Override
			public void interrupt(){
				System.out.println("Closing?");
				super.interrupt();
				udpSocket.disconnect();
				udpSocket.close();
			}
		};
		udpListenToServer.start();
	}

	private void processUdpPacket(PacketType type, Serializable contents){
		if(type == PacketType.CONNECTION){
			sendUdp(new Packet(type, contents));
		}else if(type == PacketType.PING){
			long now = System.currentTimeMillis();
			pingAttempt = 0;
			onPingResponse((short)(now - (long)contents));
		}else{
			processUdpPacket(contents);
		}
	}
	
	private synchronized void sendTcp(Packet packet) {
		Thread sendTcp = new Thread("Send (TCP)") {
			public void run() {
				try {
					oos.writeObject(packet);
					oos.flush();
				} catch (IOException e) {
					ErrorManager.appendToLog(e.getMessage());
				}
			}
		};
		sendTcp.start();
	}

	private synchronized void sendUdp(Packet packet) {
		Thread sendUdp = new Thread("Send (UDP)") {
			public void run() {
				try {
					byte[] data = packet.convertContentToByteArray();
					DatagramPacket datagramPacket = new DatagramPacket(data, data.length, serverAddress, port);
					udpSocket.send(datagramPacket);
				} catch (IOException e) {
					ErrorManager.appendToLog(e.getMessage());
				}
			}
		};
		sendUdp.start();
	}

	public void sendTcp(Serializable data){
		sendTcp(new Packet(PacketType.DATA, data));
	}
	
	public void sendUdp(Serializable data){
		sendUdp(new Packet(PacketType.DATA, data));
	}
	
	private boolean completeHandshake() throws IOException {
		boolean success = false;
		String errorMessage = "";
		Serializable content;

		try {
			Packet handshake = (Packet) ois.readObject();

			if (handshake.getType() == PacketType.HANDSHAKE) {
				content = handshake.getContents();

				if (content instanceof Boolean) {
					success = (Boolean) content;
				} else {
					errorMessage = "Server failed to connect to UDP Port (" + udpSocket.getPort() + ")";
				}
			}

		} catch (IOException | ClassNotFoundException e) {
			ErrorManager.appendToLog(e.getMessage());
		}

		if (!success) {
			throw new IOException("Failed to complete handshake: " + errorMessage);
		}

		return success;
	}

	private synchronized void ping() {
		pingThread = new Thread("Ping") {
			public void run() {
				pingRunning = true;
				while (pingRunning) {
					pingAttempt++;

					pingStartTime = System.currentTimeMillis();
					Packet ping = new Packet(PacketType.PING, pingStartTime);
					
					sendUdp(ping);

					Delay waitDelay = new Delay(pingDelay);
					waitDelay.start();

					while (!waitDelay.isOver());

					if (pingAttempt == maxPingAttempts) {
						pingRunning = false;
						timedOut();
					}
				}
			}
		};
		pingThread.start();

	}

	private synchronized void startAttemptingReconnect() {
		Thread attemptReconnectThread = new Thread("Reconnection Attempt") {
			public void run() {
				reconnectAttempt = 1;
				pingRunning = false;
				boolean reconnected = false;
				close();

				while (!reconnected && reconnectAttempt <= maxReconnectAttempts) {
					try {
						onAttemptReconnect(reconnectAttempt);
						tcpSocket = new Socket();
						tcpSocket.connect(serverSocketAddress, 1000);
						tcpSocket.setSoTimeout(0);
						reconnected = true;
						reconnectAttempt = 1;
					} catch (IOException e){
						reconnectAttempt ++;

						Delay waitDelay = new Delay(5000);
						waitDelay.start();

						while (!waitDelay.isOver());
					}
				}

				if (!reconnected) {
					onReconnectFailed();
				} else {
					onReconnectSucceeded();
					startClient();
				}
			}
		};
		attemptReconnectThread.start();
	}

	private void timedOut() {
		ErrorManager.write("Connected timed out");
		close();
	}

	public synchronized void close() {
		Thread closeThread = new Thread("Close connection") {
			public void run() {
				try {
					System.out.println("Trying");
					synchronized (tcpSocket) {
						tcpListening = false;
						oos.flush();
						tcpSocket.shutdownOutput();
						tcpSocket.shutdownInput();
						tcpSocket.close();

						udpListening = false;
						udpListenToServer.interrupt();
						
					}
				} catch (IOException e) {
					ErrorManager.appendToLog(e.getMessage());
				}
			}
		};
		closeThread.start();
		ErrorManager.printLog();
	}
}