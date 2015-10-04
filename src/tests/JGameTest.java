package tests;

import java.awt.Color;
import java.awt.Graphics;

import jgame.game.JGame;

public class JGameTest extends JGame {

	private static final long serialVersionUID = 1L;

	public JGameTest(int screenWidth, int screenHeight) {
		super(screenWidth, screenHeight);
		super.setJFrame(getDefaultJFrame());
		super.toggleFullScreen();
		System.out.println(super.screenshot());
	}
	
	@Override
	public void render(Graphics g){
		g.setColor(Color.green);
		g.fillRect(10, 10, 10, 10);
	}
	
	public static void main(String[] args){
		long now = System.currentTimeMillis();
		JGameTest test = new JGameTest(200, 100);
		test.start();
		
		while(System.currentTimeMillis() - now < 2000){
			
		}
		test.stop();
	}

}
