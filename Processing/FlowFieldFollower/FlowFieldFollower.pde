



Vehicle car;
FlowField field;
ArrayList<Vehicle> cars;

void setup() {
   size(640, 360);
   cars = new ArrayList<Vehicle>();
   for(int i = 0; i<40; i++) 
     cars.add(new Vehicle(new PVector(random(width), random(height)), 2, 5, 1));
   car = new Vehicle(new PVector(width/2, height/2), 3, 5, 1);
   field = new FlowField(30, new PVectorGenerator() {
     
     float xoff = 0;
     float yoff = 0;
     int last_col = -1;
     public PVector get(int col, int row) {
       if(last_col == -1) last_col = col;
       
       if(last_col != col) {
        yoff = 0;
        xoff += 0.1;
       }
       
       float theta = map(noise(xoff, yoff), 0, 1, 0, TWO_PI);
       
       yoff += 0.1;
       return new PVector(cos(theta), sin(theta));
       
     }
   });
}



void draw() {
  background(255);
  field.display();
  if(mousePressed) {
    for(Vehicle car : cars) {
    PVector mouse = new PVector(mouseX, mouseY);
    PVector toMouse = PVector.sub(mouse, car.location);
    toMouse.normalize();
    toMouse.mult(5);
    car.applyForce(toMouse); 
    }
  } else {
    car.follow(field);
    for(Vehicle car : cars) {
      car.follow(field);
    }
  }
  
 
  drawPVector(width/2, height/2, 20, new PVector( mouseX - width/2, mouseY - height/2));
  
  fill(0,0);
  rectMode(CORNERS);
  rect((float)30,(float)30,(float)width-30,(float) height-30); 
  
  //car.update();
  
  for(Vehicle car: cars) {
    car.update();
  }
}