package jgame.environment;

import jgame.entities.IUpdatable;
import jgame.graphics.IMesh;
import jgame.graphics.IRenderable;
import jgame.graphics.JGraphics;
import jgame.util.JSerializable;
import jgame.util.Vector2;

public class Tile extends JSerializable implements IRenderable, IUpdatable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3304259418245681689L;

	private int Id;
	
	private Vector2 position;

	private transient IMesh mesh;
	
	public static int TILE_SIZE = 16;
	
	public Tile(int ID, Vector2 position, IMesh mesh){
		this.Id = ID;
		this.position = position;
		this.mesh = mesh;
	}
	
	public void update(){
		if(mesh instanceof IUpdatable){
			((IUpdatable) mesh).update();
		}
	}
	
	public void render(JGraphics g){
		render(g, position);
	}
	
	public void render(JGraphics g, Vector2 position){
		mesh.render(g, position);
	}

	@Override
	protected void init() {
		
	}
	
	public int getTileId() { return Id; }

	public Vector2 position() { return position; }
	
}