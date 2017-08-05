
class DNA {
  PVector[] genes;
  float maxforce;
  float fitness;
  float recordDist = -1;

  void fitness(PVector location, Obstacle target, float finish_time) {
    float dist = PVector.dist(location, target.location); 
    if (dist < recordDist || recordDist == -1) recordDist = dist;
    fitness = (1/(finish_time * recordDist));
    //fitness = pow(fitness, 2);
  }

  DNA(int num, float maxforce) {
    genes = new PVector[num];
    for (int i = 0; i<genes.length; i++) {
      genes[i] = PVector.random2D();
      genes[i].mult(random(0, maxforce));
    }
  }

  DNA crossover(DNA partner) {
    DNA dna = new DNA(min(genes.length, partner.genes.length), (maxforce + partner.maxforce)/2);

    int crossoverpoint = int(random(dna.genes.length));
    for (int i = 0; i < dna.genes.length; i++) {
      if (i <= crossoverpoint) dna.genes[i] = genes[i];
      else dna.genes[i] = partner.genes[i];
    }

    return dna;
  }

  void mutate(float mutation_rate) {
    for (int i = 0; i<genes.length; i++) {
      if (random(1) <= mutation_rate) {
        genes[i] = PVector.random2D();
        ;
        genes[i].mult(random(0, maxforce));
      }
    }
  }
}

/*
  if(location.x > width || location.x < 0 || location.y > height || location.y < 0)
 isAlive = false;
 */
class Rocket {
  PVector location;
  PVector velocity;
  PVector acceleration;
  float mass;
  DNA dna;
  int geneCounter = 0;
  float startX, startY;
  boolean stopped = false;
  boolean hit_target = false;
  int finish_time = 0;

  Rocket(float x, float y, DNA dna, float mass) {
    location = new PVector(x, y);
    this.velocity = new PVector();
    this.acceleration = new PVector();
    this.dna = dna;
    this.mass = mass;
    startX = x;
    startY = y;
  }

  void obstacles(ArrayList<Obstacle> obstacles) {
    for (Obstacle obstacle : obstacles ) {
      if (obstacle.contains(location))
        stopped = true;
    }
  }

  void applyForce(PVector f) {
    PVector force = f.copy();
    force.div(mass);
    acceleration.add(force);
  }

  void update(Obstacle target) {
    velocity.add(acceleration);
    location.add(velocity);
    acceleration.mult(0);
    float dist = PVector.dist(location, target.location); 
    if (dist < dna.recordDist || dna.recordDist == -1) dna.recordDist = dist;
  }

  Rocket crossover(Rocket partner) {
    DNA offspring = dna.crossover(partner.dna);
    Rocket child = new Rocket(startX, startY, offspring, (mass + partner.mass)/2);
    return child;
  }



  void display() {
    fill(0, 175);
    stroke(0);
    float theta = velocity.heading();

    pushMatrix();
    translate(location.x, location.y);
    rotate(theta);
    triangle(-mass, mass, -mass, -mass, mass, 0);

    popMatrix();
  }

  void run(Obstacle target) {
    if (target.contains(location)) {
      hit_target=true;
    } else {
      finish_time++;
    }
    if (location.x > width || location.x < 0 || location.y > height || location.y < 0) stopped = true;
    if (!stopped) {
      applyForce(dna.genes[geneCounter]);
      geneCounter++;
      update(target);
    }
    display();
  }
}

class Obstacle {
  PVector location;
  float w, h;

  Obstacle(float x, float y, float w, float h) {
    location = new PVector(x, y);
    this.w = w;
    this.h = h;
  }


  void display() {
    pushMatrix();
    fill(0, 150); 
    rectMode(CORNER);
    rect(location.x, location.y, w, h);
    popMatrix();
  }


  boolean contains(PVector v) {
    return (v.x > location.x && v.x < location.x + w && v.y > location.y && v.y < location.y + h);
  }
}


class Population {
  float mutationRate;
  Rocket[] population;
  Obstacle target;
  PVector start;
  ArrayList<Rocket> matingPool;
  int generations = 0;
  int current_time = 0;
  float maxforce;
  int lifetime;
  float avgfit = 0;

  Population(float mutationRate, float maxforce, int lifetime, int population_size, Obstacle target, PVector start) {
    this.mutationRate = mutationRate;
    this.maxforce = maxforce;
    this.lifetime = lifetime;
    population = new Rocket[population_size];
    this.target = target;
    this.start = start.copy();

    for (int i = 0; i <population.length; i++) {
      population[i] =new Rocket(start.x, start.y, new DNA(lifetime, maxforce), 2);
    }
  }

  void fitness() {
    float summation = 0;
    for (int i = 0; i<population.length; i++) {
      population[i].dna.fitness(population[i].location, target, population[i].finish_time);
      if (population[i].stopped) population[i].dna.fitness *= 0.1;
      if (population[i].hit_target) population[i].dna.fitness *= 2;
      summation += (population[i].dna.fitness * 1000);
    }
    avgfit = summation/population.length;
  }
  void selection() {
    matingPool = new ArrayList<Rocket>();
    for (int i = 0; i<population.length; i++) {
      print("Population["+ i + "]: "+ population[i].dna.fitness + "\n");
      int n = (int)(population[i].dna.fitness * 100000);

      for (int j =0; j< n; j++) {
        matingPool.add(population[i]);
      }
    }
  }

  void reproduction() {
    float size = matingPool.size();
    if (size == 0) { 
      for (int i = 0; i <population.length; i++) {
        population[i] =new Rocket(start.x, start.y, new DNA(lifetime, maxforce), 2);
      }
      return;
    }
    for (int i = 0; i <population.length; i++) {

      int a = int(random(size));
      int b = int(random(size));

      Rocket parentA = matingPool.get(a);
      Rocket parentB = matingPool.get(b);
      Rocket child = parentA.crossover(parentB);
      population[i] = child;
      population[i].dna.mutate(mutationRate);
    }
  }

  void live(ArrayList<Obstacle> obstacles) {
    current_time++;
    for (int i =0; i<population.length; i++) {
      population[i].obstacles(obstacles); 
      population[i].run(target);
    }
  }

  void evolve() {
    generations++;
    fitness();
    selection();
    reproduction();
  }

  void run(ArrayList<Obstacle> obstacles) {
    live(obstacles);
    if (current_time >= lifetime) {
      current_time = 0;
      evolve();
    }
  }
}