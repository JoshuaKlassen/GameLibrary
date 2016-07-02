package jgame.util;

import java.awt.AWTException;
import java.awt.Robot;

import jgame.game.INPUT_KEY;

public final class JRobot {

	private static Robot robot;
	
	private JRobot(){}
	
	private static void createRobot(){
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	
	public static void moveMouse(int x, int y){
		if(robot == null)
			createRobot();
		robot.mouseMove(x, y);
	}
	
	public static void press(INPUT_KEY key){
		if(robot == null)
			createRobot();
		if(key.getID() >= 1 && key.getID() <= 3)
			robot.mousePress(key.getID());
		else
			robot.keyPress(key.getID());
	}
	
	public static void release(INPUT_KEY key){
		if(robot == null)
			createRobot();
		if(key.getID() >= 1 && key.getID() <= 3)
			robot.mousePress(key.getID());
		else
			robot.keyRelease(key.getID());
	}
	
	public static void mouseWheel(int wheelNotches){
		if(robot == null)
			createRobot();
		robot.mouseWheel(wheelNotches);
	}
	
}