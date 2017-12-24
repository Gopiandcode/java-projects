public class TestAuthor  {
	public static void main(String[] args) {
		Author test1 = new Author("Kiran", "Gopiandcoshow@gmail.com", 'm');
		Author test2 = new Author("J.K Rowling", "Rowling@Potter.com", 'f');
		System.out.println(test1);
		System.out.println(test2);
		test1.setEmail("gopiandpost@gmail.com");
		System.out.println(test1);
	
		System.out.println("Name: " + test1.getName() + ", Email: " + test1.getEmail() + ", gender: " + test1.getGender() );
		
	
	}


}