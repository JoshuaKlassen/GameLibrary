package jgame.UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.geom.Rectangle2D;

import jgame.graphics.JGraphics;
import jgame.util.Vector2I;

/**
 * The UILabel class.
 * <br/>
 * <br/>A prebuilt {@link UIComponent}.
 * <br/>Acts like a label to add text to.
 * @author Joshua Klassen
 */
public class UILabel extends UIComponent{

	//the text
	private String text = "";
	
	//the font
	private Font font = new Font("Times New Roman", Font.PLAIN, 12);
	
	//the color
	private Color color = Color.white;
	
	/**
	 * Creates a UILabel with its position
	 * <br/>equal to the {@link Vector2I} given,
	 * <br/>and sets the text to be empty.
	 * @param position
	 */
	public UILabel(Vector2I position){
		super(position);
	}
	
	
	/**
	 * Create a UILabel with its position and text
	 * <br/>equal to the values given.
	 * @param position
	 * @param text
	 */
	public UILabel(Vector2I position, String text){
		super(position);
		this.text = text;
	}
	
	
	/**
	 * Creates a UIlabel with its position, text, and text color
	 * <br/>equal to the values given.
	 * @param position
	 * @param text
	 * @param color
	 */
	public UILabel(Vector2I position, String text, Color color){
		super(position);
		this.text = text;
		this.color = color;
	}
	
	
	/**
	 * Creates a UILabel with its position, text, and text font
	 * <br/>equal to the values given.
	 * @param position
	 * @param text
	 * @param font
	 */
	public UILabel(Vector2I position, String text, Font font){
		super(position);
		this.text = text;
		this.font = font;
	}
	
	
	/**
	 * Creates a UILabel and sets the position, text, text color, and text font
	 * <br/>to the values given.
	 * @param position
	 * @param text
	 * @param color
	 * @param font
	 */
	public UILabel(Vector2I position, String text, Color color, Font font){
		super(position);
		this.text = text;
		this.color = color;
		this.font = font;
	}
	
	/**
	 * The overridden update method from {@link UIComponent}.
	 * <br/>Has no effect on the label as is,
	 * <br/>but allows for customization of labels.
	 */
	@Override
	public void update() {
		
	}
	
	/**
	 * The overridden render method from {@link UIComponent}.
	 * <br/>Needs to be called manually.
	 * <br/>Will have no effect the the label is hidden. See {@link #show()} for more details.
	 */
	@Override
	public void render(JGraphics g) {
		if(show){
			
			//if(width == 0 || height == 0){
				FontMetrics fm = g.getFontMetrics(font);
				Rectangle2D bounds = fm.getStringBounds(text, g.getInstance());
				setWidth((int)bounds.getWidth());
				setHeight(fm.getAscent() - fm.getDescent());
			//}
			
			//FontMetrics fm = ((Graphics2D)(g)).getFontMetrics(font);
			//int textHeight = fm.getAscent();
			g.setColor(color);
			g.setFont(font);
			g.drawString(text, position.x, position.y + height);
			g.drawRect(position.x, position.y, width, height);
		}
	}
	
	public boolean contains(Vector2I point){
		return (point.x >= position.x) && (point.x <= position.x + width)
				&& (point.y >= position.y) && (point.y <= position.y + height);
	}
	
	/**
	 * Sets the text of the label to the {@link String} given.
	 * @param text
	 */
	public void setText(String text){ this.text = text; }
	
	/**
	 * Returns the {@link Font} of the label.
	 * @return the font.
	 */
	public Font getFont(){ return font; }
	
	/**
	 * Sets the {@link Font} for the label.
	 * <br/>By default, the {@link Font} is 12pt Times New Roman.
	 * <br/>
	 * <br/>To use {@link Font}s, say 'Font font = new Font("Name", Style, Size);'.
	 * <br/>example: 'Font myFont = new Font("Aerial", Font.BOLD, 32);'.
	 * <br/>See {@link Font} for more details.
	 * @param font
	 */
	public void setFont(Font font){ this.font = font; }
	
	/**
	 * Returns the {@link Color} of the label.
	 * @return the color.
	 */
	public Color getColor(){ return color; }
	
	/**
	 * Sets the {@link Color} for the label.
	 * <br/>By default, the {@link Color} is white.
	 * <br/>Set {@link Color} for more details.
	 * @param color
	 */
	public void setColor(Color color){ this.color = color; }
	
	/**
	 * Returns a {@link String} representation of the UILabel.
	 * @return a string representation of the object.
	 */
	public String toString(){
		String result = super.toString();
		result = result.substring(0, result.length() - 1);
		result += ", text=\"" + text + "\"]";
		return result;
	}
	
}
