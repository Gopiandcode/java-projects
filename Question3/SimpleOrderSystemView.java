package simpleordersystem;

public interface SimpleOrderSystemView
{
  String getCustomerFirstName();
  String getCustomerLastName();
  String getCustomerAddress();
  String getCustomerPhone();
  String getCustomerEmail();
  String getCustomerMobile();
  void reportInvalidCustomer(String firstName, String lastName);
  int getProductCode();
  void reportInvalidProductCode(int productCode);
  int getProductQuantity();
  boolean isNextLineItem();
  void run();
  String getLine();

  void addOrderEntryController(OrderEntryController orderEntryController);
  void addCustomerEntryController(CustomerEntryController customerEntryController);
}
