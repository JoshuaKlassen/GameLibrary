package jgame.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import jgame.graphics.JGraphics;

public class ActorManager {
	
	private ArrayList<Actor> actors;
	
	private boolean sortActors = true;
	
	private Comparator<Actor> comparator;
	
	public ActorManager(){
		actors = new ArrayList<Actor>();
		comparator = new Comparator<Actor>(){
			@Override
			public int compare(Actor a, Actor b){
				return Float.compare(a.position.y, b.position.y);
				
			}
		};
	}
	
	public boolean spawn(Actor actor){
		return actors.add(actor);
	}
	
	public boolean despawn(Actor actor){
		return actors.remove(actor);
	}
	
	public void update(){
		if(sortActors){
			Collections.sort(actors, comparator);
		}
		
		for(int i = 0; i < actors.size(); i ++){
			actors.get(i).update();
		}
	}
	
	public void render(JGraphics g){
		for(int i = 0; i < actors.size(); i ++){
			actors.get(i).render(g);
		}
	}
	
	public void enableActorSort() { sortActors = true; }
	
	public void disableActorSort() { sortActors = false; }
}