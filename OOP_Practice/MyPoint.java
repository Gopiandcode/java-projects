public class MyPoint {
	private int x = 0;
	private int y = 0;
	
	public MyPoint() {}
	public MyPoint(int x, int y) {this.x = x; this.y = y;}
	public int getX() {return this.x;}
	public int getY() {return this.y;}
	public void setX(int x) {this.x = x; return;}
	public void setY(int y) {this.y = y; return;}
	public int[] getXY() {int[] out = new int[]{this.x, this.y}; return out;}
	public void setXY(int x, int y) {this.x = x; this.y = y;}
	public void setXY(int[] xy) {this.x = xy[0]; this.y = xy[1];}
	public String toString() {return "(" + this.x + "," + this.y + ")";}
	public double distance(int x, int y) {return Math.sqrt((double)((this.x-x)*(this.x-x))+((this.y-y)*(this.y-y)));}
	public double distance(MyPoint another) {return Math.sqrt((double)(((this.x-another.x)*(this.x-another.x))+((this.y-another.y)*(this.y-another.y))));}
	public double distance() {return Math.sqrt((double)((this.x*this.x)+(this.y*this.y)));}


}