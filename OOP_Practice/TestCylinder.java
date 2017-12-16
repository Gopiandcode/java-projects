public class TestCylinder {
	public static void main(String[] args) {
		Cylinder test1 = new Cylinder(10.0f);
		Cylinder test2 = new Cylinder(10.0f, 1.0f);
		System.out.println("Test1's height is: " + test1.getHeight() + " and it's area is " + test1.getVolume() + " and it's surface area is: " + test1.getArea() + "." );
		System.out.println(test2);
	}

}