import java.util.Iterator;
void setup() {
   background(10, 10, 90);
   size(1080, 720);
}

RippleManager system = new RippleManager();

void draw() {
  
   background(10, 10, 90);
   system.update();
   system.draw();
  
}


class RippleManager {
   ArrayList<Ripple> ripples = new ArrayList<Ripple>();
   
   void update() {
    if(ripples.size() < 30) {
      if(Math.random() > 0.9)
      ripples.add(new Ripple(
      (float)Math.random() * width, (float)Math.random() * height, 
      (float)Math.random() * 100 + 100, 0.5, 
   (float)Math.random() * 30 + 10,(float) Math.random() * 80 + 10, (float)Math.random() * 20 + 100,(float)Math.random() * 155 + 100));
    }
    Iterator<Ripple> it = ripples.iterator();
    while(it.hasNext()) {
     Ripple p = it.next();
     p.update();
     if(!p.isAlive()) {
       it.remove();
     }
    }
   }
   
   void draw() {
     for(Ripple p : ripples) {
       p.draw();
     }
   }
}

class Ripple {
 float x;
 float y;
 float t;
 float tSpeed;
 
 float r;
 float g;
 float b;
 float alpha;
 
 boolean isAlive() {
   return t > 0;
 }
 
 Ripple(float x, float y, float t, float tSpeed, float r, float g, float b, float alpha) {
   this.x = x;
   this.y = y;
    this.t = t;
    this.tSpeed= tSpeed;
    this.r = r;
    this.g = g;
    this.b = b;
    this.alpha = alpha;
 }
 
 void update() {
  t -= tSpeed; 
 }
 
 void draw() {
   if(t > 0) {
     fill(r, g, b, min(alpha, t));
     noStroke();
     rectMode(CENTER);
     ellipse(x,y, t, t);
   }
   
 }
  
}