package tests;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import jgame.UI.ButtonAction;
import jgame.UI.UIButton;
import jgame.UI.UILabel;
import jgame.UI.UIPanel;
import jgame.UI.UIProgressBar;
import jgame.game.INPUT_KEY;
import jgame.game.InputHandler;
import jgame.game.InputKey;
import jgame.game.JGame;
import jgame.util.Delay;
import jgame.util.Utility;
import jgame.util.Vector2I;

public class JGameTest extends JGame {

	private static final long serialVersionUID = 1L;

	InputKey testKey = new InputKey(true, INPUT_KEY.VK_A);
	
	InputKey space = new InputKey(true, INPUT_KEY.VK_SPACE);
	
	InputKey escape = new InputKey(INPUT_KEY.VK_ESCAPE);
	
	int x = 0;
	
	UIPanel panel = new UIPanel(new Vector2I(20, 50), 20, 50);
	UIPanel inner;
	UIProgressBar progressbar;
	UIButton button;
	
	Rectangle rec1 = new Rectangle(60, 60, 40, 40);
	Rectangle rec2 = new Rectangle(10, 10, 10, 10);
	
	int[][] bA = new int[rec1.width][rec1.height];
	int[][] bB = new int[rec2.width][rec2.height];
	
	Bitmask testMask = new Bitmask(Utility.loadImage("/BitmaskTest.png"));
	
	Delay delay = new Delay(1000);

	public JGameTest(int screenWidth, int screenHeight) {
		super(screenWidth, screenHeight);
		super.setJFrame(getDefaultJFrame());
		super.toggleFullScreen();
		String serial = "Serialized data";
		InputHandler.add(new InputKey(INPUT_KEY.LEFT_CLICK));
		InputHandler.add(new InputKey(INPUT_KEY.LEFT_CLICK));
		Utility.writeObject(serial, Utility.createDataFolder("objects") + "/test.dat");
		System.out.println(Utility.createDataFolder("objects"));
		serial = (String)Utility.readObject(Utility.createDataFolder("objects") + "/test.dat");
		
		System.out.println(serial);
		
		InputHandler.add(testKey, escape);
		InputHandler.add(space);
		panel.setBackgroundColor(new Color(0x0000ff));
		inner = new UIPanel(new Vector2I(10, 10), 20, 20);
		inner.add(new UILabel(new Vector2I(20, 20), "Hello world!", Color.red));
		inner.setBackgroundColor(Color.yellow);
		panel.add(inner);
		panel.setWidth(300);
		
		progressbar = new UIProgressBar(new Vector2I(10, 30), 100, 10);
		panel.add(progressbar);
		progressbar.setProgress(0.5);
		
		button = new UIButton(new Vector2I(150, 10), 50, 50);
		UIPanel temp = new UIPanel(button.getPosition(), 50, 50);
		temp.setBackgroundColor(Color.red);
		button.setHighlightedState(temp);
		UIPanel temp2 = new UIPanel(button.getPosition(), 50, 50);
		temp2.setBackgroundColor(Color.green);
		button.setPressedState(temp2);
		
		ButtonAction ba = new ButtonAction(){
			public void doAction(){
				screenshot();
			}
		};
		button.setAction(ba);
		button.trackMouse(true);
		button.addPressKeys(new InputKey(INPUT_KEY.LEFT_CLICK), new InputKey(INPUT_KEY.VK_SPACE));
		
		panel.add(button);
		
		System.out.println(panel);
		
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
		
		super.hideCursor();
		delay.start();
	}
	
	public void render(Graphics g){
		panel.render(g);
		g.setColor(Color.white);
		g.drawString(getCurrentFramesPerSecond() + ":" + getCurrentUpdatesPerSecond() + ":" + InputHandler.getMousePosition(), 10, 30);
		g.setColor(Color.green);
		g.fillRect(x, 10, 10, 10);
		g.fillRect(rec2.x, rec2.y, rec2.width, rec2.height);
		g.setColor(Color.white);
		g.fillRect(rec1.x, rec1.y, rec1.width, rec1.height);
		
		if(rec1.intersects(rec2)){
			Vector2I result = Bitmask.firstPointOfContact(rec1,rec2, testMask, new Bitmask(bB));
			g.drawString(result+"", 130, 30);
			if(result != null){
				g.setColor(Color.blue);
				g.fillRect(result.x, result.y, 1, 1);
			}
		}
	}
	
	double p = 0;
	
	@Override
	public void update(){
		x += InputHandler.getMouseWheelRotation();
		InputHandler.resetMouseWheelRotation();
		panel.update();
		if(testKey.isPressed()) {
			toggleFullScreen();
			testKey.release();
		}
		
		if(delay.isOver()){
			System.out.println("Delay is over!");
			delay.reset();
			delay.start();
		}
		
		if(escape.isPressed()) stop();
		p += 0.02;
		
		progressbar.setProgress(p%1);
		
		rec2.setLocation((int)(InputHandler.getMousePosition().x / getScaleWidth()), (int)(InputHandler.getMousePosition().y / getScaleHeight()));
		
		if(rec1.intersects(rec2)){
			//System.out.println(Bitmask.collision(rec1,rec2, new Bitmask(bA), new Bitmask(bB)));
		}
	}
	
	public static void main(String[] args){
		long now = System.currentTimeMillis();
		JGameTest test = new JGameTest(400, 200);
		test.start();
		
		while(!test.isFullScreen() ||System.currentTimeMillis() - now < 2000){
			
		}
		//test.stop();
	}

}
