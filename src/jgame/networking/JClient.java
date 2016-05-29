package jgame.networking;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

import jgame.util.Delay;
import jgame.util.ErrorManager;

public abstract class JClient {

	private int port;

	private Socket tcpSocket;
	private DatagramSocket udpSocket;

	private InetAddress serverAddress;

	private ObjectOutputStream oos;
	private ObjectInputStream ois;

	private boolean tcpListening = false;
	private boolean udpListening = false;

	public static int pingDelay = 5000;

	private long pingStartTime;
	private int pingAttempt = 0;
	
	public static int maxPingAttempts = 5;

	private boolean pingRunning = false;

	private Thread pingThread;
	
	private boolean emergencyPing = false;

	public JClient(String address, int port) {
		try {
			serverAddress = InetAddress.getByName(address);
			this.port = port;

			udpSocket = new DatagramSocket();

			tcpSocket = new Socket(serverAddress, port);

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
	
	public abstract void processTcpPacket(Packet data);

	public abstract void processUdpPacket(Packet data);
	
	public abstract void onConnection();
	
	public abstract void onPingResponse(long delay);

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
						} catch (SocketException e) {
							if(!emergencyPing){
								ErrorManager.write("Lost connection.");
								emergencyPing = true;
							}
						}
					}
				} catch (IOException e) {
					ErrorManager.appendToLog(e.getMessage());
				}
			}
		};
		tcpListenToServer.start();
	}

	private synchronized void startUdpListeningToServer() {
		Thread udpListenToServer = new Thread("Udp connection: " + serverAddress + ":" + udpSocket.getPort()) {
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

						Packet packetReceived = new Packet(type, contents);
						if (type == PacketType.CONNECTATTEMPT) {
							sendUdp(packetReceived);
						} else if (type == PacketType.PING && (Integer) contents == pingAttempt) {
							long now = System.currentTimeMillis();
							pingAttempt = 0;

							if(emergencyPing){
								emergencyPing = false;
								System.out.println("Connection re-established");
							}
							
							onPingResponse(now - pingStartTime);
						} else {
							processUdpPacket(packetReceived);
						}

					} catch (ClassNotFoundException | IOException e) {
						ErrorManager.appendToLog(e.getMessage());
					}
				}
			}
		};
		udpListenToServer.start();
	}

	public synchronized void sendTcp(Packet packet) {
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

	public synchronized void sendUdp(Packet packet) {
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

	public boolean completeHandshake() throws IOException {
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
					if(emergencyPing){
						ErrorManager.write("Attempting to re-establish connection... " + pingAttempt);
					}
					
					Packet ping = new Packet(PacketType.PING, pingAttempt);
					pingStartTime = System.currentTimeMillis();
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

	private void timedOut(){
		ErrorManager.write("Connected timed out");
		close();
	}
	
	public synchronized void close() {
		Thread closeThread = new Thread("Close connection") {
			public void run() {
				try {
					synchronized (tcpSocket) {
						pingRunning = false;
						
						tcpListening = false;
						oos.flush();
						oos.close();
						ois.close();
						tcpSocket.close();

						udpListening = false;
						udpSocket.close();
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
