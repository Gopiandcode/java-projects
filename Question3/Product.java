package simpleordersystem;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(factoryMethod="newInstance")
@XmlRootElement(name="product")
public class Product
{
  @XmlAttribute
  private int code;
  @XmlAttribute
  private int price;
  @XmlAttribute
  private String description;
  public Product() {}
  public static Product newInstance() {return new Product();}
  public Product(int code, String description, int price)
  {
    this.code = code;
    this.price = price;
    this.description = description;
  }

  public int getPrice()
  {
    return price;
  }

  public String getDescription()
  {
    return description;
  }

  public int getCode()
  {
    return code;
  }

  public String toString() {
    return "Product #" + code + ": " + description + ", Price: " + price;
  }
}
