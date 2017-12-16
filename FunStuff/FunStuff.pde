class BlackHole {
  PVector location;
  BlackHole(float x, float y) {
    location = new PVector(x,y);
  }
 
  void attract(Population p) {
    for(Thing a : p.things) {
        float dist = PVector.dist(location, a.location);
        dist = dist < 0.001 ? 0.001 : dist;
        float G = 10 / (dist * dist);
       a.applyForce(PVector.mult(PVector.sub(location, a.location),G)); 
    }
  }
  
  void draw() {
    fill(0);
    rectMode(CENTER);
    ellipse(location.x, location.y, 10, 10);
  }
  
  void consume(Population p) {
  }
}

class Population {
  ArrayList<Thing> things;
  Population(ArrayList<Thing> things) {
    this.things = things;
  }



  void draw() {
    for (Thing example : things) {
      example.draw();
    }
  }


  void update() {
    for (Thing example : things) {
      example.update();
      
      for(Thing other : things) {
        if(PVector.dist(other.location, example.location) < example.mass/2 + other.mass/2) {
          other.applyForce(PVector.sub(other.location, example.location));
        }
      }
      
    }
    //applyRandomForces();
  }



  void applyRandomForces() {
    for (Thing example : things) {
      example.applyForce(new PVector(random(-1, 1), random(-1, 1)));
    }
  }
}

class Thing {
  PVector location;
  PVector velocity;
  PVector acceleration;
  float mass;

  Thing(float x, float y) {
    location = new PVector(x, y);
    velocity = new PVector(random(-1, 1), random(-1, 1));
    acceleration = new PVector(0, 0);
    mass = random(5, 20);
  }

  void draw() {
    fill(100, 100, 10);
    rectMode(CENTER);
    ellipse(location.x, location.y, mass, mass);
  }

  void update() {
    velocity.add(acceleration);
    acceleration.mult(0);
    location.add(velocity);
    checkEdges();
    applyForce(PVector.mult(velocity, -0.1));
  }

  void checkEdges() {
    if (location.x > width || location.x < 0) {
      velocity.x *= -1;
    }

    if (location.y > height || location.y < 0) {
      velocity.y *= -1;
    }
  }


  void applyForce(PVector force) {
    acceleration.add(PVector.div(force, mass));
  }
}


Population population;
ArrayList<BlackHole> blackholes = new ArrayList<BlackHole>();

void setup() {
  size(640, 640);
  
  ArrayList<Thing> things = new ArrayList<Thing>();
  for (int i = 0; i < 10; i++)
    things.add(new Thing(random(width), random(height)));
  
  population = new Population(things);
}

void mousePressed() {
   blackholes.add(new BlackHole(mouseX, mouseY)); 
}

void draw() {
  background(255);
  for(BlackHole b : blackholes) {
     b.draw();
    b.attract(population);
  }
   
  population.update();
  population.draw();
}