class EditableNode implements NodeSelector {
  Nodes nodes;
  Node editedNode;
  StringBuilder builder = new StringBuilder();

  EditableNode(Nodes nodes) {
    this.nodes = nodes;
  }

  void onMousePressed() {
      Node selectedNode = nodes.getNodeAt(mouseX, mouseY);
      if (selectedNode != null && editedNode != selectedNode) {
        editedNode = selectedNode;
        builder = new StringBuilder();
      } 
  }
  
  void onKeyPressed() {
    if (editedNode == null) return;

    if ((key >= 'A' && key <= 'Z') || (key >= 'a' && key <= 'z') || (key >= '0' && key <= '9') || (key == ' ')) {
      builder.append(key);
      editedNode.setName(builder.toString());
    } else if(key == BACKSPACE && builder.length() > 0) {
     builder.setLength(builder.length()-1);
      editedNode.setName(builder.toString());
    }
  }

  void update() {

   
  }
  
    void drawSelector(boolean active, int offset) {
    stroke(active ? 0 : 100);
            noFill();
    rect(width - 5 - (5 + 30) * offset, height - 35, 30, 30);
    text("E", width - 5 - (5 + 30) * offset + 5, height - 25);
  }
}