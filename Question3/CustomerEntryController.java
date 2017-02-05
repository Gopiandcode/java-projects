package simpleordersystem;

import static java.lang.System.in;

/**
 * Created by root on 05/02/17.
 */
public class CustomerEntryController {
    private final SimpleOrderSystemModel model;
    private final SimpleOrderSystemView view;

    public CustomerEntryController(SimpleOrderSystemModel model, SimpleOrderSystemView view) {
        this.model = model;
        this.view = view;
    }


    public Customer findCustomer()
    {
        String lastName = view.getCustomerLastName();
        String firstName = view.getCustomerFirstName();
        return model.getCustomer(firstName, lastName);
    }

    public void addCustomer() {
        String firstName = view.getCustomerFirstName();
        String lastName = view.getCustomerLastName();
        String address = view.getCustomerAddress();
        String phone = view.getCustomerPhone();
        String mobile = view.getCustomerMobile();
        String email = view.getCustomerEmail();
        model.addCustomer(firstName, lastName, address, phone, mobile, email);
    }
}
