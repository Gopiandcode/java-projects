import java.util.Iterator;
class Particle {
  PVector location;
  PVector velocity;
  PVector acceleration;
  float mass;
  float lifetime;
  
  Particle(PVector origin) {
    location = origin.copy();
    velocity = new PVector(random(-1,1), random(0, 1));
    acceleration = new PVector(0,0);
    mass = random(1,16);
    lifetime = mass * 255/16;
  }

  void render() {
  imageMode(CENTER);
  tint(255, lifetime);
  image(img, location.x, location.y);
  }
  
  void update() {
    velocity.add(acceleration);
    location.add(velocity);
    acceleration.mult(0);
    lifetime -= 2;
  }
  
  void run() {
     update();
     //display();
     render();
  }
  
  boolean isDead() {
    return lifetime < 0.0;
  }
  
  void applyForce(PVector force) {
    acceleration.add(PVector.div(force, mass));
  }
  
  void display() {
    stroke(0, lifetime);
    fill(0, lifetime);
    rectMode(CENTER);
    ellipse(location.x, location.y, 8, 8);
  }
  
}


class ParticleSystem {
  ArrayList<Particle> particles;
  PVector origin;
  
  ParticleSystem(PVector origin) {
    this.origin = origin.copy();
    particles = new ArrayList<Particle>();
  }
  
  void addParticle(int n) {
    
    for(int i = 0; i<n; i++) {
      Particle p = new Particle(origin);
      particles.add(p);
   }
  }
  
  void addParticle() {
    addParticle(1);
  }
  
  void run() {
    Iterator<Particle> it = particles.iterator();
    while(it.hasNext()) {
      Particle p = it.next();
      p.run();
      if(p.isDead()) {
       it.remove(); 
      } 
    }
    
    
  }
  
  PVector getForce() {
     return origin;
  }

  boolean isDead() {
    return particles.size() == 0;
  }
  
  void applyForce(PVector force) {
    for(Particle p : particles)
      p.applyForce(force);
  }
  
  void applyRepeller(Repeller r) {
    for(Particle p: particles) {
      PVector force = r.repel(p);
      p.applyForce(force);
    }
  }
  
}


class Repeller {
 float strength = 1000;
 PVector location;
 float r = 10;
 
 Repeller(float x, float y) {
   location = new PVector(x, y);
 }
 
 void display() {
   stroke(255, strength);
   fill(255, strength);
   ellipse(location.x, location.y, r*2, r*2);
 }
 
 PVector repel(Particle p) {
   PVector dir = PVector.sub(location, p.location);
   float d = dir.mag();
   dir.normalize();
   d = constrain(d, 5, 100);
   
   float force = -1 * strength / ( d* d);
   dir.mult(force);
   return dir;
 }
 
}
  

int count = 20;

ParticleSystem[] systems;
PImage img;

Repeller main;
void setup() {
  img = loadImage("image.png");
  img.resize(8,8);
  //background(255);
  size(640, 360);
  systems = new ParticleSystem[count];
  for(int i = 0; i<systems.length; i++) { 
    systems[i] = new ParticleSystem(new PVector(random(width), random(height)));
    systems[i].addParticle(20);
  }
  main = new Repeller(width/2, height/2);
}



void mousePressed() {
  Repeller repeller = new Repeller(mouseX, mouseY);
  for(ParticleSystem s : systems) 
  s.applyRepeller(repeller);
}

void draw() {
  //blendMode(ADD);
  background(255);
  for(int i = 0; i<systems.length; i++) {
    systems[i].applyRepeller(main);
  }
  
  for(int i = 0; i<systems.length; i++) {
    systems[i].run();
    if(systems[i].isDead() || systems[i].particles.size() < 15) systems[i].addParticle(20);
  }
}