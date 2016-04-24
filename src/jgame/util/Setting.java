package jgame.util;

public class Setting extends JSerializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3297923471174041652L;

	private String name;
	private Object currentValue;
	private Object defaultValue;
	
	public Setting(String name, Object defaultValue){
		this.name = name;
		this.defaultValue = defaultValue;
		currentValue = defaultValue;
	}
	
	
	public void reset(){
		currentValue = defaultValue;
	}
	
	public Object getValue(){
		return currentValue;
	}
	
	public void setValue(Object value){
		currentValue = value;
	}

	public Object getDefaultValue(){
		return defaultValue;
	}
	
	public String getName(){
		return name;
	}
	
	public String toString(){
		return name + "=" + currentValue;
	}
	
	@Override
	protected void init() {
		
	}

}
