package tests;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import jgame.entities.Mob;
import jgame.entities.Particle;
import jgame.entities.ParticleFactory;
import jgame.environment.Tile;
import jgame.game.INPUT_KEY;
import jgame.game.InputHandler;
import jgame.game.InputKey;
import jgame.game.JGame;
import jgame.game.State;
import jgame.geometry.JCircle;
import jgame.geometry.JPolygon;
import jgame.geometry.JShape;
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
	
	private InputKey escape = new InputKey(INPUT_KEY.VK_ESCAPE);

	private MyMob myMob;

	private Camera camera;

	private IRenderable temp;

	private Tile[][] tileMap;

	private Sprite s;
	
	private Animation animation;
	
	private JCircle jcircle;
	
	private ArrayList<Particle> particles;
	
	private JPolygon jpolygon;
	
	public TrainingState(JGame game) {
		super(game);
		InputHandler.add(up, down, left, right, escape);
		myMob = new MyMob(new Vector2(10, 10));
		jpolygon = new JPolygon(myMob.position());

		particles = new ArrayList<Particle>();
		
		camera = new Camera(game);
		camera.follow(myMob);
		
		tileMap = new Tile[10][10];

		BufferedImage tileImage = toCompatibleImage(GraphicsUtility.loadImage("/GrassTile.png"));
		s = new Sprite(tileImage);
		
		IMesh mesh = new IMesh(){
			public void render(JGraphics g, Vector2 position){
				g.setColor(Color.CYAN);
				g.fillRect(position, Tile.TILE_SIZE, Tile.TILE_SIZE);
			}
		};
		
		animation = new Animation(new IMesh[]{new Sprite(tileImage)}, 100, true);
		
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
		jcircle = new JCircle(new Vector2(1, 1), 20);
		jcircle.setInnerColor(new Color(1, 0f, 0f, 0.5f));
		
		jpolygon.addPoint(new Vector2(0, 0));
		jpolygon.addPoint(new Vector2(5, 20));
		jpolygon.addPoint(new Vector2(0, 30));
		jpolygon.addPoint(new Vector2(5, 40));
		
		jpolygon.setOutlineColor(Color.YELLOW);
		jpolygon.setInnerColor(new Color(1, 1, 0, 0.7f));
	}

	@Override
	public void render(JGraphics g) {
		

		camera.startCapture(g);

		for (int i = 0; i < tileMap.length; i++) {
			for (int j = 0; j < tileMap[0].length; j++) {
				tileMap[i][j].render(g);
			}
		}
		
		myMob.render(g);

		temp.render(g);
		
		jcircle.render(g);
		
		jpolygon.render(g);

		camera.endCapture(g);
		
		for(int i = 0; i < particles.size(); i ++){
			particles.get(i).render(g);
		}
		
		g.setColor(Color.white);
		g.drawString(getGame().getCurrentFramesPerSecond() + ":" + testIntersection(jcircle, jpolygon), 10, 30);

	}
	
	private boolean testIntersection(JShape a, JShape b){
		Area areaA = new Area(a);
		areaA.intersect(new Area(b));
		return !areaA.isEmpty();
		
	}

	@Override
	public void update() {
		if(escape.isPressed()){
			System.exit(0);
		}
		
		particles.addAll(ParticleFactory.generateParticles(InputHandler.getScaledMousePosition(), 1, 20, 1, 2, 0.5f, 1f, 1, Color.RED));
		
		for(int i = 0; i < particles.size(); i ++){
			particles.get(i).update();
		}
		
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
		
		jpolygon.move((int)myMob.velocity().x, (int)myMob.velocity().y);

		myMob.update();
	}
	
	private BufferedImage toCompatibleImage(BufferedImage image)
	{
		// obtain the current system graphical settings
		GraphicsConfiguration gfx_config = GraphicsEnvironment.
			getLocalGraphicsEnvironment().getDefaultScreenDevice().
			getDefaultConfiguration();

		/*
		 * if image is already compatible and optimized for current system 
		 * settings, simply return it
		 */
		if (image.getColorModel().equals(gfx_config.getColorModel()))
			return image;

		// image is not optimized, so create a new image that is
		BufferedImage new_image = gfx_config.createCompatibleImage(
				image.getWidth(), image.getHeight(), image.getTransparency());

		// get the graphics context of the new image to draw the old image on
		Graphics2D g2d = (Graphics2D) new_image.getGraphics();

		// actually draw the image and dispose of context no longer needed
		g2d.drawImage(image, 0, 0, null);
		g2d.dispose();
		
		// return the new optimized image
		return new_image; 
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