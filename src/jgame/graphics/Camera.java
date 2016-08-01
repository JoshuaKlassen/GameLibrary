package jgame.graphics;

import java.awt.Graphics2D;

import jgame.entities.Actor;
import jgame.entities.IUpdatable;
import jgame.game.JGame;
import jgame.util.Vector2;

public class Camera implements IUpdatable{

	private Actor target;
	
	private boolean isFollowing = true;
	
	private boolean locked = false;
	
	private Vector2 position;
	
	private JGame gameInstance;
	
	private float zoom = 1;
	
	public Camera(JGame game){
		gameInstance = game;
		position = new Vector2();
	}
	
	public void update(){
		if(!locked && isFollowing && target != null){
			Vector2 pos = target.position();
			position.x = (pos.x - (gameInstance.getWidth() / 2) / gameInstance.getScaleWidth() / zoom);
			position.y = (pos.y - (gameInstance.getHeight() / 2) / gameInstance.getScaleHeight() / zoom);
		}
	}
	
	public void startCapture(JGraphics g){
		Graphics2D g2d = (Graphics2D)(g.getInstance());
		g2d.scale(zoom, zoom);
		g2d.translate(-position.x, -position.y);
	}
	
	public void endCapture(JGraphics g){
		Graphics2D g2d = (Graphics2D)(g.getInstance());
		g2d.scale(1/zoom, 1/zoom);
		g2d.translate(position.x, position.y);
	}
	
	public void follow(Actor e) { target = e; }
	
	public void startFollowing() { isFollowing = true; }
	
	public void stopFollowing() { isFollowing = false; }
	
	public Vector2 position() { return position; }
	
	public void SetZoom(float zoom) { 
		if(zoom <= 0) {
			throw new IllegalArgumentException("Zoom cannot be less than or equal to zero: Zoom=" + zoom);
		}
		this.zoom = zoom; 
	}
	
	public float getZoom() { return zoom; }
}