package jgame.entities;

import java.awt.Graphics;

import jgame.util.Vector2;

public class BitMask implements ICollisionArea{

	private int[][] mask;
	
	private boolean show;
	
	public BitMask(int[][] mask){
		this.mask = mask;
	}
	
	@Override
	public boolean collision(Vector2 point) {
		return false;
	}

	@Override
	public boolean collision(ICollisionArea area) {
		return false;
	}
	
	public boolean collision(BitMask area){
		return false;
	}
	
	@Override
	public void toggleShow() { show = !show; }

	@Override
	public void render(Graphics g) {
		if(show){
			
		}
	}

	@Override
	public void move(Vector2 position) {
		
	}

}