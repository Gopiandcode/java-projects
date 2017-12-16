ArrayList<Vehicle> vehicles;
Path path;
void setup() {
  size(640, 360);
  vehicles = new ArrayList<Vehicle>();
  for(int i = 0; i<20; i++) {
   vehicles.add(new Vehicle(new PVector(random(width), random(height)), 2, 5, 1));
  }
  path = new Path();
  
}


void mousePressed() {
   path.addPoint(mouseX,mouseY); 
  
}


void draw() {
  background(255);
  
  
  path.display();
  if(mousePressed) {
     for(Vehicle v : vehicles) {
      v.seek(new PVector(mouseX, mouseY)); 
     }
  } else {
     for(Vehicle v: vehicles) {
       
      v.followPath(path); 
     }
  }
  
  
  for(Vehicle v : vehicles) {
    v.separate(vehicles);
    v.update(); 
  }
  
}
  