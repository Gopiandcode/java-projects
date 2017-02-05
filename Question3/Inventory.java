package simpleordersystem;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.crypto.Data;
import java.util.ArrayList;

/**
 * Created by root on 05/02/17.
 */
@XmlType(factoryMethod="newInstance")
@XmlRootElement(name="inventory")
public class Inventory {
    @XmlElement(name="product-quantities")
    private ArrayList<Integer> quantities;
    SimpleOrderSystemModel model;

    public Inventory() {}

    public static Inventory newInstance() {return new Inventory();}

    public Inventory(ArrayList<Integer> input, SimpleOrderSystemModel model) {
        quantities = new ArrayList<>(input);
        this.model = model;
    }

    public void addProduct(Product p, int q) {
        ArrayList<Product> productList = new ArrayList<>(model.getProducts());
        int i = 0;
        for(; i<productList.size(); i++) {
            if(productList.get(i) == p) break;
        }
        quantities.set(i, quantities.get(i) + q);
    }
    public void removeProduct(Product p, int q) {
        ArrayList<Product> productList = new ArrayList<>(model.getProducts());
        int i = 0;
        for(; i<productList.size(); i++) {
            if(productList.get(i) == p) break;
        }
        quantities.set(i, quantities.get(i) - q);
    }
    public boolean canPurchase(Product p, int q) {
        ArrayList<Product> productList = new ArrayList<>(model.getProducts());
        int i = 0;
        for(; i<productList.size(); i++) {
            if(productList.get(i) == p) break;
        }
        if(quantities.get(i) > 0 && quantities.get(i) >= q) return true;
        else return false;
    }
}
