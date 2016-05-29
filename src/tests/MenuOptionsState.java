package tests;

import java.awt.Color;
import java.awt.Graphics;

import jgame.UI.ButtonAction;
import jgame.UI.UIButton;
import jgame.UI.UILabel;
import jgame.UI.UITextField;
import jgame.game.INPUT_KEY;
import jgame.game.InputHandler;
import jgame.game.InputKey;
import jgame.game.State;
import jgame.graphics.Drawable;
import jgame.util.Vector2I;

public class MenuOptionsState extends State{

	private Vector2I gotoMenuButtonPosition;
	private int gotoMenuButtonWidth = 50;
	private int gotoMenuButtonHeight = 20;
	private int gotoMenuButtonHighlight = 10;
	
	private Color gotoMenuButtonColor = Color.green;
	private UIButton gotoMenuButton;
	
	private UITextField textField;
	
	private InputKey leftClick = new InputKey(INPUT_KEY.LEFT_CLICK);
	
	private InputKey escapeKey = new InputKey(INPUT_KEY.VK_ESCAPE);
	
	private InputKey enterKey = new InputKey(INPUT_KEY.VK_ENTER);
	
	UILabel temp = new UILabel(new Vector2I(50, 10), "test");
	
	public MenuOptionsState(JGameTest game) {
		super(game);
		
		InputHandler.add(escapeKey, leftClick, enterKey);

		gotoMenuButtonPosition = new Vector2I(10, (int)(getGame().getHeight() / getGame().getScaleHeight()) - gotoMenuButtonHeight - 10);
		gotoMenuButton = new UIButton(gotoMenuButtonPosition, gotoMenuButtonWidth, gotoMenuButtonHeight);
		
		UILabel gotoMenuButtonLabel = new UILabel(Vector2I.add(gotoMenuButtonPosition, new Vector2I(5, 5)), "Go back", Color.black);
		
		Drawable gotoMenuButtonNormalState = new Drawable(){
			public void render(Graphics g){
				g.setColor(gotoMenuButtonColor);
				g.fillRect(gotoMenuButton.getPosition().x, gotoMenuButton.getPosition().y, gotoMenuButton.getWidth(), gotoMenuButton.getHeight());
				gotoMenuButtonLabel.render(g);
			}
			
			public void update(){}
		};
		
		Drawable gotoMenuButtonHighlightState = new Drawable(){
			public void render(Graphics g){
				g.setColor(gotoMenuButtonColor);
				g.fillRect(gotoMenuButton.getPosition().x - gotoMenuButtonHighlight, gotoMenuButton.getPosition().y - gotoMenuButtonHighlight, gotoMenuButton.getWidth()+gotoMenuButtonHighlight*2, gotoMenuButton.getHeight()+gotoMenuButtonHighlight*2);
				gotoMenuButtonLabel.render(g);
			}
			
			public void update(){}
		};
		
		Drawable gotoMenuButtonPressedState = new Drawable(){
			public void render(Graphics g){
				g.setColor(gotoMenuButtonColor);
				g.fillRect(gotoMenuButton.getPosition().x, gotoMenuButton.getPosition().y, gotoMenuButton.getWidth()-gotoMenuButtonHighlight/2, gotoMenuButton.getHeight()-gotoMenuButtonHighlight/2);
				gotoMenuButtonLabel.render(g);
			}
			
			public void update(){}
		};
		
		ButtonAction gotoMenuButtonAction = new ButtonAction(){
			public void doAction(){
				game.settings.getSetting("Player Name").setValue(textField.getText());;
				((JGameTest)(getGame())).saveSettings();
				getGame().transitionState(new MainMenuState((JGameTest)getGame()));
			}
		};
		
		gotoMenuButton.setNormalState(gotoMenuButtonNormalState);
		gotoMenuButton.setHighlightedState(gotoMenuButtonHighlightState);
		gotoMenuButton.setPressedState(gotoMenuButtonPressedState);
		gotoMenuButton.setAction(gotoMenuButtonAction);
		gotoMenuButton.trackMouse(true);
		gotoMenuButton.addPressKeys(leftClick);
		
		textField = new UITextField(new Vector2I(10, 10), game.settings.getSetting("Player Name").getValue().toString());
		
		
	}

	@Override
	public void render(Graphics g) {
		gotoMenuButton.render(g);
		textField.render(g);
		
		g.setColor(Color.white);
		g.drawString(textField.hasFocus() + ":" + InputHandler.getScaledMousePosition() + ":" + textField.getPosition(), 10, 60);
	}

	@Override
	public void update() {
		gotoMenuButton.update();
		if(escapeKey.isPressed()){
			getGame().transitionState(new MainMenuState((JGameTest)getGame()));
		}
		textField.update();
		
		if(textField.contains(InputHandler.getScaledMousePosition())){
			if(leftClick.isPressed()){
				textField.setFocus(true);
			}
		}
		
		if(textField.hasFocus()){
			if(enterKey.isPressed()){
				((JGameTest)(getGame())).settings.getSetting("Player Name").setValue(textField.getText());
				((JGameTest)(getGame())).saveSettings();
				textField.setFocus(false);
			}
		}
	}

}
