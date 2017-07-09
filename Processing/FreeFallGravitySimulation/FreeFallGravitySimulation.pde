float G = 1;

class Mover {
  PVector location;
  PVector velocity;
  PVector acceleration;
  float mass;
  float radius;
  
  void checkEdges() {
    if(location.x > width) location.x = -1 * radius;
    else if (location.x < -1 * radius) location.x = width;
    
    if(location.y > height + radius) location.y = 0;
    else if (location.y < 0) location.y = height + radius;
  }
  
  void draw() {
     stroke(mass);
     fill(acceleration.x * mass, acceleration.y * mass, acceleration.mag() * mass);
     ellipse(location.x, location.y, radius, radius);
  }
  
  void update() {
     velocity.add(acceleration);
     location.add(velocity);
     acceleration.mult(0);
     velocity.limit(10);
    
    checkEdges();
    
  }
  
  Mover() {
   location = new PVector(random(width), random(height));
   velocity = new PVector(0,0);
   acceleration = new PVector(0,0);
   mass = random(64);
   radius = mass;
  }
  
  void attract(Mover mover) {
     PVector force = PVector.sub(mover.location, location);
     float distance = force.mag();
     distance = constrain(distance, 0.01, 10000);
     force.normalize();
     float m = (G * mass * mover.mass) / (distance * distance);
     force.mult(m);
     applyForce(force);
  }
    
    
    void applyForce(PVector force) {
        PVector act = force.get();
        act.div(mass);
        acceleration.add(act);
    }
}


Mover[] movers;

void setup() {
  background(255);
  size(640, 360);
  movers = new Mover[20];
  for(int i = 0; i<movers.length; i++) {
    movers[i] = new Mover();
  }
}

void draw() {
  background(255, 10);
  for(int i = 0; i < movers.length-1; i++) {
    for(int j = i+1; j < movers.length; j++) {
      movers[i].attract(movers[j]);
      movers[j].attract(movers[i]);
    }
  }
   if(mousePressed) {
    PVector mouse = new PVector(mouseX, mouseY);
     for(int i = 0; i<movers.length; i++) {
      PVector force = PVector.sub(mouse, movers[i].location);
      force.normalize();
      force.mult(2);
      movers[i].applyForce(force);
      
    }
   }
  
  for(int i = 0; i < movers.length; i++) {
    movers[i].update();
    movers[i].draw();
  }
  
}