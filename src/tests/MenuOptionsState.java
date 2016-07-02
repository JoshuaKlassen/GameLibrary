package tests;

import java.awt.Color;

import jgame.UI.ButtonAction;
import jgame.UI.UIButton;
import jgame.UI.UILabel;
import jgame.UI.UITextField;
import jgame.game.INPUT_KEY;
import jgame.game.InputHandler;
import jgame.game.InputKey;
import jgame.game.State;
import jgame.graphics.IRenderable;
import jgame.graphics.JGraphics;
import jgame.util.Vector2;

public class MenuOptionsState extends State{

	private Vector2 gotoMenuButtonPosition;
	private int gotoMenuButtonWidth = 50;
	private int gotoMenuButtonHeight = 20;
	private int gotoMenuButtonHighlight = 10;
	
	private Color gotoMenuButtonColor = Color.green;
	private UIButton gotoMenuButton;
	
	private UITextField textField;
	
	private InputKey leftClick = new InputKey(INPUT_KEY.LEFT_CLICK);
	
	private InputKey escapeKey = new InputKey(INPUT_KEY.VK_ESCAPE);
	
	private InputKey enterKey = new InputKey(INPUT_KEY.VK_ENTER);
	
	UILabel temp = new UILabel(new Vector2(50, 10), "test");
	
	public MenuOptionsState(JGameTest game) {
		super(game);
		
		InputHandler.add(escapeKey, leftClick, enterKey);

		gotoMenuButtonPosition = new Vector2(10, (int)(getGame().getHeight() / getGame().getScaleHeight()) - gotoMenuButtonHeight - 10);
		gotoMenuButton = new UIButton(gotoMenuButtonPosition, gotoMenuButtonWidth, gotoMenuButtonHeight);
		
		UILabel gotoMenuButtonLabel = new UILabel(Vector2.add(gotoMenuButtonPosition, new Vector2(5, 5)), "Go back", Color.black);
		
		IRenderable gotoMenuButtonNormalState = new IRenderable(){
			public void render(JGraphics g){
				g.setColor(gotoMenuButtonColor);
				g.fillRect(gotoMenuButton.getPosition(), gotoMenuButton.getWidth(), gotoMenuButton.getHeight());
				gotoMenuButtonLabel.render(g);
			}
		};
		
		IRenderable gotoMenuButtonHighlightState = new IRenderable(){
			public void render(JGraphics g){
				g.setColor(gotoMenuButtonColor);
				g.fillRect((int)gotoMenuButton.getPosition().x - gotoMenuButtonHighlight, (int)gotoMenuButton.getPosition().y - gotoMenuButtonHighlight, gotoMenuButton.getWidth()+gotoMenuButtonHighlight*2, gotoMenuButton.getHeight()+gotoMenuButtonHighlight*2);
				gotoMenuButtonLabel.render(g);
			}
		};
		
		IRenderable gotoMenuButtonPressedState = new IRenderable(){
			public void render(JGraphics g){
				g.setColor(gotoMenuButtonColor);
				g.fillRect(gotoMenuButton.getPosition(), gotoMenuButton.getWidth()-gotoMenuButtonHighlight/2, gotoMenuButton.getHeight()-gotoMenuButtonHighlight/2);
				gotoMenuButtonLabel.render(g);
			}
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
		
		textField = new UITextField(new Vector2(10, 10), game.settings.getSetting("Player Name").getValue().toString());
		
		
	}

	@Override
	public void render(JGraphics g) {
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