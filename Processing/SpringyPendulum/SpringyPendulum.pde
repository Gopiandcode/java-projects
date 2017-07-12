
class Mover {
  PVector acceleration;
  PVector velocity;
  PVector location;
  float mass;
  
  Mover(float mass) {
    this();
    this.mass = mass;
  }
  
  Mover() {
    location = new PVector(random(width), random(height));
    velocity = new PVector(0,0);
    acceleration = new PVector(0,0);
    mass = random(64);
  }
  
  void update() {
    velocity.add(acceleration);
    location.add(velocity);
    acceleration.mult(0);
  }
  
  void display() {
    stroke(0);
    fill(0, 50);
    ellipse(location.x, location.y, mass * 5, mass * 5);
  }
  
  void applyFriction() {
     PVector negative = PVector.mult(velocity, -1);
     negative.normalize();
     negative.mult(10);
     applyForce(negative);
    
  }
  
  void applyForce(PVector force) {
   PVector dac = PVector.div(force, mass);
   acceleration.add(dac);
  }
    
}

final PVector global_anchor = new PVector(width/2, height/8);

class Rope {
  Mover bob;
  PVector anchor;
  float k;
  float len;
  
  Rope(Mover m) {
    this(m, global_anchor,PVector.sub(m.location, global_anchor).mag());
    }
  
  Rope(Mover m, PVector anchor, float len) {
     this.anchor = anchor;
     bob = m;
     k = random(10);
     this.len = len;
  }
  void display() {
    stroke(10);
    fill(0);
    line(bob.location.x, bob.location.y, anchor.x, anchor.y);
  }
  
  
  
  void update() {
     PVector dir = PVector.sub(anchor, bob.location);
     float full_length = dir.mag();
     float ext = full_length - len;
     float force_mag = ext * k;
     dir.normalize();
     dir.mult(force_mag);
     bob.applyForce(dir);
  }

}

  
Mover main;
Rope rope;

void setup() {
  size(640, 360);
  background(255);
  global_anchor.set(width/2, height/8);
  main = new Mover();
  rope = new Rope(main);
}

void draw() {
  background(255);
   PVector grav = new PVector(0,1);
   grav.mult(9.8 * main.mass);
   main.applyForce(grav);
   main.applyFriction();
   rope.update();
   rope.display();
   main.update();
   main.display();
  
}