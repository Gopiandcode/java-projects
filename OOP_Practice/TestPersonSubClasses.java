public class TestPersonSubClasses {
	public static void main(String[] args) {
		Staff Graham = new Staff("Graham Roberts", "52 Gower Street", "UCL", 100000.0F);
		Student Kiran = new Student("Kiran Gopinathan", "105 Stone Meadow", "COMP101P", 2016, 1000.0F);
		Person[] people = new Person[2];
		people[0] = Kiran;
		people[1] = Graham;

		for(int i = 0; i<2; i++) {System.out.println(people[i]);}
		Graham.setPay(0.0F);
		Staff GrahamClone = (Staff)people[1];
		GrahamClone.setPay(100.0F);
		for(Person p : people) {System.out.println(p);}

	}
}