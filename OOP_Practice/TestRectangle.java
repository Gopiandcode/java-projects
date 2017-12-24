public class TestRectangle { 
	public static void main(String[] args) {
		Rectangle r1 = new Rectangle();
		Rectangle r2 = new Rectangle(1.0f, 20.1f);
		System.out.println("The rectangle width is " + r1.getLength() + " and the rectangle length is " + r1.getWidth() + " and it's area is " + r1.getArea() + " and it's perimeter is " + r1.getPerimeter() + ".");
		System.out.println("The rectangle width is " + r2.getLength() + " and the rectangle length is " + r2.getWidth() + " and it's area is " + r2.getArea() + " and it's perimeter is " + r2.getPerimeter() + ".");		



		System.out.println(r1);
		System.out.println(r2);
	}
}