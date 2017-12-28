
class Building {
  private int maxFloor;
  private Lift lift;
  private int buildingWidth;
  private int buildingHeight;
  private List<List<Person>> buildingOccupants;


  public Building(Lift lift) {
    this(lift, (height - 10)/lift.getMaxFloor());
  }

  public Building(Lift lift, int cellSize) {
    this(lift, cellSize, cellSize);
  }

  public Building(Lift lift, int buildingWidth, int buildingHeight) { 
    this.maxFloor = lift.getMaxFloor();
    this.lift = lift;
    this.buildingWidth = buildingWidth;
    this.buildingHeight = buildingHeight;
    this.buildingOccupants = new ArrayList();
    for (int i = 0; i < maxFloor; i++) {
      buildingOccupants.add(new ArrayList());
    }
  }

  public void randomPerson() {
    int floorFrom = (int) (random(1) * maxFloor);
    int floorTo = (int) (random(1) * maxFloor);
    String name = Names.names[(int) (random(1) * Names.names.length)];

    buildingOccupants.get(floorFrom).add(new Person(name, floorTo));
  }

  public void leaveLift() {
    List<Person> leavers =  lift.getOff();
    buildingOccupants.get(lift.getFloor()).addAll(leavers);
  }

  public void enterLift() {
    List<Person> occupants = buildingOccupants.get(lift.getFloor());
    Iterator<Person> iter = occupants.iterator();
    while(iter.hasNext()) {
       Person p = iter.next();
       if(p.getDesiredFloor() != lift.getFloor()) {
         lift.getOn(p);
         iter.remove();
       }
    }
  }
  
  public void moveLiftUp() {
     lift.moveUp(); 
  }
  
  public void moveLiftDown() {
     lift.moveDown(); 
  }
  
  public void removeSatisifiedCustomers() {
   for(int i = 0; i < buildingOccupants.size(); i++) {
     List<Person> occupants = buildingOccupants.get(i);
     Iterator<Person> iter = occupants.iterator();
    while(iter.hasNext()) {
       Person p = iter.next();
       if(p.getDesiredFloor() == i) {
         iter.remove();
       }
    }
   }
  }

  public void draw() {
    rectMode(CORNERS);
    rect(0, 0, buildingWidth, maxFloor * buildingHeight);
    for (int i = 0; i < maxFloor; i++) {
      fill(0);
      text("[" + (maxFloor - i - 1) + "]", buildingWidth/2, buildingHeight * (i+1));
          
      List<Person> floorPeople = buildingOccupants.get((maxFloor - i - 1));
      int posI = 0;
      for (Person p : floorPeople) {
        if (p.getDesiredFloor() == (maxFloor - i - 1)) fill(0, 255, 0); 
        text(p.getName() + " (" + p.getDesiredFloor() + ")", buildingWidth+20, buildingHeight * i + posI++ * 15+15);
        fill(0);
      }

      fill(255);
      if (lift.getFloor() == (maxFloor - i - 1)) {
        fill(0);

        List<Person> people = lift.getPeople();
        posI = 0;
        for (Person p : people) {
          if (p.getDesiredFloor() == lift.getFloor())
            fill(255, 0, 0);
          text(p.getName() + " (" + p.getDesiredFloor() + ")", -buildingWidth-20, buildingHeight * i + posI++ * 15 + 10);
          fill(0);
        }
        fill(200);
      }
      rect(0, buildingHeight * i, buildingWidth, buildingHeight * (i+1));
            fill(0);
      text("[" + (maxFloor - i - 1) + "]", buildingWidth/2-6, buildingHeight * (i+0.5));
     
    }
  }
}