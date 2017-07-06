// Constant Acceleration Simulation

class Mover {
  
  PVector location;
  PVector velocity;
  PVector acceleration;
  
  
  void display() {
    stroke(0);
    fill(175);
    ellipse(location.x, location.y, 16,16);
  }
  
  
  void checkEdges() {
    if(location.x > width) {
      location.x = 0;
    } else if(location.x < 0) {
      location.x = width;
    }
    
    if(location.y > height) {
      location.y = 0;
    } else if(location.y < 0) {
      location.y = height;
    }
  }
  
  
  void step() {
    velocity.add(acceleration);
    location.add(velocity);
    checkEdges();
  }
  
  Mover() {
    location = new PVector(random(width), random(height));
    velocity = new PVector(0,0);
    acceleration = new PVector(random(-0.01,0.01), random(-0.01,0.01));
  }
  
}
  Mover []particles;
  void setup() {
    size(640, 360);
    particles = new Mover[3];
    
    for(int i = 0; i<particles.length; i++) {
      particles[i] = new Mover();
    }
    background(255);
  }
  
  
  void draw() {
    background(255,1);
    for(int i = 0; i < particles.length; i++) {
      particles[i].step();
      particles[i].display();
    }
  }
  
  