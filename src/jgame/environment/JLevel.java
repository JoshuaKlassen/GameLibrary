package jgame.environment;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;

import jgame.entities.ActorManager;
import jgame.game.JGame;
import jgame.graphics.Camera;
import jgame.graphics.JGraphics;
import jgame.util.JSerializable;

public class JLevel extends JSerializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7711890841790840816L;

	private ActorManager actorManager;
	private EnvironmentManager environmentManager;
	
	private JGame game;
	
	private LevelData levelData;
	
	private Camera camera;
	
	public JLevel(JGame game){
		this.game = game;
		actorManager = new ActorManager();
		environmentManager = new EnvironmentManager();
	}
	
	public JLevel(JGame game, LevelData data){
		this.game = game;
		levelData = data;
		loadLevelData(levelData);
	}
	
	public boolean loadLevelData(LevelData data){
		actorManager = data.getActorManager();
		environmentManager = data.getEnvironmentManager();
		ArrayList<EnvironmentLayer> environmentLayers = data.getEnvironmentLayers();
		for(int i = 0; i < environmentLayers.size(); i ++){
			environmentManager.addLayer(environmentLayers.get(i));
		}
		return true;
	}
	
	@Override
	protected void init() {
		
	}

	public void update(){
		environmentManager.update();
		actorManager.update();
	}
	
	public void render(JGraphics g){
		for(int i = 0; i <= environmentManager.getMaxZ(); i ++){
			if(camera != null){
				Rectangle bounds = camera.getBounds();
				environmentManager.render(g, bounds, i);
				actorManager.render(g, bounds, i);
			}else{
				environmentManager.render(g, i);
				actorManager.render(g, i);
			}
			
		}
	}

	public ActorManager getActorManager() { return actorManager; }
	
	public EnvironmentManager getEnvironmentManager() { return environmentManager; }
	
	public Camera getCamera() { return camera; }
	
	public void setCamera(Camera camera) { this.camera = camera; }
}