class Nodes implements NodeSelector {
  ArrayList<Node> nodes;

  Nodes() {
    nodes = new ArrayList<Node>();
  }

  void addNode(Node node) {
    nodes.add(node);
  }

  Node getNodeAt(int x, int y) {
    for (Node node : nodes) {
      if (node.contains(x, y)) return node;
    }
    return null;
  }
  
  void onKeyPressed() {
  }
  
  void onMousePressed() {
     if(getNodeAt(mouseX, mouseY) == null) {
       addNode(new Node(mouseX, mouseY)); 
     }
  }

  void draw() {
    for (Node node : nodes) {
      node.draw();
    }
  }

  void update() {
    for (Node node : nodes) {
      node.update();
    }
  }
  
    void drawSelector(boolean active, int offset) {
    stroke(active ? 0 : 100);
    
    noFill();
    rect(width - 5 - (5 + 30) * offset, height - 35, 30, 30);
    
    line(width - 5 - (5 + 30) * offset + 15, height - 30, width - 5 - (5 + 30) * offset + 15, height-10);
    
    line(width - 5 - (5 + 30) * offset + 5, height - 20, width - 5 - (5 + 30) * offset + 25, height-20);
  }
}