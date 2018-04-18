import java.util.Iterator;
class Circle {
  PVector position;
  PVector velocity;
 float radius;
 
 float red;
 float green;
 float blue;
 float alpha;
 
 Circle(float x, float y, float radius) {
   this.position = new PVector(x,y);
   this.velocity = new PVector(random(-10,10), random(-10,10));
   this.radius = radius;
   
   this.red = random(255);
   this.green = random(255);
   this.blue = random(255);
   this.alpha = random(255);
 }
 
 void update() {
   position.add(velocity);
   velocity.mult(0.99);
 }
 
 
 boolean isOffScreen() {
  return position.x + radius/2 < 0 || position.x - radius/2 > width || position.y - radius/2 > height || position.y + radius/2 < 0; 
 }
 void draw() {
   noStroke();
   fill(red, green, blue, alpha);
   rectMode(CENTER);
   ellipse(position.x,position.y, radius, radius);
 }
}


void mousePressed() {
  entities.add(new Circle(mouseX, mouseY, random(1, 100)));
}


ArrayList<Circle> entities = new ArrayList<Circle>();

void setup() {
 background(0, 33, 100); 
 size(1280, 720);
}

void draw() {
  
 background(0, 33, 100); 
 Iterator<Circle> iter = entities.iterator();
 
 while(iter.hasNext()) {
  Circle c = iter.next();
  c.update();
  c.draw();
  if(c.isOffScreen()) {
   iter.remove(); 
  }
 }
}