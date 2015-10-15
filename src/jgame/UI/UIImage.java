package jgame.UI;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

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
	public void render(Graphics g) {
		if(show){
			g.drawImage(image, position.x, position.y, width, height, null);
		}
	}
	
	public void setImage(BufferedImage image){ this.image = image; }

	public BufferedImage getImage(){ return image; }
	
}
