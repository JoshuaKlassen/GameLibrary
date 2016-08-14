package jgame.environment;

import jgame.entities.ActorManager;
import jgame.game.JGame;
import jgame.graphics.JGraphics;
import jgame.util.JSerializable;

public class JLevel extends JSerializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7711890841790840816L;

	private ActorManager actorManager;
	
	private JGame game;
	
	public JLevel(JGame game){
		this.game = game;
		actorManager = new ActorManager();
	}
	
	
	@Override
	protected void init() {
		
	}

	public void update(){
		actorManager.update();
	}
	
	public void render(JGraphics g){
		actorManager.render(g);
	}
	
}