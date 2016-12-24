public class MyCircle {
	private MyPoint center = new MyPoint();
	private int radius = 1;

	public MyCircle() {}
	public MyCircle(int x, int y, int radius) {this.center.setXY(new int[]{x,y}); this.radius = radius;}
	public MyCircle(MyPoint center, int radius) {this.center = center; this.radius = radius;}
	
	public int getRadius() {return this.radius;}
	public void setRadius(int radius) {this.radius = radius; return;}
	public MyPoint getCenter() {return this.center;}
	public void setCenter(MyPoint center) {this.center = center; return;}
	public void setCenter(int x, int y) {this.center.setXY(new int[]{x,y}); return;}
	public void setCenter(int[] xy) {this.center.setXY(xy); return;}
	public int getCenterX() {return this.center.getX();}
	public void setCenterX(int x) {this.center.setX(x); return;}
	public int getCenterY() {return this.center.getY();}
	public void setCenterY(int y) {this.center.setY(y); return;}
	public String toString() {return "MyCircle[radius=" + this.radius + ",center=" + this.center.toString() + "]";}
	public double getArea() {return ((double) this.radius * this.radius) * Math.PI;}
	public double getCircumference() {return ((double) this.radius) * 2 * Math.PI;}
	public double distance(MyCircle another) {return this.center.distance(another.center);}



}