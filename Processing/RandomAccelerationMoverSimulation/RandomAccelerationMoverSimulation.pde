class Mover {
  PVector location;
  PVector velocity;
  PVector acceleration;
  
  
  void draw() {
    stroke(10);
    fill(175);
    ellipse(location.x, location.y, 16,16);
  }
  
  
  void checkEdges() {
    if(location.x > width) {
      location.x = 0;
    }
    else if(location.x < 0) {
      location.x = width;
    }
    if(location.y > height) {
      location.y = 0;
    } else if(location.y < 0) {
      location.y = height;
    }
  }
  
  
  void update() {
    acceleration.x = random(-0.01, 0.01);
    acceleration.y = random(-0.01, 0.01);
    velocity.add(acceleration);
    location.add(velocity);
    checkEdges();  
}
  
  Mover() {
    location = new PVector(random(width), random(height));
    velocity = new PVector(0,0);
    acceleration = new PVector(random(-0.01, 0.01), random(-0.01, 0.01));
  }
}

Mover[] locations;
void setup() {
  locations = new Mover[5];
  for(int i = 0; i < locations.length; i++) {
    locations[i] = new Mover();
  }
  size(640, 360);
  background(255);
}

void draw() {
  background(255, 1);
  for(int i = 0; i < locations.length; i++) {
    locations[i].update();
    locations[i].draw();
  }
}