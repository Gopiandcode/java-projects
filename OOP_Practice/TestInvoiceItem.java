public class TestInvoiceItem {
	public static void main(String[] args) {
		InvoiceItem oranges = new InvoiceItem("Oranges","A bag of oranges", 20, 2.0);
		System.out.println(oranges);
		oranges.setQty(300);
		System.out.println(oranges);
		oranges.setUnitPrice(1.423);
		System.out.println(oranges);
		System.out.println(oranges.getTotal());
		
	}


}