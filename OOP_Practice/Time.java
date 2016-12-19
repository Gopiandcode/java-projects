public class Time {
	private int hour;
	private int minute;
	private int second;
	
	public Time(int hour, int minute, int second) {this.hour = hour; this.minute = minute; this.second =second; return;}
	public int getHour() {return this.hour;}
	public int getMinute() {return this.minute;}
	public int getSecond() {return this.second;}
	
	public void setHour(int hour) {this.hour = hour; return;}
	public void setMinute(int minute) {this.minute = minute; return;}
	public void setSecond(int second) {this.second = second; return;}
	
	public void setTime(int hour, int minute, int second){this.hour = hour; this.minute = minute; this.second = second; return;}
	
	public String toString() { return this.hour + ":" + this.minute + ":" + this.second;}
	public Time nextSecond() { 
		this.second += 1;
		if(this.second > 60) {this.second -=60; this.minute += 1;}
		if(this.minute > 60 ){ this.minute -=60; this.hour += 1;}
		return this;
	}
	public Time previousSecond() {
		this.second -= 1;
		if(this.second < 0) {this.second += 60; this.minute -= 1;}
		if(this.minute < 0) {this.minute += 60; this.hour -= 1; }
		return this;
	}
}