import java.lang.StringBuilder;
public class Book  {
	private String name;
	private Author[] author;
	private double price;
	private int qty = 0;

	public Book(String name, Author author, double price) {this.name = name; this.author = new Author[] {author}; this.price = price;}
	public Book(String name, Author author, double price, int qty) {this.name = name; this.author = new Author[] {author}; this.price = price;}

	public Book(String name, Author[] author, double price) {this.name = name; this.author = author; this.price = price;}

	public Book(String name, Author[] author, double price, int qty) {this.name = name; this.author = author; this.price = price; this.qty = qty;}


	public String getName() {return this.name;}
	public Author[] getAuthor() {return this.author;}
	public double getPrice() {return this.price;}

	public void setPrice(double price) {this.price = price; return;}

	public int getQty() {return this.qty;}

	public void setQty(int qty) {this.qty = qty; return;}
	public String toString() {
		StringBuilder out = new StringBuilder();
		out.append("Book[name=" + this.name +"," + "{");
		for(Author writer : this.author) { out.append(writer.toString()); out.append(",");}
		out.append("}" + ",price=" + this.price + ",qty=" + this.qty + "]");
		return out.toString();

	}

	public String getAuthorName() {
		StringBuilder out = new StringBuilder();
		for(Author writer : this.author) { out.append(writer.getName()); out.append(" ");}
		return out.toString();
	}
	public String getAuthorEmail() {
		StringBuilder out = new StringBuilder();
		for(Author writer : this.author) { out.append(writer.getEmail()); out.append(" ");}
		return out.toString();
	}
	public char getAuthorGender() {
		StringBuilder out = new StringBuilder();
		for(Author writer : this.author) { out.append(writer.getGender()); out.append(" ");}
		return out.toString().charAt(0);
	}
}