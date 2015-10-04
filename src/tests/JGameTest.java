package tests;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import jgame.game.JGame;
import jgame.input.INPUT_KEY;
import jgame.input.InputHandler;
import jgame.input.InputKey;

public class JGameTest extends JGame {

	private static final long serialVersionUID = 1L;

	InputKey testKey = new InputKey(true, INPUT_KEY.VK_Y, INPUT_KEY.VK_U, INPUT_KEY.VK_T);
	
	int x = 0;
	
	InputHandler i;
	
	public JGameTest(int screenWidth, int screenHeight) {
		super(screenWidth, screenHeight);
		super.setJFrame(getDefaultJFrame());
		//super.toggleFullScreen();
		System.out.println(super.screenshot());
		super.getInputHandler().addInputKey(testKey);
		System.out.println(super.getInputHandler());
	}
	
	public void render(Graphics g){
		g.setColor(Color.white);
		g.drawString(x + ":" + testKey.timesFired() + ":" + this.getInputHandler().getMouseWheelRotation(), 10, 30);
		g.setColor(Color.green);
		g.fillRect(x, 10, 10, 10);
		g.fillRect(getInputHandler().getMousePosition().x, getInputHandler().getMousePosition().y, 10, 10);
	}
	
	@Override
	public void update(){
		x += this.getInputHandler().getMouseWheelRotation();
		getInputHandler().resetMouseWheelRotation();
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
