class Mover {
  PVector location;
  PVector velocity;
  
  
  void checkEdges() {
    if(location.x > width + 8) location.x = -8;
    else if (location.x < -8) location.x = width + 8;
    
    if(location.y  > height + 8) location.y = -8;
    else if(location.y < -8) location.y = height + 8;
  }
  void update() {
    location.add(velocity);
    
    checkEdges();
  }
  
  
  void draw() {
    fill(175);
    stroke(10);
    pushMatrix();
    translate(location.x, location.y);
    rectMode(CENTER);
    float angle = atan2(velocity.y, velocity.x);
    rotate(angle);
    rect(0,0, 16, 16);
    popMatrix();
  }
  
  Mover() {
    location = new PVector(width/2, height/2);
    velocity = new PVector(0,0);
  }
  
}


Mover car;

void setup() {
  car = new Mover();
  size(640, 360);
  background(255);
}
float deadzone = 10;
float timer = 0;
void draw() {
  timer ++;
    background(255);
   car.update();
   car.draw();
   if(keyPressed && timer >= deadzone) {
     timer = 0;
      switch(keyCode) {
       case LEFT:
         car.velocity.rotate(0.1);
         break;
       case RIGHT:
         car.velocity.rotate(-0.1);
         break;
       case UP:
         if(car.velocity.mag() != 0) {
            PVector add = car.velocity.copy();
            add.normalize();
            add.mult(0.5);
           car.velocity.add(add);
         }
           else car.velocity.add(new PVector(1,1));
      }    
   }
   car.velocity.mult(0.99);
   car.velocity.limit(10);
}