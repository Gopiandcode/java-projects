class Pendulum {
  float r;
  float angle;
  float aVelocity;
  float aAcceleration;
  PVector origin = new PVector(width/2, height/4);
  
  Pendulum() {
    angle = 1;
    aVelocity = 0;
    r = 125;
  }

  void update() {
    //      |*
    //angle-|U\
    //      |  \
    //      |   \ 
    //      |    \
    //      |    {\<--- 90 degrees
    //      |    {/O
    //      |    / |\
    //      |   /  |u\<- angle
    //      |  /   V  \
    //      | / gravity
    //      |/
    //       V
    //      Angular force
    float damping = 0.995;
    float gravity = 0.4f;
    aAcceleration = -1 * gravity * sin(angle) / r;
    aVelocity += aAcceleration;
    aVelocity *= damping;
    angle += aVelocity;
  }
  
  void display() {
    //translate(width/2, height/2-150);
    //rotate(PI/2);
    PVector location = new PVector(r*sin(angle), r* cos(angle));
    location.add(origin);
    stroke(0);
    fill(0, 50);
    line(origin.x, origin.y, location.x, location.y);
    ellipse(location.x, location.y, 16,16);
  }
    
}
Pendulum main;
void setup() {
  background(255);
  size(640, 360);
  main = new Pendulum();
}
void draw() {
  background(255);
  main.update();
  main.display();
}