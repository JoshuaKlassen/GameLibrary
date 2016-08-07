package jgame.geometry;

import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import jgame.graphics.JGraphics;
import jgame.util.Vector2;

public class JPolygon extends JShape{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1683268957268417965L;

	private Vector2 position;
	
	private Polygon polygon;
	
	public JPolygon(){
		position = new Vector2();
		polygon = new Polygon();
	}
	
	public JPolygon(Vector2 position){
		this.position = position;
		polygon = new Polygon();
	}
	
	public void addPoint(Vector2 point){
		polygon.addPoint((int)(position.x + point.x), (int)(position.y + point.y));
	}
	
	
	@Override
	public void move(int dx, int dy) {
		polygon.translate(dx, dy);
	}

	@Override
	public void render(JGraphics g) {
		g.setColor(innerColor);
		g.fillPolygon(polygon);
		g.setColor(outlineColor);
		g.drawPolygon(polygon);
	}

	@Override
	protected void init() {
		
	}

	@Override
	public boolean contains(Point2D p) {
		return polygon.contains(p);
	}

	@Override
	public boolean contains(Rectangle2D r) {
		return polygon.contains(r);
	}

	@Override
	public boolean contains(double x, double y) {
		return polygon.contains(x, y);
	}

	@Override
	public boolean contains(double x, double y, double w, double h) {
		return polygon.contains(x, y, w, h);
	}

	@Override
	public Rectangle getBounds() {
		return polygon.getBounds();
	}

	@Override
	public Rectangle2D getBounds2D() {
		return polygon.getBounds2D();
	}

	@Override
	public PathIterator getPathIterator(AffineTransform at) {
		return polygon.getPathIterator(at);
	}

	@Override
	public PathIterator getPathIterator(AffineTransform at, double flatness) {
		return polygon.getPathIterator(at, flatness);
	}

	@Override
	public boolean intersects(Rectangle2D r) {
		return polygon.intersects(r);
	}

	@Override
	public boolean intersects(double x, double y, double w, double h) {
		return polygon.intersects(x, y, w, h);
	}
}