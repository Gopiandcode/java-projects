public class TestMyPoint {
	public static void main(String[] args) {
		MyPoint p1 = new MyPoint(3,4);
		MyPoint p2 = new MyPoint(5,6);
		System.out.println(p1);
		System.out.println(p2);
		System.out.println(p1.distance(p2) + " should equal root(8).");
		System.out.println(p1.distance() + " should equal 5.");
		System.out.println("Point p1, x=" + p1.getX() + ", y=" + p1.getY() + ".");
		p1.setXY(1,2);
		System.out.println(p1);
		p1.setXY(p1.getXY());
		System.out.println(p1);

		MyPoint[] points = new MyPoint[10];
		for(int i = 0; i < points.length; i++) { points[i] = new MyPoint(); points[i].setXY(new int[]{i,i}); }

		for(MyPoint p : points) {System.out.println(p);}
	}

}