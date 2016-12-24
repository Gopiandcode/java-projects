public class TestMovablePoint {
	public static void main(String[] args) {
		MovablePoint A = new MovablePoint();
		MovablePoint B = new MovablePoint(1.0f,2.0f);
		MovablePoint C = new MovablePoint(1.0f,2.0f, 3.0f, 4.0f);
		MovableCircle D = new MovableCircle(1,2,3,4,5);
		for(int i = 0; i<10; i++) {
			A.moveUp();
			B.moveLeft();
			C.moveRight();
			D.moveDown();
			System.out.println("Point A: " + A.toString());
			System.out.println("Point B: " + B.toString());
			System.out.println("Point C: " + C.toString());	
			System.out.println("Circle D: " + D.toString());
		}
	}


}