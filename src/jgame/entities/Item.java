package jgame.entities;

import jgame.graphics.JGraphics;

public class Item extends Actor{

	private static final long serialVersionUID = 7150235249287185499L;

	private String name = "";
	
	public Item(ActorManager actorManager, String name){
		super(actorManager);
		this.name = name;
	}
	
	@Override
	public void update() {
		
	}

	@Override
	public void render(JGraphics g) {
		render(g);
	}

	@Override
	protected void init() {
		
	}
	
	public String toString(){
		return name;
	}

	@Override
	public boolean isAlive() {
		return false;
	}

}
