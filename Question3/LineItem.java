package simpleordersystem;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

public class LineItem
{
  private int quantity;

  private Product product;

  public LineItem(int quantity, Product product)
  {
    this.quantity = quantity;
    this.product = product;
  }

  public int getQuantity()
  {
    return quantity;
  }

  public Product getProduct()
  {
    return product;
  }

  public int getSubTotal()
  {
    return product.getPrice() * quantity;
  }

  public String toString() {
    return "Lineitem x" + quantity + ":\n\t" + product + "\n";
  }
}
