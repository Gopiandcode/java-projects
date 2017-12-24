public class MovablePoint extends Point implements Movable{
	float xSpeed = 0.0f;
	float ySpeed = 0.0f;
	
	public MovablePoint(float x, float y, float xSpeed, float ySpeed) {super(x,y); this.xSpeed = xSpeed; this.ySpeed = ySpeed;}
	public MovablePoint(float xSpeed, float ySpeed) {super(); this.xSpeed = xSpeed; this.ySpeed = ySpeed;}
	public MovablePoint() {}

	public float getXSpeed() {return this.xSpeed;}
	public void setXSpeed(float xSpeed) {this.xSpeed = xSpeed; return;}
	
	public float getYSpeed() {return this.ySpeed;}
	public void setYSpeed(float ySpeed) {this.ySpeed = ySpeed; return;}
	
	public float[] getSpeed() {return new float[]{this.xSpeed, this.ySpeed};}
	public void setSpeed(float[] Speed) {this.xSpeed  = Speed[0]; this.ySpeed = Speed[1]; return;}
	public void setSpeed(float xSpeed, float ySpeed) {this.xSpeed = xSpeed; this.ySpeed = ySpeed;}
	
	public void move() {this.setX(this.getX() + this.xSpeed); this.setY(this.getY()+this.ySpeed);}	

	public void moveUp() {super.setY(super.getY() + 1); return;}
	public void moveDown() {super.setY(super.getY() - 1); return;}
	public void moveLeft() {super.setX(super.getX() - 1); return;}
	public void moveRight() {super.setX(super.getX() + 1); return;}

	@Override
	public String toString() {return super.toString()  + ",speed=(" + this.xSpeed + "," + this.ySpeed + ")";}	
}