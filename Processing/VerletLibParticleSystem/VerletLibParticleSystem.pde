import toxi.physics2d.*;
import toxi.physics2d.behaviors.*;
import toxi.geom.*;

VerletPhysics2D physics;

class Particle extends VerletParticle2D {
  float r;
  Particle(Vec2D loc) {    
    super(loc);
    r = 4;
    physics.addBehavior(new AttractionBehavior2D(this, r*2, -1));
  }
  
  void display() {
    fill(0,150);
    stroke(0);
    ellipse(x,y,r*2, r*2);
  }
}

class Attractor extends VerletParticle2D {
  float r;
  Attractor(Vec2D loc) {
    super(loc);
    r = 24;
    physics.addBehavior(new AttractionBehavior2D(this, width, 0.1));
  }
  
  void display() {
    fill(0,150);
    stroke(0);
    ellipse(x,y, r*2, r*2);
  }
  
}
 
 Attractor attractor;
 ArrayList<Particle> particles;
 void setup() {
   size(640, 360);
   physics = new VerletPhysics2D();
    Vec2D center = new Vec2D(width/2, height/2);
    attractor = new Attractor(new Vec2D(width/2, height/2));
    physics.addParticle(attractor);
    particles = new ArrayList<Particle>();
    for(int i = 0; i<10; i++){
      particles.add(new Particle(center.add(Vec2D.randomVector())));
    physics.addParticle(particles.get(i));  
    physics.setWorldBounds(new Rect(0,0, width, height));
  }
    
 }
 
 
 void draw() {
   background(255);
   
 
   if(mousePressed) {
    attractor.lock();
    attractor.set(new Vec2D(mouseX, mouseY));
    attractor.unlock();
     
   }
 
 physics.update();
 
 
 
 
 
 attractor.display();
 
 for(Particle p: particles) {
  p.display(); 
 }
 }