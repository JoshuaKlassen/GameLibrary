package jgame.UI;

import java.awt.Color;

import jgame.game.InputHandler;
import jgame.graphics.JGraphics;
import jgame.util.Vector2I;

import com.sun.glass.events.KeyEvent;

public class UITextField extends UIComponent{

	private UILabel userTextLabel;
	
	private String text;
	
	private Color userTextColor = Color.red; //TODO temp
	
	private boolean hasFocus;
	
	public UITextField(Vector2I position, String defaultText) {
		super(position);
		text = defaultText;
		
		init();
	}
	
	public UITextField(Vector2I position, int width, int height, String defaultText) {
		super(position, width, height);
		text = defaultText;
		
		init();
	}

	private void init(){
		userTextLabel = new UILabel(position, text);
		userTextLabel.setColor(userTextColor);
		userTextLabel.show();
	}
	
	@Override
	public void update() {
		if(hasFocus){
			int key = InputHandler.getKeyTyped();
			if(key > 0){
				if(key != KeyEvent.VK_BACKSPACE){
					text += (char)key;
					
				}else{
					if(!text.isEmpty()){
						text = text.substring(0, text.length()-1);
					}
				}
				userTextLabel.setText(text);
			}
		}
	}
	
	public boolean contains(Vector2I point){
		return userTextLabel.contains(point);
	}
	
	public void setFocus(boolean hasFocus){
		this.hasFocus = hasFocus;
	}

	public boolean hasFocus(){
		return hasFocus;
	}
	
	@Override
	public void render(JGraphics g) {
		userTextLabel.render(g);
		
	}
	
	public String getText(){
		return text;
	}

}
