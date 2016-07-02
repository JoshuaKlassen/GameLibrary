package jgame.util;

import java.util.ArrayList;

public class SettingsList extends JSerializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4542835314941811422L;

	private ArrayList<Setting> settings;
	
	public SettingsList(){
		settings = new ArrayList<Setting>();
	}
	
	public Setting addSetting(String name, Object value){
		Setting result = new Setting(name, value);
		settings.add(result);
		return result;
	}
	
	public Setting getSetting(String name){
		Setting result = null;
		Setting currentSetting;
		
		for(int i = 0; result == null && i < settings.size(); i ++){
			currentSetting = settings.get(i);
			if(currentSetting.getName().equals(name)){
				result = currentSetting;
			}
		}
		
		return result;
	}
	
	public String toString(){
		return settings.toString();
	}
	
	@Override
	protected void init() {
	}
}