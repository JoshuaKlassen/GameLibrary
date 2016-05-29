package jgame.networking;

class LogItem {

	private boolean isError;
	
	private String message;
	
	private int year;
	private int month;
	private int day;
	private int hour;
	private int minute;
	private int second;
	private int millis;
	
	public LogItem(boolean isError, String message){
		this.isError = isError;
		this.message = message;
	}
	
	public boolean isError(){ return isError; }
	
	public String message(){ return message; }

	public int getYear() { return year; }

	public void setYear(int year) { this.year = year; }

	public int getMonth() { return month; }

	public void setMonth(int month) { this.month = month; }

	public int getDay() { return day; }

	public void setDay(int day) { this.day = day; }

	public int getHour() { return hour; }

	public void setHour(int hour) { this.hour = hour; }

	public int getMinute() { return minute; }

	public void setMinute(int minute) { this.minute = minute; }

	public int getSecond() { return second; }

	public void setSecond(int second) { this.second = second; }

	public int getMillis() { return millis; }

	public void setMillis(int millis) { this.millis = millis; }
	
}
