package jgame.networking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

import jgame.UI.IdGenerator;

public class ConnectedClient {

	private Socket tcpSocket;
	private DatagramSocket udpSocket;
	
	private ObjectOutputStream tcpOutputStream;
	private ObjectInputStream tcpInputStream;
	
	private InetAddress address;
	
	private int tcpPort;
	private int udpPort;
	
	private int id;
	
	private int connectAttempts = 0;
	
	private boolean attemptingConnection = false;
	
	private boolean connected = true;
	
	public ConnectedClient(Socket tcpSocket){
		connected = true;
		this.tcpSocket = tcpSocket;
		tcpPort = tcpSocket.getLocalPort();
		address = tcpSocket.getInetAddress();
		
		try {
			tcpOutputStream = new ObjectOutputStream(tcpSocket.getOutputStream());
			tcpInputStream = new ObjectInputStream(tcpSocket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		id = IdGenerator.getId();
	}
	
	public Socket getTcpSocket() { return tcpSocket; }
	
	public void connectUdpSocket(int port) throws SocketException{
		udpPort = port;
		udpSocket = new DatagramSocket();
	}
	
	public DatagramSocket getUdpSocket() { return udpSocket; }
	
	public int getTcpPort() { return tcpPort; }
	
	public int getUdpPort() { return udpPort; }
	
	public InetAddress getAddress() { return address; }
	
	public int getID() { return id; }
	
	public ObjectOutputStream getTcpOutputStream() { return tcpOutputStream; }
	
	public ObjectInputStream getTcpInputStream() { return tcpInputStream ; }
	
	public int getConnectAttempts() { return connectAttempts ; }
	
	public void attemptedConnection() { connectAttempts ++ ; }
	
	public void resetConnectAttempts() { 
		attemptingConnection = false;
		connectAttempts = 0; 
	}

	public void timeOut(){
		try{
			connected = false;
			tcpOutputStream.flush();
			tcpSocket.shutdownOutput();
			tcpSocket.shutdownInput();
			tcpSocket.close();
			
			udpSocket.disconnect();
			udpSocket.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	boolean isAttemptingConnection() { return attemptingConnection; }
	
	void isAttemptingConnection(boolean attempting) { attemptingConnection = attempting; }
	
	public boolean isConnected() { return connected; }
	
}