public class MovableCircle implements Movable {
	private int radius;
	private MovablePoint center;
	
	public MovableCircle(int x, int y, int xSpeed, int ySpeed, int radius) {this.center = new MovablePoint(x,y, xSpeed, ySpeed); this.radius = radius;}
	public String toString() {return "MovableCircle[center=" + center.toString() + ",radius=" + this.radius + "]";}
	public void moveUp() {center.setY(center.getY() + 1);return;}
	public void moveDown() {center.setY(center.getY() - 1); return;}
	public void moveLeft() {center.moveLeft(); return;}
	public void moveRight() {center.moveRight(); return;}

}