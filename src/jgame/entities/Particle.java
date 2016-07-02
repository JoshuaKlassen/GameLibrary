package jgame.entities;

import java.awt.Color;

import jgame.graphics.Animation;
import jgame.graphics.JGraphics;
import jgame.graphics.Sprite;
import jgame.util.Vector2F;

public class Particle extends Entity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6519802817785033467L;
	
	private float lifespan;
	private int size;
	
	private Color color;
	
	private transient Sprite sprite;
	
	private Animation animation;
	
	public Particle(Vector2F position, float lifespan, Vector2F velocity, Color color, int size){
		this.lifespan = lifespan;
		this.position = position;
		this.velocity = velocity;
		this.color = color;
		this.size = size;
	}
	
	public Particle(float lifespan, Vector2F position, Vector2F velocity, Sprite sprite){
		this.lifespan = lifespan;
		this.position = position;
		this.velocity = velocity;
		this.sprite = sprite;
	}
	
	public Particle(float lifespan, Vector2F position, Vector2F velocity, Animation animation){
		this.lifespan = lifespan;
		this.position = position;
		this.velocity = velocity;
		this.animation = animation;
	}
	
	@Override
	public void update() {
		if(isAlive()){
			position = Vector2F.add(position, velocity);
			lifespan -= 0.1f;
			if(animation != null){
				animation.update();
			}
		}
	}

	@Override
	public void render(JGraphics g) {
		if(isAlive()){
			if(sprite != null){
				g.drawSprite(sprite, position);
			}else if(animation != null){
				g.drawSprite(animation.getCurrentFrame(), position);
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