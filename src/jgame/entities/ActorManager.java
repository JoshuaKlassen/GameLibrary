package jgame.entities;

import java.awt.Shape;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import jgame.graphics.JGraphics;

public class ActorManager {
	
	private ArrayList<Actor> actors;
	
	private boolean sortActorsOnY = true;
	
	private Comparator<Actor> comparator;
	
	public ActorManager(){
		actors = new ArrayList<Actor>();
		comparator = new Comparator<Actor>(){
			@Override
			public int compare(Actor a, Actor b){
				int zCompare = Integer.compare(a.getZ(), b.getZ());
				return zCompare != 0 ? zCompare : Float.compare(a.position().y, b.position().y);
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
		if(sortActorsOnY){
			Collections.sort(actors, comparator);
		}
		
		for(int i = 0; i < actors.size(); i ++){
			actors.get(i).update();
		}
	}
	
	public void render(JGraphics g, Shape bounds, int z){
		for(int i = 0; i < actors.size(); i ++){
			Actor actor = actors.get(i);
			if(actor.getZ() == z && bounds.contains(actor.position().x, actor.position().y)){
				actor.render(g);
			}
		}
	}
	
	public void render(JGraphics g, int z){
		for(int i = 0; i < actors.size(); i ++){
			Actor actor = actors.get(i);
			if(actor.getZ() == z){
				actor.render(g);
			}
		}
	}
	
	public void enableActorSortOnY() { sortActorsOnY = true; }
	
	public void disableActorSortOnY() { sortActorsOnY = false; }
}