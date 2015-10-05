package jgame.UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import jgame.util.Vector2I;

public class UILabel extends UIComponent{

	private String text = "";
	
	private Font font = new Font("Times New Roman", Font.PLAIN, 12);
	
	private Color color = Color.white;
	
	public UILabel(Vector2I position){
		super(position);
	}
	
	public UILabel(Vector2I position, String text){
		super(position);
		this.text = text;
	}
	
	public UILabel(Vector2I position, String text, Color color){
		super(position);
		this.text = text;
		this.color = color;
	}
	
	public UILabel(Vector2I position, String text, Font font){
		super(position);
		this.text = text;
		this.font = font;
	}
	
	public UILabel(Vector2I position, String text, Color color, Font font){
		super(position);
		this.text = text;
		this.color = color;
		this.font = font;
	}

	@Override
	public void update() {
		
	}

	@Override
	public void render(Graphics g) {
		if(show){
			g.setColor(color);
			g.setFont(font);
			g.drawString(text, position.x, position.y);
		}
	}
	
	public void setText(String text){ this.text = text; }
	
	public Font getFont(){ return font; }
	
	public void setFont(Font font){ this.font = font; }
	
	public Color getColor(){ return color; }
	
	public void setColor(Color color){ this.color = color; }
	
}
