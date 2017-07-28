import toxi.physics2d.*;
import toxi.physics2d.behaviors.*;
import toxi.geom.*;

VerletPhysics2D physics;

class Particle extends VerletParticle2D {
  
  Particle(Vec2D pos) {
   super(pos); 
  }
  
  void display() {
   fill(0,150);
   stroke(0);
   ellipse(x, y, 16,16);
  }
}


ArrayList<Particle> particles;

void setup() {
 size(640, 360); 
  physics = new VerletPhysics2D();
  physics.setWorldBounds(new Rect(0,0, width, height));
  physics.addBehavior(new GravityBehavior2D(new Vec2D(0,0.5)));
  particles = new ArrayList<Particle>();
  
  
  for(int i = 0; i<20; i++) {
   Particle particle = new Particle(new Vec2D(width/2, i*10+10));
   
    if(i == 0) {
      particle.lock();    
    } else {
      Particle prev = particles.get(i-1);
      VerletSpring2D spring = new VerletSpring2D(particle, prev,  10, 0.1);
      physics.addSpring(spring);
    }
   physics.addParticle(particle);
   
   particles.add(particle);
    
 }
}


void draw() {
  background(255);
  
  if(mousePressed) {
   Particle last = particles.get(particles.size()-1);
   last.lock();
   last.set(new Vec2D(mouseX, mouseY));
   last.unlock();
  }
  
  physics.update();
  stroke(0);
  noFill();
  beginShape();
  for(Particle p : particles) {
    vertex(p.x, p.y);
  }
  endShape();
  
  Particle tail = particles.get(particles.size()-1);
  tail.display();
}