package jgame.game;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import jgame.util.Utility;
import jgame.util.Utility.OS;

/*
 * Copyright notice
 * Do not distribute
 * Date: October 2, 2015
 * Author: Joshua Klassen
 */

public abstract class JGame extends Canvas implements Runnable{
	
	private static final long serialVersionUID = -5429375465168456291L;
	
	private OS os = Utility.getOperatingSystem();
	
	private int screen_width;
	private int screen_height;
	
	private boolean running = false;
	
	private int preferredUpdatesPerSecond = 60;
	
	private int currentUpdatesPerSecond;
	private int currentFramesPerSecond;
	
	private JFrame frame;
	
	private boolean isFullScreen = false;
	
	private float scaleWidth = 1.0f;
	private float scaleHeight = 1.0f;
	
	private Thread gameThread;
	
	private BufferStrategy bufferStrategy;
	
	private GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
	private GraphicsDevice graphicsDevice = graphicsEnvironment.getDefaultScreenDevice();
	
	public JGame(int screenWidth, int screenHeight){
		screen_width = screenWidth;
		screen_height = screenHeight;
	}
	
	public synchronized void start(){
		if(frame == null) setJFrame(getDefaultJFrame());
		running = true;
		gameThread = new Thread(this, "Main thread");
		gameThread.start();
		frame.setVisible(true);
	}
	
	public synchronized void stop(){
		running = false;
		frame.dispose();
		System.exit(0);
	}
	
	@Override
	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / preferredUpdatesPerSecond;
		double delta = 0;
		
		int updates = 0;
		int frames  = 0;
		
		while(running){
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			
			while(delta >= 1){
				update();
				updates ++;
				delta --;
			}
			
			renderScreen();
			frames ++;
			
			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				currentUpdatesPerSecond = updates;
				currentFramesPerSecond = frames;
				updates = 0;
				frames = 0;
			}
			
		}
	}
	
	private void renderScreen(){
		bufferStrategy = getBufferStrategy();
		if(bufferStrategy == null){
			createBufferStrategy(2);
			return;
		}
		
		Graphics g = bufferStrategy.getDrawGraphics();
		
		((Graphics2D)g).scale(scaleWidth, scaleHeight);
		
		render(g);
		
		g.dispose();
		bufferStrategy.show();
		
	}
	
	public JFrame getDefaultJFrame(){
		JFrame result = new JFrame();
		result.setSize(getScreenWidth(), getScreenHeight());
		result.setIgnoreRepaint(true);
		result.setAutoRequestFocus(true);
		
		try{
			UIManager.setLookAndFeel(UIManager.getLookAndFeel());
		}catch(UnsupportedLookAndFeelException e){
			//TODO
		}
		
		result.setLocationRelativeTo(null);
		result.setUndecorated(true);
		return result;
	}
	
	public void setJFrame(JFrame frame){
		if(this.frame != null) this.frame.dispose();
		this.frame = frame;
		this.setSize(this.frame.getWidth(), this.frame.getHeight());
		this.frame.add(this);
		this.frame.pack();
		this.createBufferStrategy(2);
		initInput();
		this.requestFocus();
	}
	
	private void initInput(){
		//TODO
	}
	
	
	public void render(Graphics g){
		//TODO
	}
	
	public void update(){
		//TODO
	}
	
	public void toggleFullScreen(){
		frame.setVisible(false);
		if(!isFullScreen && graphicsDevice.isFullScreenSupported()){
			isFullScreen = true;
			fullScreenMode();
		}
		else {
			isFullScreen = false;
			windowMode();
		}
		
		scaleWidth = (frame.getWidth() / screen_width);
		scaleHeight = (frame.getHeight() / screen_height);
		
		frame.setVisible(true);
	}
	
	private void fullScreenMode(){
		frame.dispose();
		
		//TODO
		if(os == OS.WINDOWS){
			
		}else if(os == OS.MAC){
			
		}else if(os == OS.LINUX){
			
		}else{
			
		}
		
		frame.setLocationRelativeTo(null);
	}
	
	private void windowMode(){
		graphicsDevice.setFullScreenWindow(null);
		frame.dispose();
		setJFrame(getDefaultJFrame());
	}
	
	public int getScreenWidth() { return screen_width; }
	
	public void setScreenWidth(int screenWidth) { screen_width = screenWidth; }
	
	public int getScreenHeight() { return screen_height; }
	
	public void setScreenHeight(int screenHeight) { screen_height = screenHeight; }
	
	public void setPerferredUpdatesPerSecond(int updates) { preferredUpdatesPerSecond = updates; }

	public int getCurrentUpdatesPerSecond() { return currentUpdatesPerSecond; }

	public void setCurrentUpdatesPerSecond(int updates) { this.currentUpdatesPerSecond = updates; }

	public int getCurrentFramesPerSecond() { return currentFramesPerSecond; }

	public void setCurrentFramesPerSecond(int frames) { this.currentFramesPerSecond = frames; }
	
	public float getScaleWidth() { return scaleWidth; }
	
	public void setScaleWidth(float scale) { scaleWidth = scale; }
	
	public float getScaleHeight() { return scaleHeight; }
	
	public void setScaleHeight(float scale) { scaleHeight = scale; } 
	
}
