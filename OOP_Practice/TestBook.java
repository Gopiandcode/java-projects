public class TestBook {
	public static void main(String[] args) {
		Author test1 = new Author("Kiran", "gopiandpost@gmail.com", 'm');
		Author test3 = new Author("Gman", "gopiandcoshow@gmail.com", 'm');
		Author[] autharr = new Author[] {test1, test3};
		Book test2 = new Book("The art of being a L337 HAXR", autharr, 1337.10);
		System.out.println(test2);
		System.out.println("Name: " + test2.getName() + ", author: " + test2.getAuthor()[0].toString() + ", Price: " + test2.getPrice() + ", qty: " + test2.getQty());
		System.out.println("Author name: " + test2.getAuthorName() + ", Author email: " + test2.getAuthorEmail() + ", Author gender: " + test2.getAuthorGender()); 
	}

}