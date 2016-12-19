public class TestCircle{

	public static void main(String[] args) {
		circle c1 = new circle();
		
		System.out.println("The circle has a radius of " + 
			c1.getRadius() + " and area of " + c1.getArea());

		circle c2 = new circle(2.0);
		System.out.println("The circle has a radius of " + 
			c2.getRadius() + " and area of " + c2.getArea());
		
		System.out.println("But because the values were initialized outside of any individual function, they are class level? but they're not abstract? so what will c1.getRadius() return? Let's find out:" + c1.getRadius() );
		System.out.println("So they are local to indivual objects. Nice. I wonder which keyword changes that?");

		circle c3 = new circle(2.0, "blue");
		System.out.println("The circle has a radius of " + 
			c3.getRadius() + " and area of " + c3.getArea() + " and *brand new* a color of " + c3.getColor());
				
	System.out.println(c1);
	System.out.println(c2);
	System.out.println(c3);
	}



}