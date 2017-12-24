public class MyRectangle {
	private MyPoint topLeft;
	private MyPoint bottomRight;

	public MyRectangle(int x1, int y1, int x2, int y2) {this.topLeft = new MyPoint(x1, y1);this.bottomRight = new MyPoint(x2, y2);}
	public MyRectangle(MyPoint topLeft, MyPoint bottomRight) {this.topLeft = topLeft;this.bottomRight = bottomRight;}
	
	public String toString() {return "MyRectangle[topLeft=" + this.topLeft.toString() + ",bottomRight=" + this.bottomRight.toString() + "]";}
	
	public double getPerimeter(){return 2*(Math.abs(this.topLeft.getY() - this.bottomRight.getY()) + Math.abs(this.topLeft.getX() - this.bottomRight.getX()));}

	public double getArea(){return Math.abs(this.topLeft.getY() - this.bottomRight.getY())*Math.abs(this.topLeft.getX() - this.bottomRight.getX());}


}