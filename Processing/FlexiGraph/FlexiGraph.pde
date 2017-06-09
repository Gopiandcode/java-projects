graph Graph = new graph(80, 610, 600, 1140, 0, 20000, 5000, 10, 5, "Random Ints", 1000);
void setup() {
  size(1280, 720);
  Graph.startTiming();
}




void draw() {
  background(#ffffff);
  Graph.update();
  
}