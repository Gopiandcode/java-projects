package simpleordersystem;

import java.util.ArrayList;
import java.util.Iterator;

public class OrderEntryController
{
  private final SimpleOrderSystemModel model;
  private final SimpleOrderSystemView view;

  public OrderEntryController(SimpleOrderSystemModel model,
                              SimpleOrderSystemView view)
  {
    this.model = model;
    this.view = view;
  }

  public ArrayList<LineItem> getLineItemsList()
  {
    ArrayList<LineItem> items = new ArrayList<LineItem>();
    while (view.isNextLineItem())
    {
      LineItem item = getLineItemFromView();
      if (item != null) { items.add(item);}
    }
    return items;
  }

  public float calculateSystemOrderTotal() {
    Iterator<Customer> iter = model.getCustomerIterator();
    float total = 0;
    while(iter.hasNext()) {
      total += iter.next().getTotalForAllOrders();
    }
    return total;
  }

  public LineItem getLineItemFromView()
  {
    Product product = getProductFromView();
    if (product == null)
    {
      return null;
    }
    int quantity = getProductQuantityFromView();
    return new LineItem(quantity,product);
  }

  public int getProductQuantityFromView()
  {
    return view.getProductQuantity();
  }

  public Product getProductFromView()
  {
    int productCode = view.getProductCode();
    Product product = model.getProduct(productCode);
    if (product == null)
    {
      view.reportInvalidProductCode(productCode);
    }
    return product;
  }

  public void addOrderToCustomer(Customer customer) {
    Order order = new Order();
    addLineItems(order);
    if (order.getLineItemCount() == 0)
    {
      System.out.println("Cannot have an empty order");
      return;
    }
    customer.addOrder(order);
  }

  private void addLineItems(Order order)
  {
    while (true)
    {
      System.out.print("Enter line item (y/n): ");
      String reply = view.getLine();
      if (reply.startsWith("y"))
      {
        LineItem item = getLineItemFromView();
        if (item != null && model.getInventory().canPurchase(item.getProduct(), 1))
        {
          order.add(item);
          model.getInventory().removeProduct(item.getProduct(), 1);
        }
      }
      else
      {
        break;
      }
    }
  }

}