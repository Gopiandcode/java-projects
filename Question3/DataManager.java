package simpleordersystem;

import com.sun.org.apache.xml.internal.security.encryption.Serializer;
import jdk.nashorn.internal.ir.debug.JSONWriter;
import jdk.nashorn.internal.parser.JSONParser;

import javax.xml.bind.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.XMLFormatter;
@XmlRootElement
public class DataManager implements SimpleOrderSystemModel
{
  private ArrayList<Customer> customers;
  private ArrayList<Product> products;
  private Inventory inventory;

  public DataManager()
  {
    customers = new ArrayList<Customer>();
    products = new ArrayList<Product>();
  }

  @XmlElementWrapper(name="customer-list")
  @XmlElement(name="customer")
  public ArrayList<Customer> getCustomers() {return this.customers;}
  @XmlElementWrapper(name="product-list")
  @XmlElement(name="product")
  public ArrayList<Product> getProducts() {return this.products;}


  public void addCustomer(String firstName, String lastName,
                          String address, String phone, String mobile, String email)
  {
    Customer customer = new Customer(firstName, lastName,
                                     address, phone, mobile, email);
    customers.add(customer);
  }

  public Customer getCustomer(String firstName, String lastName)
  {
    for (Customer customer : customers)
    {
      if (customer.getFirstName().equals(firstName)
          && customer.getLastName().equals(lastName))
      {
        return customer;
      }
    }
    return null;
  }

  public void removeCustomer(String firstName, String lastName) {
    customers.removeIf((c) -> c.getFirstName() == firstName && c.getLastName() == lastName);
    return;
  }

  public Iterator<Customer> getCustomerIterator()
  {
    return customers.iterator();
  }

  public Iterator<Product> getProductIterator() {return products.iterator();}

  public Product getProduct(int code)
  {
    for (Product product : products)
    {
      if (product.getCode() == code)
      {
        return product;
      }
    }
    return null;
  }

  public void addProduct(int code, String description, int price)
  {
    Product product = new Product(code,description,price);
    products.add(product);
  }

  public boolean isAvailableProductCode(int code)
  {
    if (code < 1)
    {
      return false;
    }
    for (Product product : products)
    {
      if (product.getCode() == code)
      {
        return false;
      }
    }
    return true;
  }

  public int overallTotal()
  {
    int total = 0;
    for (Customer customer : customers)
    {
      total += customer.getTotalForAllOrders();
    }
    return total;
  }

  public void constructInventory(ArrayList<Integer> quantities) {
      this.inventory = new Inventory(quantities, this);
  }
    @XmlElement(name="inventory")
  public Inventory getInventory() {return this.inventory;}

}
