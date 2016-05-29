package jgame.networking;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import jgame.util.Delay;
import jgame.util.ErrorManager;

public abstract class JServer {

	private int port;
	private int maxPacketSize;

	private ServerSocket tcpSocket;
	private DatagramSocket udpSocket;

	private InetAddress serverAddress;

	private Thread tcpServerThread;
	private Thread udpServerThread;

	private boolean tcpServerRunning;
	private boolean udpServerRunning;

	private int maxConnectionAttempts = 5;

	private int timeOutDelay = 5000;

	private ArrayList<ConnectedClient> clients;
	private ArrayList<Integer> clientsConnected;

	public JServer(int port) {
		try {
			serverAddress = InetAddress.getLocalHost();

			this.port = port;

			clients = new ArrayList<ConnectedClient>();
			clientsConnected = new ArrayList<Integer>();

			tcpSocket = new ServerSocket(port);
			udpSocket = new DatagramSocket(port);
		} catch (IOException e) {
			ErrorManager.appendToLog(e.getMessage());
		}
	}

	public void start() {
		startTcpServer();
		startUdpServer();
	}

	private synchronized void startTcpServer() {
		tcpServerRunning = true;

		tcpServerThread = new Thread("TcpServer: " + serverAddress + ":" + port) {
			public void run() {
				while (tcpServerRunning) {
					try {
						Socket connectedSocket = tcpSocket.accept();

						ConnectedClient client = new ConnectedClient(connectedSocket);

						if (!clients.contains(client)) {
							clients.add(client);

							try {
								completeHandshake(client);
								onClientConnection(client);
								startTcpListenToClient(client);
							} catch (IOException e) {
								ErrorManager.appendToLog(e.getMessage());
							}
						}
					} catch (IOException e) {
						ErrorManager.appendToLog(e.getMessage());
					}
				}
			}
		};
		tcpServerThread.start();
	}

	private synchronized void startTcpListenToClient(final ConnectedClient client) {
		Thread listenToClient = new Thread("Tcp connection: " + client.getAddress() + ":" + client.getTcpPort()) {
			public void run() {
				boolean listenToClient = true;
				while (listenToClient && client.isConnected()) {
					try {
						Packet data = (Packet) client.getTcpInputStream().readObject();
						processTcpPacket(client, data);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						if (!client.isAttemptingConnection()) {
							client.isAttemptingConnection(true);
							checkClientConnectionStatus(client);
						}
					}
				}
			}
		};

		listenToClient.start();
	}

	private synchronized void completeHandshake(ConnectedClient client) throws IOException {
		boolean success = false;
		String errorMessage = "";
		ObjectInputStream objectInputStream = client.getTcpInputStream();
		Packet response;

		try {
			Packet handshake = (Packet) objectInputStream.readObject();
			Serializable contents = handshake.getContents();

			if (contents instanceof Integer) {
				client.connectUdpSocket((Integer) handshake.getContents());
				success = true;
			} else {
				errorMessage = "Invalid port sent (" + contents + ").";
			}

		} catch (IOException | ClassNotFoundException e) {
			errorMessage = e.getMessage();
		}

		response = new Packet(PacketType.HANDSHAKE, success);
		sendTcp(client, response);

		if (!success) {
			throw new IOException("Failed to complete handshake: " + errorMessage);
		}
	}

	private synchronized void startUdpServer() {
		udpServerRunning = true;

		udpServerThread = new Thread("UdpServer: " + serverAddress + ":" + port) {
			public void run() {
				while (udpServerRunning) {
					byte[] data = new byte[Packet.Max_Size];
					DatagramPacket packet = new DatagramPacket(data, data.length);
					try {
						udpSocket.receive(packet);
						ByteArrayInputStream bais = new ByteArrayInputStream(packet.getData());
						ObjectInputStream ois = new ObjectInputStream(bais);
						PacketType type = (PacketType) ois.readObject();
						Serializable packetData = (Serializable) ois.readObject();
						Packet packetRecieved = new Packet(type, packetData);

						ConnectedClient sender = null;
						for (int i = 0; i < clients.size() && sender == null; i++) {
							ConnectedClient temp = clients.get(i);
							if (temp.getAddress().equals(packet.getAddress())
									&& temp.getUdpPort() == packet.getPort()) {
								sender = temp;
							}
						}

						if (sender != null) {
							if (type == PacketType.CONNECTATTEMPT) {
								clientsConnected.add(sender.getID());
							} else if (type == PacketType.PING) {
								sendUdp(sender, packetRecieved);
							} else {
								processUdpPacket(packetRecieved);
							}
						}

						bais.close();
					} catch (IOException | ClassNotFoundException e) {
						ErrorManager.appendToLog(e.getMessage());
					}
				}

			}
		};
		udpServerThread.start();
	}

	public synchronized void sendTcp(ConnectedClient client, Packet packet) {
		Thread sendTcp = new Thread("Send (TCP) to: " + client.getAddress() + ":" + client.getTcpPort()) {
			public void run() {
				ObjectOutputStream oos = client.getTcpOutputStream();
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

	public synchronized void sendUdp(ConnectedClient client, Packet data) {
		Thread sendUdp = new Thread("Send (UDP) to: " + client.getAddress() + ":" + client.getUdpPort()) {
			public void run() {
				try {
					byte[] packetData = data.convertContentToByteArray();
					DatagramPacket packet = new DatagramPacket(packetData, packetData.length, client.getAddress(),
							client.getUdpPort());
					client.getUdpSocket().send(packet);
				} catch (IOException e) {
					ErrorManager.appendToLog(e.getMessage());
				}
			}
		};
		sendUdp.start();
	}

	public synchronized void sendToAllUdp(Packet packet) {
		for (int i = 0; i < clients.size(); i++) {
			sendUdp(clients.get(i), packet);
		}
	}

	private synchronized void checkClientConnectionStatus(ConnectedClient client) {
		Thread clientConnectionStatusThread = new Thread("Client: " + client.getID() + " connection status") {
			public void run() {
				boolean checkStatusRunning = true;
				ErrorManager.write("Lost connection to client.");
				while (checkStatusRunning) {
					client.attemptedConnection();
					ErrorManager.write("Attempting connection... " + client.getConnectAttempts());
					Packet packet = new Packet(PacketType.CONNECTATTEMPT, "");
					sendUdp(client, packet);

					Delay waitDelay = new Delay(timeOutDelay);
					waitDelay.start();

					while (!waitDelay.isOver())
						;

					if (clientsConnected.contains(client.getID())) {
						clientsConnected.remove((Integer) client.getID());
						client.resetConnectAttempts();
						checkStatusRunning = false;
						client.isAttemptingConnection(false);
					}

					if (client.getConnectAttempts() == maxConnectionAttempts) {
						timeOut(client);
						checkStatusRunning = false;
					}
				}
			}
		};
		clientConnectionStatusThread.start();
	}

	private void timeOut(ConnectedClient client) {
		clients.remove(client);
		client.timeOut();
		onTimeOut(client);
	}

	public abstract void onTimeOut(ConnectedClient client);

	public abstract void onClientConnection(ConnectedClient client);

	public abstract void processTcpPacket(ConnectedClient client, Packet packet);

	public abstract void processUdpPacket(Packet packet);

	public int getPort() {
		return port;
	}

	public String getAddress() {
		return serverAddress.toString();
	}

	public int getMaxPacketSize() {
		return maxPacketSize;
	}

	public void setMaxPacketSize(int packetSize) {
		maxPacketSize = packetSize;
	}
}
