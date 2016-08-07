package jgame.geometry;

import java.awt.Color;
import java.awt.Shape;

import jgame.graphics.IRenderable;
import jgame.graphics.JGraphics;
import jgame.util.JSerializable;
import jgame.util.Vector2;

public abstract class JShape extends JSerializable implements Shape, IRenderable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2744971923330603784L;

	protected Vector2 position;
	
	protected Color outlineColor = Color.BLACK;
	protected Color innerColor = Color.BLACK;
	
	public abstract void render(JGraphics g);
	
	public abstract void move(int dx, int dy);
	
	public Vector2 getPosition() { return position; }
	
	public Color getOutlineColor() { return outlineColor; }
	
	public void setOutlineColor(Color color) { outlineColor = color; }
	
	public Color getInnerColor() { return innerColor; }
	
	public void setInnerColor(Color color) { innerColor = color; }
}