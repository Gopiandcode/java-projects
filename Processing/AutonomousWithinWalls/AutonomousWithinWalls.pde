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
    velocity.add(acceleration);
    
    velocity.limit(maxspeed);
    
    location.add(velocity);
    
    acceleration.mult(0);
  }
  
  void display() {
   float angle = velocity.heading();
   
   fill(0,150);
   stroke(0);
   rectMode(CENTER);
   
   pushMatrix();
   
   translate(location.x, location.y);
   rotate(angle);
   
   triangle(mass*5,0, -mass*5, mass*5, -mass*5, -mass*5);
   
   popMatrix();
  }
  
  void update() {
   step(); 
    display();
  }

  void applyFriction(){ 
   PVector dir = velocity.copy();
   dir.mult(-0.01);
   applyForce(dir);
  }

  void bindVehicle(float x1, float y1, float x2, float y2) {
   if(location.x < x1) {
     PVector desired = new PVector(maxspeed, velocity.y);
     PVector steer   = PVector.sub(desired, velocity);
     applyForce(steer);
   } else if(location.x > x2) {
     PVector desired = new PVector(-maxspeed, velocity.y);
     PVector steer   = PVector.sub(desired, velocity);
     applyForce(steer);
   }
    
    
    if(location.y < y1) {
     PVector desired = new PVector(velocity.x, maxspeed);
     PVector steer   = PVector.sub(desired, velocity);
     applyForce(steer);
    } else if(location.y > y2) {
     PVector desired = new PVector(velocity.x, -maxspeed);
     PVector steer   = PVector.sub(desired, velocity);
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


Vehicle car;


void setup() {
   size(640, 360);
   car = new Vehicle(new PVector(width/2, height/2), 3, 5, 1);
  
}


void draw() {
  background(255);
  if(mousePressed) {
    PVector mouse = new PVector(mouseX, mouseY);
    PVector toMouse = PVector.sub(mouse, car.location);
    toMouse.normalize();
    toMouse.mult(5);
    car.applyForce(toMouse); 
  }
  car.applyFriction(); 
  car.bindVehicle(30,30,width-30,height-30);
  fill(0,0);
  rectMode(CORNERS);
  rect((float)30,(float)30,(float)width-30,(float) height-30); 
  
  car.update();
  
}