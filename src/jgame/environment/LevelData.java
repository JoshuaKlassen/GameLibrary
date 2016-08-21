package jgame.environment;

import java.util.ArrayList;

import jgame.entities.ActorManager;
import jgame.util.JSerializable;

public class LevelData extends JSerializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 702422681281391869L;
	
	private ArrayList<EnvironmentLayer> environmentLayers;
	
	private ActorManager actorManager;
	private EnvironmentManager environmentManager;
	
	public LevelData(ActorManager actorManager, EnvironmentManager environmentManager){
		environmentLayers = new ArrayList<EnvironmentLayer>();
		this.actorManager = actorManager;
		this.environmentManager = environmentManager;
	}
	
	public LevelData(ActorManager actorManager, EnvironmentManager environmentManager, ArrayList<EnvironmentLayer> environmentLayers){
		this.environmentLayers = environmentLayers;
		this.actorManager = actorManager;
		this.environmentManager = environmentManager;
	}
	
	public ArrayList<EnvironmentLayer> getEnvironmentLayers() { return environmentLayers; }
	
	@Override
	protected void init() {
		
	}
	
	public ActorManager getActorManager() { return actorManager; }
	
	public EnvironmentManager getEnvironmentManager() { return environmentManager; }
	
}