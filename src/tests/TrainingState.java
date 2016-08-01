package tests;

import java.awt.Color;
import java.awt.image.BufferedImage;

import jgame.entities.Mob;
import jgame.environment.Tile;
import jgame.game.INPUT_KEY;
import jgame.game.InputHandler;
import jgame.game.InputKey;
import jgame.game.JGame;
import jgame.game.State;
import jgame.graphics.Animation;
import jgame.graphics.Camera;
import jgame.graphics.GraphicsUtility;
import jgame.graphics.IMesh;
import jgame.graphics.IRenderable;
import jgame.graphics.JGraphics;
import jgame.graphics.Sprite;
import jgame.util.Vector2;

public class TrainingState extends State {

	private InputKey up = new InputKey(INPUT_KEY.UP);
	private InputKey down = new InputKey(INPUT_KEY.DOWN);
	private InputKey left = new InputKey(INPUT_KEY.LEFT);
	private InputKey right = new InputKey(INPUT_KEY.RIGHT);

	private MyMob myMob;

	private Camera camera;

	private IRenderable temp;

	private Tile[][] tileMap;

	Sprite s;
	
	private Animation animation;
	
	public TrainingState(JGame game) {
		super(game);
		InputHandler.add(up, down, left, right);
		myMob = new MyMob(new Vector2(10, 10));

		camera = new Camera(game);
		camera.follow(myMob);

		tileMap = new Tile[100][100];

		BufferedImage tileImage = GraphicsUtility.loadImage("/GrassTile.png");
		s = new Sprite(tileImage);
		
		IMesh mesh = new IMesh(){
			public void render(JGraphics g, Vector2 position){
				g.setColor(Color.CYAN);
				g.fillRect(position, Tile.TILE_SIZE, Tile.TILE_SIZE);
			}
		};
		
		animation = new Animation(new IMesh[]{new Sprite(tileImage), mesh}, 100, true);
		
		for (int i = 0; i < tileMap.length; i++) {
			for (int j = 0; j < tileMap[0].length; j++) {
				Vector2 position = new Vector2(i * Tile.TILE_SIZE, j * Tile.TILE_SIZE);
				tileMap[i][j] = new Tile(1, position, animation);
			}
		}

		animation.start();
		
		temp = new IRenderable() {
			public void render(JGraphics g) {
				g.setColor(Color.YELLOW);
				g.fillRect(100, 100, 10, 10);
			}
		};
	}

	@Override
	public void render(JGraphics g) {
		g.setColor(Color.white);
		g.drawString(animation.getIndex() + "-" + animation.isRunning(), 10, 30);

		camera.startCapture(g);

		for (int i = 0; i < tileMap.length; i++) {
			for (int j = 0; j < tileMap[0].length; j++) {
				tileMap[i][j].render(g);
			}
		}
		
		myMob.render(g);

		temp.render(g);

		camera.endCapture(g);

	}

	@Override
	public void update() {
		camera.update();

		animation.update();
		
		for (int i = 0; i < tileMap.length; i++) {
			for (int j = 0; j < tileMap[0].length; j++) {
				tileMap[i][j].update();
			}
		}
		
		if (up.isPressed()) {
			myMob.velocity().y = -1;
		} else if (down.isPressed()) {
			myMob.velocity().y = 1;
		} else {
			myMob.velocity().y = 0;
		}

		if (left.isPressed()) {
			myMob.velocity().x = -1;
		} else if (right.isPressed()) {
			myMob.velocity().x = 1;
		} else {
			myMob.velocity().x = 0;
		}

		myMob.update();
	}

}

class MyMob extends Mob {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5656241623369186092L;

	public MyMob(Vector2 position) {
		this.position = position;
		setMaxHealth(50);
	}

	@Override
	public void render(JGraphics g) {
		g.setColor(Color.BLUE);
		g.fillRect(position, 10, 10);
	}

	public void render(JGraphics g, Vector2 position){
		
	}
	
	@Override
	public void update() {
		position = Vector2.add(position, velocity);
	}

	@Override
	protected void init() {

	}
}