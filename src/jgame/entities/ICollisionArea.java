package jgame.entities;

import java.awt.Graphics;

import jgame.util.Vector2;

public interface ICollisionArea {

	boolean collision(Vector2 point);
	
	boolean collision(ICollisionArea area);
	
	void toggleShow();
	
	void render(Graphics g);
	
	void move(Vector2 position);
}