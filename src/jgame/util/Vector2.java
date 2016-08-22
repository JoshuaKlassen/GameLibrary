package jgame.util;

import java.awt.Point;
import java.io.Serializable;
import java.util.Random;

/**
 * The Vector2F class.
 * <br/>Represents a vector with the coordinates as floats.
 * <br/>
 * @author Joshua Klassen
 */
public class Vector2 implements Serializable{
	//auto generated
	private static final long serialVersionUID = 2940611008973133602L;
	
	//the coordinates
	public float x;
	public float y;
	
	/**
	 * Initializes the vector with the coordinates (0, 0).
	 */
	public Vector2(){
		x = 0;
		y = 0;
	}
	
	/**
	 * Initializes the vector with the coordinates that are passed.
	 * <br/>in as parameters, i.e. (x, y).
	 * @param x coordinate.
	 * @param y coordinate.
	 */
	public Vector2(float x, float y){
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Initializes the vector with the coordinates of the point.
	 * <br/>that is passed in as a parameter.
	 * @param point
	 */
	public Vector2(Point point){
		x = point.x;
		y = point.y;
	}
	
	/**
	 * Returns the distance between two vectors a and b.
	 * @param a vector.
	 * @param b vector.
	 * @return the distance between a and b.
	 */
	public static double distance(Vector2 a, Vector2 b){
		float dx = b.x - a.x;
		float dy = b.y - a.y;
		return Math.sqrt(dx * dx + dy * dy);
	}
	
	/**
	 * Returns the resulting vector of adding two vectors a and b.
	 * @param a vector.
	 * @param b vector.
	 * @return the addition of vectors a and b.
	 */
	public static Vector2 add(Vector2 a, Vector2 b){
		return new Vector2(a.x + b.x, a.y + b.y);
	}
	
	/**
	 * Returns the resulting vector of subtracting vectors b from a.
	 * @param a vector.
	 * @param b vector.
	 * @return the subtraction of vectors b from a.
	 */
	public static Vector2 subtract(Vector2 a, Vector2 b){
		return new Vector2(a.x - b.x, a.y - b.y);
	}
	
	/**
	 * Returns the resulting vector of multiplying vector a by a given scaler value.
	 * @param a vector.
	 * @param scaler value.
	 * @return vector a multiplied by a scaler value.
	 */
	public static Vector2 scale(Vector2 a, float scaler){
		return new Vector2(a.x * scaler, a.y * scaler);
	}
	
	/**
	 * Returns true if vectors a and b are equal, returns false otherwise.
	 * @param a vector.
	 * @param b vector.
	 * @return true if a and b are equal, or false otherwise.
	 */
	public static boolean equals(Vector2 a, Vector2 b){
		return (a.x == b.x && a.y == b.y);
	}
	
	/**
	 * Returns the length of the given vector.
	 * @param a vector.
	 * @return the length of a vector.
	 */
	public static double length(Vector2 a){
		return Math.sqrt(a.x * a.x + a.y * a.y);
	}
	
	/**
	 * Returns a randomly generated vector with coordinates in the given range.
	 * @param minX (minimum x coordinate).
	 * @param minY (minimum y coordinate).
	 * @param maxX (maximum x coordinate).
	 * @param maxY (maximum y coordinate).
	 * @return A vector within the given range.
	 */
	public static Vector2 generateVector(float minX, float minY, float maxX, float maxY){
		Random random = new Random();
		float x = random.nextFloat() * (maxX - minX) + minX;
		float y = random.nextFloat() * (maxY - minY) + minY;
		return new Vector2(x, y);
	}
	
	/**
	 * Normalizes the vector
	 * <br/>i.e. transforms the vector to a vector with a length of 0.
	 */
	public Vector2 normalize(){
		Vector2 result = new Vector2(x, y);
		if(length() != 0){
			float s = 1.0f / (float) length();
			result.x *= s;
			result.y *= s;
		}
		return result;
	}
	
	/**
	 * Returns the distance between the vector that called this method,
	 * <br/>and a given vector.
	 * @param a vector.
	 * @return the distance between two vectors.
	 */
	public double distance(Vector2 a){
		float dx = a.x - x;
		float dy = a.y - y;
		return Math.sqrt(dx * dx + dy * dy);
	}
	
	/**
	 * Adds vector a to the vector that called this method.
	 * @param a vector.
	 */
	public void add(Vector2 a){
		x += a.x;
		y += a.y;
	}
	
	/**
	 * Subtracts vector a from the vector that called this method.
	 * @param a vector.
	 */
	public void subtract(Vector2 a){
		x -= a.x;
		y -= a.y;
	}
	
	/**
	 * Multiplies the vector that called this method by the given scaler value.
	 * @param scaler value.
	 */
	public void scale(float scaler){
		x *= scaler;
		y *= scaler;
	}
	
	/**
	 * Returns true if the vector that called this method is equal to the vector given.
	 * @param a vector.
	 * @return true if two vectors are equal, or false otherwise.
	 */
	public boolean equals(Vector2 a){
		return (x == a.x && y == a.y);
	}
	
	/**
	 * Returns a copy of the vector that called this method.
	 * @return a copy of a vector.
	 */
	public Vector2 clone(){
		return new Vector2(x, y);
	}
	
	/**
	 * Returns the length of the vector that called this method.
	 * @return
	 */
	public double length() { 
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