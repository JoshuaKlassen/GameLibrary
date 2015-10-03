package game;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/*
 * Copyright 2015
 * Do not distribute
 * Date: October 2, 2015
 * Author: Joshua Klassen
 */

public abstract class JGame extends Canvas implements Runnable{
	
	private static final long serialVersionUID = -5429375465168456291L;
	
	private int screen_width;
	private int screen_height;
	
	private boolean running = false;
	
	private int preferredUpdatesPerSecond = 60;
	
	private JFrame frame;
	
	private GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
	private GraphicsDevice graphicsDevice = graphicsEnvironment.getDefaultScreenDevice();
	
	public JGame(int screenWidth, int screenHeight){
		screen_width = screenWidth;
		screen_height = screenHeight;
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
	
	@Override
	public void run() {
		//TODO
	}
	
	public void render(Graphics g){
		//TODO
	}
	
	public void update(){
		//TODO
	}
	
	public int getScreenWidth() { return screen_width; }
	
	public void setScreenWidth(int screenWidth) { screen_width = screenWidth; }
	
	public int getScreenHeight() { return screen_height; }
	
	public void setScreenHeight(int screenHeight) { screen_height = screenHeight; }
	
	public void setPerferredUpdatesPerSecond(int updates) { preferredUpdatesPerSecond = updates; }
	
}
