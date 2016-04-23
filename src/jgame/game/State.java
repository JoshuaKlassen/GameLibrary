package jgame.game;

import jgame.graphics.Drawable;

public abstract class State implements Drawable{
	
	private JGame game;
	
	public State(JGame game){
		this.game = game;
	}

	public JGame getGame(){
		return game;
	}
}
