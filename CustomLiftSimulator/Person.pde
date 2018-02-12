import java.util.Optional;
class Person {
   private final String name;
   private int desiredFloor;
   
   public Person(String name, int floor) {
    this.name = name; 
    this.desiredFloor = floor;  
   }
   
   public int getDesiredFloor() {
     return desiredFloor;
   }
   
   public String getName() {
     return name;
   }
   
   public void setDesiredFloor(int floor) {
     desiredFloor = floor;
   }
}