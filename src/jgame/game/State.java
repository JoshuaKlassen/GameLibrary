package jgame.game;

import jgame.entities.IUpdatable;
import jgame.graphics.IRenderable;

public abstract class State implements IRenderable, IUpdatable{
	
	private JGame game;
	
	public State(JGame game){
		this.game = game;
	}

	public JGame getGame(){
		return game;
	}
}