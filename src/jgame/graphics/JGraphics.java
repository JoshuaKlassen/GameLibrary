package jgame.graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class JGraphics{

	private Graphics graphics;
		
	public JGraphics(Graphics graphics){
		this.graphics = graphics;
	}
	
	public void setColor(Color c){
		graphics.setColor(c);
	}
	
	/**
	 * Returns a new image thats a rotated version of a given image
	 * @param image
	 * @param degrees
	 * @return
	 */
	public static BufferedImage rotate(BufferedImage image, double degrees){
		AffineTransform at = new AffineTransform();
		at.rotate(Math.toRadians(degrees), image.getWidth() / 2, image.getHeight() / 2);
		AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
		return op.filter(image, null);
	}
	
	/**
	 * Changes every pixel in a given image, of a given color, to a new color
	 */
	public void swapColour(BufferedImage image, int oldRGB, int newRGB){
		for(int x = 0; x < image.getWidth(); x ++){
			for(int y = 0; y < image.getHeight(); y ++){
				if(image.getRGB(x, y) == oldRGB) image.setRGB(x, y, newRGB);
			}
		}
	}
	
	/**
	 * Scales a given image by a given x and y value.
	 * @param image
	 * @param scaleX
	 * @param scaleY
	 * @return
	 */
	public static BufferedImage scaleImage(BufferedImage image, float scaleX, float scaleY){
		int width = image.getWidth();
		int height = image.getHeight();
		BufferedImage after = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		AffineTransform at = new AffineTransform();
		at.scale(scaleX, scaleY);
		AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
		return scaleOp.filter(image, after);
	}

	/**
	 * Returns a sub image at the given position on the given image.
	 * @param image
	 * @param position
	 * @param width
	 * @param height
	 * @return
	 */
//	public static BufferedImage getSubImage(BufferedImage image, Vector2I position, int width, int height){
//		return SpriteSheet.grabSubImage(image, position, width, height);
//	}
	
	/**
	 * Returns a random square sub image from the given image.
	 * @param image
	 * @param size
	 * @return
	 */
//	public static BufferedImage getRandomSubImage(BufferedImage image, int size){
//		return SpriteSheet.grabSubImage(image, new Vector2I(new Random().nextInt(image.getWidth() - size), (new Random().nextInt(image.getHeight() - size))), size, size);
//	}
	
	/**
	 * Compares to given images, and returns true if the are identical, or false if they are not identical.
	 * @param img1
	 * @param img2
	 * @return
	 */
	public static boolean compareImages(BufferedImage img1, BufferedImage img2){
		
		if(img1.getWidth() != img2.getWidth()) return false;
		if(img1.getHeight() != img2.getHeight()) return false;
		
		for(int i = 0; i < img1.getWidth(); i ++){
			for(int j = 0; j < img1.getHeight(); j ++){
				if(img1.getRGB(i, j) != img2.getRGB(i, j)) return false;
			}
		}
		return true;
	}
}
