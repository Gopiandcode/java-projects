
class NodeSystem {
   Nodes nodes;
   EditableNode editor;
   MoveableNode mover;
   
   NodeSystem() {
    nodes = new Nodes();
    editor = new EditableNode(nodes);
    mover = new MoveableNode(nodes);
   }
   
   void onKeyPressed() {
    editor.onKeyPressed(); 
   }
   
   void onMousePressed() {
     nodes.onMousePressed();
      editor.onMousePressed(); 
      mover.onMousePressed();
   }
   
   void draw() {
    nodes.draw(); 
     
   }
  
  void update() {
   nodes.update();
   editor.update();
   mover.update();
  }
}