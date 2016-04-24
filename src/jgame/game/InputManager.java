package jgame.game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;

import jgame.util.Vector2I;

/**
 * The InputManager class.
 * <br/>This class combines the keyboard and the mouse to be a central class to handle input.
 * <br/>This class is not meant to be seen by the user, instead it is supposed to 
 * <br/>act behind the scenes. The wrapper class {@link InputHandler} is meant to be used.
 * <br/>
 * <br/>This class will respond when a key is pressed/released, when the mouse is pressed/released,
 * <br/>or when the mouse wheel is scrolled.
 * @author Joshua Klassen
 */
final class InputManager extends KeyAdapter implements MouseListener, MouseMotionListener, MouseWheelListener {
	
	//The input keys
	private ArrayList<InputKey> inputKeys = new ArrayList<InputKey>();
	
	//the mouse position
	private Vector2I mousePosition = new Vector2I(0, 0);
	
	//the mouse wheel rotation
	private int mouseWheelRotation = 0;
	
	private int keyTyped = 0;
	
	/**
	 * Adds {@link InputKey}'s for the InputManager to listen for.
	 * @param inputKey
	 */
	public void addInputKey(InputKey... inputKey){
		for(int i = 0; i < inputKey.length; i ++) {
			InputKey input = inputKey[i];
			if(!inputKeys.contains(input)){
				inputKeys.add(input);
			}
		}
	}
	
	/**
	 * Removes {@link InputKey}s from the InputManager.
	 * @param inputKey
	 */
	public void removeInputKey(InputKey... inputKey){
		for(int i = 0; i < inputKey.length; i ++){
			InputKey input = inputKey[i];
			if(inputKeys.contains(input)){
				inputKeys.remove(input);
			}
		}
	}
	
	/**
	 * Returns an {@link ArrayList} for the {@link InputKey}s
	 * <br/>that the InputManager is listening for.
	 * @return InputKeys.
	 */
	public ArrayList<InputKey> getInputKeys(){
		return inputKeys;
	}
	
	/**
	 * This method is called when a key is pressed,
	 * <br/>and should not be called manually.
	 */
	@Override
	public void keyPressed(KeyEvent e){
		for(int i = 0; i < inputKeys.size(); i ++){
			ArrayList<INPUT_KEY> keys = inputKeys.get(i).getKeys();
			for(int j = 0; j < keys.size(); j ++){
				if(keys.get(j).getID() == e.getKeyCode()){
					keys.get(j).press();
				}
			}
		}
	}
	
	/**
	 * This method is called when a key is released,
	 * <br/>and should not be called manually.
	 */
	@Override
	public void keyReleased(KeyEvent e){
		for(int i = 0; i < inputKeys.size(); i ++){
			ArrayList<INPUT_KEY> temp = inputKeys.get(i).getKeys();
			for(int j = 0; j < temp.size(); j ++){
				if(temp.get(j).getID() == e.getKeyCode()){
					temp.get(j).release();
				}
			}
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e){
		keyTyped = e.getKeyChar();
	}
	
	protected int getKeyTyped(){
		int result = 0;
		if(keyTyped != 0){
			result = keyTyped;
			keyTyped = 0;
		}
		return result;
	}
	
	/**
	 * This method is called when the mouse wheel is scrolled,
	 * <br/>and should not be called manually.
	 */
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		mouseWheelRotation = e.getWheelRotation();
	}
	
	/**
	 * Returns the current rotation of the mouse wheel.
	 * <br/>See: {@link MouseWheelEvent#getWheelRotation()} for more info.
	 * @return The mouse wheel rotation
	 */
	public int getMouseWheelRotation(){ return mouseWheelRotation; }

	/**
	 * Resets the mouse wheel rotation back to 0.
	 * <br/>Should be called after calling {@link #getMouseWheelRotation()}.
	 */
	public void resetMouseWheelRotation(){ mouseWheelRotation = 0; }
	
	/**
	 * This method is called when the mouse is dragged,
	 * <br/>and should not be called manually.
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
		mousePosition.x = e.getX();
		mousePosition.y = e.getY();
	}

	/**
	 * This method is called when the mouse is dragged,
	 * <br/>and should not be called manually.
	 */
	@Override
	public void mouseMoved(MouseEvent e) {
		mousePosition.x = e.getX();
		mousePosition.y = e.getY();
	}

	/**
	 * Returns the mouse position on the JGame screen as a {@link jgame.util.Vector2I}.
	 * @return the mouse position.
	 */
	protected Vector2I getMousePosition() { return mousePosition; }
	
	/**
	 * This method is called when the mouse is clicked,
	 * <br/>and should not be called manually.
	 */
	@Override
	public void mouseClicked(MouseEvent e) {}

	/**
	 * This method is called when the mouse is pressed,
	 * <br/>and should not be called manually.
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		for(int i = 0; i < inputKeys.size(); i ++){
			ArrayList<INPUT_KEY> temp = inputKeys.get(i).getKeys();
			for(int j = 0; j < temp.size(); j ++){
				if(temp.get(j).getID() == e.getButton()){
					temp.get(j).press();
				}
			}
		}
	}

	/**
	 * This method is called when the mouse is released,
	 * <br/>and should not be called manually.
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		for(int i = 0; i < inputKeys.size(); i ++){
			ArrayList<INPUT_KEY> temp = inputKeys.get(i).getKeys();
			for(int j = 0; j < temp.size(); j ++){
				if(temp.get(j).getID() == e.getButton()){
					temp.get(j).release();
				}
			}
		}
	}

	/**
	 * This method is called when the mouse enters a component,
	 * <br/>and should not be called manually.
	 */
	@Override
	public void mouseEntered(MouseEvent e) {}

	/**
	 * This method is called when the mouse exits a component,
	 * <br/>and should not be called manually.
	 */
	@Override
	public void mouseExited(MouseEvent e) {}

	/**
	 * Returns a string representation of the InputManager.
	 * @return A string representing the InputManager.
	 */
	public String toString(){
		String result = "";
		for(int i = 0; i < inputKeys.size(); i ++){
			result += inputKeys.get(i);
			if(i < inputKeys.size() - 1) result += ", ";
		}
		return result;
	}
	
}
