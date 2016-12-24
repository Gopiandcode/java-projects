public class TestMyCircle {
	public static void main(String[] args) {
		MyPoint p1 =  new MyPoint(3,4);
		MyPoint p2 = new MyPoint(6,8);
		MyCircle c1 = new MyCircle(p1, 10);
		MyCircle c2 = new MyCircle(p2, 10);
		System.out.println(c1);
		System.out.println("C2's Center is" + c2.getCenter().toString() + " and it's radius is " + c2.getRadius() + ".");
		System.out.println(c2);
		System.out.println("The distance between c1 and c2 is " + c2.distance(c1));
		c2.setCenter(10,20);
		System.out.println(c2);
		c2.setCenterX(1);
		c2.setCenterY(2);
		System.out.println(c2);





	}
}