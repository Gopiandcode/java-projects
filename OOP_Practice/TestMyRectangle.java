public class TestMyRectangle {
	public static void main(String[] args) {
		MyPoint a = new MyPoint(0,0);
		MyPoint b = new MyPoint(10,10);
		MyRectangle test1 = new MyRectangle(a,b);
		MyRectangle test2 = new MyRectangle(1,1,11,11);
		System.out.println(test1);
		System.out.println(test2);
		
		System.out.println(test1.getArea() + " is the area of test1, the area of test2 is " + test2.getArea());
		System.out.println(test1.getPerimeter() + " is the perimeter of test1, the perimeter of test2 is " + test2.getPerimeter());
		
	}


}