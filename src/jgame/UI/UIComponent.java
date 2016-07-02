package jgame.UI;

import jgame.entities.IUpdatable;
import jgame.graphics.IRenderable;
import jgame.graphics.JGraphics;
import jgame.util.Vector2;

/**
 * The UIComponent abstract class.
 * <br/>
 * <br/>The parent class to the prebuilt {@link UIPanel}, {@link UILabel}, and {@link UIProgressBar} classes.
 * <br/>
 * <br/>Is meant to replace Java's UI.
 * <br/>Allows for the creation of custom UIComponents for the game to display.
 * @author Joshua Klassen
 */
public abstract class UIComponent implements IRenderable, IUpdatable{

	//the position
	protected Vector2 position;
	
	//the dimensions
	protected int width = 0;
	protected int height = 0;
	
	//if its currently visible
	protected boolean show = true;
	
	/**
	 * Creates a UIComponent with it's position being
	 * <br/>equal to the {@link Vector2I} given.
	 * @param position
	 */
	public UIComponent(Vector2 position){
		this.position = position;
	}
	
	/**
	 * Creates a UIComponent with its position, width, and height
	 * <br/>equal to the given values. 
	 * @param position vector
	 * @param width
	 * @param height
	 */
	public UIComponent(Vector2 position, int width, int height){
		this.position = position;
		this.width = width;
		this.height = height;
	}
	
	/**
	 * The update method.
	 * <br/>Must be overridden by children UIComponents,
	 * <br/>and called manually.
	 * <br/>Gives users more customization in creating UIComponents.
	 */
	public abstract void update();
	
	/**
	 * The render method.
	 * <br/>Must be overridden by children UIComponents,
	 * <br/>and called manually.
	 * <br/>Renders the UIComponent to the screen.
	 * @param g graphics
	 */
	public abstract void render(JGraphics g);
	
	/**
	 * Returns a {@link Vector2I} representing the position
	 * <br/> of the UIComponent that called this method.
	 * @return position.
	 */
	public Vector2 getPosition(){ return position; }
	
	public void setPosition(Vector2 pos){ this.position = pos; }
	
	/**
	 * Returns the width of the UIComponent that 
	 * <br/>called this method.
	 * @return width.
	 */
	public int getWidth(){ return width; }
	
	/**
	 * Sets the width of the UIComponent that
	 * <br/>called this method.
	 * @param width
	 */
	public void setWidth(int width){ this.width = width; }
	
	/**
	 * Returns the height of the UIComponent that
	 * <br/>called this method.
	 * @return height.
	 */
	public int getHeight(){ return height; }
	
	/**
	 * Sets the height of the UIComponent that
	 * <br/>called this method.
	 * @param height
	 */
	public void setHeight(int height){ this.height = height; }
	
	/**
	 * Shows the UIComponent.
	 * <br/>By default every UIComponent is shown.
	 * <br/>To hide the UIComponent see {@link #hide()}.
	 */
	public void show(){ show = true; }
	
	/**
	 * Hides the UIComponent.
	 * <br/>By default every UIComponent is shown.
	 * <br/>To show a hidden UIComponent see {@link #show()}.
	 */
	public void hide(){ show = false; }
	
	/**
	 * Toggles the visibility of a UIComponent.
	 * <br/>If a UIComponent is hidden, calling this method
	 * <br/>will show it, and vice versa.
	 */
	public void toggleVisibilty(){
		if(show)hide();
		else show();
	}
	
	public boolean contains(Vector2 point){
		return (point.x >= position.x) && (point.x <= position.x + width)
				&& (point.y >= position.y) && (point.y <= position.y + height);
	}
	
	/**
	 * Returns a {@link String} representation of the UIComponent.
	 * @return a string representation of the object.
	 */
	public String toString(){
		return this.getClass().getSimpleName() + " [position=" + position + ", width=" + 
				width + ", height=" + height + ", show=" + show + "]";
	}
	
}