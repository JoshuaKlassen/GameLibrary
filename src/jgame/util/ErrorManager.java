package jgame.util;

import java.util.ArrayList;

public final class ErrorManager {

	private static ArrayList<String> log = new ArrayList<String>();
	
	public static void appendToLog(String error){
		log.add(error);
	}
	
	public static void write(String error){
		System.err.println(error);
	}
	
	public static void printLog(){
		for(int i = 0; i < log.size(); i ++){
			System.err.println(log.get(i));
		}
	}
	
	public static void clearLog(){
		log.clear();
	}
	
}