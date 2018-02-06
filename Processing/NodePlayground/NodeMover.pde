class MoveableNode {
  Nodes nodes;
  Node editedNode;

  MoveableNode(Nodes system) {
    nodes = system;
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