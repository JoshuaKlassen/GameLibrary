package jgame.physics;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.util.ArrayList;

import jgame.geometry.JShape;
import jgame.graphics.IRenderable;
import jgame.graphics.JGraphics;

public class CollisionArea implements IRenderable{

	private Color collisionColor = new Color(0.5f, 0.5f, 0.5f, 0.5f);
	
	private ArrayList<JShape> shapes;
	
	private ICollisionAction collisionAction;
	
	private Area bounds;
	
	public CollisionArea(ArrayList<JShape> shapes, Color collisionColor, ICollisionAction collisionAction){
		this.shapes = shapes;
		this.collisionColor = collisionColor;
		this.collisionAction = collisionAction;
		
		bounds = new Area();
		buildBounds();
	}
	
	@Override
	public void render(JGraphics g) {
		for(int i = 0; i < shapes.size(); i ++){
			shapes.get(i).render(g);
		}
	}
	
	public void move(int dx, int dy){
		for(int i = 0; i < shapes.size(); i ++){
			shapes.get(i).move(dx, dy);
		}
	}
	
	public void buildBounds(){
		for(int i = 0; i < shapes.size(); i ++){
			JShape shape = shapes.get(i);
			if(!bounds.contains(shape.getBounds2D())){
				bounds.add(new Area(shape));
			}
		}
	}
	
	public Color getCollisionColor() { return collisionColor; }
	
	public void setCollisionColor(Color color) { collisionColor = color; }
	
	public ArrayList<JShape> getShapes() { return shapes; }
	
	public ICollisionAction getCollisionAction() { return collisionAction; }
	
	public void setCollisionAction(ICollisionAction action) { collisionAction = action; }
	
	public Rectangle getBounds() { return bounds.getBounds(); }
}