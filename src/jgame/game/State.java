package jgame.game;

import jgame.entities.IUpdatable;
import jgame.graphics.IRenderable;
import jgame.graphics.JGraphics;
import jgame.util.Vector2;

public abstract class State implements IRenderable, IUpdatable{
	
	private JGame game;
	
	public State(JGame game){
		this.game = game;
	}

	public JGame getGame(){
		return game;
	}
	
	public void render(JGraphics g, Vector2 position){
		//TODO: really need to determine how to handle render
	}
}