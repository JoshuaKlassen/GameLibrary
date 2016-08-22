package jgame.graphics;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import jgame.util.Utility;

public final class GraphicsUtility {
	public static BufferedImage loadImage(String filePath) {
		try {
			return ImageIO.read(Utility.class.getResourceAsStream(filePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	public static int[][] getSameColoredPixels(BufferedImage image, int rgb){
		int[][] result = new int[image.getWidth()][image.getHeight()];
		
		for(int i = 0; i < result.length; i ++){
			for(int j = 0; j < result[0].length; j ++){
				if(image.getRGB(i, j) == rgb){
					result[i][j] = 1;
				} else result[i][j] = 0;
			}
		}
		return result;
	}
	
	public static BufferedImage scaleImage(BufferedImage image, float scaleX, float scaleY){
		BufferedImage after = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		AffineTransform affineTransform = new AffineTransform();
		affineTransform.scale(scaleX, scaleY);
		AffineTransformOp scaleOp = new AffineTransformOp(affineTransform, AffineTransformOp.TYPE_BILINEAR);
		return scaleOp.filter(image, after);
	}
	
	public static BufferedImage flipImage(BufferedImage image){
		AffineTransform affineTransform = AffineTransform.getScaleInstance(-1, 1);
		affineTransform.translate(-image.getWidth(null), 0);
		AffineTransformOp flipOp = new AffineTransformOp(affineTransform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		return flipOp.filter(image, null);
	}
	
	public static BufferedImage[] ripColumnFromImage(BufferedImage image, int numberOfSprites, int x, int y, int width, int height){
		BufferedImage[] result = new BufferedImage[numberOfSprites];
		
		for(int i = 0; i < numberOfSprites; i ++){
			result[i] = image.getSubimage(x, y + (height * i) + i, width, height);
		}
		
		return result;
	}
}