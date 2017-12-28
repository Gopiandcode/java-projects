import java.util.List;
import java.util.Iterator;

class Lift {
  private int floor;
  private final int maxFloor;
  private final List<Person> occupants;


  public Lift(int maxFloor) {
    floor = 0;
    this.maxFloor = maxFloor;
    occupants = new ArrayList();
  }
  
  public void getOn(Person person) {
     occupants.add(person); 
  }
  
  public List<Person> getOff() {
    List<Person> leavers = new ArrayList();
    
    Iterator<Person> iter = occupants.iterator();
    while(iter.hasNext()) {
       Person p = iter.next();
       if(p.getDesiredFloor() == floor) {
          leavers.add(p);
          iter.remove();
       }
    }
    return leavers;
  }
  
  public int getFloor() {
    return floor;
  }
  
  public int getMaxFloor() {
    return maxFloor;  
  }
  
  public List<Person> getPeople() {
    return new ArrayList<Person>(occupants);
  }
  
  public void moveUp() {
    floor++;
    if(floor >= this.maxFloor) floor = maxFloor -1;
  }
  public void moveDown() {
    floor--;
    if(floor < 0) floor = 0;
  }
}