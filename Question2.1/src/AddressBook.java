import java.util.ArrayList;

/**
 * Created by gopia on 22/01/2017.
 */
public class AddressBook {
    private ArrayList<AddressBookEntry> entries;
    public void addEntry(AddressBookEntry entry) {
            entries.add(entry);
        return;
    }

    public void removeEntry(String name, String phone, String email) {
        for (int i = 0; i<this.entries.size(); i++) {
            AddressBookEntry entry = entries.get(i);
            if ((entry.getName() == name) && (entry.getEmail() == email) && (entry.getPhone() == phone)) {entries.remove(i);}
        }
        return;
    }

    public AddressBookEntry searchEntry(String name) {
        for (AddressBookEntry entry : this.entries) {
            if ((entry.getName() == name)) {
                return new AddressBookEntry(entry.getName(), entry.getPhone(), entry.getEmail());
            }
        }
        return null;
    }

}
