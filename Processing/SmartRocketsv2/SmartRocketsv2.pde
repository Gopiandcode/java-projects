Population population;
ArrayList<Obstacle> obstacles;
void setup() {
  size(640, 360);
  population = new Population(0.05, 1, 300, 20, new Obstacle(width/2-10,20,20,20), new PVector(width/2, height-20));
  obstacles = new ArrayList<Obstacle>();
  obstacles.add(new Obstacle(width/2-150, height/2, 300, 20));
}


void draw() {
  background(255);
  population.run(obstacles);
  text("Generation #: " + population.generations,20, 20);
  text("Cycles left: " + (population.lifetime - population.current_time), 20, 40);
  text("Average fitness: " + population.avgfit, 20,60); 
  
   population.target.display();
   for(Obstacle o: obstacles) {
    o.display(); 
   }
 
}