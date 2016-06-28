package jgame.entities;

import java.awt.Graphics;

import jgame.util.Vector2F;

public interface ICollisionArea {

	boolean collision(Vector2F point);
	
	boolean collision(ICollisionArea area);
	
	void toggleShow();
	
	void render(Graphics g);
	
	void move(Vector2F position);
}
