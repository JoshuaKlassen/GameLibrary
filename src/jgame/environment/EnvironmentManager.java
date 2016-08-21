package jgame.environment;

import java.awt.Shape;
import java.util.ArrayList;

import jgame.graphics.JGraphics;

public class EnvironmentManager {

	private int maxZ = 1;
	
	private ArrayList<EnvironmentLayer> environmentLayers;
	
	public EnvironmentManager(){
		environmentLayers = new ArrayList<EnvironmentLayer>();
	}
	
	public void addLayer(EnvironmentLayer layer){
		environmentLayers.add(layer);
	}
	
	public void removeLayer(EnvironmentLayer layer){
		environmentLayers.remove(layer);
	}
	
	public void spawn(EnvironmentObject environmentObject){
		//environmentLayers.get(i);
	}
	
	public void despawn(EnvironmentObject environmentObject){
		//environmentLayers.get(environmentObject.getZ())
	}
	
	public void update(){
		
	}
	
	public void render(JGraphics g, Shape bounds, int z){
		for(int i = 0; i < environmentLayers.size(); i ++){
			environmentLayers.get(i).render(g, bounds);
		}
	}
	
	public void render(JGraphics g, int z){
		for(int i = 0; i < environmentLayers.size(); i ++){
			environmentLayers.get(i).render(g);
		}
	}
	
	public int getMaxZ() { return maxZ; }
	
}