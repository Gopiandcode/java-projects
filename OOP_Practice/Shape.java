public class Shape {
	private String color = "red";
	private boolean filled = true;

	public Shape() {}
	public Shape(String color, boolean filled) {this.color = color; this.filled = filled; return;}
	
	public String getColor() {return this.color;}
	public void setColor(String color) {this.color = color; return;}
	
	public boolean isFilled() {return this.filled;}
	public void setFilled(boolean filled) {this.filled = filled; return;}

	public String toString() {return "Shape[color=" + this.color + ",filled=" + this.filled + "]";}

}