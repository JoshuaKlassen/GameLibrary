package tests;

import jgame.game.JGame;
import jgame.game.State;
import jgame.util.SettingsList;
import jgame.util.Utility;

public class JGameTest extends JGame {

	private static final long serialVersionUID = 1L;

	private State mainMenuState;
	private State trainingState;
	
	private String TITLE = "TestGame";
	public String dataFilePath = "";
	private String settingsFileName = "/settings.dat";
	public String playerName = "Josh";
	public int randomSetting = 0;
	public SettingsList settings;
	
	public JGameTest(int screenWidth, int screenHeight) {
		super(screenWidth, screenHeight);
		super.setJFrame(getDefaultJFrame());
//		super.toggleFullScreen();
		
		System.setProperty("sun.java2d.opengl", "True");
		System.setProperty("sun.java2d.trace", "log");
		
		mainMenuState = new MainMenuState(this);
		trainingState = new TrainingState(this);
		this.transitionState(mainMenuState);
		
		dataFilePath = Utility.createDataFolder(TITLE);
		Object o = Utility.readObject(dataFilePath + settingsFileName);
		
		if(o instanceof SettingsList){
			settings = (SettingsList)o;
		}else{
			writeDefaultSettings();
		}
		
	}
	
	public void writeDefaultSettings(){
		settings = new SettingsList();
		settings.addSetting("Player Name", playerName);
		settings.addSetting("Random Setting", true);
		Utility.writeObject(settings, dataFilePath + settingsFileName);
	}
	
	public void saveSettings(){
		Utility.writeObject(settings, dataFilePath + settingsFileName);
	}
	
	public void update(){
		super.update();
	}
	
	public static void main(String[] args){
		System.setProperty("sun.java2d.opengl", "True");
		System.setProperty("sun.java2d.nodraw", "True");
		System.setProperty("sun.java2d.d3d", "False");
		
		long now = System.currentTimeMillis();
		JGameTest test = new JGameTest(400, 200);
		test.start();
		
	}

}
