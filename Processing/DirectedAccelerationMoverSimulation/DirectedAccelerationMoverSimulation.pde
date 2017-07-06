class Mover {
  PVector location;
  PVector velocity;
  PVector acceleration;
  
  Mover() {
    location = new PVector(random(width), random(height));
    velocity = new PVector(0,0);
    acceleration = new PVector(random(-0.01, 0.01), random(-0.01, 0.01));
  }
  
  void draw() {
     stroke(10);
     fill(175);
     ellipse(location.x, location.y, 16,16);
  }
  
  void checkEdges() {
    if(location.x > width) location.x = 0;
    else if(location.x < -16) location.x = width;
    
    if(location.y > height+16) location.y = 0;
    else if(location.y < 0) location.y = height + 16;
  }
  
  
  void update() {
    float dx = location.x - mouseX;
    float dy = location.y - mouseY;
    
    if(dx > 0) acceleration.x -= 0.1;
    else if(dx < 0) acceleration.x += 0.1;
    
    if(dy > 0) acceleration.y -= 0.1;
    else if(dy < 0) acceleration.y += 0.1;
    
    velocity.add(acceleration);
    location.add(acceleration);
    
    checkEdges();
  }
  
}


Mover[] movers;

void setup() {
  size(640, 360);
  background(255);
  movers = new Mover[10];
  for(int i = 0; i < movers.length; i++){
    movers[i] = new Mover();  
  }
  
}

void draw() {
   background(255, 10);
  for(int i = 0; i < movers.length; i++){
    Mover current = movers[i];
    current.update();
    current.draw();
    
  }
}