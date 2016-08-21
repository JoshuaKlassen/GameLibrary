package jgame.environment;

import java.awt.Shape;
import java.util.ArrayList;

import jgame.graphics.JGraphics;

public class EnvironmentLayer{

	private ArrayList<EnvironmentObject> environmentObjects;
	
	private int z = 1;
	
	private boolean show = true;
	
	public EnvironmentLayer(){
		environmentObjects = new ArrayList<EnvironmentObject>();
	}
	
	public void update(){
		for(int i = 0; i < environmentObjects.size(); i ++){
			environmentObjects.get(i).update();
		}
	}
	
	public void render(JGraphics g, Shape bounds){
		if(show){
			for(int i = 0; i < environmentObjects.size(); i ++){
				EnvironmentObject environmentObject = environmentObjects.get(i);
				if(bounds.contains(environmentObject.position().x, environmentObject.position().y)){
					environmentObjects.get(i).render(g);
				}
			}
		}
	}
	
	public void render(JGraphics g){
		if(show){
			for(int i = 0; i < environmentObjects.size(); i ++){
				environmentObjects.get(i).render(g);
			}
		}
	}
	
	public void spawn(EnvironmentObject environmentObject){
		environmentObjects.add(environmentObject);
	}
	
	public void despawn(EnvironmentObject environmentObject){
		environmentObjects.remove(environmentObject);
	}
	
	public void setZ(int z) { this.z = z; }
	
	public void hide() { show = false; }
	
	public void show() { show = true; }
	
}