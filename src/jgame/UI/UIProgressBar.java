package jgame.UI;

import java.awt.Color;
import java.awt.Graphics;

import jgame.util.Vector2I;

import org.w3c.dom.ranges.RangeException;

public class UIProgressBar extends UIComponent {

	private double progress = 0;
	
	private Color outlineColor = Color.black;
	private Color progressColor = Color.green;
	private Color backgroundColor = Color.gray;
	
	public UIProgressBar(Vector2I position) {
		super(position);
	}
	
	public UIProgressBar(Vector2I position, int width, int height){
		super(position, width, height);
	}

	@Override
	public void update() {
		
	}

	@Override
	public void render(Graphics g) {
		if(show){
			int drawProgress = (int)(width * progress);
			g.setColor(backgroundColor);
			g.fillRect(position.x + drawProgress, position.y, width - drawProgress, height);
			g.setColor(progressColor);
			g.fillRect(position.x, position.y, drawProgress, height);
			g.setColor(outlineColor);
			g.drawRect(position.x, position.y, width, height);
		}
	}

	public double getProgress(){ return progress; }
	
	public void setProgress(double progress){ 
		if(progress < 0 && progress > 100){
			throw new RangeException(RangeException.BAD_BOUNDARYPOINTS_ERR, 
					"Progress cannout be less than 0 or greater than 1 (progress=" + progress + ")");
		}else this.progress = progress; 
	}
	
	public Color getOutlineColor(){ return outlineColor; }
	
	public void setOutlineColor(Color outline){ outlineColor = outline; }
	
	public Color getProgressColor(){ return progressColor; }
	
	public void setProgressColor(Color progress){ progressColor = progress; }
	
	public Color getBackgroundColor(){ return backgroundColor; }
	
	public void setBackgroundColor(Color background){ backgroundColor = background; }
	
	
}
