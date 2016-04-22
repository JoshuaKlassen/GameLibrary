package jgame.UI;

import java.awt.Graphics;

import jgame.game.INPUT_KEY;
import jgame.game.InputHandler;
import jgame.game.InputKey;
import jgame.util.Vector2I;

//TODO redesign
//Not to happy with this :/
public class UIButton extends UIComponent{

	private UIComponent normalState;	//could be a text label or an image
	private UIComponent highlightedState;
	private UIComponent pressedState;
	private UIComponent currentState;
	private InputKey leftClick;
	
	private ButtonAction action;
	
	private boolean highlighted;
	private boolean isPressed;
	
	public UIButton(Vector2I position) {
		super(position);
		normalState = new UILabel(position, "Button");
		currentState = normalState;
	}
	
	public UIButton(Vector2I position, int width, int height){
		super(position, width, height);
		normalState = new UILabel(position, "Button");
		currentState = normalState;
		
		leftClick = new InputKey(INPUT_KEY.LEFT_CLICK);
		if(!InputHandler.getKeys().contains(leftClick)){
			InputHandler.add(leftClick);
		}
	}
	
	public void setNormalState(UIComponent state){
		normalState = state;
	}
	
	public void setHighlightedState(UIComponent state){
		highlightedState = state;
	}
	
	public void setPressedState(UIComponent state){
		pressedState = state;
	}
	
	public void setAction(ButtonAction action){
		this.action = action;
	}

	@Override
	public void update() {
		if(contains(InputHandler.getScaledMousePosition())){
			if(!highlighted){
				highlighted = true;
			}
			if(leftClick.isPressed()){
				isPressed = true;
			}
		}else{
			highlighted = false;
		}
		
		if(isPressed && !leftClick.isPressed()){
			if(action != null){
				action.doAction();
			}
			isPressed = false;
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
	public void render(Graphics g) {
		currentState.render(g);
	}
	
	public boolean contains(Vector2I pos){
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
	
	public void release(){
		if(isPressed){
			isPressed = false;
		}
	}
	
}
