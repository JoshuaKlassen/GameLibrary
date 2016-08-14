package jgame.entities;

import java.awt.Color;

import jgame.graphics.Animation;
import jgame.graphics.IMesh;
import jgame.graphics.JGraphics;
import jgame.util.Vector2;

public class Particle extends Actor{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6519802817785033467L;
	
	private float lifespan;
	private int size;
	
	private Color color;
	
	private transient IMesh mesh;
	
	public Particle(Vector2 position, float lifespan, Vector2 velocity, Color color, int size){
		super(null);
		this.lifespan = lifespan;
		this.position = position;
		this.velocity = velocity;
		this.color = color;
		this.size = size;
	}
	
	public Particle(float lifespan, Vector2 position, Vector2 velocity, IMesh mesh){
		super(null);
		this.lifespan = lifespan;
		this.position = position;
		this.velocity = velocity;
		this.mesh = mesh;
	}
	
	@Override
	public void update() {
		if(isAlive()){
			position = Vector2.add(position, velocity);
			lifespan -= 0.1f;
			if(mesh != null){
				if(mesh instanceof IUpdatable){
					((IUpdatable)mesh).update();
				}
			}
		}
	}

	@Override
	public void render(JGraphics g){
		if(isAlive()){
			if(mesh != null){
				mesh.render(g, position);
			}else{
				g.setColor(color);
				g.fillRect(position, size, size);
			}
		}
	}
	
	@Override
	protected void init() {
		
	}

	public boolean isAlive() { return lifespan > 0; }
}