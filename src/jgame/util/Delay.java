package jgame.util;

//TODO Document
//allows for greater accuracy with timings, instead of relying on frames per second
//in which case on a different speed machines will run differently, so this helps to avoid that
public class Delay {

	private int length;
	private long endTime;
	private boolean started;
	
	public Delay(int length){
		this.length = length;
		started = false;
	}
	
	public boolean isOver(){
		boolean result = false;
		
		if(started){
			result = Time.getTime() > endTime;
		}
		
		return result;
	}
	
	public void start(){
		started = true;
		endTime = ((long)length * 1000000) + Time.getTime();
	}
	
	public void reset(){
		started = false;
	}
}