package jgame.UI;

import java.util.ArrayList;

import jgame.game.InputHandler;
import jgame.game.InputKey;
import jgame.graphics.IRenderable;
import jgame.graphics.JGraphics;
import jgame.util.Vector2;

//TODO redesign
//Not to happy with this :/
public class UIButton extends UIComponent{

	private IRenderable normalState;	//could be a text label or an image
	private IRenderable highlightedState;
	private IRenderable pressedState;
	private IRenderable currentState;
	private ArrayList <InputKey> pressKeys;
	
	private ButtonAction action;
	
	private boolean highlighted;
	private boolean isPressed;
	private boolean mouseEnable;
	
	public UIButton(Vector2 position) {
		super(position);
		init();
	}
	
	public UIButton(Vector2 position, int width, int height){
		super(position, width, height);
		init();
	}
	
	private void init(){
		normalState = null;
		currentState = normalState;
		pressKeys = new ArrayList<InputKey>();
	}
	
	public void addPressKeys(InputKey... keys){
		for(InputKey key : keys){
			pressKeys.add(key);
		}
	}
	
	public ArrayList<InputKey> getPressKeys(){
		return pressKeys;
	}
	
	public void setNormalState(IRenderable state){
		normalState = state;
	}
	
	public void setHighlightedState(IRenderable state){
		highlightedState = state;
	}
	
	public void setPressedState(IRenderable state){
		pressedState = state;
	}
	
	public void setAction(ButtonAction action){
		this.action = action;
	}

	@Override
	public void update() {
		boolean keyPressed = false;
		for(int i = 0; !keyPressed && i < pressKeys.size(); i ++){
			if(pressKeys.get(i).isPressed())
				keyPressed = true;
		}
		
		//TODO keyboard enable and mouse enable
		if(mouseEnable && contains(InputHandler.getScaledMousePosition())){
			if(!highlighted){
				highlighted = true;
			}
			if(keyPressed){
				isPressed = true;
			}
		}else if(mouseEnable && !contains(InputHandler.getScaledMousePosition())){
			highlighted = false;
		}
		
		if(mouseEnable && isPressed && !keyPressed){
			release();
		}
		if(highlighted && !isPressed && highlightedState != null){
			currentState = highlightedState;
		}else if(isPressed && pressedState != null){
			currentState = pressedState;
		}else{
			currentState = normalState;
		}
	}

	@Override
	public void render(JGraphics g) {
		if(currentState != null)
			currentState.render(g);
	}
	
	public boolean contains(Vector2 pos){
		return (pos.x >= position.x) && (pos.x <= position.x + width)
				&& (pos.y >= position.y) && (pos.y <= position.y + height);
	}
	
	public void unhighlight(){
		if(highlighted){
			highlighted = false;
		}
	}
	
	public void highlight(){
		if(!highlighted){
			highlighted = true;
		}
	}
	
	public void press(){
		if(highlighted && !isPressed){
			isPressed = true;
		}
	}
	
	public void safeRelease(){
		if(isPressed){
			isPressed = false;
		}
	}
	
	public void release(){
		if(action != null){
			action.doAction();
		}
		safeRelease();
	}
	
	public void trackMouse(boolean track){
		mouseEnable = track;
	}
	
	public boolean isHighlighted(){
		return highlighted;
	}
	
	public boolean isPressed(){
		return isPressed;
	}
	
	public IRenderable getCurrentState(){
		return currentState;
	}
	
}