class Node {

  PVector position;
  PVector velocity;
  PVector acceleration;
  Nodes parentGroup;
  String name = "";

  void setParentGroup(Nodes nodes) {
    this.parentGroup = nodes;
  }

  Node(int x, int y, Nodes parentGroup) {
    position = new PVector(x, y);
    velocity = new PVector();
    acceleration = new PVector();
    this.parentGroup = parentGroup;
  }
 String getName() {
   return name;
 }

  void setName(String name) {
    this.name = name;
  }

void setPosition(float x, float y) {
   this.position = new PVector(x,y); 
}

  float getWidth() {
    return textWidth(name) + 20;
  }

  float getHeight() {
    return 40;
  }

  boolean contains(int x, int y) {
    float w = getWidth();
    float h = getHeight();
    println("Checking containment for x(" + x  + ") and y(" + y + ") in position.x(" + position.x + ") position.y(" + position.y + ") with width(" + getWidth() + "), height(" + getHeight() + ")"); 
    println("Returning " + (
      (position.x - w/2.0 < x && position.x + w/2/0 > x) 
      && 
      (position.y - h/2.0 < y && position.y + h/2.0  > y)
      ));
    return (
      (position.x - w/2.0 < x && position.x + w/2/0 > x) 
      && 
      (position.y - h/2.0 < y && position.y + h/2.0  > y)
      );
  }


  void draw() {
    fill(255);
    ellipse(position.x , position.y, getWidth(), getHeight());
    fill(0);
    text(name, position.x - textWidth(name)/2 + 10, position.y + getHeight()/8);
  }
  
  void update() {
    
  }
}