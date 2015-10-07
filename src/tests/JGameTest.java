package tests;

import java.awt.Color;
import java.awt.Graphics;

import jgame.UI.UIComponent;
import jgame.UI.UILabel;
import jgame.UI.UIPanel;
import jgame.UI.UIProgressBar;
import jgame.game.JGame;
import jgame.input.INPUT_KEY;
import jgame.input.InputHandler;
import jgame.input.InputKey;
import jgame.util.Vector2I;

public class JGameTest extends JGame {

	private static final long serialVersionUID = 1L;

	InputKey testKey = new InputKey(true, INPUT_KEY.VK_Y, INPUT_KEY.VK_U, INPUT_KEY.VK_T);
	
	int x = 0;
	
	InputHandler i;
	
	UIPanel panel = new UIPanel(new Vector2I(50, 50), 20, 50);
	UIPanel inner;
	UIProgressBar progressbar;
	
	public JGameTest(int screenWidth, int screenHeight) {
		super(screenWidth, screenHeight);
		super.setJFrame(getDefaultJFrame());
		//super.toggleFullScreen();
		super.getInputHandler().addInputKey(testKey);
		System.out.println(super.getInputHandler());
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
		
	}
	
	public void render(Graphics g){
		panel.render(g);
		g.setColor(Color.white);
		g.drawString(getCurrentFramesPerSecond() + ":" + getCurrentUpdatesPerSecond(), 10, 30);
		g.setColor(Color.green);
		g.fillRect(x, 10, 10, 10);
		g.fillRect((int)(getInputHandler().getMousePosition().x / getScaleWidth()), (int)(getInputHandler().getMousePosition().y / getScaleHeight()), 10, 10);
	}
	
	double p = 0;
	
	@Override
	public void update(){
		x += this.getInputHandler().getMouseWheelRotation();
		getInputHandler().resetMouseWheelRotation();
		if(testKey.isPressed()) toggleFullScreen();
		p += 0.01;
		if(p > 1) p = 0;
		
		progressbar.setProgress(p);
		
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
