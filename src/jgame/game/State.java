package jgame.game;

import jgame.entities.Updatable;
import jgame.graphics.Drawable;

public abstract class State implements Drawable, Updatable{
	
	private JGame game;
	
	public State(JGame game){
		this.game = game;
	}

	public JGame getGame(){
		return game;
	}
}