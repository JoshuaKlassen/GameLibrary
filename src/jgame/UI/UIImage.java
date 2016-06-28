package jgame.UI;

import java.awt.image.BufferedImage;

import jgame.graphics.JGraphics;
import jgame.util.Vector2I;

public class UIImage extends UIComponent{

	private BufferedImage image;
	
	public UIImage(Vector2I position, BufferedImage image) {
		super(position, image.getWidth(), image.getHeight());
		this.image = image;
	}

	public UIImage(Vector2I position, int width, int height, BufferedImage image){
		super(position, width, height);
		this.image = image;
	}
	
	@Override
	public void update() {
		
	}

	@Override
	public void render(JGraphics g) {
		if(show){
			g.drawImage(image, position.x, position.y, width, height);
		}
	}
	
	public void setImage(BufferedImage image){ this.image = image; }

	public BufferedImage getImage(){ return image; }
	
}
