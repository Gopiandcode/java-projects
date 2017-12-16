public class TestPointD {
	public static void main(String[] args) {
		Point2D A = new Point2D(1.0f, 2.0f);
		Point2D B = new Point2D(3.0f, 3.0f);
		System.out.println(A);
		System.out.println(B);
		Point3D C = new Point3D(1.0f, 2.0f, 3.0f);
		float[] xyz = new float[]{1.0f, 3.0f, 1.0f};
		Point3D D = new Point3D();
		D.setXYZ(xyz);
		System.out.println(C);
		System.out.println(D);

	}


}