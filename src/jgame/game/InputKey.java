package jgame.game;

import java.util.ArrayList;

/**
 * The InputKey class.
 * <br/>Not to be confused with the {@link INPUT_KEY} enum.
 * <br/>InputKeys are used with {@link INPUT_KEY}s and a {@link InputHandler} to handle input.
 * <br/>
 * <br/>To use, map {@link INPUT_KEY}s to a InputKey object, then add the InputKey to a {@link InputHander}.
 * <br/>An InputKey can have just one {@link INPUT_KEY} to emulate one button, or it can have multiple
 * <br/>{@link INPUT_KEY}s to emulate a combination of buttons that only fires once all buttons are pressed.
 * <br/>
 * <br/>When pressed, the {@link InputHandler} containing the InputKey will be fired depending on the fire mode.
 * <br/>Use {@link #isPressed} to check if a InputKey is currently pressed.
 * <br/>See {@link #InputKey(boolean, INPUT_KEY...)} for more details on the fire mode.
 * @author Joshua Klassen
 */
public class InputKey {

	//The array list of INPUT_KEYs
	private ArrayList<INPUT_KEY> inputKeys;
	
	//if the input key is a once-fire key
	private boolean onceFire = false;
	
	//if the key is currently pressed
	private boolean isPressed = false;
	
	//how many times the key has been pressed
	private int timesFired = 0;
	
	/**
	 * Creates an InputKey from given {@link INPUT_KEY}s.
	 * <br/>The onceFire property is false by default,
	 * <br/>and can be changed later with {@link #setOnceFire(boolean)}.
	 * @param inputKeys
	 */
	public InputKey(INPUT_KEY... inputKeys){
		this.inputKeys = new ArrayList<INPUT_KEY>();
		for(int i = 0; i < inputKeys.length; i ++) this.inputKeys.add(inputKeys[i]);
	}
	
	/**
	 * Creates an InputKey from given {@link INPUT_KEY}s,
	 * <br/>also sets the onceFire property to the first parameter.
	 * <br/>
	 * <br/>If onceFire is true, the key will only fire once upon press,
	 * <br/>and can only be fired again till all {@link INPUT_KEY}s are released.
	 * <br/>If onceFire is false, it will fire upon and during press.
	 * @param onceFire
	 * @param inputKeys
	 */
	public InputKey(boolean onceFire, INPUT_KEY... inputKeys){
		this.onceFire = onceFire;
		this.inputKeys = new ArrayList<INPUT_KEY>();
		for(int i = 0; i < inputKeys.length; i ++) this.inputKeys.add(inputKeys[i]);
	}
	
	/**
	 * Returns true if every {@link INPUT_KEY} is pressed,
	 * <br/>depending on the fire mode. See {@link #setOnceFire(boolean)} for more details on the fire mode.
	 * @return is pressed.
	 */
	public boolean isPressed(){
		boolean result = true;
		for(INPUT_KEY key: inputKeys){
			if(!key.isPressed()){
				isPressed = false;
				result = false;
			}
		}
		if(result){
			if(isPressed && onceFire) result = false;
			else{
				result = true;
				isPressed = true;
				timesFired ++;
			}
		}
		return result;
	}
	
	/**
	 * Clears all {@link INPUT_KEY}s in an {@link InputKey},
	 * <br/>and adds the given {@link INPUT_KEY}s.
	 * @param inputKeys
	 */
	public void map(INPUT_KEY... inputKeys){
		this.inputKeys.clear();
		for(INPUT_KEY key: inputKeys) this.inputKeys.add(key);
	}
	
	public void release(){
		for(INPUT_KEY key: inputKeys){
			key.release();
		}
	}
	
	/**
	 * Returns an {@link ArrayList} of the {@link INPUT_KEY}s
	 * <br/>in an {@link InputKey}.
	 * @return An <Type>ArrayList of <Type>INPUT_KEYs.
	 */
	public ArrayList<INPUT_KEY> getKeys(){ return inputKeys; }
	
	/**
	 * Returns the number of times an {@link InputKey} has fired.
	 * @return times fired.
	 */
	public int timesFired() { return timesFired; }
	
	/**
	 * Reset the times fired value to 0.
	 */
	public void resetTimesFired() { timesFired = 0; }
	
	/**
	 * Sets the onceFire property of an {@link InputKey}.
	 * <br/>@see {@link #InputKey(boolean, INPUT_KEY...)} for more details
	 * <br/>about this property.
	 * @param onceFire
	 */
	public void setOnceFire(boolean onceFire) { this.onceFire = onceFire; }
	
	public boolean equals(Object o){
		boolean result = false;
		
		if(o instanceof InputKey){
			InputKey ok = (InputKey)o;
			result = ((InputKey) o).getKeys().equals(getKeys());
		}
		
		return result;
	}
	
	/**
	 * Returns a string representation of an {@link InputKey},
	 * <br/>in the form '{@link INPUT_KEY} name ({@link INPUT_KEY} isPressed), for all
	 * <br/>{@link INPUT_KEY}s in an {@link InputKey}.
	 * @return a string representation.
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
