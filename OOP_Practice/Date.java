public class Date {
	private int day;
	private int month;
	private int year;

	public Date(int day, int month, int year) {this.day = day; this.month = month; this.year = year;}
	public int getDay() {return this.day;}
	public int getMonth() {return this.month;}
	public int getYear() {return this.year;}
	
	public void setDay(int day) {this.day = day; return;}
	public void setMonth(int month) {this.month = month; return;}
	public void setYear(int year) {this.year = year; return;}
	public void setDate(int day, int month, int year) {this.day = day; this.month = month; this.year = year; return;}
	
	public String toString() {
		return this.day + "/" + this.month + "/" + this.year;
	}	

}