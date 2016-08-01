package jgame.networking;

import java.util.ArrayList;
import java.util.Calendar;

public class ConnectionLog {

	private ArrayList<LogItem> log;
	
	private Calendar calendar;
	
	public ConnectionLog(){
		log = new ArrayList<LogItem>();
		calendar = Calendar.getInstance();
	}
	
	public void appendToLog(boolean isError, String message){
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int hour = calendar.get(Calendar.HOUR);
		int minute = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);
		int millis = calendar.get(Calendar.MILLISECOND);
		
		LogItem item = new LogItem(isError, message);
		item.setYear(year);
		item.setMonth(month);
		item.setDay(day);
		item.setHour(hour);
		item.setMinute(minute);
		item.setSecond(second);
		item.setMillis(millis);
		
		log.add(item);
	}
	
	public void printLog(){
		LogItem currentItem = null;
		for(int i = 0; i < log.size(); i ++){
			currentItem = log.get(i);
			if(currentItem.isError()){
				System.err.printf("[%d-%02d-%02d %02d:%02d:%02d.%03d] ", 
						currentItem.getYear(), 
						currentItem.getMonth(), 
						currentItem.getDay(), 
						currentItem.getHour(), 
						currentItem.getMinute(), 
						currentItem.getSecond(), 
						currentItem.getMillis());
				System.err.println(currentItem.message());
			}else{
				System.out.printf("[%d-%02d-%02d %02d:%02d:%02d.%03d] ", 
						currentItem.getYear(), 
						currentItem.getMonth(), 
						currentItem.getDay(), 
						currentItem.getHour(), 
						currentItem.getMinute(), 
						currentItem.getSecond(), 
						currentItem.getMillis());
				System.out.println(currentItem.message());
			}
		}
	}
	
	public void clear(){ log.clear(); }
}