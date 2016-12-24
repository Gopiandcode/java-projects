public class Square extends Rectangle{
	public Square() {}
	public Square(double size) {super(size, size);}

	public void setSide(double side) {super.setWidth(side); super.setLength(side);}

	@Override
	public void setWidth(double side) {this.setSide(side);}
	@Override
	public void setLength(double side) {this.setSide(side);}
	@Override
	public String toString() {return "Square[side=" + super.getWidth() + ",Rectangle" + super.toString() + "]";}
}