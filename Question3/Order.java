package simpleordersystem;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
@XmlRootElement
public class Order
{
  @XmlElement
  private ArrayList<LineItem> lineItems;

  public Order()
  {
    lineItems = new ArrayList<LineItem>();
  }

  public int getLineItemCount()
  {
    return lineItems.size();
  }

  public void add(LineItem item)
  {
    lineItems.add(item);
  }

  public boolean contains(Product p) {
    for(LineItem i : lineItems) {
      if(i.getProduct() == p) return true;
    }
    return false;
  }

  public int getTotal()
  {
    int total = 0;
    for (LineItem item : lineItems)
    {
      total += item.getSubTotal();
    }
    return total;
  }
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for(LineItem item : lineItems) {
      sb.append(item.toString());
      sb.append("\n");
    }
    return sb.toString();
  }
}
