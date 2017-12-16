import java.util.Iterator;
class Particle {
  PVector location;
  PVector velocity;
  PVector acceleration;
  float lifetime;
  
  Particle(PVector l) {
    acceleration = new PVector(0,0.05);
    velocity = new PVector(random(-1,1), random(-2,0));
    location = l.copy();
    lifetime = 255;
  }
  
  void run() {
    update();
    display();
  }
  
  void update() {
    velocity.add(acceleration);
    location.add(velocity);
    lifetime -= 2;
  }
  
  boolean isDead() {
    return lifetime < 0.0;
  }
  
  void display() {
    fill(0, lifetime);
    stroke(0, lifetime);
    rectMode(CENTER);
    ellipse(location.x, location.y, 8,8);
  }
  
}

class ParticleSystem {
  ArrayList<Particle> particles;
  PVector origin;
  
  ParticleSystem(PVector origin) {
    this.origin = origin.copy();
    particles = new ArrayList<Particle>();
  }
  
  void run() {
    Iterator<Particle> it = particles.iterator();
    while(it.hasNext()) {
      Particle p = it.next();
      p.run();
      if(p.isDead()) it.remove();
    }
  }
  
  void addParticle(int n) {
    for(int i = 0; i<n; i++) {
       float dice = random(0,1);
       if(dice > 0.5)
        particles.add(new Particle(origin));
        else
          particles.add(new Confetti(origin));
    }
  }
  
  void addParticle() {
    addParticle(1);
  }
  
  boolean isDead() {
    return particles.size() == 0;
  }
}


class Confetti extends Particle {
  Confetti(PVector l) {
    super(l);
  }
  
  void display() {
    float theta = map(location.x, 0, width, 0, TWO_PI*2);
    rectMode(CENTER);
    fill(175, lifetime);
    stroke(0, lifetime);
    pushMatrix();
    translate(location.x, location.y);
    rotate(theta);
    rect(0,0, 8,8);
    popMatrix();  
  }
}
  


ArrayList<ParticleSystem> systems;

void setup() {
  size(640, 360);
  systems = new ArrayList<ParticleSystem>();
}

void mousePressed() {
  ParticleSystem ps = new ParticleSystem(new PVector(mouseX, mouseY));
  ps.addParticle(50);
  systems.add(ps);
}

void draw() {
  background(255);
  Iterator<ParticleSystem> it = systems.iterator();
  while(it.hasNext()) {
   ParticleSystem ps = it.next();
   ps.run();
   if(ps.isDead()) it.remove();
  }
}