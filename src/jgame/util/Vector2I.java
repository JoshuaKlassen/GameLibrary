package jgame.util;

import java.awt.Point;
import java.util.Random;

/**
 * The Vector2I class.
 * <br/>A child of the {@link Vector2F} class.
 * <br/>Represents a vector with it's coordinates as integers.
 * <br/>
 * <br/>Since it's coordinates are whole numbers, this class should be used to represent
 * <br/>an objects position on the screen,
 * <br/>while the {@link Vector2F} class should be used to represent objects that won't be drawn to the screen.
 * @author Joshua Klassen
 *
 */
public class Vector2I extends Vector2F{
	//auto generated
	private static final long serialVersionUID = 5619112057608236102L;

	//the coordinates
	public int x = (int)this.x;
	public int y = (int)this.y;
	
	/**
	 * Initializes the vector with the coordinates (0, 0).
	 */
	public Vector2I(){
		x = 0;
		y = 0;
	}
	
	/**
	 * Initializes the vector with the coordinates that are passed.
	 * <br/>in as parameters, i.e. (x, y).
	 * @param x coordinate.
	 * @param y coordinate.
	 */
	public Vector2I(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Initializes the vector with the coordinates of the point.
	 * <br/>that is passed in as a parameter.
	 * @param point
	 */
	public Vector2I(Point point){
		x = point.x;
		y = point.y;
	}
	
	/**
	 * Returns the distance between two vectors a and b.
	 * @param a vector.
	 * @param b vector.
	 * @return the distance between a and b.
	 */
	public static double distance(Vector2I a, Vector2I b){
		int dx = b.x - a.x;
		int dy = b.y - a.y;
		return Math.sqrt(dx * dx + dy * dy);
	}
	
	/**
	 * Returns the resulting vector of adding two vectors a and b.
	 * @param a vector.
	 * @param b vector.
	 * @return the addition of vectors a and b.
	 */
	public static Vector2I add(Vector2I a, Vector2I b){
		return new Vector2I(a.x + b.x, a.y + b.y);
	}
	
	/**
	 * Returns the resulting vector of subtracting vectors b from a.
	 * @param a vector.
	 * @param b vector.
	 * @return the subtraction of vectors b from a.
	 */
	public static Vector2I subtract(Vector2I a, Vector2I b){
		return new Vector2I(a.x - b.x, a.y - b.y);
	}
	
	/**
	 * Returns the resulting vector of multiplying vector a by a given scaler value.
	 * @param a vector.
	 * @param scaler value.
	 * @return vector a multiplied by a scaler value.
	 */
	public static Vector2I scale(Vector2I a, int scaler){
		return new Vector2I(a.x * scaler, a.y * scaler);
	}
	
	/**
	 * Returns true if vectors a and b are equal, returns false otherwise.
	 * @param a vector.
	 * @param b vector.
	 * @return true if a and b are equal, or false otherwise.
	 */
	public static boolean equals(Vector2I a, Vector2I b){
		return (a.x == b.x && a.y == b.y);
	}
	
	/**
	 * Returns a randomly generated vector with coordinates in the given range.
	 * @param minX (minimum x coordinate).
	 * @param minY (minimum y coordinate).
	 * @param maxX (maximum x coordinate).
	 * @param maxY (maximum y coordinate).
	 * @return A vector within the given range.
	 */
	public static Vector2I generateVector(int minX, int minY, int maxX, int maxY){
		Random random = new Random();
		int x = random.nextInt(maxX - minX) + maxX;
		int y = random.nextInt(maxY - minY) + maxY;
		return new Vector2I(x, y);
	}
	
	/**
	 * Returns the length of the given vector.
	 * @param a vector.
	 * @return the length of a vector.
	 */
	public static double length(Vector2F a){
		return Math.sqrt(a.x * a.x + a.y * a.y);
	}
	
	/**
	 * Normalizes the vector
	 * <br/>i.e. transforms the vector to a vector with a length of 0.
	 */
	public void normalize(){
		if(length() != 0){
			int s = (int)(1 / length());
			x *= s;
			y *= s;
		}
	}
	
	/**
	 * Returns the distance between the vector that called this method,
	 * <br/>and a given vector.
	 * @param a vector.
	 * @return the distance between two vectors.
	 */
	public double distance(Vector2I a){
		int dx = a.x - x;
		int dy = a.y - y;
		return Math.sqrt(dx * dx + dy * dy);
	}
	
	/**
	 * Adds vector a to the vector that called this method.
	 * @param a vector.
	 */
	public void add(Vector2I a){
		x += a.x;
		y += a.y;
	}
	
	/**
	 * Subtracts vector a from the vector that called this method.
	 * @param a vector.
	 */
	public void subtract(Vector2I a){
		x -= a.x;
		y -= a.y;
	}
	
	/**
	 * Multiplies the vector that called this method by the given scaler value.
	 * @param scaler value.
	 */
	public void scale(int scaler){
		x *= scaler;
		y *= scaler;
	}
	
	/**
	 * Returns true if the vector that called this method is equal to the vector given.
	 * @param a vector.
	 * @return true if two vectors are equal, or false otherwise.
	 */
	public boolean equals(Vector2I a){
		return (x == a.x && y == a.y);
	}
	
	/**
	 * Returns a copy of the vector that called this method.
	 * @return a copy of a vector.
	 */
	public Vector2I clone(){
		return new Vector2I(x, y);
	}
	
	/**
	 * Returns the length of the vector that called this method.
	 * @return
	 */
	public double length(){
		return Math.sqrt(x * x + y * y);
	}
	
	/**
	 * Returns a string representation of the vector
	 * <br/>in the form '(x coordinate, y coordinate).
	 * @return a string representing the vector
	 */
	public String toString(){
		return "(" + x + ", " + y + ")";
	}
	
}