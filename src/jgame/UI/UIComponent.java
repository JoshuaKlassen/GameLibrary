package jgame.UI;

import java.awt.Graphics;

import jgame.util.Vector2I;

public abstract class UIComponent {

	protected Vector2I position;
	
	protected int width = 0;
	protected int height = 0;
	
	protected boolean show = true;
	
	public UIComponent(Vector2I position){
		this.position = position;
	}
	
	public UIComponent(Vector2I position, int width, int height){
		this.position = position;
		this.width = width;
		this.height = height;
	}
	
	public abstract void update();
	
	public abstract void render(Graphics g);
	
	public Vector2I getPosition(){ return position; }
	
	public int getWidth(){ return width; }
	
	public void setWidth(int width){ this.width = width; }
	
	public int getHeight(){ return height; }
	
	public void setHeight(int height){ this.height = height; }
	
	public void show(){ show = true; }
	
	public void hide(){ show = false; }
	
	public void toggleVisibilty(){
		if(show)hide();
		else show();
	}
	
}
