class Point {
   float x;
   float y;
   
   Point(float x, float y) { this.x = x; this.y = y;}
   
   boolean isEqual(Point other) {
     return (other.x == x && other.y == y);
   }
   
   void display() { point(x,y);}
  
}
class Oscillator {
  PVector angle;
  PVector velocity;
  PVector amplitude;
  ArrayList<Point> points;
  
  
  Oscillator() {
    angle = new PVector();
    velocity = new PVector(random(-0.05, 0.05), random(-0.05, 0.05));
    amplitude = new PVector(random(width/2), random(height/2));
    points = new ArrayList<Point>();
  }
  
  void update() {
   angle.add(velocity); 
  }
  
  void display() {
   stroke(10);
   fill(175);
   rectMode(CENTER);
   float x = width/2 + amplitude.x * cos(angle.x);
   float y = height/2 + amplitude.y * sin(angle.y);
   line(width/2, height/2, x,y);
   ellipse(x,y, 16,16); 
   boolean shouldAdd = true;
   Point next = new Point(x,y);
   for(int i = 0; i < points.size(); i++) {
     Point current = points.get(i);
     if(shouldAdd) shouldAdd = !current.isEqual(next);
     current.display();
   }
   if(shouldAdd) {
      points.add(next);
      next.display();
   }
  }
  
}


Oscillator[] bobs;

void setup() {
  background(255);
  size(640, 360);
  bobs = new Oscillator[10];
  for(int i = 0; i < bobs.length; i++) {
    bobs[i] = new Oscillator();
  }
}


void draw() {
  background(255);
  for(int i = 0; i<bobs.length; i++) {
    bobs[i].update();
    bobs[i].display();
  }
}