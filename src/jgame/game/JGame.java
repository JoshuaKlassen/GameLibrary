package jgame.game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import jgame.util.OS;
import jgame.util.Time;
import jgame.util.Utility;
import jgame.util.Vector2I;

//TODO Document

/**
 * Copyright notice
 * Do not distribute
 * Date: October 2, 2015
 * @author Joshua Klassen
 */

public abstract class JGame extends Canvas implements Runnable{
	
	//Auto-generated
	private static final long serialVersionUID = -5429375465168456291L;
	
	//The os
	private OS os = Utility.getOperatingSystem();
	
	//The screen dimensions
	private int screen_width;
	private int screen_height;
	
	//if the game is running
	private boolean running = false;
	
	//the preferred updates per second by default is 60
	private int preferredUpdatesPerSecond = 60;
	
	//the current frames and updates per second
	private int currentUpdatesPerSecond;
	private int currentFramesPerSecond;
	
	//the input handler
	//private static InputHandler inputHandler = new InputHandler();
	
	//the screen
	private JFrame frame;
	
	//if the game is in full screen
	private boolean isFullScreen = false;
	
	//the scaling for the screen
	private float scaleWidth = 1.0f;
	private float scaleHeight = 1.0f;
	
	//the thread for the game
	private Thread gameThread;
	
	//the buffer strategy
	private BufferStrategy bufferStrategy;
	
	//screen shot location, name, and in progress
	private String screenshotLocation;
	private String screenshotName;
	private boolean screenshotInProgress = false;
	
	//the screen capture itself
	private BufferedImage screenCapture;
	
	//the graphics device for full screen
	private GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
	private GraphicsDevice graphicsDevice = graphicsEnvironment.getDefaultScreenDevice();
	
	private State currentState;
	
	/**
	 * Constructor for the JGame object.
	 * <br/> Takes a screen width and a screen height as parameters.
	 * @param screenWidth
	 * @param screenHeight
	 */
	public JGame(int screenWidth, int screenHeight){
		screen_width = screenWidth;
		screen_height = screenHeight;
	}
	
	/**
	 * Starts the game thread.
	 */
	public synchronized void start(){
		if(!running){
			if(frame == null) setJFrame(getDefaultJFrame());
			running = true;
			gameThread = new Thread(this, "Main thread");
			gameThread.start();
			frame.setVisible(true);
		}
	}
	
	/**
	 * Stops the game thread and terminates the program.
	 */
	public synchronized void stop(){
		running = false;
		frame.dispose();
		System.exit(0);
	}
	
	/**
	 * The run method of the game thread.
	 */
	@Override
	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / preferredUpdatesPerSecond;
		double delta = 0;
		
		int updates = 0;
		int frames  = 0;
		
		Time.init();
		
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
	
	/**
	 * Renders the screen and handles scaling.
	 */
	private void renderScreen(){
		bufferStrategy = getBufferStrategy();
		if(bufferStrategy == null){
			createBufferStrategy(2);
			return;
		}
		
		Graphics g = bufferStrategy.getDrawGraphics();
		
		g.setColor(Color.black);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		((Graphics2D)g).scale(scaleWidth, scaleHeight);
		
		render(g);
		
		if(screenshotInProgress){
			screenCapture = new BufferedImage(frame.getWidth(), frame.getHeight(), BufferedImage.TYPE_INT_RGB);
			Graphics screenCaptureGraphics = screenCapture.getGraphics();
			((Graphics2D)screenCaptureGraphics).scale(scaleWidth, scaleHeight);
			render(screenCaptureGraphics);
			saveScreenshot();
			screenshotInProgress = false;
		}
		
		g.dispose();
		bufferStrategy.show();
		
	}
	
	/**
	 * The render method.
	 * Uses g to render to the screen.
	 * @param g
	 */
	public void render(Graphics g){
		if(currentState != null){
			currentState.render(g);
		}
	}
	
	/**
	 * The update method.
	 * Handles all of the updates.
	 */
	public void update(){
		if(currentState != null){
			currentState.update();
		}
	}
	
	public void transitionState(State nextState){
		currentState = nextState;
		System.gc();
	}
	
	/**
	 * Toggles the screen between windowed mode and full screen mode.
	 */
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
		this.requestFocus();
	}
	
	/**
	 * Tries to put the screen in full screen mode.
	 */
	private void fullScreenMode(){
		
		if(os == OS.OTHER){
			//TODO add error system
		}else{
			frame.dispose();
			if(os == OS.WINDOWS){
				fullScreenModeWindows();
			}else if(os == OS.MAC){
				fullScreenModeMac();
			}else if(os == OS.LINUX){
				fullScreenModeLinux();
			}
			
		}
		
		frame.setLocationRelativeTo(null);
	}
	
	/**
	 * Gets called if the OS is Windows.
	 * Puts the screen in full screen mode.
	 */
	private void fullScreenModeWindows(){
		graphicsDevice.setFullScreenWindow(frame);
	}
	
	/**
	 * Gets called if the OS is Mac.
	 * Puts the screen in full screen mode.
	 */
	private void fullScreenModeMac(){
		setJFrame(getDefaultJFrame());
		frame.setSize(graphicsEnvironment.getMaximumWindowBounds().width, graphicsEnvironment.getMaximumWindowBounds().height);
	}
	
	/**
	 * Gets called if the OS is Linux.
	 * Puts the screen in full screen mode.
	 */
	private void fullScreenModeLinux(){
		//TODO handle full screen in Linux
	}
	
	/**
	 * Puts the screen in windowed mode.
	 */
	private void windowMode(){
		graphicsDevice.setFullScreenWindow(null);
		frame.dispose();
		setJFrame(getDefaultJFrame());
	}
	
	/**
	 * Initializes the input handler.
	 */
	private void initInput(){ 
		InputHandler.init(this);
		this.addKeyListener(InputHandler.getInstance());
		this.addMouseListener(InputHandler.getInstance());
		this.addMouseMotionListener(InputHandler.getInstance());
		this.addMouseWheelListener(InputHandler.getInstance());
		this.requestFocus();
	}
	
	public Vector2I getScaledMousePosition(){
		Vector2I mousePosition = InputHandler.getMousePosition();
		return new Vector2I((int)(mousePosition.x / scaleWidth), (int)(mousePosition.y / scaleHeight));
	}
	
	/**
	 * Generates a JFrame.
	 * @return a generated JFrame
	 */
	public JFrame getDefaultJFrame(){
		JFrame result = new JFrame();
		result.setSize(screen_width, screen_height);
		result.setIgnoreRepaint(true);
		result.setAutoRequestFocus(true);
		
		try{
			UIManager.setLookAndFeel(UIManager.getLookAndFeel());
		}catch(UnsupportedLookAndFeelException e){
			//TODO add error system
		}
		
		result.setLocationRelativeTo(null);
		result.setUndecorated(true);
		return result;
	}
	
	/**
	 * Set the JFrame for the game window.
	 * @param frame
	 */
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
	
	/**
	 * Returns the screen.
	 * @return frame
	 */
	public JFrame getFrame(){ return frame; }
	
	/**
	 * Returns true if the screen is in full screen mode, returns false otherwise.
	 * @return is the screen in full screen mode
	 */
	public boolean isFullScreen() { return isFullScreen; }
	
	/**
	 * Hide the mouse cursor on the screen
	 */
	public void hideCursor(){
		Cursor c = Toolkit.getDefaultToolkit().createCustomCursor(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB), new Point(0, 0), "");
		this.setCursor(c);
	}
	
	/**
	 * Show the mouse cursor on the screen
	 */
	public void showCursor(){
		this.setCursor(Cursor.getDefaultCursor());
	}
	
	/**
	 * Sets the preferred updates per second for the game.
	 * <br/>Will have no effect if {@link #start() start} has been called.
	 * @param updatesPerSecond
	 */
	public void setPerferredUpdatesPerSecond(int updatesPerSecond) { preferredUpdatesPerSecond = updatesPerSecond; }

	/**
	 * Returns the current updates per second.
	 * @return updates per second
	 */
	public int getCurrentUpdatesPerSecond() { return currentUpdatesPerSecond; }

	/**
	 * Returns the current frames per second.
	 * @return frames per second
	 */
	public int getCurrentFramesPerSecond() { return currentFramesPerSecond; }
	
	/**
	 * Returns the screens scale width.
	 * @return scale width
	 */
	public float getScaleWidth() { return scaleWidth; }
	
	/**
	 * Set the scale width of the screen.
	 * @param scale
	 */
	public void setScaleWidth(float scale) { scaleWidth = scale; }
	
	/**
	 * Returns the screens scale height.
	 * @return scale height
	 */
	public float getScaleHeight() { return scaleHeight; }
	
	/**
	 * Set the scale height of the screen.
	 * @param scale
	 */
	public void setScaleHeight(float scale) { scaleHeight = scale; } 
	
	/**
	 * Sets the default screen shot save location to the location given.
	 * @param location
	 */
	public void setScreenshotSaveLocation(String location) { screenshotLocation = location; }
	
	/**
	 * Takes a screen shot of the game and saves it.
	 * <br/>Can specify the location for the screen shot by calling
	 * <br/> {@link #setScreenshotSaveLocation(String) setScreenshotSaveLocation}.
	 * @return screen shot name
	 */
	public String screenshot(){ 
		screenshotName = new String(new Timestamp(new Date().getTime()) + "screenshot.png").replaceAll(":", "_");
		screenshotInProgress = true; 
		return screenshotName;
	}
	
	/**
	 * Saves the screen shot image.
	 */
	private void saveScreenshot() {
		if(screenshotLocation == null) {
			screenshotLocation = System.getProperty("user.home");
			if(os == OS.WINDOWS) screenshotLocation += "\\Desktop";
			else if(os == OS.MAC) screenshotLocation += "/Desktop";
			else if(os == OS.LINUX); //TODO find home directory on Linux
		}
		
		File file = new File(screenshotLocation, screenshotName);
		if(!file.exists())
			try {
				file.createNewFile();
				ImageIO.write(screenCapture, "png", file);
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
}
