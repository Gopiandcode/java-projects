public class Employee {
	private int id;
	private String firstName;
	private String lastName;
	private int salary;

	public Employee(int id, String firstName, String lastName, int salary) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.salary = salary;
	}

	public int getID() {
		return this.id;
	}
	
	public String getFirstName() {
		return this.firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public String getName() {
		return this.firstName + this.lastName;
	}

	public int getSalary() {
		return this.salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
		return;
	}

	public int getAnnualSalary() {
		return this.salary * 12;
	}

	public int raiseSalary(int percent) {
		this.salary = this.salary * (1 + (percent/100));
		return this.salary;
	}

	public String toString() {
		return "Employee[id=" + this.id + ",name=" + this.firstName + this.lastName + ",salary=" + this.salary + "]";

	}
}