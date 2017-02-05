package simpleordersystem;

import javax.swing.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

import static simpleordersystem.SimpleOrderSystemTerminalUI.State.*;

public class SimpleOrderSystemTerminalUI implements SimpleOrderSystemView
{
    enum State { ADD_CUSTOMER (1, "Add Customer"), ADD_ORDER(2, "Add Order"),
      ADD_PRODUCT (3, "Add Product"), LIST_CUSTOMERS (4, "List customers"),
        EDIT_DETAILS(5, "Edit a customer's details"), LIST_ORDERS(6, "List orders for a customer"),
        SYSTEM_ORDER_TOTAL(7, "System order total"), ORDERS_CONTAINING(8, "List orders containing a product"),
        SAVE_STATE(9, "Save the state of the system"), LOAD_STATE(10, "Load a previously saved state"),
        ADD_PRODUCT_STOCK(11, "Add to product stock."), QUIT(12, "Exit");
      private int id;
      private String message;
      State(int code, String message) {
          this.id = code;
          this.message = message;
      }

      public String toString() {
          return String.valueOf(this.id) + ". " + message;
      }

      public int getId() {
          return this.id;
      }
      public static State getEnum(int id) {
          for(State s : State.values()) {
              if(s.getId() == id) return s;
          }
          return null;
      }
    }
    private void doOption(int option)
    {
        Product p;
        switch (State.getEnum(option))
        {
            case ADD_CUSTOMER:
                addCustomer();
                break;
            case ADD_ORDER:
                addOrder();
                break;
            case ADD_PRODUCT:
                addProduct();
                break;
            case LIST_CUSTOMERS:
                listCustomers();
                break;
            case SYSTEM_ORDER_TOTAL:
                systemOrderTotal();
                break;
            case EDIT_DETAILS:
                editCustomerDetails();
                break;
            case LIST_ORDERS:
                Customer c = selectCustomer();
                listOrders(c);
                break;
            case ORDERS_CONTAINING:
                p = selectProduct();
                listOrdersContaining(p);
                break;
            case SAVE_STATE:
                saveSystemState();
                break;
            case LOAD_STATE:
                loadSystemState();
                break;
            case ADD_PRODUCT_STOCK:
                p = selectProduct();
                addProductStock(p);
                break;
            default:
                System.out.println("Invalid option - try again");
        }
  }

  private Input in;
  private SimpleOrderSystemModel model;
  private OrderEntryController orderEntryController;
  private CustomerEntryController customerEntryController;


  public SimpleOrderSystemTerminalUI(Input in, SimpleOrderSystemModel model)
  {
    this.in = in;
    this.model = model;
  }

  public void addOrderEntryController(OrderEntryController orderEntryController)
  {
    this.orderEntryController = orderEntryController;
  }

  public void addCustomerEntryController(CustomerEntryController customerEntryController) {
      this.customerEntryController = customerEntryController;
  }

  public void run()
  {
    while(true)
    {
      displayMenu();
      int option = getMenuInput();
      if (option == QUIT.getId())
      {
        break;
      }
      doOption(option);
    }
  }

  private void displayMenu()
  {
    System.out.println("Simple Order System Menu");
    for(State s : State.values()) {
        System.out.println(s);
    }
  }

  private int getMenuInput()
  {
    System.out.print("Enter menu selection: ");
    int option = in.nextInt();
    in.nextLine();
    return option;
  }

  private void addCustomer()
  {
    System.out.println("Add new customer");
    System.out.println("Enter first name:");
    String firstName = in.nextLine();
    System.out.println("Enter last name:");
    String lastName = in.nextLine();
    System.out.println("Enter address:");
    String address = in.nextLine();
    System.out.println("Enter phone number:");
    String phone = in.nextLine();
    System.out.println("Enter mobile number:");
    String mobile = in.nextLine();
    System.out.println("Enter email address:");
    String email = in.nextLine();
    model.addCustomer(firstName,lastName,address,phone,mobile,email);
  }

  private void addOrder()
  {
    Customer customer = customerEntryController.findCustomer();
    if (customer == null)
    {
      System.out.println("Unable to add order");
      return;
    }
    orderEntryController.addOrderToCustomer(customer);
  }

  private String getWithPrompt(String prompt)
  {
    System.out.print(prompt);
    return in.nextLine();
  }

  public String getCustomerFirstName()
  {
    return getWithPrompt("Enter customer first name: ");
  }

  public String getCustomerLastName()
  {
    return getWithPrompt("Enter customer last name: ");
  }

  public String getCustomerAddress() {return getWithPrompt("Enter customer address:");}

  public String getCustomerPhone() {return getWithPrompt("Enter customer phone:");}

  public String getCustomerMobile() {return getWithPrompt("Enter customer mobile:");}

  public String getCustomerEmail() {return getWithPrompt("Enter customer email:");}

  public void reportInvalidCustomer(String firstName, String lastName)
  {
    System.out.println("Cannot find a customer called: "
                       + firstName + " " + lastName);
  }


  public String getLine() {
      return in.nextLine();
  }


  public boolean isNextLineItem()
  {
      System.out.print("Enter line item (y/n): ");
      String reply = in.nextLine();
      if (reply.startsWith("y"))
      {
        return true;
      }
      return false;
  }

  public int getProductCode()
  {
    System.out.print("Enter product code: ");
    int code = in.nextInt();
    in.nextLine();
    return code;
  }

  public void reportInvalidProductCode(int productCode)
  {
    System.out.println("Product code: " + productCode + " is invalid");
  }

  public int getProductQuantity()
  {
    System.out.print("Enter quantity: ");
    int quantity = in.nextInt();
    in.nextLine();
    return quantity;
  }

  private void addProduct()
  {
    System.out.print("Enter product code: ");
    int code = in.nextInt();
    in.nextLine();
    if (!model.isAvailableProductCode(code))
    {
      return;
    }
    System.out.print("Enter product description: ");
    String description = in.nextLine();
    System.out.print("Enter product price: ");
    int price = in.nextInt();
    in.nextLine();
    model.addProduct(code, description, price);
  }

  private void listCustomers()
  {
    System.out.println("List of customers");
    Iterator<Customer> customers = model.getCustomerIterator();
    int i = 1;
    while (customers.hasNext())
    {
        System.out.println("Customer " + i + ": ");
      Customer customer = customers.next();
      System.out.println(customer);
      i++;
    }
  }

  private void listProducts() {
      System.out.println("List of Products");
      Iterator<Product> products = model.getProductIterator();
      int i = 1;
      while (products.hasNext())
      {
          System.out.println("Product " + i + ": ");
          Product product = products.next();
          System.out.println(product);
          i++;
      }
  }

  private void systemOrderTotal() {
    float total = orderEntryController.calculateSystemOrderTotal();
      System.out.println("The total for all orders is " + total);
  }

  private void editCustomerDetails() {
      Customer oldCustomerEntry = customerEntryController.findCustomer();
      if(oldCustomerEntry == null) {
          System.out.println("Customer not found");
          return;
      }

      System.out.println("Current stored details are:");
      System.out.println(oldCustomerEntry);
      model.removeCustomer(oldCustomerEntry.getFirstName(), oldCustomerEntry.getLastName());

      customerEntryController.addCustomer();
      System.out.println("Customer Entry Updated");

  }

  private Customer selectCustomer() {
      System.out.println("Select the customer index from the following list:");
      listCustomers();
      int i = 1, index = in.nextInt();
      Iterator<Customer> iter = model.getCustomerIterator();
      Customer c = null;
      while(iter.hasNext()) {
          i++;
          c = iter.next();
          if(i == index) {break;}
      }
      return c;
  }

  private void listOrders(Customer c) {
      ArrayList<Order> items = c.getOrders();
      for(Order o : items) {
          System.out.print(o.toString());
      }
  }

  private Product selectProduct() {
      System.out.println("Enter the index of the product from the list below:");
      listProducts();
      int i = 1, index = in.nextInt();
      Iterator<Product> iter = model.getProductIterator();
      Product p = null;
      while(iter.hasNext()) {
          i++;
          p = iter.next();
          if(i == index) {break;}
      }
      return p;
  }

  private void listOrdersContaining(Product p) {
      System.out.println("The following orders all contain the product " + p.getDescription());
      Iterator<Customer> iter = model.getCustomerIterator();
      while(iter.hasNext()) {
          Customer c = iter.next();
          ArrayList<Order> valid = c.getOrdersContaining(p);
          if(valid != null && valid.size() != 0) {
              System.out.println("Customer " + c.getFirstName() + ", " + c.getLastName() + ": ");
              for(Order o: valid) {
                  System.out.println(o);
              }
          }

      }
  }

  private void saveStateTo(String filename) throws FileNotFoundException {
        FileOutputStream file = new FileOutputStream(filename + ".xml", true);
        try {
            JAXBContext context = JAXBContext.newInstance(DataManager.class);
            Marshaller jabxMarshaller = context.createMarshaller();
            jabxMarshaller.marshal(model, file);
            file.close();
        } catch (JAXBException | IOException e) {
            e.printStackTrace();
        }
  }

    public void loadStateFrom(String filename) throws FileNotFoundException {
        FileInputStream file = new FileInputStream(filename + ".xml");
        try {
            JAXBContext context = JAXBContext.newInstance(DataManager.class);

            Unmarshaller jabxUnMarshaller = context.createUnmarshaller();
            this.model = (SimpleOrderSystemModel) jabxUnMarshaller.unmarshal(file);
            file.close();
        } catch (JAXBException | IOException e) {
            e.printStackTrace();
        }
    }


  private void saveSystemState() {
      System.out.println("Enter the filename (minus ext):");
      String fileName = in.next();
      try {
          saveStateTo(fileName);
      } catch (FileNotFoundException e) {
          System.out.println("Could not load file");
          return;
      }
      System.out.println("Saved system state to " + fileName + ".xml");
  }

  private void loadSystemState() {
      System.out.println("Enter the filename (minus ext):");
      String fileName = in.next();
      try {
          loadStateFrom(fileName);
      } catch (FileNotFoundException e) {
          System.out.println("Could not load file");
          return;
      }
      System.out.println("Loaded system state from " + fileName + ".xml");
  }
  private void addProductStock(Product p) {
      Inventory i = model.getInventory();
      System.out.println("Print the quantity to add to " + p + ": ");
      int qty = in.nextInt();
      i.addProduct(p, qty);
      System.out.println("Extra quantity " + qty + " added to " + p);
  }
}
