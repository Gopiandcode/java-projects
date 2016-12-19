public class TestAccount {
	public static void main(String[] args) {
		Account test1 = new Account("test1", "Test1account", 1000);
		Account test2 = new Account("test2", "Test2account");
		System.out.println("Attempting to remove money from test 1, new balance: " + test1.debit(100));
		System.out.println("Attempting to remove money from test 2, new balance: " + test2.debit(100));
		System.out.println(test1);
		System.out.println(test2);
		System.out.println("Sending money from test1 to test2, balance of test 1 now: " + test1.transferTo(test2, 100));
		System.out.println(test1);
		System.out.println(test2);
	}


}