public class circle {
	private double radius = 1.0;
	private String color = "red";
	

	public circle() {}
	public circle(double r){
		radius = r;
	}

	public circle(double r, String c) {
		radius = r;
		color = c;
	}
	
	public double getRadius() {
		return radius;
	}

	public double getArea() 
	{
		return Math.PI * radius * radius;
	}

	public String getColor() {
		return color;
	}
	public String toString() {
	return "Circle[radius=" + this.radius + ",color=" + this.color + "]";

	}
}