import java.util.List;
// Graph Drawing algorithm
// TODO(Kiran): Implement algorithm
// Reference: A  Technique  for  Drawing  Directed  Graphs - Emden R. Gansner, Eleftherios  Koutsofios, Stephen  C.  North, Kiem-Phong Vo

Graph graph = new Graph(10);

void mousePressed() {
  int from = (int)random(10);
  int to   =  (int) random(10);
  int weight = (int) random(10);
  if(graph.isEdgeBetween(from,to))
  graph.removeEdgeBetween(from,to);
  else 
  graph.addEdgeBetween(from,to,weight);
  for(int i = 0; i < 10; i++) {
      println("[" + i + "]: " + graph.getNeighbors(i)); 
   }
   println("\n");
}

Node node;
void setup() {
   size(640, 360); 
   for(int i = 0; i < 10; i++) {
      println("[" + i + "]: " + graph.getNeighbors(i)); 
   }
   println("\n");
  node = new Node(width/2, height/2, "Example Node 23");
}

void draw() {
  background(255);
  
  node.draw();
}