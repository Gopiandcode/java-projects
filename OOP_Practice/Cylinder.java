public class Cylinder extends circle {
	private double height = 1.0;

	public Cylinder() {super();}
	public Cylinder(double height) {super(); this.height = height;}
	public Cylinder(double radius, double height) {super(radius); this.height = height;}
	public double getHeight() {return this.height;}
	public double getVolume() {return super.getArea()*height;}
	@Override
	public double getArea() {return 2 * Math.PI * this.getRadius() + 2 * super.getArea();}
	
	public String toString() {return "Cylinder[height=" + this.height + ",radius=" + this.getRadius() + "]";}
}