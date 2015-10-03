package tests;

import game.JGame;

import java.awt.Color;
import java.awt.Graphics;

public class JGameTest extends JGame {

	private static final long serialVersionUID = 1L;

	public JGameTest(int screenWidth, int screenHeight) {
		super(screenWidth, screenHeight);
	}
	
	@Override
	public void render(Graphics g){
		g.setColor(Color.green);
		g.fillRect(10, 10, 10, 10);
	}
	
	public static void main(String[] args){
		JGameTest test = new JGameTest(100, 100);
		test.start();
	}

}
