package jgame.networking;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Packet implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7397769226016673434L;
	
	public static int Max_Size = 1024;
	
	private Serializable contents;
	
	private PacketType type;

	
	public Packet(PacketType type, Serializable contents){
		this.type = type;
		this.contents = contents;
	}
	
	public PacketType getType() { return type; }
	
	public Serializable getContents() { return contents; }
	
	public byte[] convertContentToByteArray(){
		byte[] data = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try{
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(type);
			oos.writeObject(contents);
			data = baos.toByteArray();
			oos.flush();
			oos.close();
		} catch(IOException e){
			e.printStackTrace();
		}
		return data;
	}
	
	public String toString(){
		return "Type: " + type.name() + " Contents: " + contents;
	}
	
}