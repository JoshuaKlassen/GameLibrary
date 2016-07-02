package tests;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import jgame.UI.ButtonAction;
import jgame.UI.UIButton;
import jgame.UI.UIButtonList;
import jgame.UI.UILabel;
import jgame.entities.Entity;
import jgame.game.INPUT_KEY;
import jgame.game.InputHandler;
import jgame.game.InputKey;
import jgame.game.State;
import jgame.graphics.Drawable;
import jgame.graphics.JGraphics;
import jgame.util.Vector2I;

public class MainMenuState extends State {

	private UILabel gameTitle;
	private String[] menuTitles = {"Single player", "Multiplayer", "Training", "Options", "Quit"};
	
	private InputKey up = new InputKey(true, INPUT_KEY.UP);
	private InputKey down = new InputKey(true, INPUT_KEY.DOWN);
	private InputKey leftClick = new InputKey(INPUT_KEY.LEFT_CLICK);
	private InputKey enter = new InputKey(true, INPUT_KEY.VK_C);
	
	private int BUTTON_WIDTH = 70;
	private int BUTTON_HEIGHT = 40;
	private int BUTTON_LIST_HEIGHT = 90;
	private int NUMBER_OF_BUTTONS = 5;
	
	private Color buttonBackground = Color.gray;
	
	private UIButtonList menuButtons;
	
	private int buttonListPosition = 0;
	
	private List<Entity> entities;
	
	public MainMenuState(JGameTest game) {
		super(game);
		
		int centreScreen = (int)(getGame().getWidth()/2 / getGame().getScaleWidth());
		
		gameTitle = new UILabel(new Vector2I(centreScreen - 40, 70), "Sandbox", Color.RED);
		
		InputHandler.add(up, down, leftClick, enter);
		menuButtons = new UIButtonList(new Vector2I(centreScreen - BUTTON_WIDTH/2 - 10, BUTTON_LIST_HEIGHT));
		
		for(int i = 0; i < NUMBER_OF_BUTTONS; i ++){
			int height = BUTTON_LIST_HEIGHT + (i*15);
			UIButton b = new UIButton(new Vector2I(centreScreen - BUTTON_WIDTH/2 - 10, height), BUTTON_WIDTH, BUTTON_HEIGHT/4);
			UILabel name = new UILabel(b.getPosition().clone(), menuTitles[i]);
			name.setColor(Color.blue);
			name.setFont(new Font(name.getFont().getFontName(), name.getFont().getStyle(), 9));
			Drawable normal = new Drawable(){
				public void render(JGraphics g){
					g.setColor(buttonBackground);
					g.fillRect(b.getPosition().x, b.getPosition().y, b.getWidth(), b.getHeight());
					name.render(g);
				}
			};
			
			int highlightSize = 5;
			Drawable highlighted = new Drawable(){
				public void render(JGraphics g){
					g.setColor(buttonBackground);
					g.fillRect(b.getPosition().x-highlightSize, b.getPosition().y-highlightSize, b.getWidth()+highlightSize*2, b.getHeight()+highlightSize*2);
					name.render(g);
				}
			};
			
			int pressedSize = 5;
			Drawable pressed = new Drawable(){
				public void render(JGraphics g){
					g.setColor(buttonBackground);
					g.fillRect(b.getPosition().x+pressedSize, b.getPosition().y+pressedSize, b.getWidth()-pressedSize*2, b.getHeight()-pressedSize*2);
					name.render(g);
				}
			};
			b.setNormalState(normal);
			b.setHighlightedState(highlighted);
			b.setPressedState(pressed);
			b.addPressKeys(leftClick);
			b.trackMouse(true);
			menuButtons.addButton(b);
		}
		
		//first button
		UIButton startButton = menuButtons.getButtons().get(0);
		ButtonAction singlePlayerAction = new ButtonAction(){
			public void doAction(){
//				entities.addAll((ParticleFactory.generateParticles(new Vector2F(50, 50), 50, 5, 200, -7f, 4f, 3f, 5f, 3, Color.RED)));

				getGame().transitionState(new GameState((JGameTest)getGame()));
			}
		};
		startButton.setAction(singlePlayerAction);
		
		UIButton optionsButton = menuButtons.getButtons().get(NUMBER_OF_BUTTONS-2);
		ButtonAction optionsAction = new ButtonAction(){
			public void doAction(){
				InputHandler.removeAll();
				getGame().transitionState(new MenuOptionsState((JGameTest)getGame()));
			}
		};
		optionsButton.setAction(optionsAction);
		
		UIButton trainingButton = menuButtons.getButtons().get(NUMBER_OF_BUTTONS-3);
		ButtonAction traningAction = new ButtonAction(){
			public void doAction(){
				InputHandler.removeAll();
				getGame().transitionState(new TrainingState((JGameTest)getGame()));
			}
		};
		trainingButton.setAction(traningAction);
		
		//last button
		UIButton exitButton = menuButtons.getButtons().get(menuButtons.getButtons().size()-1);
		ButtonAction exitAction = new ButtonAction(){
			public void doAction(){
				getGame().stop();
			}
		};
		exitButton.setAction(exitAction);
		
		entities = new ArrayList<Entity>();
	}

	@Override
	public void render(JGraphics g) {
		gameTitle.render(g);
		
		menuButtons.render(g);
		
		g.setColor(Color.white);
		g.drawString(buttonListPosition + "", 10, 10);
		g.fillRect(new Vector2I(50, 50), 3, 3);
		
		for(int i = 0; i < entities.size(); i ++){
			entities.get(i).render(g);
		}
		
	}

	@Override
	public void update() {
		gameTitle.update();
		
		for(int i = 0; i < entities.size(); i ++){
			Entity e = entities.get(i);
			e.update();
			if(!e.isAlive()){
				entities.remove(e);
			}
		}
		
		if(up.isPressed()){
			buttonListPosition --;
		} 
		if(down.isPressed()){
			buttonListPosition ++;
		}
		
		if(buttonListPosition < 0){
			buttonListPosition = NUMBER_OF_BUTTONS-1;
		}else if(buttonListPosition >= NUMBER_OF_BUTTONS){
			buttonListPosition = 0;
		}
		
		
		menuButtons.update();
		
//		UIButton highlightedButton = menuButtons.getButtons().get(buttonListPosition);
//
//		if(!highlightedButton.isHighlighted()){
//			highlightedButton.highlight();
//			for(int i = 0; i < NUMBER_OF_BUTTONS; i ++){
//				if(i != buttonListPosition)
//					menuButtons.getButtons().get(i).unhighlight();
//			}
//		}
//		
//		if(enter.isPressed()){
//			System.out.println(enter.isPressed());
//			highlightedButton.press();
//		}

	}
}
