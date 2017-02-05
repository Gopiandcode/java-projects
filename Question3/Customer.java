package simpleordersystem;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
@XmlType(factoryMethod="newInstance")
@XmlRootElement(name="customer")
public class Customer
{
  @XmlElement
  private String firstName;
  @XmlElement
  private String lastName;
  @XmlElement
  private String address;
  @XmlElement
  private String phone;
  @XmlElement
  private String mobile;
  @XmlElement
  private String email;
  private ArrayList<Order> orders;
  public Customer() {}
  public static Customer newInstance() {return new Customer();}
  public Customer(String firstName, String lastName, String address, String phone, String mobile, String email)
  {
    this.firstName = firstName;
    this.lastName = lastName;
    this.address = address;
    this.phone = phone;
    this.mobile = mobile;
    this.email = email;
    orders = new ArrayList<Order>();
  }
  public String getFirstName()
  {
    return firstName;
  }

  public String getLastName()
  {
    return lastName;
  }

  public String getAddress()
  {
    return address;
  }

  public String getPhone()
  {
    return phone;
  }

  public String getMobile() {return mobile;}

  public String getEmail()
  {
    return email;
  }

  public void addOrder(Order order)
  {
    orders.add(order);
  }

  public ArrayList<Order> getOrders()
  {
    return new ArrayList<Order>(orders);
  }

  public ArrayList<Order> getOrdersContaining(Product p) {
    ArrayList<Order> validOrders = new ArrayList<>();
    for(Order o : orders) {
      if(o.contains(p)) validOrders.add(o);
    }
    return validOrders;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("FirstName: " + this.getFirstName() + "\n");
    sb.append("LastName: " + this.getLastName() + "\n");
    sb.append("Address: " + this.getAddress() + "\n");
    sb.append("Phone: " + this.getPhone() + "\n");
    sb.append("Mobile: " + this.getMobile() + "\n");
    sb.append("Email: " + this.getEmail() + "\n");

    return sb.toString();
  }

  public int getTotalForAllOrders()
  {
    int total = 0;
    for (Order order : orders)
    {
      total += order.getTotal();
    }
    return total;
  }
}
