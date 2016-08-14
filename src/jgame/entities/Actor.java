package jgame.entities;

import jgame.graphics.IRenderable;
import jgame.util.JSerializable;
import jgame.util.Vector2;

public abstract class Actor extends JSerializable implements IRenderable, IUpdatable{

	private static final long serialVersionUID = 1478579988881050203L;

	protected Vector2 position = new Vector2();
	
	protected Vector2 velocity = new Vector2();
	
	public abstract boolean isAlive();
	
	private ActorManager actorManager;
	
	public Actor(ActorManager actorManager){
		this.actorManager = actorManager;
	}
	
	public Vector2 position() { return position; }
	
	public void setPosition(Vector2 pos) { position = pos; }
	
	public Vector2 velocity() { return velocity; }
	
	public void setVelocity(Vector2 vel) { velocity = vel; }
	
	public void spawn(){
		actorManager.spawn(this);
	}
	
	public void despawn(){
		actorManager.despawn(this);
	}
}