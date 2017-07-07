class Mover {
  
  PVector location;
  PVector velocity;
  PVector acceleration;
  float mass;
  
  void applyForce(PVector force) {
    acceleration = PVector.add(acceleration, PVector.mult(force, 1/mass));
  }
  
  
  void update() {
    velocity.add(acceleration);
    velocity.limit(10);
    location.add(velocity);
    acceleration.mult(0);
    
   
    checkEdges();
  }
  
 void checkEdges() {
    if(location.x > width) {
      location.x = -(16 * mass/16);
      velocity.x *= -1;
    }
    else if (location.x < -(16 * mass/16)) {
      location.x = width;
      velocity.x *= -1;
    }
    
    if(location.y > height - (16 * mass/16)) {
      location.y = height - (16 * mass/16);
      velocity.y *= -1;
    }
    else if(location.y < 0) {
      location.y = 0;
      velocity.y *= -1;
      }
     
   }
 
 void draw() {
   stroke(5 * mass);
   fill(mass , velocity.mag() * 40, acceleration.mag() * 90);
   ellipse(location.x, location.y, (16 * mass/16), (16 * mass/16));
 }
 
 Mover() {
   location = new PVector(random(width), random(height));
   velocity = new PVector(0,0);
   acceleration = new PVector(random(-0.01, 0.01), random(-0.01, 0.01));
   mass = random(16);

 }
 
 boolean isInside(Liquid l) {
   if(location.x > l.x && location.x < l.x+l.w && location.y > l.y && location.y < l.y + l.h)
   return true;
   else return false;
 }
 
 void drag(Liquid l) {
      float speed =  velocity.mag();
      float dragMagnitude = l.c * speed * speed ;
      
      PVector drag = velocity.copy();
      drag.mult(-1);
      drag.normalize();
      drag.mult(dragMagnitude);
      
      applyForce(drag);
  
   
 }
}


class Liquid {
  float x,y,w,h;
  float c;
  
  Liquid(float x, float y, float w, float h, float c) {
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
    this.c = c;
  }
  
  void display() {
    noStroke();
    fill(c*175, 100);
    rect(x,y, w, h);  
  }
}

Mover[] particles;
Liquid liquid;
void setup() {
  size(640,360);
  background(255);
  liquid = new Liquid(0, height/2, width, height/2, 0.6);
  particles = new Mover[32];
  for(int i = 0; i<particles.length; i++)
    particles[i] = new Mover();
}

void draw() {
  background(255, 10);
  liquid.display();
   PVector wind = new PVector(random(-0.01, 0.01), 0);
   PVector gravity = new PVector(0, 0.01);
  
  for(int i = 0; i<particles.length; i++) {
    
    
      float c = 0.01;
      PVector friction = particles[i].velocity.copy();
      friction.mult(-1);
      friction.normalize();
      friction.mult(c);
      
      if(particles[i].isInside(liquid)) particles[i].drag(liquid);
    particles[i].applyForce(friction);
     // apply gravity
     particles[i].applyForce(wind);
     particles[i].applyForce(PVector.mult(gravity, particles[i].mass));
    particles[i].update();
    particles[i].draw();
  }
}