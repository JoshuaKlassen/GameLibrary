package jgame.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import jgame.util.Utility;

public final class GraphicsUtility {
	public static BufferedImage loadImage(String filePath) {
		try {
			return ImageIO.read(Utility.class
					.getResourceAsStream(filePath));
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
}
