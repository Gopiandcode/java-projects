class Nodes {
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
}