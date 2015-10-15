package tests;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import jgame.util.Vector2I;

public class Bitmask {

	public int[][] mask;
	
	public Bitmask(int width, int height){
		
	}
	
	public Bitmask(int[][] mask){
		this.mask = mask;
	}
	
	public Bitmask(BufferedImage image){
		mask = createBitmask(image);
	}
	
	private int[][] createBitmask(BufferedImage image){
		int[][] result = new int[image.getWidth()][image.getHeight()];
		
		for(int i = 0; i < result.length; i ++){
			for(int j = 0; j < result[0].length; j ++){
				if(image.getRGB(i, j) == Color.black.getRGB()){
					result[i][j] = 1;
				} else result[i][j] = 0;
			}
		}
		return result;
	}
	
	public static Vector2I firstPointOfContact(Rectangle aR, Rectangle bR, Bitmask bA, Bitmask bB){
		Rectangle intersection = aR.intersection(bR);
		
		int minaX = intersection.x - aR.x;
		int maxaX = minaX + intersection.width;
		
		int minaY = intersection.y - aR.y;
		int maxaY = minaY + intersection.height;
		
		int minbX = intersection.x - bR.x;
		int maxbX = minbX + intersection.width;
		
		int minbY = intersection.y - bR.y;
		int maxbY = minbY + intersection.height;
		
		for(int i = minaY; i < maxaY; i ++){
			for(int j = minaX; j < maxaX; j ++){
				for(int i2 = minbY; i2 < maxbY; i2 ++){
					for(int j2 = minbX; j2 < maxbX; j2 ++){
//						System.out.print((bA.mask[i][j] + bB.mask[i2][j2]) + ", ");
						if(bA.mask[i][j] + bB.mask[i2][j2] == 2){
							//System.out.println("Collision:" + (aR.x + i) + ", " + (aR.y + j));
							return new Vector2I(aR.y + j, aR.x + i);
						}
					}
//					System.out.println();
				}
			}
		}
//		System.out.println();
		return null;
	}
}
