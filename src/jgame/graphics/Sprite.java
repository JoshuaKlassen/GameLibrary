package jgame.graphics;

import java.awt.image.BufferedImage;

import jgame.entities.ICollisionArea;

public class Sprite {

	private BufferedImage image;
	
	private ICollisionArea collisionArea;
	private ICollisionArea hurtArea;
	private ICollisionArea hitArea;
	
	public Sprite(BufferedImage image){
		this.image = image;
	}
	
	public BufferedImage getImage(){
		return image;
	}
	
}
