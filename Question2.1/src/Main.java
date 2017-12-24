import java.util.Scanner;

/**
 * Created by gopia on 22/01/2017.
 */
public class Main {
    private static AddressBook addressBook = new AddressBook();
    private enum State {SELECT, ADD, REMOVE, RETRIEVE_ONE}
    private static State state = State.SELECT;
    public static void main(String[] args) {
        while(true) {
            switch (state) {
                case SELECT:
                    selectionScreen();
                    break;
                case ADD:
                    addEntry();
                    state = state.SELECT;
                    break;
                case REMOVE:
                    removeEntry();
                    state = state.SELECT;
                    break;
                case RETRIEVE_ONE:
                    retrieveOne();
                    state = state.SELECT;
                    break;
            }
        }
    }

    private static void selectionScreen() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please select one of the following options:");
        System.out.println("1. Add an Entry");
        System.out.println("2. Remove an Entry.");
        System.out.println("3. Retrieve a stored entry by name.");
        System.out.print("Your Selection: ");
        int value = sc.nextInt();
        switch(value) {
            case 1:
                state = State.ADD;
                return;
            case 2:
                state = State.REMOVE;
                return;
            case 3:
                state = State.RETRIEVE_ONE;
                return;
            default:
                state = State.SELECT;
                return;
        }
    }


    private static void addEntry() {
        String name, phoneNum, email;
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the name of the person:");
        name = sc.nextLine();
        System.out.println("Enter the phone number of the person:");
        phoneNum = sc.nextLine();
        System.out.println("Enter the email of the person:");
        email = sc.nextLine();
        AddressBookEntry newEntry = new AddressBookEntry(name, phoneNum, email);
        addressBook.addEntry(newEntry);
        System.out.println("The person " + name + " has been added to the addressbook.");
    }

    private static void removeEntry() {
        String name;
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the name of the person you wish to remove:");
        name = sc.nextLine();
        AddressBookEntry entry = addressBook.searchEntry(name);
        if(entry != null) addressBook.removeEntry(entry.getName(), entry.getPhone(), entry.getEmail());
        System.out.println("The person " + name + " has been removed from the addressbook.");
    }


    private static void retrieveOne() {
        String name;
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the name of the person you wish to retrieve:");
        name = sc.nextLine();
        AddressBookEntry entry = addressBook.searchEntry(name);

        if(entry != null) System.out.println("The person " + name + " has the email " + entry.getEmail() + " and the number " + entry.getPhone());
        else System.out.println("The person " + name + " was not found on the system.");
        return;
    }


}
