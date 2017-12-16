public class TestEmployee {
	public static void main(String[] args) {
		Employee joey = new Employee(10, "Joey", "Gonzales", 100);
		Employee boss = new Employee(1, "Kiran", "Gopinathan", 10000);
		System.out.println(joey);
		System.out.println(boss);

		System.out.println(joey.getFirstName() + "'s salary is " + joey.getSalary() + ", but he might be in for some good luck");
		System.out.println(boss.getFirstName() + ", the boss, is looking for an employee to give a raise. Could " + joey.getFirstName() + " have a chance for this oppourtunity?");

		System.out.println("A voice calls out over the loudspeakers: " + joey.getLastName() + " please come to the boss's office, there is some important information to be heard.");
		System.out.println("Heart beating quickly, Joey, rushes to the room of " + boss.getLastName() + " to hear the news");
		System.out.println("Mr." + boss.getLastName() + " sits in his chair and turns to face " + joey.getFirstName() + ", he says: Mr." + joey.getLastName() + ", I have some good news to tell you. Someone's in for a raise");
		System.out.println(joey.getFirstName() + " can barely contain his excitement. Oh really sir?, Could I ask who?");
		System.out.println("Mr." + boss.getLastName() + " replies, why of course. One of our most promising employees yet. I believe he goes by the name of...");
		System.out.println(boss.getFirstName());
		System.out.println(joey.getFirstName() + ": WHAT?");
		System.out.println(boss.getLastName() + ": We'll, not be having that language in this workspace, you're now fired.");
		joey.setSalary(0);
		boss.raiseSalary(100);
		System.out.println(boss);
		System.out.println(joey);




	}
}