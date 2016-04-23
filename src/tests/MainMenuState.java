package tests;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import jgame.UI.ButtonAction;
import jgame.UI.UIButton;
import jgame.UI.UIButtonList;
import jgame.UI.UILabel;
import jgame.game.INPUT_KEY;
import jgame.game.InputHandler;
import jgame.game.InputKey;
import jgame.game.JGame;
import jgame.game.State;
import jgame.graphics.Drawable;
import jgame.util.Vector2I;

public class MainMenuState extends State {

	private UILabel gameTitle;
	private String[] menuTitles = {"Single player", "Multiplayer", "Training", "Options", "Quit"};
	
	private InputKey up = new InputKey(true, INPUT_KEY.UP);
	private InputKey down = new InputKey(true, INPUT_KEY.DOWN);
	private InputKey leftClick = new InputKey(INPUT_KEY.LEFT_CLICK);
	
	private int BUTTON_WIDTH = 70;
	private int BUTTON_HEIGHT = 40;
	private int BUTTON_LIST_HEIGHT = 90;
	
	private Color buttonBackground = Color.gray;
	
	private UIButtonList menuButtons;
	
	public MainMenuState(JGame game) {
		super(game);
		
		int centreScreen = (int)(getGame().getWidth()/2 / getGame().getScaleWidth());
		
		gameTitle = new UILabel(new Vector2I(centreScreen - 40, 70), "Sandbox", Color.RED);
		
		InputHandler.add(up, down, leftClick);

		menuButtons = new UIButtonList(new Vector2I(centreScreen - BUTTON_WIDTH/2 - 10, BUTTON_LIST_HEIGHT));
		
		for(int i = 0; i < 5; i ++){
			int height = BUTTON_LIST_HEIGHT + (i*15);
			UIButton b = new UIButton(new Vector2I(centreScreen - BUTTON_WIDTH/2 - 10, height), BUTTON_WIDTH, BUTTON_HEIGHT/4);
			UILabel name = new UILabel(b.getPosition().clone(), menuTitles[i]);
			name.getPosition().x += 15;
			name.setColor(Color.blue);
			name.setFont(new Font(name.getFont().getFontName(), name.getFont().getStyle(), 9));
			name.getPosition().y += 7;
			Drawable normal = new Drawable(){
				public void render(Graphics g){
					g.setColor(buttonBackground);
					g.fillRect(b.getPosition().x, b.getPosition().y, b.getWidth(), b.getHeight());
					name.render(g);
				}
				public void update(){System.out.println("in my drawable");}
			};
			
			int highlightSize = 5;
			Drawable highlighted = new Drawable(){
				public void render(Graphics g){
					g.setColor(buttonBackground);
					g.fillRect(b.getPosition().x-highlightSize, b.getPosition().y-highlightSize, b.getWidth()+highlightSize*2, b.getHeight()+highlightSize*2);
					name.render(g);
				}
				public void update(){System.out.println("in my drawable");}
			};
			
			int pressedSize = 5;
			Drawable pressed = new Drawable(){
				public void render(Graphics g){
					g.setColor(buttonBackground);
					g.fillRect(b.getPosition().x+pressedSize, b.getPosition().y+pressedSize, b.getWidth()-pressedSize*2, b.getHeight()-pressedSize*2);
					name.render(g);
				}
				public void update(){System.out.println("in my drawable");}
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
				getGame().transitionState(new GameState(getGame()));
			}
		};
		startButton.setAction(singlePlayerAction);
		
		//last button
		UIButton exitButton = menuButtons.getButtons().get(menuButtons.getButtons().size()-1);
		ButtonAction exitAction = new ButtonAction(){
			public void doAction(){
				getGame().stop();
			}
		};
		exitButton.setAction(exitAction);
		
		
	}

	@Override
	public void render(Graphics g) {
		gameTitle.render(g);
		
		
		menuButtons.render(g);
		
	}

	@Override
	public void update() {
		gameTitle.update();
		
		menuButtons.update();

	}
}
