
class NodeSystem {
   Nodes nodes;
   int selected = 0;
   ArrayList<NodeSelector> selectors = new ArrayList<NodeSelector>();
   EditableNode editor;
   MoveableNode mover;
   
   NodeSystem() {
    nodes = new Nodes();
    selectors.add(new EditableNode(nodes));
    selectors.add(new MoveableNode(nodes));
    selectors.add(nodes);
   }
   
   void onKeyPressed() {
    selectors.get(selected).onKeyPressed(); 
    if(key == CODED && keyCode == LEFT) {
        println("Left pressed");
        selected = (selected + 1)%selectors.size();
    } else if(key == CODED && keyCode == RIGHT) {
      println("Right pressed");
        selected = (selected - 1 +  selectors.size()) % selectors.size();
    }
   }
   
   void onMousePressed() {
      selectors.get(selected).onMousePressed(); 
   }
   
   void draw() {
    nodes.draw(); 
    
    for(int i = 0; i < selectors.size(); i++) {
     selectors.get(i).drawSelector(selected == i, i + 1); 
    }
     
   }
  

  

  

  
  void update() {
   for (NodeSelector selector : selectors) {
    selector.update(); 
   }
  }
}