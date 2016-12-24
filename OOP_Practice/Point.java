public class Point {
	private float x = 0.0f;
	private float y = 0.0f;
	
	public Point(float x, float y) {this.x = x; this.y = y;}
	public Point() {}

	public float getX() {return this.x;}
	public void setX(float x) {this.x = x; return;}
	
	public float getY() {return this.y;}
	public void setY(float y) {this.y = y; return;}

	public void setXY(float x, float y) {this.x = x; this.y = y; return;}
	public float[] getXY() {return new float[]{this.x, this.y};}

	public String toString() {return "(" + this.x + "," + this.y + ")";}
}