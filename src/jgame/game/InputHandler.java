package jgame.game;

import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;

import jgame.util.Vector2;

/*
 * This class is a wrapper for the InputManager class.
 * Since the InputManager class contains methods that the user should not see,
 * this class was made to contain the InputManager, and only allow access to it for the JGame
 * so it can be added to the JFrame, while keeping the important functionality like
 * adding input keys, removing input keys, getting mouse position, etc.
 */

/**
 * The InputHandler class.
 * <br/>This class combines the keyboard and the mouse to be a central class to handle input.
 * <br/>
 * <br/>To use, add {@link InputKey}'s for the InputHandler to listen for. See {@link #add(InputKey...)}.
 * <br/>This class will respond when a key is pressed/released, when the mouse is pressed/released,
 * <br/>or when the mouse wheel is scrolled.
 * <br/>
 * <br/>To know if a InputKey is pressed, use {@link InputKey#isPressed()}.
 * <br/>To know where the mouse is on the screen, use {@link #getMousePosition()}.
 * <br/>To know the mouse wheel rotation, use {@link #getMouseWheelRotation()}. 
 * @author Joshua Klassen
 */
public final class InputHandler{

	private static InputManager inputManager = new InputManager();
	
	private static JGame game;
	
	/**
	 * Adds {@link InputKey}s for the InputHandler to listen for.
	 * @param keys
	 */
	public static void add(InputKey... keys){
		inputManager.addInputKey(keys);
	}
	
	/**
	 * Removes {@link InputKey}s from the InputHandler
	 * <br/>so the InputHandler stops listening for the given {@link InputKey}s.
	 * @param keys
	 */
	public static void remove(InputKey... keys){
		inputManager.removeInputKey(keys);
	}
	
	public static void removeAll(){
		inputManager.getInputKeys().clear();
	}
	
	public static int getKeyTyped(){
		return inputManager.getKeyTyped();
	}
	
	/**
	 * Returns an {@link ArrayList} of the {@link InputKey}s
	 * <br/>the InputHandler is listening for.
	 * @return the InputKeys.
	 */
	public static ArrayList<InputKey> getKeys(){
		return inputManager.getInputKeys();
	}
	
	public static boolean hasKey(INPUT_KEY key){
		return inputManager.getInputKeys().contains(key);
	}
	
	/**
	 * Returns the current rotation of the mouse wheel.
	 * <br/>See: {@link MouseWheelEvent#getWheelRotation()} for more info.
	 * @return The mouse wheel rotation
	 */
	public static int getMouseWheelRotation(){
		return inputManager.getMouseWheelRotation();
	}
	
	/**
	 * Resets the mouse wheel rotation back to 0.
	 * <br/>Should be called after calling {@link #getMouseWheelRotation()}.
	 */
	public static void resetMouseWheelRotation(){
		inputManager.resetMouseWheelRotation();
	}
	
	/**
	 * Returns the mouse position on the JGame screen as a {@link jgame.util.Vector2I}.
	 * @return the mouse position.
	 */
	public static Vector2 getMousePosition(){
		return inputManager.getMousePosition();
	}
	
	public static Vector2 getScaledMousePosition(){
		Vector2 result = null;
		if(game != null){
			result = game.getScaledMousePosition();
		}
		return result;
	}
	
	protected static void init(JGame game){
		InputHandler.game = game;
		
	}
	
	/*
	 * The getInstance method.
	 * It should only be seen within this package
	 * so the main JGame object can see it, but users cannot,
	 * since users are not supposed to see the InputManager.
	 */
	protected static InputManager getInstance(){
		return inputManager;
	}
}