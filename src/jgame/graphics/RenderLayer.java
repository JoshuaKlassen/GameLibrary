package jgame.graphics;

import java.util.ArrayList;

public class RenderLayer implements IRenderable{

	private ArrayList<IRenderable> renderables;
	
	public RenderLayer(){
		renderables = new ArrayList<IRenderable>();
	}
	
	public void add(IRenderable renderable) { renderables.add(renderable); }
	
	public void remove(IRenderable renderable) { renderables.remove(renderable) ; }
	
	public ArrayList<IRenderable> getRenderables() { return renderables; }
	
	@Override
	public void render(JGraphics g) {
		for(int i = 0; i < renderables.size(); i ++){
			renderables.get(i).render(g);
		}
	}
}