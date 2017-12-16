final float G = 10;

class Mover {
  PVector location;
  PVector velocity;
  PVector acceleration;
  float angle;
  float angular_velocity;
  float angular_acceleration;
  float radius;
  float mass;
  
  void checkEdges() {
    if(location.x > width) location.x = -1 * radius;
    else if(location.x < -1 * radius) location.x = width;
    
    if(location.y > height + radius) location.y = 0;
    else if(location.y < 0) location.y = height + radius;
  }
  
  Mover() {
    location = new PVector(random(width), random(height));
    velocity = new PVector(0,0);
    acceleration = new PVector(0,0);
    mass = random(64);
    angular_velocity = 0;
    angle = 0;
    radius = mass * 1;
    angular_acceleration = random(0.001, 0.01);
  }
  
  void update() {
    velocity.add(acceleration);
    location.add(velocity);
    velocity.limit(10);
    acceleration.mult(0);
    angular_velocity += angular_acceleration;
    angle += angular_velocity;
    checkEdges();
  }
  
  void applyForce(PVector action) {
    PVector force = action.copy();
    force.div(mass);
    acceleration.add(force);
  }
  
  void attract(Mover m) {
    PVector dir = PVector.sub(m.location, location);
    float distance = dir.mag();
    distance = constrain(distance,0.00001, 1000000000);
    float magnitude = G * m.mass * mass / (distance * distance);
    dir.normalize();
    dir.mult(magnitude);
    applyForce(dir);
  }
  
  void draw() {
    float wangle = atan2(velocity.y,velocity.x);
    stroke(mass);
    fill(velocity.mag(), acceleration.mag(), angular_acceleration);
    rectMode(CENTER);
    pushMatrix();
    translate(location.x, location.y);
    rotate(wangle);
    rect(0,0, radius, radius);
    popMatrix();
  }
    
}


Mover[] movers;



void setup() {
  size(640, 360);
  background(255);
  movers = new Mover[25];
  for(int i = 0; i<movers.length; i++)
    movers[i] = new Mover();
}

void draw() {
 background(255);
 
 for(int i = 0; i<movers.length-1; i++) {
   for(int j = i+1; j <movers.length; j++) {
     movers[i].attract(movers[j]);
     movers[j].attract(movers[i]);
   }
 }
 
 for(int i = 0; i<movers.length; i++) {
    movers[i].update();
   movers[i].draw();
 }
  
}