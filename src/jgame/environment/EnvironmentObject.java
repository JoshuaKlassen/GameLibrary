package jgame.environment;

import jgame.entities.IUpdatable;
import jgame.graphics.IRenderable;
import jgame.util.JSerializable;
import jgame.util.Vector2;

public abstract class EnvironmentObject extends JSerializable implements IRenderable, IUpdatable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4235098629495691905L;
	
	protected Vector2 position = new Vector2();
	
	protected int z = 1;
	
	private EnvironmentManager environmentManager;
	
	public EnvironmentObject(EnvironmentManager environmentManager){
		this.environmentManager = environmentManager;
	}
	
	public void spawn(){
		environmentManager.spawn(this);
	}
	
	public void despawn(){
		environmentManager.despawn(this);
	}
	
	public Vector2 position() { return position; }
	
	public void setPosition(Vector2 pos) { position = pos; }
	
	public int getZ() { return z; }
	
	public void setZ(int z){
		if(z < 0) throw new IndexOutOfBoundsException("z cannot be less than 0 - (z:" + z + ")."); //TODO
		this.z = z;
	}
}