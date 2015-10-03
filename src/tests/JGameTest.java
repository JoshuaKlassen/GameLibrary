package tests;

import java.awt.Color;
import java.awt.Graphics;

import jgame.game.JGame;
import jgame.util.Utility;

public class JGameTest extends JGame {

	private static final long serialVersionUID = 1L;

	public JGameTest(int screenWidth, int screenHeight) {
		super(screenWidth, screenHeight);
		super.setJFrame(getDefaultJFrame());
	}
	
	@Override
	public void render(Graphics g){
		g.setColor(Color.green);
		g.fillRect(10, 10, 10, 10);
	}
	
	public static void main(String[] args){
		JGameTest test = new JGameTest(100, 100);
		test.start();
		System.out.println(Utility.getOperatingSystem());
	}

}
