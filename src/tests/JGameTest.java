package tests;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import jgame.UI.UILabel;
import jgame.UI.UIPanel;
import jgame.UI.UIProgressBar;
import jgame.game.INPUT_KEY;
import jgame.game.InputHandler;
import jgame.game.InputKey;
import jgame.game.JGame;
import jgame.util.Utility;
import jgame.util.Vector2I;

public class JGameTest extends JGame {

	private static final long serialVersionUID = 1L;

	InputKey testKey = new InputKey(true, INPUT_KEY.VK_Y, INPUT_KEY.VK_U, INPUT_KEY.VK_T);
	
	int x = 0;
	
	InputHandler i;
	
	UIPanel panel = new UIPanel(new Vector2I(50, 50), 20, 50);
	UIPanel inner;
	UIProgressBar progressbar;
	
	Rectangle rec1 = new Rectangle(60, 60, 40, 40);
	Rectangle rec2 = new Rectangle(10, 10, 10, 10);
	
	int[][] bA = new int[rec1.width][rec1.height];
	int[][] bB = new int[rec2.width][rec2.height];
	
	Bitmask testMask = new Bitmask(Utility.loadImage("/BitmaskTest.png"));

	public JGameTest(int screenWidth, int screenHeight) {
		super(screenWidth, screenHeight);
		super.setJFrame(getDefaultJFrame());
		//super.toggleFullScreen();
		InputHandler.add(testKey);
		panel.setBackgroundColor(Color.blue);
		inner = new UIPanel(new Vector2I(10, 10), 20, 20);
		inner.add(new UILabel(new Vector2I(20, 20), "Hello world!", Color.red));
		inner.setBackgroundColor(Color.yellow);
		panel.add(inner);
		
		panel.setWidth(300);
		
		progressbar = new UIProgressBar(new Vector2I(10, 30), 100, 10);
		
		panel.add(progressbar);
		progressbar.setProgress(0.5);
		
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
		
	}
	
	public void render(Graphics g){
		//panel.render(g);
		g.setColor(Color.white);
		g.drawString(getCurrentFramesPerSecond() + ":" + getCurrentUpdatesPerSecond(), 10, 30);
		g.setColor(Color.green);
		g.fillRect(x, 10, 10, 10);
		g.fillRect(rec2.x, rec2.y, rec2.width, rec2.height);
		g.setColor(Color.white);
		g.fillRect(rec1.x, rec1.y, rec1.width, rec1.height);
		
		if(rec1.intersects(rec2)){
			Vector2I result = Bitmask.firstPointOfContact(rec1,rec2, testMask, new Bitmask(bB));
			g.drawString(result+"", 100, 30);
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
		if(testKey.isPressed()) toggleFullScreen();
		p += 0.01;
		if(p > 1) p = 0;
		
		progressbar.setProgress(testKey.timesFired() * 0.1);
		
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
		test.stop();
	}

}
