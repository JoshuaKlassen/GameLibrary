package jgame.graphics;

import java.awt.image.BufferedImage;

import jgame.entities.ICollisionArea;

public class Sprite{

	private BufferedImage image;
	
	private ICollisionArea collisionArea;
	private ICollisionArea hurtArea;
	private ICollisionArea hitArea;
	
	public Sprite(BufferedImage image){
		this.image = image;
	}
	

	public void setCollisionArea(ICollisionArea area) { collisionArea = area; }
	
	public ICollisionArea getCollisionArea(){
		return collisionArea;
	}
	
	public void setHurtArea(ICollisionArea area) { hurtArea = area; }
	
	public void setHitArea(ICollisionArea area) { hitArea = area; }
	
	public BufferedImage getImage(){
		return image;
	}	
}