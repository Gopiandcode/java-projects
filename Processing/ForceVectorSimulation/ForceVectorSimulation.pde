class Mover {
  
  PVector location;
  PVector velocity;
  PVector acceleration;

  float mass;

  void applyForce(PVector force_orig) {
    PVector force = force_orig.copy();
    force.div(mass);
    acceleration.add(force);
  }
 
 void checkEdges() {
   if(location.x > width) location.x = -16;
   else if (location.x < -16) location.x = width;
   
   if(location.y > height+16) location.y = 0;
   else if(location.y < 0) location.y = height+16;
 }
 
 
 void update() {
   
   velocity.add(acceleration);
   velocity.limit(10);
   velocity.mult(map(mass, 0, 32, 0.999,0.99));
   location.add(velocity);
   acceleration.mult(0);
   
   checkEdges();
}


void display() {
  stroke(10);
  fill(175);
  ellipse(location.x, location.y, mass, mass);
}

  Mover() {
   location = new PVector(random(width), random(height));
   velocity = new PVector(0,0);
   acceleration = new PVector(random(-0.01, 0.01), random(-0.01,0.01));
    mass = random(32);
  }

}



Mover[] particles;


void setup() {
  size(640, 360);
  background(255);
  particles = new Mover[16];
  
  for(int i = 0; i<particles.length; i++)
    particles[i] = new Mover();
    
}

void draw() {
  background(255, 10);
  
  for(int i = 0; i<particles.length; i++) {
    particles[i].update();
    particles[i].display();
    if(mousePressed) {
       // apply force in direction of mouse
       
       PVector mouse = new PVector(mouseX, mouseY);
       PVector diff   = PVector.sub(mouse, particles[i].location);
       
       particles[i].applyForce(diff);
       
    }
}
  
}