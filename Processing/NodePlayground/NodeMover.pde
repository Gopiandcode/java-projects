class MoveableNode implements NodeSelector {
  Nodes nodes;
  Node editedNode;

  MoveableNode(Nodes system) {
    nodes = system;
  }

  void onKeyPressed() {
    
  }
  
  void drawSelector(boolean active, int offset) {
    stroke(active ? 0 : 100);
        noFill();
    rect(width - 5 - (5 + 30) * offset, height - 35, 30, 30);
    line(width - 5 - (5 + 30) * offset + 8, height -13 , width - 5 - (5 + 30) * offset + 23, height - 28);
        fill(active ? 0 : 100);
    triangle(width - 5 - (5 + 30) * offset + 20, height - 28, width - 5 - (5 + 30) * offset + 23, height - 28, width - 5 - (5 + 30) * offset + 23, height - 25);
  }
  
  void onMousePressed() {
    Node selectedNode = nodes.getNodeAt(mouseX, mouseY);
    if (editedNode != null) {
     if (selectedNode == editedNode) {
        editedNode = null;
      } else {
        editedNode.setPosition(mouseX, mouseY); 
      }
    } else {
       if (selectedNode != null) {
        editedNode = selectedNode;
      }
       
    }
  }

  void update() {
    if (editedNode == null) return;
    editedNode.setPosition(mouseX, mouseY);
  }
}