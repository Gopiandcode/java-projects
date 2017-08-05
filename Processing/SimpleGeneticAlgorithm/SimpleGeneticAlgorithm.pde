class DNA {
  float fitness;
  char[] genes;

  void fitness(String target) {
    int score = 0;
    for (int i = 0; i <genes.length; i++) {
      if (genes[i] == target.charAt(i))
        score++;
    }
    fitness = float(score)/target.length();
  }

  DNA(int length) {
    genes = new char[length];
    for (int i = 0; i <genes.length; i++ ) {
      genes[i] = (char)random(23, 128);
    }
  }

  DNA crossover(DNA partner) {
    DNA child = new DNA(genes.length);

    int midpoint = int(random(genes.length));
    for (int i =0; i<genes.length; i++) {
      if (i > midpoint) child.genes[i] = genes[i];
      else child.genes[i] = partner.genes[i];
    }

    return child;
  }

  void mutate(float mutationRate) {
    for (int i  =0; i <genes.length; i++) {
      if (random(1) < mutationRate) {
        genes[i] = (char) random(32, 128);
      }
    }
  }
}

class Population {
  DNA[] population;
  int generation_count = 0;
  float mutation_rate;
  String target;

  DNA best;
  float average_fitness;

  Population(int population_size, float mutation_rate, String target) {
    population = new DNA[population_size];
    this.mutation_rate = mutation_rate;
    this.target = target;

    float fitness = 0;
    float best_seen = 0;
    for (int i = 0; i <population.length; i++) {
      population[i] = new DNA(target.length()); 
      population[i].fitness(target);
      fitness += population[i].fitness;
      if (population[i].fitness > best_seen) {
        best_seen = population[i].fitness;
        best = population[i];
      }
    }
    average_fitness = fitness / population_size;
  }


  void generate() {
    if((new String(best.genes)).equals(target)) return;
    generation_count++;
    float fitness = 0;
    float best_seen = 0;
    for (int i = 0; i <population.length; i++) {
      population[i].fitness(target);
      fitness += population[i].fitness;
      if (population[i].fitness > best_seen) {
        best_seen = population[i].fitness;
        best = population[i];
      }
    }
    average_fitness = fitness / population.length;
    
   ArrayList<DNA> mating_pool = new ArrayList<DNA>();
   
   for(int i = 0; i <population.length; i++) {
     float n = population[i].fitness * 100;
     
     for(int j = 0; j < n; j++) {
       mating_pool.add(population[i]);
     }
   }
   
   for(int i = 0; i <population.length; i++) {
    int a = int(random(mating_pool.size())); 
     int b = int(random(mating_pool.size()));
     
     DNA parentA = mating_pool.get(a);
     DNA parentB = mating_pool.get(b);
     DNA child = parentA.crossover(parentB);
     child.mutate(mutation_rate);
     population[i] = child;
   }
  }
}

Population population;

void setup() {
  size(640, 360);
  population = new Population(150, 0.01, "to be or not to be");
}

void draw() {
  background(255);

  fill(0);
  textSize(10);

  text("Best phrase:",20,20);
  
  textSize(30);
  
  text(new String(population.best.genes), 20, 60);
  
  textSize(10);

  for(int i = 0; i < (height - 40)/20; i++) {
   text(new String(population.population[i].genes), width-180, 20 + i * 20);
  }

  text("Total generations: " + population.generation_count, 20, height-60);
  text("Average fitness: " + population.average_fitness, 20, height-40);
  text("Mutation rate: " + population.mutation_rate, 20, height-20);
  
  population.generate();
}