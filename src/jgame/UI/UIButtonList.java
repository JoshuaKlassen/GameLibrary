package jgame.UI;

import java.awt.Graphics;
import java.util.ArrayList;

import jgame.util.Vector2I;

//TODO keyboard highlights vs mouse highlights
public class UIButtonList extends UIComponent{

	private ArrayList<UIButton> buttonList = new ArrayList<UIButton>();
	
	public UIButtonList(Vector2I position) {
		super(position);
		// TODO Auto-generated constructor stub
	}
	
	public UIButtonList(Vector2I position, int width, int height) {
		super(position, width, height);
		// TODO Auto-generated constructor stub
	}

	public void addButton(UIButton button){
		buttonList.add(button);
	}
	
	public ArrayList<UIButton> getButtons(){
		return buttonList;
	}
	
	@Override
	public void update() {
		for(UIButton button : buttonList){
			button.update();
			if(button.isPressed() || button.isHighlighted()){
				for(UIButton button2 : buttonList){
					if(!button2.equals(button)){
						button2.safeRelease();
						button2.unhighlight();
					}
				}
			}
		}
	}

	@Override
	public void render(Graphics g) {
		for(UIButton button : buttonList){
			button.render(g);
		}
	}

}
