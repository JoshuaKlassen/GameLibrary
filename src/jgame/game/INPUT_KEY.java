package jgame.game;

import java.awt.event.KeyEvent;

/**
 * The INPUT_KEY enum.
 * <br/>Contains every keyboard button and mouse button on a standard machine.
 * <br/>Is used in {@link InputKey}s and an {@link InputHandler} to handle input.
 * @author Joshua Klassen
 */
public enum INPUT_KEY {
	LEFT_CLICK(1),
	MOUSE_WHEEL(2),
	RIGHT_CLICK(3),
	
	UP(KeyEvent.VK_UP),
	DOWN(KeyEvent.VK_DOWN),
	LEFT(KeyEvent.VK_LEFT),
	RIGHT(KeyEvent.VK_RIGHT),
	
	VK_A(KeyEvent.VK_A),
	VK_B(KeyEvent.VK_B),
	VK_C(KeyEvent.VK_C),
	VK_D(KeyEvent.VK_D),
	VK_E(KeyEvent.VK_E),
	VK_F(KeyEvent.VK_F),
	VK_G(KeyEvent.VK_G),
	VK_H(KeyEvent.VK_H),
	VK_I(KeyEvent.VK_I),
	VK_J(KeyEvent.VK_J),
	VK_K(KeyEvent.VK_K),
	VK_L(KeyEvent.VK_L),
	VK_M(KeyEvent.VK_M),
	VK_N(KeyEvent.VK_N),
	VK_O(KeyEvent.VK_O),
	VK_P(KeyEvent.VK_P),
	VK_Q(KeyEvent.VK_Q),
	VK_R(KeyEvent.VK_Q),
	VK_S(KeyEvent.VK_S),
	VK_T(KeyEvent.VK_T),
	VK_U(KeyEvent.VK_U),
	VK_V(KeyEvent.VK_V),
	VK_W(KeyEvent.VK_W),
	VK_X(KeyEvent.VK_X),
	VK_Y(KeyEvent.VK_Y),
	VK_Z(KeyEvent.VK_Z),
	
	VK_0(KeyEvent.VK_0),
	VK_1(KeyEvent.VK_1),
	VK_2(KeyEvent.VK_2),
	VK_3(KeyEvent.VK_3),
	VK_4(KeyEvent.VK_4),
	VK_5(KeyEvent.VK_5),
	VK_6(KeyEvent.VK_6),
	VK_7(KeyEvent.VK_7),
	VK_8(KeyEvent.VK_8),
	VK_9(KeyEvent.VK_9),
	
	VK_NUMPAD0(KeyEvent.VK_NUMPAD0),
	VK_NUMPAD1(KeyEvent.VK_NUMPAD1),
	VK_NUMPAD2(KeyEvent.VK_NUMPAD2),
	VK_NUMPAD3(KeyEvent.VK_NUMPAD3),
	VK_NUMPAD4(KeyEvent.VK_NUMPAD4),
	VK_NUMPAD5(KeyEvent.VK_NUMPAD5),
	VK_NUMPAD6(KeyEvent.VK_NUMPAD6),
	VK_NUMPAD7(KeyEvent.VK_NUMPAD7),
	VK_NUMPAD8(KeyEvent.VK_NUMPAD8),
	VK_NUMPAD9(KeyEvent.VK_NUMPAD9),
	
	VK_F1(KeyEvent.VK_F1),
	VK_F2(KeyEvent.VK_F2),
	VK_F3(KeyEvent.VK_F3),
	VK_F4(KeyEvent.VK_F4),
	VK_F5(KeyEvent.VK_F5),
	VK_F6(KeyEvent.VK_F6),
	VK_F7(KeyEvent.VK_F7),
	VK_F8(KeyEvent.VK_F8),
	VK_F9(KeyEvent.VK_F9),
	VK_F10(KeyEvent.VK_F10),
	VK_F11(KeyEvent.VK_F11),
	VK_F12(KeyEvent.VK_F12),
	
	VK_SPACE(KeyEvent.VK_SPACE),
	VK_ENTER(KeyEvent.VK_ENTER),
	VK_ESCAPE(KeyEvent.VK_ESCAPE),
	VK_SHIFT(KeyEvent.VK_SHIFT),
	VK_BACK_SPACE(KeyEvent.VK_BACK_SPACE),
	VK_CAPS_LOCK(KeyEvent.VK_CAPS_LOCK),
	VK_ALT(KeyEvent.VK_ALT),
	VK_MINUS(KeyEvent.VK_MINUS),
	VK_ADD(KeyEvent.VK_ADD),
	VK_CTRL(KeyEvent.VK_CONTROL),
	VK_DELETE(KeyEvent.VK_DELETE),
	VK_OTHER(KeyEvent.CHAR_UNDEFINED), //TODO maybe remove this
	NUMBER_OF_KEYS(-1);
	
	private final int ID;
	private boolean isPressed;
	INPUT_KEY(int ID){ this.ID = ID; }
	public int getID(){ return ID; }
	protected void press() { isPressed = true; }
	protected void release() { isPressed = false; }
	protected boolean isPressed() { return isPressed; }
	
	public String toString() { return  this.name() +  "(" + isPressed + ")"; }
}
