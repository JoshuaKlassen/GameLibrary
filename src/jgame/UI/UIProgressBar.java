package jgame.UI;

import java.awt.Color;
import java.awt.Graphics;

import org.w3c.dom.ranges.RangeException;

import jgame.util.Vector2I;

/**
 * The UIProgressBar class.
 * <br/>
 * <br/>A prebuilt {@link UIComponent}.
 * <br/>Acts as a progress bar, with progress being in between 0.0 and 1.0.
 * @author Joshua Klassen
 */
public class UIProgressBar extends UIComponent {

	//the progress
	private double progress = 0;
	
	//the colors
	private Color outlineColor = Color.black;
	private Color foregroundColor = Color.green;
	private Color backgroundColor = Color.gray;
	
	/**
	 * Creates a UIProgressBar with its position
	 * <br/>equal to the {@link Vector2I} given.
	 * @param position
	 */
	public UIProgressBar(Vector2I position) {
		super(position);
	}
	
	/**
	 * Creates a UIProgressBar with its position, width, and height
	 * <br/>equal to the values given.
	 * @param position
	 * @param width
	 * @param height
	 */
	public UIProgressBar(Vector2I position, int width, int height){
		super(position, width, height);
	}

	/**
	 * The overridden update method from {@link UIComponent}.
	 * <br/>Has no effect on the prebuilt progressbar, but
	 * <br/>allows for customization.
	 */
	@Override
	public void update() {
		
	}

	/**
	 * The overridden render method from {@link UIComponent}.
	 * <br/>Needs to be called manually.
	 * <br/>Has no effect if the progressbar is hidden. See {@link #show()} for more details.
	 */
	@Override
	public void render(Graphics g) {
		if(show){
			int drawProgress = (int)(width * progress);
			g.setColor(backgroundColor);
			g.fillRect(position.x + drawProgress, position.y, width - drawProgress, height);
			g.setColor(foregroundColor);
			g.fillRect(position.x, position.y, drawProgress, height);
			g.setColor(outlineColor);
			g.drawRect(position.x, position.y, width, height);
		}
	}

	/**
	 * Returns the progress of the progressbar.
	 *<br/>The progress value will always be within 0 and 1.
	 *<br/>(0.0 <= progress <= 1.0).
	 * @return the progress of the progressbar.
	 */
	public double getProgress(){ return progress; }
	
	/**
	 * Sets the progress value of the progressbar to
	 * <br/>the given value.
	 * @param progress
	 */
	public void setProgress(double progress){ 
		if(progress < 0 || progress > 1){
			throw new RangeException(RangeException.BAD_BOUNDARYPOINTS_ERR, 
					"Progress cannout be less than 0 or greater than 1 (progress=" + progress + ")");
		}else{
			this.progress = progress;
		}
	}
	
	/**
	 * Returns the outline {@link Color}.
	 * @return outline color.
	 */
	public Color getOutlineColor(){ return outlineColor; }
	
	/**
	 * Sets the outline {@link Color} to the given {@link Color}.
	 * <br/>The outline is the border for the progress bar.
	 * <br/>By default the outline {@link Color} is black.
	 * @param outline color
	 */
	public void setOutlineColor(Color outline){ outlineColor = outline; }
	
	/**
	 * Returns the foreground {@link Color}.
	 * @return foreground color.
	 */
	public Color getforegroundColor(){ return foregroundColor; }
	
	/**
	 * Sets the foreground {@link Color} to the given {@link Color}.
	 * <br/>The foreground {@link Color} is the progress {@link Color}.
	 * <br/>By default the foreground {@link Color} is green.
	 * @param foreground color
	 */
	public void setforegroundColor(Color foreground){ foregroundColor = foreground; }
	
	/**
	 * Returns the background {@link Color}.
	 * @return background color.
	 */
	public Color getBackgroundColor(){ return backgroundColor; }
	
	/**
	 * Sets the background {@link Color} to the {@link Color} given.
	 * <br/>The background is what's seen behind the progress bar,
	 * <br/>i.e. when the progress bar is depleted.
	 * <br/>By default the background {@link Color} is gray.
	 * @param background color
	 */
	public void setBackgroundColor(Color background){ backgroundColor = background; }
	
	/**
	 * Returns a {@link String} representation of the UIProgressBar.
	 * @return a string representation of the object.
	 */
	public String toString(){
		String result = super.toString();
		result = result.substring(0, result.length() - 1);
		result += ", progress=" + progress + "]";
		return result;
	}
	
}
