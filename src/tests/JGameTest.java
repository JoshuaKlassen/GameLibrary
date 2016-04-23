package tests;

import jgame.game.JGame;

public class JGameTest extends JGame {

	private static final long serialVersionUID = 1L;

	private MainMenuState mainMenuState;
	
	public JGameTest(int screenWidth, int screenHeight) {
		super(screenWidth, screenHeight);
		super.setJFrame(getDefaultJFrame());
		super.toggleFullScreen();
		
		mainMenuState = new MainMenuState(this);
		this.transitionState(mainMenuState);
		
	}
	
	public void update(){
		super.update();
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
