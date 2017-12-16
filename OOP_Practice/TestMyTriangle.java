public class TestMyTriangle {
	public static void main(String[] args) {
		MyPoint a = new MyPoint(0,0);
		MyPoint b = new MyPoint(3,0);
		MyPoint c = new MyPoint(3,4);
		MyTriangle test1 = new MyTriangle(a,b,c);
		MyTriangle test2 = new MyTriangle(2,0, 0,2, 0,-2);

		System.out.println(test1);
		System.out.println(test2);
		System.out.println("Test1 has a perimeter of " + test1.getPerimeter() + ", and has a type of " + test1.getType()); 


	}
}