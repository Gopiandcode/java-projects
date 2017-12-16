
class Vehicle {
  PVector location;
  PVector velocity;
  PVector acceleration;
  float mass;
  float maxspeed;
  float maxforce;
  
  void seek(PVector target) {
   PVector desired = PVector.sub(target, location);
  float dist = desired.mag();
   desired.normalize();
  
   if(dist < 100) {
     float approx = map(dist, 0, 100, 0, maxspeed);
     desired.mult(approx);
     
     
   } else desired.mult(maxspeed);
   
   PVector steer = PVector.sub(desired, velocity);
   
   steer.limit(maxforce);
   applyForce(steer);
  }
  
  void applyForce(PVector force) {
    PVector action = force.copy();
    action.div(mass);
    acceleration.add(action);
  }
  
  void step() {
    velocity.add(acceleration);
    location.add(velocity);
    checkEdges();    
    velocity.limit(maxspeed);
    acceleration.mult(0);
  }
  
  void display() {
   float angle = velocity.heading();
   pushMatrix();
   translate(location.x, location.y);
   rotate(angle);
   fill(0,150);
   stroke(0);
   rectMode(CENTER);
   triangle( 2*mass, 0, 0,-1*mass, 0,mass);
   popMatrix();
    
  }
  
  void update() {
   step();
   display();
  }
  
  void checkEdges() {
   if(location.x > width) location.x = 0;
   else if(location.x < 0) location.x = width;
   
   if(location.y > height) location.y = 0;
   else if(location.y < 0) location.y = height;
    
  }
  
  void reynolds() {
   if(velocity.mag() < 0.1) {
    velocity = PVector.sub(location, new PVector(mouseX, mouseY));
    velocity.limit(maxspeed);
   } else {
     PVector next_location = PVector.add(location, velocity);
     PVector rand = PVector.random2D();
     rand.normalize();
     rand.mult(max(mass*2,PVector.sub(location, velocity).mag()));
     next_location.add(rand);
     seek(next_location);
   }
  }
  
  Vehicle(PVector location, float mass, float maxspeed, float maxforce){
    this.location = location.copy();
    this.velocity = new PVector();
    this.acceleration = new PVector();
    this.mass = mass;
    this.maxspeed = maxspeed;
    this.maxforce = maxforce;
  }
}
  
  
  Vehicle car;
  PVector target;
  void setup() {
    size(640, 360);
   car = new Vehicle(new PVector (width/2, height/2), 6, 4, 0.5); 
    
  }
  
 boolean reynolds = false;
  
  void draw() {
    background(255);
   if (mousePressed && (mouseButton == LEFT)){ 
    target = new PVector(mouseX, mouseY); 
   }
   if(mousePressed && (mouseButton == RIGHT)) {
     reynolds = !reynolds;
   }
  
   
   
   if(target != null) {
     if(reynolds) { car.reynolds(); text("Reynolds: True", 10,10);} else {
       text("Reynolds: False", 10,10);
      car.seek(target);
     }
    stroke(0);
    fill(0,0);
    rectMode(CENTER);
    ellipse(target.x,target.y, 10,10);
     
   }
   
   
   car.update();
    
  }
  