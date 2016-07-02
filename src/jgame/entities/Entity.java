package jgame.entities;

import jgame.graphics.Drawable;
import jgame.util.JSerializable;
import jgame.util.Vector2F;

public abstract class Entity extends JSerializable implements Drawable, Updatable{

	private static final long serialVersionUID = 1478579988881050203L;

	protected Vector2F position = new Vector2F();
	
	protected Vector2F velocity = new Vector2F();
	
	public abstract boolean isAlive();
	
	public Vector2F position() { return position; }
	
	public void setPosition(Vector2F pos) { position = pos; }
	
	public Vector2F velocity() { return velocity; }
	
	public void setVelocity(Vector2F vel) { velocity = vel; }
	
}