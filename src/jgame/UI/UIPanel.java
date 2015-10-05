package jgame.UI;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import jgame.util.Vector2I;

public class UIPanel extends UIComponent{
	
	private ArrayList<UIComponent> components = new ArrayList<UIComponent>();
	
	private Color backgroundColor = new Color(0, 0, 0);
	
	public UIPanel(Vector2I position){
		super(position);
	}
	
	public UIPanel(Vector2I position, UIComponent... uiComponents) {
		super(position);
		add(uiComponents);
	}

	public UIPanel(Vector2I position, int width, int height){
		super(position, width, height);
	}
	
	public UIPanel(Vector2I position, int width, int height, UIComponent... uiComponents){
		super(position, width, height);
		add(uiComponents);
	}
	
	public void add(UIComponent... uiComponents){
		for(UIComponent c : uiComponents) {
			components.add(c); 
			if(c instanceof UIPanel){
				ArrayList<UIComponent> cs = ((UIPanel) c).getComponents();
				for(int i = 0; i < cs.size(); i ++) cs.get(i).position.add(position);
			}
			c.position.add(position);
		}
	}
	
	public ArrayList<UIComponent> getComponents(){
		return components;
	}
	
	public void clear(){
		components.clear();
	}
	
	@Override
	public void update() {
		for(UIComponent c : components) c.update();
	}

	@Override
	public void render(Graphics g) {
		if(show){
			g.setColor(backgroundColor);
			g.fillRect(position.x, position.y, width, height);
			for(UIComponent c : components) c.render(g);
		}
	}

	public void setBackgroundColor(Color color){ backgroundColor = color; }
	
	public Color getBackgroundColor(){ return backgroundColor; }
	
}
