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
   this.velocity = new PVector(random(-5,5), random(-5,5));
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

class Source {
  PVector position;
  PVector velocity;
  float time;
  float speed;
  
  
  Source() {
    time = random(0,100);
    speed= random(0.3,2);
   this.position = new PVector(width/2, height/2);
   this.velocity = new PVector(0, 0);
  }
  
  
  void update() {
    if(random(0,1) > 0.8) {
    this.velocity.x = speed * sin(time);
    this.velocity.y = speed * cos(time);
    }
    
    if(position.x < 0 || position.x > width) {
     this.velocity.x *= -1; 
    }
    if(position.y < 0 || position.y > height) {
     this.velocity.y *= -1; 
    }
    
    if(position.x < 0) {
     position.x += 1; 
    }
    if(position.x > width) {
     position.x -= 1; 
    }
    
    if(position.y < 0) {
     position.y += 1; 
    }
    if(position.y > height) {
     position.y -= 1; 
    }
    
    time += PI/360;
    
    position.add(velocity);
    if(random(0,1) > 0.99) {
        entities.add(new Circle(position.x, position.y, random(1, 100)));
    }
    
   
  }
  
  void draw() {
    // nothing to draw
    //stroke(10);
    //fill(100,100,0);
    //rectMode(CENTER);
    //ellipse(position.x, position.y, 10,10);
  }
}



void keyPressed() {
  if(key == 's') {
    
  sources.clear();
  } else if(key == 'c') {
   entities.clear(); 
  }
}

void mousePressed() {
  sources.add(new Source());
}


ArrayList<Circle> entities = new ArrayList<Circle>();
ArrayList<Source> sources = new ArrayList<Source>();

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
 
 for(Source source : sources) {
  source.update();
  source.draw();
 }
}