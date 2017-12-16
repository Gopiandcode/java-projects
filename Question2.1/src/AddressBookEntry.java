/**
 * Created by gopia on 22/01/2017.
 */
public class AddressBookEntry {
    private String name;
    private String number;
    private String email;


    public AddressBookEntry(String name, String phone, String email) {
        this.name = name;
        this.number = phone;
        this.email = email;
    }


    public String getName() {return this.name;}
    public String getPhone() {return this.number;}
    public String getEmail() {return this.email;}
}
