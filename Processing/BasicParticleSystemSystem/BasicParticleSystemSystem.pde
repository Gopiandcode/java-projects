import java.util.Iterator;

class Particle {
  PVector location;
  PVector velocity;
  PVector acceleration;
  float lifetime;

  Particle(PVector location) {
    this.location = location.copy();
    this.velocity = new PVector(random(-0.5, 0.5), random(-0.5, 0.5));
    this.acceleration = new PVector(0, 0.05);
    lifetime = 255;
  }

  void update() {
    velocity.add(acceleration);
    location.add(velocity);
    lifetime -= 2;
  }

  void display() {
    stroke(0, lifetime);
    fill(0, lifetime);
    rectMode(CENTER);
    ellipse(location.x, location.y, 8, 8);
  }

  void run() {
    update();
    display();
  }

  boolean isDead() {
    return (lifetime < 0.0);
  }
}


class ParticleSystem {
  ArrayList<Particle> particles;
  PVector origin;

  ParticleSystem() {
    this(new PVector(width/2, height/8));
  }

  ParticleSystem(PVector origin) {
    this.origin = origin.copy();
    this.particles = new ArrayList<Particle>();
  }

  void addParticle() {
    particles.add(new Particle(origin));
  }

  void addParticle(int n) {
    for (int i = 0; i< n; i++) 
      particles.add(new Particle(origin));
  }

  void run() {
    Iterator<Particle> it = particles.iterator();
    while (it.hasNext()) {
      Particle p = it.next();
      p.run();
      if (p.isDead()) it.remove();
    }
  }
  
  boolean isDead() {
    return particles.size() == 0;
  }
}


ArrayList<ParticleSystem> systems;


void setup() {
  size(600,200);
  systems = new ArrayList<ParticleSystem>();
}

void mousePressed() {
  ParticleSystem sys = new ParticleSystem(new PVector(mouseX, mouseY));
  
  sys.addParticle(30);
  systems.add(sys);
}


void draw() {
  background(255);
  Iterator<ParticleSystem> it = systems.iterator();
  while(it.hasNext()) {
    ParticleSystem sys = it.next();
    sys.run();
    if(sys.isDead()) it.remove();
  }
}