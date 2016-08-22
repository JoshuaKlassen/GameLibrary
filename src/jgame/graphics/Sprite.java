package jgame.graphics;

import java.awt.image.BufferedImage;

import jgame.entities.ICollisionArea;
import jgame.util.Vector2;

public class Sprite implements IMesh{

	private BufferedImage image;
	
	private ICollisionArea collisionArea;
	private ICollisionArea hurtArea;
	private ICollisionArea hitArea;
	
	private Vector2 size;
	
	public Sprite(BufferedImage image){
		this.image = image;
		size = new Vector2();
	}
	
	public void render(JGraphics g, Vector2 position){
		if(size.x == 0 && size.y == 0){
			g.drawImage(image, position);
		}else{
			g.drawImage(image, position, (int)size.x, (int)size.y);
		}
		
	}

	public void setCollisionArea(ICollisionArea area) { collisionArea = area; }
	
	public ICollisionArea getCollisionArea(){
		return collisionArea;
	}
	
	public void setHurtArea(ICollisionArea area) { hurtArea = area; }
	
	public void setHitArea(ICollisionArea area) { hitArea = area; }
	
	public void setSpriteSize(Vector2 size) { this.size = size; }
	
	public BufferedImage getImage(){
		return image;
	}	
}