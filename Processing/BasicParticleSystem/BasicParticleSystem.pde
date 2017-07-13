import java.util.Iterator;
class Particle {
  PVector location;
  PVector velocity;
  PVector acceleration;
  float lifespan;
  float mass;
  
  Particle(PVector l) {
    location = l.get();
    acceleration = new PVector(0, 0.05);
    velocity =new PVector(random(-1, 1), random(-1, 1));
    mass = random(10);
    lifespan = 255 * mass/10;
  }
  
  void update() {
    velocity.add(acceleration);
    location.add(velocity);
    lifespan -= 2;
  }
  
  void run() {
    update();
    display();
  }
  
  void applyForce(PVector act) {
    PVector force = act.copy();
    force.div(mass);
    acceleration.add(force);
  }
  
  void display() {
    stroke(0, lifespan);
    fill(175, lifespan);
    rectMode(CENTER);
    ellipse(location.x, location.y, mass*0.8,mass*0.8);
  }
  
  boolean isDead() {
    if(lifespan < 0.0) return true;
    else return false;
  }
}

class ParticleSystem {
  ArrayList<Particle> particles;
  PVector origin;
  
  ParticleSystem() {
    this( new PVector(width/2, 80));
  }
  
  ParticleSystem(PVector origin) {
    this.origin = origin.copy();
    particles = new ArrayList<Particle>();
  }
  
  void addParticle() {
    particles.add(new Particle(origin));
  }
  
  void addParticle(Particle p) {
    particles.add(p);
  }
  
  void addParticle(int n) {
    for(int i = 0; i<n; i++) 
    particles.add(new Particle(origin));
  }
  
  void run() {
    Iterator<Particle> it = particles.iterator();
    while(it.hasNext()) {
      Particle p = it.next();
      p.run();
      if(p.isDead()) it.remove();
    }
  }
  
    
  int getCount() {
    return particles.size();
  }
  
  
  void setOrigin(PVector origin) {
    this.origin = origin.copy();
  }
}

ParticleSystem ps;
void setup() {
  size(640, 360);
  ps = new ParticleSystem();
  ps.addParticle(100);
}

void draw() {
  fill(255, 10);
   rect(0,0, width*2, height*2);
  ps.run();
  if(ps.getCount() < 100) ps.addParticle(100-ps.getCount());
  
  ps.setOrigin(new PVector(mouseX, mouseY));

}