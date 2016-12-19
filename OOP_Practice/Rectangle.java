public class Rectangle {
	private float length = 1.0f;
	private float width = 1.0f;

	public Rectangle() {}
	public Rectangle(float length, float width) {
		this.length = length;
		this.width = width;
	}
	public float getLength() {
		return this.length;
	}
	public void setLength(float length) {
		this.length = length;
		return;
	}
	public float getWidth() {
		return this.width;
	}
	public void setWidth(float width) {
		this.width = width;
		return;
	}
	public double getArea() {
		return ((double)this.length) * ((double)this.width);
	}
	public double getPerimeter() {
		return (double) 2.0 * (this.length + this.width);
	}
	public String toString() {
		return "Rectangle[width=" + this.width + ",length="  + this.length + "]";
	}
}