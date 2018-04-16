import java.util.Iterator;
void setup() {
   background(10, 10, 90);
   size(1080, 720);
}

ParticleSystem system = new ParticleSystem();

void draw() {
  
   background(10, 10, 90);
  system.update();
  system.draw();
  
}

class ParticleSystem {
   ArrayList<Particle> particles = new ArrayList<Particle>();
   
   void update() {
    if(particles.size() < 30) {
      if(Math.random() > 0.8)
      particles.add(new Particle((float)Math.random() * width, (float)Math.random() * height/4 + height * 3 /4, (float)Math.random() * 10,(float) Math.random() * -5, 
  (float) Math.random() * 200 + 80, 80, 200, 
   (float)Math.random() * 1000 + 1000, 10 * PI, 
   (float)Math.random() * 30 + 10,(float) Math.random() * 80 + 10, (float)Math.random() * 20 + 100, 
   (float)Math.random() * 255, 80, 150));
    }
    Iterator<Particle> it = particles.iterator();
    while(it.hasNext()) {
     Particle p = it.next();
     p.update();
     if(!p.isAlive()) {
       it.remove();
     }
    }
   }
   
   void draw() {
     for(Particle p : particles) {
       p.draw();
     }
   }
}

class Particle {
   float size;
   float actualSize;
   
   float x;
   float xvel;
   float y;
   float yvel;
   float t;
   
   float minSize;
   float maxSize;
   float tSpeed;
   
      float red;
      float green;
      float blue;
   float alpha;
   float minAlpha; 
   float maxAlpha;
   float currentAlpha;
   
   boolean isAlive() {
     return t > 0;
   }
   
   
   Particle(float x, float y, float xvel, float yvel, 
   float size, float minSize, float maxSize, 
   float t, float tSpeed, 
   float red, float green, float blue, 
   float alpha, float minAlpha, float maxAlpha) {
     this.x = x;
     this.y = y;
     this.size = size;
     this.t = t;
     this.xvel = xvel;
     this.yvel = yvel;
     this.actualSize = size;
     this.minSize = minSize;
     this.maxSize = maxSize;
     this.tSpeed = tSpeed;
     this.red = red;
     this.green = green;
     this.blue=blue;
     this.alpha = alpha;
     this.minAlpha = minAlpha;
     this.maxAlpha = maxAlpha;
     this.currentAlpha = alpha;
   }
   
   
   void update() {
     if(Math.random() > 0.5) {
       x += (float)(xvel * Math.sin((float)t/tSpeed));
     }
     if(Math.random() > 0.3) {
       y += yvel;
     }
     actualSize = max(min(size * sin(t/tSpeed), maxSize), minSize);
     currentAlpha =  max(min(alpha * cos(t/tSpeed), maxAlpha), minAlpha);
     t--;
   }
   
   void draw() {
     fill(red, green, blue, currentAlpha);
     noStroke();
     rectMode(CENTER);
     ellipse(x, y, actualSize, actualSize);
   }
}