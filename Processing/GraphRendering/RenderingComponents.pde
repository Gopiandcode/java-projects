class Node {
  PVector position;
  String name;
  
  Node(int x, int y, String name) {
   this.position = new PVector(x,y);
   this.name = name;
  }
  
  void draw() {
     float textWidth = textWidth(name);
     noFill();
     stroke(1);
     rectMode(CENTER);
     ellipse(position.x,position.y, textWidth+10, 50);
     fill(1);
     text(name, position.x - (textWidth)/2, position.y+5);
  }
}

class Edge {
 List<PVector> splinePoints;
 Node start;
 Node end;
 
 Edge(Node start, Node end, List<PVector> splinePoints) {
   this.start = start;
   this.end = end;
   this.splinePoints = splinePoints;
 }
 
 void draw() {
   noFill();
   stroke(0);
  beginShape();
  for(PVector controlPoint : splinePoints) {
   curveVertex(controlPoint.x, controlPoint.y); 
  }
  endShape();
 }
  
}

class Visualization {
   List<Node> nodes;
   List<Edge> edges;
   
   Visualization(Graph graph) {
     List<Integer> lambdaRank = rank(graph);
     
   }
   
   List<Integer> rank(Graph graph) {
      List<Integer> ranking = new ArrayList<Integer>();
      Graph internalGraph =  graph.copy();
      
      
      
      return ranking;
   }
   
   
   void draw() {
      for(Edge edge : edges) {
       edge.draw(); 
      }
      for(Node node : nodes) {
       node.draw(); 
      }
   }
  
}