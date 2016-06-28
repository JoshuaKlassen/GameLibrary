package jgame.entities;

import jgame.graphics.JGraphics;

public class Item extends Entity{

	private static final long serialVersionUID = 7150235249287185499L;

	private String name = "";
	
	public Item(String name){
		this.name = name;
	}
	
	@Override
	public void update() {
		
	}

	@Override
	public void render(JGraphics g) {
	}

	@Override
	protected void init() {
		
	}
	
	public String toString(){
		return name;
	}

}
