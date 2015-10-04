package jgame.input;

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
 * The InputHandler class.
 * <br/>This class combines the keyboard and the mouse to be a central class to handle input.
 * <br/>
 * <br/>To use, add {@link InputKey}'s for the InputHandler to listen for. @see {@link #addInputKey(InputKey...)}.
 * <br/>This class will respond when a key is pressed/released, when the mouse is pressed/released,
 * <br/>or when the mouse wheel is scrolled.
 * <br/>This class is not meant to instantiated, rather it should be called from {@link jgame.game.JGame#getInputHandler()},
 * <br/>then it can be used.
 * <br/>
 * <br/>Since this class extends KeyAdapter, some methods are visible but should not be used.
 * <br/>Methods that should be used are as follows:
 * <br/>To know if a InputKey is pressed, use {@link InputKey#isPressed()}.
 * <br/>To know where the mouse is on the screen, use {@link #getMousePosition()}.
 * <br/>To know the mouse wheel rotation, use {@link #getMouseWheelRotation()}. 
 * @author Joshua Klassen
 */
public final class InputHandler extends KeyAdapter implements MouseListener, MouseMotionListener, MouseWheelListener {

	//The input keys
	private static ArrayList<InputKey> inputKeys = new ArrayList<InputKey>();
	
	//the mouse position
	private static Vector2I mousePosition = new Vector2I(0, 0);
	
	//the mouse wheel rotation
	private int mouseWheelRotation = 0;
	
	/**
	 * Adds {@link InputKey}'s for the InputHandler to listen for.
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
	public Vector2I getMousePosition() { return mousePosition; }
	
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
	 * Returns a string representation of the InputHandler.
	 * @return A string representing the InputHandler.
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
