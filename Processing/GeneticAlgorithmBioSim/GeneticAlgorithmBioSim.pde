import java.util.Iterator;

class DNA {
  float[] genes;
  DNA() {
    genes = new float[3];
    for (int i = 0; i<genes.length; i++) {
      genes[i] = random(0, 1);
    }
  }

  DNA crossover(DNA partner) {
    DNA child = new DNA();
    for (int i = 0; i<genes.length; i++) {
      if (random(1) < 0.5) child.genes[i] = genes[i];
      else child.genes[i] = partner.genes[i];
    }
    return child;
  }

  void mutate(float mutation_rate) {
    for (int i = 0; i<genes.length; i++) {
      if (random(1) < mutation_rate) {
        genes[i] = random(0, 1);
      }
    }
  }

  DNA copy() {
    DNA child = new DNA();
    for (int i = 0; i<genes.length; i++) {
      child.genes[i] = genes[i];
    }
    return child;
  }
}

class Bloop {
  float health = 10000;
  PVector location;
  float r;
  float maxspeed;
  float xoff, yoff;

  float aphrodesiac;
  float aggressiveness;
  DNA dna;

  Bloop(float x, float y, DNA dna) {
    location = new PVector(x, y);
    xoff = random(0, 100);
    yoff = random(0, 100);

    this.r = map(dna.genes[0], 0, 1, 0, 60);
    this.maxspeed=  map(dna.genes[0], 0, 1, 0, 10);
    this.dna = dna;


    this.aphrodesiac = dna.genes[1];
    this.aggressiveness = dna.genes[2];
  }

  Bloop reproduce() {
    if (random(1) < 0.0005) {
      DNA childDNA = dna.copy();
      childDNA.mutate(0.01);
      return new Bloop(location.x, location.y, childDNA);
    } else {
      return null;
    }
  }

  Bloop reproduce(Bloop partner) {
    DNA childDNA = dna.crossover(partner.dna);
    childDNA.mutate(0.01);
    return new Bloop(location.x, location.y, childDNA);
  }

  boolean dead() {
    return health < 0.0;
  }

  void eat(ArrayList<PVector> food) {
    for (int i = food.size()-1; i >=0; i--) {
      PVector foodlocation = food.get(i);
      float d = PVector.dist(location, foodlocation);
      if (d < r/2) {
        health += 100;
        food.remove(i);
      }
    }
  }

  void interact(ArrayList<Bloop> bloops, ArrayList<Bloop> children) {
    if (health <= 0) return;

    for (Bloop b : bloops) {
      if (health <= 0) return;
      if (PVector.dist(location, b.location) < r/2) {
        if (random(1) < 0.01) {
          if (b.aggressiveness < aphrodesiac) {
            children.add(reproduce(b));
            health = 0;
            b.health = 0;
          }
        } 
      }
    }
  }

  void run() {
    update();
    display();
  }

  void update() {
    float vx = map(noise(xoff), 0, 1, -maxspeed, maxspeed);
    float vy = map(noise(yoff), 0, 1, -maxspeed, maxspeed);
    PVector velocity = new PVector(vx, vy);
    xoff += 0.01;
    yoff += 0.01;
    location.add(velocity);
    health -=1 * r/2;
    if (location.x > width) {
      location.x = 0;
    } else if (location.x < 0) {
      location.x = width;
    }

    if (location.y > height) {
      location.y = 0;
    } else if (location.y < 0) {
      location.y = height;
    }
  }

  void display() {
    rectMode(CENTER);
    fill(0, r);
    ellipse(location.x, location.y, r, r);
  }
}


class World {
  ArrayList<Bloop> bloops;
  ArrayList<PVector> food;

  World(int num) {
    bloops = new ArrayList<Bloop>();
    food = new ArrayList<PVector>();

    for (int i = 0; i<num; i++) {
      bloops.add(new Bloop(random(width), random(height), new DNA()));
    }
  }

  void run() {
    for(int i = 0; i<random(10); i++) {
      if(random(1) < 0.01) food.add(new PVector(random(width),random(height)));
    }
    
    for (PVector vector : food) {
      fill(0);
      ellipse(vector.x, vector.y, 2, 2);
    }

    ArrayList<Bloop> next =  new ArrayList<Bloop>();
    Iterator<Bloop> it = bloops.iterator();
    while (it.hasNext()) {
      Bloop b = it.next();

      if (b.dead()) {
        it.remove();
        food.add(b.location);
      } else {
        b.interact(bloops, next);
        b.run();
        b.eat(food);
      }
    }
    bloops.addAll(next);
  }
}

World world;
void setup() {
  size(640, 360); 
  world = new World(50);
}

void draw() {
  background(255);
  world.run();
}