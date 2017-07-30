class Vehicle {
  PVector location;
  PVector velocity;
  PVector acceleration;
  float mass;
  float maxspeed;
  float maxforce;

  void applyForce(PVector action) {
    PVector force = action.copy();
    force.limit(maxforce);
    force.div(mass);
    acceleration.add(force);
  }

  void step() {
    checkEdges();
    velocity.add(acceleration);

    velocity.limit(maxspeed);

    location.add(velocity);

    acceleration.mult(0);
  }

  void display() {
    float angle = velocity.heading();

    fill(0, 150);
    stroke(0);
    rectMode(CENTER);

    pushMatrix();

    translate(location.x, location.y);
    rotate(angle);

    triangle(mass*5, 0, -mass*5, mass*5, -mass*5, -mass*5);

    popMatrix();
  }

  void update() {
    step(); 
    display();
  }

  void follow(FlowField flow) {
   PVector desired = flow.lookup(location);
   desired.normalize();
   desired.mult(maxspeed);
    PVector steer = PVector.sub(desired, velocity);
    applyForce(steer);
  }
  
  void checkEdges() {
   if(location.x < 0) {
    PVector desired = new PVector(maxspeed, velocity.y);
    desired.limit(maxspeed);
    
    PVector steer = PVector.sub(desired, velocity);
    applyForce(steer);
   } else if(location.x > width) {
     
    PVector desired = new PVector(-maxspeed, velocity.y);
    desired.limit(maxspeed);
    
    PVector steer = PVector.sub(desired, velocity);
    applyForce(steer);
   }
   
   if(location.y < 0) {
     
    PVector desired = new PVector(velocity.x,maxspeed);
    desired.limit(maxspeed);
    
    PVector steer = PVector.sub(desired, velocity);
    applyForce(steer);
     
   } else if(location.y > height) {
    PVector desired = new PVector(velocity.x,-maxspeed);
    desired.limit(maxspeed);
    
    PVector steer = PVector.sub(desired, velocity);
    applyForce(steer);
      
   }
  }

  Vehicle(PVector location, float mass, float maxspeed, float maxforce) {
    this.location = location;
    this.velocity = new PVector();
    this.acceleration = new PVector();
    this.mass = mass;
    this.maxspeed = maxspeed;
    this.maxforce = maxforce;
  }
}


class FlowField {
  PVector[][] field;
  int cols;
  int rows;

  int resolution;

  FlowField(int resolution) {
    this.resolution = resolution;
    cols = width/resolution;
    rows = height/resolution;
    field = new PVector[cols][rows];
    for (int i = 0; i<cols; i++)
      for (int j = 0; j < rows; j++)
        field[i][j] = new PVector(1, 0);
  }
  
  FlowField(int resolution, PVectorGenerator gen) {
   this.resolution = resolution;
   cols = width/resolution;
   rows = height/resolution;
   field = new PVector[cols][rows];
   for(int i = 0; i<cols; i++) 
   for(int j = 0; j<rows; j++)
   field[i][j] = gen.get(i,j);
    
  }
  
  PVector lookup(PVector lookup) {
   int column = int(constrain(lookup.x/resolution, 0, cols-1));
   int row = int(constrain(lookup.y/resolution,0, rows-1));
   return field[column][row].get();
    
  }
  
  void display() {
    for(int i = 0; i<cols; i++) 
    for(int j = 0; j<rows; j++)
    drawPVector(i*resolution + resolution/2, j*resolution + resolution/2, resolution, field[i][j]);
  }
}

@FunctionalInterface
interface PVectorGenerator {
  PVector get(int col, int row);
}