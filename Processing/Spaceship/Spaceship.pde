class Mover {
  PVector acceleration;
  PVector velocity;
  float angle;
  PVector location;
  
  
  void checkEdges() {
    if(location.x > width) location.x = -16;
    else if (location.x < -16) location.x = width;
    
    if(location.y > height) location.y = -16;
    else if(location.y < -16) location.y = height;
  }
  
  
  void update() {
    applyFriction();    
    velocity.add(acceleration);
    PVector directional = new PVector(0,1);
    directional.mult(velocity.mag());
    directional.rotate(angle);
    location.add(directional);
    acceleration.mult(0);
    velocity.limit(10);
    checkEdges(); 
  }
  
  void applyFriction() {
    PVector force = velocity.copy();
    force.normalize();
    force.mult(-1* 0.8);
    applyForce(force);
  }
  
  void applyThrust() {
    PVector force = velocity.copy();
    force.normalize();
    if(force.mag() != 0)
      force.mult(20);
    else {
       PVector add = new PVector(1,1);
       add.rotate(force.heading());
       force.add(add);
      
    }
    applyForce(force);
  }
  
  void applyForce(PVector act) {
    PVector force = act.copy();
    force.div(16);
    acceleration.add(force);
  }
  void tiltLeft() {
      angle += 0.07;
   }
  void tiltRight() { 
    angle -= 0.07;
  }
  
  void draw() {
     fill(175);
     stroke(10);
     rectMode(CENTER);
     pushMatrix();
     translate(location.x, location.y);
     rotate(angle);
     triangle(-8,0, 0, 16, 8, 0);
     popMatrix();
  }

    Mover() {
      location = new PVector(width/2, height/2);
      velocity = new PVector(0,0);
      acceleration = new PVector(0,0);
    }
}
Mover spaceship;
void setup() {
  spaceship = new Mover();
  background(255);
  size(640, 360);
 
}


void draw() {
  background(255);
  print(keyPressed);
   if(keyPressed) {
     print(key);
     
     switch(key) {
       case 'a':
       spaceship.tiltLeft();
       break;
       case 'd':
       spaceship.tiltRight();
       break;
       case 'w':
       spaceship.applyThrust();
       break;
       case 's':
       spaceship.applyFriction();
       break;
     }   
   }
   spaceship.update();
   spaceship.draw();
   
  
   print(spaceship.acceleration); 
}