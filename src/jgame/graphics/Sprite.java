package jgame.graphics;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.imageio.ImageIO;

import jgame.util.Vector2;

public class Sprite implements IMesh, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7281485039868967706L;

	private transient BufferedImage image;
	
	private int width = -1;
	private int height = -1;
	
	public Sprite(BufferedImage image){
		this.image = image;
	}
	
	public void render(JGraphics g, Vector2 position){
			g.drawImage(image, position, (width != -1 ? width : image.getWidth()), (height != -1 ? height : image.getHeight()));
	}
	
	public void setSpriteWidth(int width) { this.width = width; }
	
	public void setSpriteHeight(int height) { this.height = height; }
	
	public BufferedImage getImage(){
		return image;
	}	
	
	private void writeObject(ObjectOutputStream out) throws IOException{
		out.defaultWriteObject();
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		ImageIO.write(image,  "png", buffer);
		
		out.writeInt(buffer.size());
		buffer.writeTo(out);
	}
	
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		int bufferSize = in.readInt();
		byte[] buffer = new byte[bufferSize];
		
		in.readFully(buffer);
		image = ImageIO.read(new ByteArrayInputStream(buffer));
	}
}