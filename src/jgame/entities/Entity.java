package jgame.entities;

import java.awt.Graphics;

import jgame.util.JSerializable;
import jgame.util.Vector2F;

public abstract class Entity extends JSerializable{

	private static final long serialVersionUID = 1478579988881050203L;

	protected Vector2F position;

	public abstract void update();
	
	public abstract void render(Graphics g);
	
}