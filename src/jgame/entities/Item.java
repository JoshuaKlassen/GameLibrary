package jgame.entities;

import java.awt.Graphics;

public class Item extends Entity{

	private static final long serialVersionUID = 7150235249287185499L;

	String name = "";
	
	public Item(String name){
		this.name = name;
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		
	}
	
	public String toString(){
		return name;
	}

}
