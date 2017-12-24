public class Point3D extends Point2D {
	private float z = 0.0f;
	
	public Point3D(float x, float y, float z) {super(x,y); this.z = z;}
	public Point3D(){}
	
	public float getZ() {return this.z;}
	public void setZ(float z) {this.z = z; return;}

	public float[] getXYZ() {return new float[] {super.getX(), super.getY(), this.getZ()};}
	public void setXYZ(float x, float y, float z) {super.setX(x); super.setY(y); this.setZ(z);}
	public void setXYZ(float[] xyz) {super.setX(xyz[0]); super.setY(xyz[1]); this.setZ(xyz[2]);}

	public String toString() {return "(" + super.getX() + "," + super.getY() + "," + this.getZ() + ")";}
}