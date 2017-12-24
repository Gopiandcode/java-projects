public class TestTime {
	public static void main(String[] args) {
		Time test1 = new Time(1, 23, 1);
		System.out.println("Test1:");
		System.out.println(test1);
		Time test2 = test1.previousSecond();
		System.out.println("Test2:");		
		System.out.println(test2);
		test1.nextSecond();
		System.out.println("Test1:");
		System.out.println(test1);
		System.out.println("Test2:");
		System.out.println(test2);
		

	}


}