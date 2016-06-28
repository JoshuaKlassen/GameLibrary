package jgame.graphics;

/**
 * Animation class
 * @author Josh
 */

public class Animation {
	
	//an array of all the frames in the animation
	private Sprite[] frames;
	
	//the speed at which each frame animates
	private int speed;
	
	//the currentFrame position in the array of frames in the animation
	private int currentFrame = 0;
	
	//how many ticks have passed since the last change in the currentFrame of the animation
	private int ticksPassed = 0;

	//if the animation has stopped
	private boolean stopped = false;
	
	//if the animation will repeat
	private boolean repeat;
	
	public Animation(Sprite[] frames, int speed, boolean repeat){
		this.frames = frames;
		this.speed = speed;
		this.repeat = repeat;
		
		if(speed < 0) throw new IndexOutOfBoundsException("Animation speed cannot be less than 0 (speed=" + speed + ")");
	}
	
	
	public void update(){
		if(!stopped){
			ticksPassed ++;
			if(ticksPassed >= speed){
				currentFrame++;
				ticksPassed = 0;
			}
			if(currentFrame >= frames.length){
				if(repeat) reset();
				else stop();
			}
		}
	}
	
	public void reset(){
		ticksPassed = 0;
		currentFrame = 0;
	}
	
	public void start(){
		stopped = false;
	}
	
	public void stop(){
		stopped = true;
	}
	
	public Sprite getCurrentFrame(){
		if(frames == null) return null;
		if(currentFrame > getNumberOfFrames()) return null;
		return frames[currentFrame];
	}

	public int getNumberOfFrames(){ return frames.length; }
	
	public void repeat(boolean repeat){ this.repeat = repeat; }
	
	public boolean repeat(){ return repeat; }
	
	public Sprite[] getFrames(){ return frames; }
	
	public Sprite getFrame(int frame){
		if(frame < 0 || frame > frames.length) 
			throw new IndexOutOfBoundsException("Cannot get a frame that is less than 0 or greater than the size of the frame array(frame=" + frame + ")");
		return frames[frame];
	}
	
	public int getSpeed(){ return speed; }
	
	public void setSpeed(int speed){ this.speed = speed; }
	
	public static Sprite[] generateRotationFrames(Sprite frame){
		Sprite[] sprites = new Sprite[360];
		//for(int i = 0; i < sprites.length; i ++) sprites[i] = new Sprite(ImageManipulator.rotate(frame.getImage(), i));
		return sprites;
	}
	
	public String toString(){
		return "Animation (currentSprite= " + getCurrentFrame() + ", currentFrame=" + currentFrame + ", numberOfFrames=" + getNumberOfFrames() + ", speed=" + speed+  ", repeat=" + repeat + ")";
	}
	
}
