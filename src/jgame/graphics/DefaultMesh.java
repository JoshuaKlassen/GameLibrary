package jgame.graphics;

import java.awt.image.BufferedImage;

import jgame.environment.Tile;
import jgame.util.Vector2;

public class DefaultMesh implements IMesh{

	private static BufferedImage image = GraphicsUtility.loadImage("/DefaultMesh.png");
	
	@Override
	public void render(JGraphics g, Vector2 position) {
		g.drawImage(image, position, Tile.TILE_SIZE, Tile.TILE_SIZE);
	}
}