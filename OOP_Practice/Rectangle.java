public class Rectangle extends Shape{
	private double width = 1.0;
	private double length = 1.0;
	
	public Rectangle() {}
	public Rectangle(double width, double length) {super(); this.width = width; this.length = length; return;}
	public Rectangle(double width, double length, String color, boolean filled) {super(color, filled); this.width = width; this.length = length; return;}

	public double getWidth() {return this.width;}
	public void setWidth(double width) {this.width = width; return;}
	public double getLength() {return this.length;}
	public void setLength(double length) {this.length = length; return;}

	public double getArea() {return this.width * this.length;}
	public double getPerimeter() {return 2*(this.width + this.length);}
	
	@Override
	public String toString() {return "Rectangle[width=" + this.width + ",length=" + this.length + ",shape=" + super.toString() + "]";}
}