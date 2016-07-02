package tests;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;

import jgame.entities.Entity;
import jgame.entities.ParticleFactory;
import jgame.game.INPUT_KEY;
import jgame.game.InputHandler;
import jgame.game.InputKey;
import jgame.game.State;
import jgame.graphics.GraphicsUtility;
import jgame.graphics.IRenderable;
import jgame.graphics.JGraphics;
import jgame.util.Delay;
import jgame.util.Utility;
import jgame.util.Vector2;

public class GameState extends State {

	private InputKey testKey = new InputKey(true, INPUT_KEY.VK_A);
	private InputKey space = new InputKey(true, INPUT_KEY.VK_SPACE);
	private InputKey escape = new InputKey(INPUT_KEY.VK_ESCAPE);
	
	private int mouseWheelDemonstration = 0;
	
	private Rectangle rec1 = new Rectangle(60, 60, 40, 40);
	private Rectangle rec2 = new Rectangle(10, 10, 10, 10);
	
	private int[][] bA = new int[rec1.width][rec1.height];
	private int[][] bB = new int[rec2.width][rec2.height];
	
	private Bitmask testMask = new Bitmask(GraphicsUtility.loadImage("/BitmaskTest.png"));
	
	private Delay delay = new Delay(10000);
	
	private IRenderable drawItem;
	
	private ArrayList<Entity> entities;
	
	private String serializedEntitiesPath = "/entities.dat";
	
	private JGameTest game;
	
	@SuppressWarnings("unchecked")
	public GameState(JGameTest game) {
		super(game);
		this.game = game;
		if(!Utility.directoryExists(game.dataFilePath + serializedEntitiesPath)){
			Utility.createDataFolder(game.dataFilePath + serializedEntitiesPath);
			entities = new ArrayList<Entity>();
		}else{
			entities = (ArrayList<Entity>)Utility.readObject(game.dataFilePath + serializedEntitiesPath);
		}
		
		game.hideCursor();
		
		InputHandler.add(testKey, escape, space);
		
		for(int i = 0; i < bA.length; i ++){
			for(int j = 0; j < bA[0].length; j ++){
				bA[i][j] = 1;
			}
		}
		
		for(int i = 0; i < bB.length; i ++){
			for(int j = 0; j < bB[0].length; j ++){
				bB[i][j] = 1;
			}
		}
		
		delay.start();
		
		drawItem = new IRenderable(){
			public void render(JGraphics g){
				g.setColor(Color.white);
				g.drawString("Drawable item", 300, 150);
			}
		};
		
	}

	@Override
	public void render(JGraphics g) {
		g.setColor(Color.white);
		g.drawString(getGame().getCurrentFramesPerSecond() + ":" + getGame().getCurrentUpdatesPerSecond() + ":" + InputHandler.getMousePosition(), 10, 30);
		g.setColor(Color.green);
		g.fillRect(mouseWheelDemonstration, 10, 10, 10);
		
		Vector2 mousePosition = getGame().getScaledMousePosition();
		g.fillRect(mousePosition, 1, 1);
		
		g.setColor(Color.white);
		g.fillRect(rec1.x, rec1.y, rec1.width, rec1.height);
		
		if(rec1.intersects(rec2)){
			Vector2 result = Bitmask.firstPointOfContact(rec1,rec2, testMask, new Bitmask(bB));
			g.drawString(result+"", 130, 30);
			if(result != null){
				g.setColor(Color.blue);
				g.fillRect(result, 1, 1);
			}
		}
		drawItem.render(g);
		
		for(int i = 0; i < entities.size(); i ++){
			entities.get(i).render(g);
		}
	}

	@Override
	public void update() {
		entities.addAll(ParticleFactory.generateParticles(InputHandler.getScaledMousePosition(), 1, 2, 1, 2, 0.5f, 1f, 1, Color.RED));

		for(int i = 0; i < entities.size(); i ++){
			Entity e = entities.get(i);
			e.update();
			if(!e.isAlive()){
				entities.remove(e);
			}
		}
		
		mouseWheelDemonstration += InputHandler.getMouseWheelRotation();
		InputHandler.resetMouseWheelRotation();
		if(testKey.isPressed()) {
			getGame().toggleFullScreen();
			testKey.release();
		}
		
		if(delay.isOver()){
			System.out.println("Delay is over!");
			delay.reset();
			delay.start();
		}
		
		if(escape.isPressed()) {
			Utility.writeObject(entities, game.dataFilePath + serializedEntitiesPath);
			getGame().stop();
		}
		
		if(rec1.intersects(rec2)){
			//System.out.println(Bitmask.collision(rec1,rec2, new Bitmask(bA), new Bitmask(bB)));
		}
	}

}