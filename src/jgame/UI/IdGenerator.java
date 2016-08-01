package jgame.UI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class IdGenerator {

	private static final int NUM_IDS = 5000;
	
	private static List<Integer> Ids = new ArrayList<Integer>();
	
	static{
		for(int i = 0; i < NUM_IDS; i ++)
			Ids.add(i);
		
		Collections.shuffle(Ids);
	}
	
	public static int getId(){
		int id = -1;
		
		if(!Ids.isEmpty()){
			id = Ids.get(0);
			Ids.remove(0);
		}
		
		return id;
	}
	
	public static void addId(int id){ Ids.add(id); }
}