ArrayList<Vehicle> vehicles;

void setup() {
  size(640, 360);
  vehicles = new ArrayList<Vehicle>();
  for(int i = 0; i<30; i++) {
   vehicles.add(new Vehicle(new PVector(random(width), random(height)), 1, 5, 0.1));
  }
  
}



void draw() {
  background(255);
  
  if(mousePressed) {
     for(Vehicle v : vehicles) {
      v.seekTo(new PVector(mouseX, mouseY)); 
     }
  }
  
  
  for(Vehicle v : vehicles) {
    v.flock(vehicles);
    v.update(); 
  }
  
}
  