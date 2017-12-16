class DNA {
  float[] genes;
  int len ;

  DNA crossover(DNA partner) {
    DNA child = new DNA(min(partner.len, len));
    int crossoverpoint = int(random(0, min(len, partner.len)));

    for (int i = 0; i < min(partner.len, len); i++) {
      if (i < crossoverpoint) child.genes[i] = partner.genes[i];
      else child.genes[i] = genes[i];
    }
    return child;
  }

  void mutate(float mutation_rate) {
    for (int i = 0; i <len; i++) {
      if (random(1) < mutation_rate) genes[i] = random(0, 1);
    }
  }

  DNA(int len) {
    genes = new float[len];
    this.len = len;
    for (int i = 0; i < genes.length; i++) {
      genes[i] = random(0, 1);
    }
  }
}


class Face { 

  DNA dna;
  float fitness;

  Face() {
    dna = new DNA(20);
    fitness = 0;
  }

  void display(float x, float y) {
    pushMatrix();
    translate(x, y);
    colorMode(RGB, 1);

    float r = map(dna.genes[0], 0, 1, 0, 70);
    color c = color(dna.genes[1], dna.genes[2], dna.genes[3]);
    float eye_y = map(dna.genes[4], 0, 1, 0, 5);
    float eye_x = map(dna.genes[5], 0, 1, 0, 10);
    float eye_size = map(dna.genes[6], 0, 1, 0, 10);
    color eyecolor = color(dna.genes[7], dna.genes[8], dna.genes[9]);
    color mouthColor = color(dna.genes[10], dna.genes[11], dna.genes[12]);
    float mouth_y = map(dna.genes[13], 0, 1, 0, 25);
    float mouth_x = map(dna.genes[14], 0, 1, -25, 25);
    float mouthw = map(dna.genes[15], 0, 1, 0, 50);
    float mouthh = map(dna.genes[16], 0, 1, 0, 10);

    rectMode(CENTER);

    fill(c);
    ellipse(0, 0, r, r);

    fill(eyecolor);
    ellipse(eye_x, eye_y, eye_size, eye_size);
    ellipse(eye_x + mouthw, eye_y, eye_size, eye_size);

    fill(mouthColor);

    rect(mouth_x, mouth_y, mouthw, mouthh);
    popMatrix();  
}
}

class Population {
  Face[] population;
  ArrayList<Face> mating_pool;
  float mutation_rate;
  int generation_count;

  Population(int size, float mutation_count) {
    population = new Face[size];
    this.mutation_rate = mutation_count;

    for (int i = 0; i <population.length; i++) {
      population[i] = new Face();
    }
  }
  void selection() {
    mating_pool = new ArrayList<Face>();
    for (int i = 0; i<population.length; i++) {
      int n = int(population[i].fitness * 100);
      for (int j = 0; j<n; j++) {
        mating_pool.add(population[i]);
      }
    }
  }
  void evolve() {
   selection();
   reproduction();
   generation_count++;
  }

  void display() {
   for(int i = 0; i < min((width-20)/90, population.length); i++) {
    rectMode(CENTER);
    fill(0,0);
    rect(80 + i*90, 80, 70,70);
    population[i].display(80 + i * 90, 80);
    
    
   colorMode(RGB, 255);
   fill(0);
    
    text("" + population[i].fitness ,50+i*90, 150);
   }
   colorMode(RGB, 255);
   fill(0);
   text("Generation #: "+generation_count, 20, height-20);
   
  }

  void rollover(float x, float y) {
   if(y > 45 && y < 115 && x > 10 && x < width-10) {
          int pos = int((x-10)/90);
          if(pos < population.length){
             population[pos].fitness++; 
          }
     
   }
  }

  void reproduction() {
    if (mating_pool.size() == 0) {
      for (int i = 0; i <population.length; i++) {
        population[i] = new Face();
      }
    } else {
      for(int i = 0; i<population.length; i++) {
       int a = int(random(mating_pool.size()));
       int b = int(random(mating_pool.size()));
       
       Face parentA = mating_pool.get(a);
       Face parentB = mating_pool.get(b);
       
       Face child = new Face();
       child.dna = parentA.dna.crossover(parentB.dna);
       population[i] = child;
       population[i].dna.mutate(mutation_rate);
      }
      
    }
  }
}



Population population;

void setup() {
  size(780, 200);
  float mutationRate = 0.05;
  population =new Population(10, mutationRate);
}

void draw() {
  background(255);
  population.display();
  
  population.rollover(mouseX, mouseY);
}

void mousePressed() {
 population.evolve(); 
}