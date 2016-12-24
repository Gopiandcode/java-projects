public class Point2D {
	private float x = 0.0f;
	private float y = 0.0f;

	public Point2D(float x, float y) {this.x = x;this.y = y;}
	public Point2D() {}
	public float getX() {return this.x;}
	public float getY() {return this.y;}
	
	public void setX(float x) {this.x = x; return;}
	public void setY(float y) {this.y = y; return;}

	public void setXY(float x, float y) {this.x = x; this.y = y; return;}
	public void setXY(float[] xy) {this.x = xy[0]; this.y = xy[1]; return;}
	
	public float[] getXY() {return new float[] {this.x, this.y}; }

	public String toString() {return "(" + this.x + "," + this.y + ")"; }

}