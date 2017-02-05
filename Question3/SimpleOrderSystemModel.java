package simpleordersystem;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

public interface SimpleOrderSystemModel
{
  void addCustomer(String firstName, String lastName,
                   String address, String phone, String model, String email);

  Customer getCustomer(String firstName, String lastName);

  void removeCustomer(String firstName, String lastName);

  Iterator<Customer> getCustomerIterator();

  Iterator<Product> getProductIterator();

  Product getProduct(int code);

  ArrayList<Product> getProducts();

  void addProduct(int code, String description, int price);

  boolean isAvailableProductCode(int code);

  Inventory getInventory();

  void constructInventory(ArrayList<Integer> quantites);

}
