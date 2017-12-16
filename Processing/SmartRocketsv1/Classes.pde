class DNA {
  PVector[] genes;
  float maxforce;
  float fitness;

  void fitness(PVector location, PVector target, float max_distance) {
      float dist = PVector.dist(location, target); 
      float max_dist = sqrt(width * width + height * height);
      fitness = 1 - dist/max_dist;
      fitness = fitness * fitness * fitness;
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
        genes[i] = PVector.random2D();;
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

  Rocket(float x, float y, DNA dna, float mass) {
    location = new PVector(x, y);
    this.velocity = new PVector();
    this.acceleration = new PVector();
    this.dna = dna;
    this.mass = mass;
    startX = x;
    startY = y;
  }


  void applyForce(PVector f) {
    PVector force = f.copy();
    force.div(mass);
    acceleration.add(force);
  }

  void update() {

    velocity.add(acceleration);
    location.add(velocity);
    acceleration.mult(0);
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

  void run() {
    applyForce(dna.genes[geneCounter]);
    geneCounter++;
    update();
    display();
}
}


class Population {
  float mutationRate;
  Rocket[] population;
  PVector target;
  PVector start;
  ArrayList<Rocket> matingPool;
  int generations = 0;
  int current_time = 0;
  float maxforce;
  int lifetime;
  float avgfit = 0;

  Population(float mutationRate, float maxforce, int lifetime, int population_size, PVector target, PVector start) {
   this.mutationRate = mutationRate;
   this.maxforce = maxforce;
   this.lifetime = lifetime;
   population = new Rocket[population_size];
   this.target = target.copy();
   this.start = start.copy();
   
   for(int i = 0; i <population.length; i++) {
    population[i] =new Rocket(start.x, start.y, new DNA(lifetime, maxforce), 2); 
   }
  }

  void fitness() {
    float distance = PVector.dist(target, start);
    float summation = 0;
    for (int i = 0; i<population.length; i++) {
      population[i].dna.fitness(population[i].location, target, distance);
    summation += population[i].dna.fitness;
  }
  avgfit = summation/population.length;
}
  void selection() {
    matingPool = new ArrayList<Rocket>();
    for (int i = 0; i<population.length; i++) {
      int n = (int)(population[i].dna.fitness * 100);
      
      for (int j =0; j< n; j++) {
        matingPool.add(population[i]);
      }
    }
  }

  void reproduction() {
    float size = matingPool.size();
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

  void live() {
    current_time++;
    for (int i =0; i<population.length; i++) population[i].run();
  }

  void evolve() {
    generations++;
        fitness();
    selection();
    reproduction();
  }
  
  void run() {
   live();
   if(current_time >= lifetime) {
    current_time = 0;
    evolve();
   }
  }
}