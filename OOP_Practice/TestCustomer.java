public class TestCustomer {
	public static void main(String[] args) {
		Customer test1 = new Customer(0,"Test1",10);
		System.out.println(test1);
		System.out.println(test1.toString() + "'s id is " + test1.getID() + " and his name is " + test1.getName() + " and his discount is " + test1.getDiscount() + ".");
		test1.setDiscount(20);
		System.out.println(test1.toString() + "'s discount is now " + test1.getDiscount());
	}
}