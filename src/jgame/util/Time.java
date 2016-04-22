package jgame.util;

public final class Time {
	private static long currentTime;
	private static long lastTime;
	
	public static long getTime(){
		return System.nanoTime();
	}
	
	public static void init(){
		lastTime = getTime();
		currentTime = getTime();
	}
	
	public static long getDelta(){
		return currentTime - lastTime;
	}
	
	public static void update(){
		lastTime = currentTime;
		currentTime = getTime();
	}
	
}
