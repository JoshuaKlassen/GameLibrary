package jgame.geometry;

import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import jgame.graphics.JGraphics;
import jgame.util.Vector2;

public class JCircle extends JShape{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1769116937837160231L;
	
	private int width;
	private int height;
	
	private Ellipse2D ellipse;
	
	public JCircle(Vector2 position, int diameter){
		this.position = position;
		this.width = diameter;
		this.height = diameter;
		ellipse = new Ellipse2D.Float(position.x, position.y, diameter, diameter);
	}
	
	public JCircle(Vector2 position, int width, int height){
		this.position = position;
		this.width = width;
		this.height = height;
		ellipse = new Ellipse2D.Float(position.x, position.y, width, height);
	}
	
	@Override
	public void move(int dx, int dy) {
		position.x += dx;
		position.y += dy;
		ellipse = new Ellipse2D.Float(position.x, position.y, width, height);
	}
	
	public PathIterator getPathIterator() { return ellipse.getPathIterator(null); }
	
	public void setCenterPosition(Vector2 center){
	}
	
	public Vector2 getCenterPosition() { return new Vector2(position.x + (width / 2), position.y + (height / 2)) ; }

	@Override
	public void render(JGraphics g) {
		g.setColor(innerColor);
		g.fillCircle(position, width, height);
		g.setColor(outlineColor);
		g.drawCircle(position, width, height);
	}

	@Override
	protected void init() {
		
	}

	@Override
	public boolean contains(Point2D arg0) {
		return contains(arg0);
	}

	@Override
	public boolean contains(Rectangle2D arg0) {
		return contains(arg0);
	}

	@Override
	public boolean contains(double arg0, double arg1) {
		return contains(arg0, arg1);
	}

	@Override
	public boolean contains(double arg0, double arg1, double arg2, double arg3) {
		return ellipse.contains(arg0, arg1, arg2, arg3);
	}

	@Override
	public Rectangle getBounds() {
		return ellipse.getBounds();
	}

	@Override
	public Rectangle2D getBounds2D() {
		return ellipse.getBounds2D();
	}

	@Override
	public PathIterator getPathIterator(AffineTransform arg0) {
		return ellipse.getPathIterator(arg0);
	}

	@Override
	public PathIterator getPathIterator(AffineTransform arg0, double arg1) {
		return ellipse.getPathIterator(arg0, arg1);
	}

	@Override
	public boolean intersects(Rectangle2D arg0) {
		return ellipse.intersects(arg0);
	}

	@Override
	public boolean intersects(double arg0, double arg1, double arg2, double arg3) {
		return ellipse.contains(arg0, arg1, arg2, arg3);
	}
}