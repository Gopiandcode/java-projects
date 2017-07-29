
class Vehicle {
  PVector location;
  PVector velocity;
  PVector acceleration;
  float mass;
  float maxspeed;
  float maxforce;
  
  void seek(PVector target) {
   PVector desired = PVector.sub(target, location);
   desired.normalize();
   desired.mult(maxspeed);
   
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
  
  Vehicle(PVector location, float mass, float maxspeed, float maxforce){
    this.location = location.copy();
    this.velocity = new PVector();
    this.acceleration = new PVector();
    this.mass = mass;
    this.maxspeed = maxspeed;
    this.maxforce = maxforce;
  }
}
  
  ArrayList<Float> xs = new ArrayList<Float>();
  ArrayList<Float> ys = new ArrayList<Float>();
  
  Vehicle car;
  PVector target;
  void setup() {
    size(640, 360);
   car = new Vehicle(new PVector (width/2, height/2), 10, 4, 1); 
    
  }
  
  void draw() {
    background(255);
   if (mousePressed){ 
    target = new PVector(mouseX, mouseY); 
   }
   
   if(target != null) {
    car.seek(target);
    stroke(0);
    fill(0,0);
    rectMode(CENTER);
    ellipse(target.x,target.y, 10,10);
     
   }
   xs.add(car.location.x);
   ys.add(car.location.y);
   
   beginShape();
   for(int i = 0; i<xs.size(); i++) {
    vertex(xs.get(i), ys.get(i)); 
   }
   endShape();
   
   car.update();
    
  }
  