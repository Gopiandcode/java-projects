Population population;

void setup() {
  size(640, 360);
  population = new Population(0.05, 1, 300, 20, new PVector(width/2, 20), new PVector(width/2, height-20));
}


void draw() {
  background(255);
  population.run();
  text("Generation #: " + population.generations,20, 20);
  text("Cycles left: " + (population.lifetime - population.current_time), 20, 40);
  text("Average fitness: " + population.avgfit, 20,60); 
 
   rectMode(CENTER);
   ellipse(population.target.x, population.target.y, 16,16);
}