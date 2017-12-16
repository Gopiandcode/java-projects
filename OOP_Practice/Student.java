public class Student extends Person{
	private String program;
	private int year;
	private double fee;

	public Student (String name, String address, String program, int year, double fee) {super(name, address);this.program = program;this.year = year;this.fee = fee;}
	
	public String getProgram() {return this.program;}
	public void setProgram(String program) {this.program = program; return;}
	
	public int getYear() {return this.year;}
	public void setYear(int year) {this.year = year; return;}
	
	public double getFee() {return this.fee;}
	public void setFee(double fee) {this.fee = fee; return;}
	
	@Override
	public String toString() {return "Student[" + super.toString() + ",program=" + this.program + ",year=" + this.year + ",fee=" + this.fee + "]";}
}