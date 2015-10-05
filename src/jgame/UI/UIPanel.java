package jgame.UI;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import jgame.util.Vector2I;

/**
 * The UIPanel class.
 * <br/>
 * <br/>A prebuilt {@link UIComponent}.
 * <br/>Acts like a panel to add multiple {@link UIComponent}s to.
 * @author Joshua Klassen
 */
public class UIPanel extends UIComponent{
	
	//the components
	private ArrayList<UIComponent> components = new ArrayList<UIComponent>();
	
	//the background color
	private Color backgroundColor = Color.white;
	
	/**
	 * Creates a UIPanel with its position
	 * <br/>equal to the {@link Vector2I} given.
	 * @param position
	 */
	public UIPanel(Vector2I position){
		super(position);
	}
	
	/**
	 * Creates a UIPanel with its position
	 * <br/>equal to the {@link Vector2I} is given,
	 * <br/>and adds the given {@link UIComponent}s to the UIPanel.
	 * @param position
	 * @param uiComponents
	 */
	public UIPanel(Vector2I position, UIComponent... uiComponents) {
		super(position);
		add(uiComponents);
	}

	/**
	 * Creates a UIPanel with its position, width, and height
	 * <br/>to the values given.
	 * @param position
	 * @param width
	 * @param height
	 */
	public UIPanel(Vector2I position, int width, int height){
		super(position, width, height);
	}
	
	/**
	 * Creates a UIPanel with its position, width, and height
	 * <br/>to the values given, and adds the given {@link UIComponent}s to the panel.
	 * @param position
	 * @param width
	 * @param height
	 * @param uiComponents
	 */
	public UIPanel(Vector2I position, int width, int height, UIComponent... uiComponents){
		super(position, width, height);
		add(uiComponents);
	}
	
	/**
	 * Adds the given {@link UIComponent}s to the panel.
	 * @param uiComponents
	 */
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
	
	/**
	 * Returns an {@link ArrayList} of the {@link UIComponent}s currently
	 * <br/>on the panel.
	 * @return ArrayList of UIComponents
	 */
	public ArrayList<UIComponent> getComponents(){
		return components;
	}
	
	/**
	 * Removes all {@link UIComponent}s from the panel.
	 */
	public void clear(){
		components.clear();
	}
	
	/**
	 * The overridden update method from {@link UIComponent}.
	 * <br/>Updates all {@link UIComponent}s on the panel.
	 * <br/>Needs to be called manually.
	 */
	@Override
	public void update() {
		for(UIComponent c : components) c.update();
	}

	/**
	 * The overridden render method from {@link UIComponent}.
	 * <br/>Needs to be called manually.
	 * <br/>Will have no effect if the panel is hidden. See {@link #show()} for more details.
	 */
	@Override
	public void render(Graphics g) {
		if(show){
			g.setColor(backgroundColor);
			g.fillRect(position.x, position.y, width, height);
			for(UIComponent c : components) c.render(g);
		}
	}

	/**
	 * Sets the background color of the panel.
	 * <br/>By default the color is white.
	 * @param color
	 */
	public void setBackgroundColor(Color color){ backgroundColor = color; }
	
	/**
	 * Returns the background color of the panel.
	 * @return background color.
	 */
	public Color getBackgroundColor(){ return backgroundColor; }
	
}
