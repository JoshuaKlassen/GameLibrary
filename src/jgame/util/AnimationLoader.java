package jgame.util;

import jgame.graphics.Animation;

public class AnimationLoader implements IResourceLoader{

	@Override
	public Animation load(String filePath) {
		return (Animation) Utility.readObject(filePath);
	}
}