package tests;

import java.awt.Color;

import jgame.entities.Mob;
import jgame.game.INPUT_KEY;
import jgame.game.InputHandler;
import jgame.game.InputKey;
import jgame.game.JGame;
import jgame.game.State;
import jgame.graphics.Camera;
import jgame.graphics.IRenderable;
import jgame.graphics.JGraphics;
import jgame.util.Vector2;

public class TrainingState extends State{

	private InputKey up = new InputKey(INPUT_KEY.UP);
	private InputKey down = new InputKey(INPUT_KEY.DOWN);
	private InputKey left = new InputKey(INPUT_KEY.LEFT);
	private InputKey right = new InputKey(INPUT_KEY.RIGHT);
	
	private MyMob myMob;
	
	private Camera camera;
	
	private IRenderable temp;
	
	public TrainingState(JGame game) {
		super(game);
		InputHandler.add(up, down, left, right);
		myMob = new MyMob(new Vector2(10, 10));
		
		camera = new Camera(game);
		camera.follow(myMob);
		
		temp = new IRenderable(){
			public void render(JGraphics g){
				g.setColor(Color.YELLOW);
				g.fillRect(100, 100, 10, 10);
			}
		};
	}

	@Override
	public void render(JGraphics g) {
		g.setColor(Color.white);
		g.drawString(InputHandler.getMouseWheelRotation() + "", 10, 30);
		
		camera.startCapture(g);
		
		myMob.render(g);
		
		temp.render(g);
		
		camera.endCapture(g);
		
		g.setColor(Color.RED);
		g.drawRect(camera.position(), 1, 1);
	}

	@Override
	public void update() {
		camera.update();
		
		if(up.isPressed()){
			myMob.velocity().y = -1;
		}else if(down.isPressed()){
			myMob.velocity().y = 1;
		}else{
			myMob.velocity().y = 0;
		}
		
		if(left.isPressed()){
			myMob.velocity().x = -1;
		}else if(right.isPressed()){
			myMob.velocity().x = 1;
		}else{
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

	public MyMob(Vector2 position){
		this.position = position;
		setMaxHealth(50);
	}

	@Override
	public void render(JGraphics g) {
		g.setColor(Color.BLUE);
		g.fillRect(position, 10, 10);
	}

	@Override
	public void update() {
		position = Vector2.add(position, velocity);
	}

	@Override
	protected void init() {
		
	}
}