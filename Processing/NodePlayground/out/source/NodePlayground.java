import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class NodePlayground extends PApplet {



NodeSystem nodes;

public void setup() {
  
  nodes = new NodeSystem();
}

public void keyPressed() {
  nodes.onKeyPressed();
}
public void mousePressed() {
  nodes.onMousePressed();
}

public void draw() {
  background(255);
  nodes.draw();
  nodes.update();

}
class Node {

  PVector position;
  PVector velocity;
  PVector acceleration;
  Nodes parentGroup;
  String name = "";

  public void setParentGroup(Nodes nodes) {
    this.parentGroup = nodes;
  }

  Node(int x, int y, Nodes parentGroup) {
    position = new PVector(x, y);
    velocity = new PVector();
    acceleration = new PVector();
    this.parentGroup = parentGroup;
  }
 public String getName() {
   return name;
 }

  public void setName(String name) {
    this.name = name;
  }

public void setPosition(float x, float y) {
   this.position = new PVector(x,y); 
}

  public float getWidth() {
    return textWidth(name) + 20;
  }

  public float getHeight() {
    return 40;
  }

  public boolean contains(int x, int y) {
    float w = getWidth();
    float h = getHeight();
    println("Checking containment for x(" + x  + ") and y(" + y + ") in position.x(" + position.x + ") position.y(" + position.y + ") with width(" + getWidth() + "), height(" + getHeight() + ")"); 
    println("Returning " + (
      (position.x - w/2.0f < x && position.x + w/2/0 > x) 
      && 
      (position.y - h/2.0f < y && position.y + h/2.0f  > y)
      ));
    return (
      (position.x - w/2.0f < x && position.x + w/2/0 > x) 
      && 
      (position.y - h/2.0f < y && position.y + h/2.0f  > y)
      );
  }


  public void draw() {
    fill(255);
    ellipse(position.x , position.y, getWidth(), getHeight());
    fill(0);
    text(name, position.x - textWidth(name)/2 + 10, position.y + getHeight()/8);
  }
  
  public void update() {
    
  }
}
class EditableNode implements NodeSelector {
  Nodes nodes;
  Node editedNode;
  StringBuilder builder = new StringBuilder();

  EditableNode(Nodes nodes) {
    this.nodes = nodes;
  }

  public void onMousePressed() {
      Node selectedNode = nodes.getNodeAt(mouseX, mouseY);
      if (selectedNode != null && editedNode != selectedNode) {
        editedNode = selectedNode;
        builder = new StringBuilder();
        builder.append(editedNode.getName());
      } 
  }
  
  public void onKeyPressed() {
    if (editedNode == null) return;

    if ((key >= 'A' && key <= 'Z') || (key >= 'a' && key <= 'z') || (key >= '0' && key <= '9') || (key == ' ')) {
      builder.append(key);
      editedNode.setName(builder.toString());
    } else if(key == BACKSPACE && builder.length() > 0) {
     builder.setLength(builder.length()-1);
      editedNode.setName(builder.toString());
    }
  }

  public void update() {

   
  }
  
    public void drawSelector(boolean active, int offset) {
    stroke(active ? 0 : 100);
            noFill();
    rect(width - 5 - (5 + 30) * offset, height - 35, 30, 30);
    text("E", width - 5 - (5 + 30) * offset + 10, height - 15);
  }
}
class MoveableNode implements NodeSelector {
  Nodes nodes;
  Node editedNode;

  MoveableNode(Nodes system) {
    nodes = system;
  }

  public void onKeyPressed() {
    
  }
  
  public void drawSelector(boolean active, int offset) {
    stroke(active ? 0 : 100);
        noFill();
    rect(width - 5 - (5 + 30) * offset, height - 35, 30, 30);
    line(width - 5 - (5 + 30) * offset + 8, height -13 , width - 5 - (5 + 30) * offset + 23, height - 28);
        fill(active ? 0 : 100);
    triangle(width - 5 - (5 + 30) * offset + 20, height - 28, width - 5 - (5 + 30) * offset + 23, height - 28, width - 5 - (5 + 30) * offset + 23, height - 25);
  }
  
  public void onMousePressed() {
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

  public void update() {
    if (editedNode == null) return;
    editedNode.setPosition(mouseX, mouseY);
  }
}
interface NodeSelector {
 public void onMousePressed();
 public void onKeyPressed();
 public void update();
 public void drawSelector(boolean active, int offset);
}

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
   
   public void onKeyPressed() {
    selectors.get(selected).onKeyPressed(); 
    if(key == CODED && keyCode == LEFT) {
        println("Left pressed");
        selected = (selected + 1)%selectors.size();
    } else if(key == CODED && keyCode == RIGHT) {
      println("Right pressed");
        selected = (selected - 1 +  selectors.size()) % selectors.size();
    }
   }
   
   public void onMousePressed() {
      selectors.get(selected).onMousePressed(); 
   }
   
   public void draw() {
    nodes.draw(); 
    
    for(int i = 0; i < selectors.size(); i++) {
     selectors.get(i).drawSelector(selected == i, i + 1); 
    }
     
   }
  

  

  

  
  public void update() {
   for (NodeSelector selector : selectors) {
    selector.update(); 
   }
  }
}
class Nodes implements NodeSelector {
  ArrayList<Node> nodes;

  Nodes() {
    nodes = new ArrayList<Node>();
  }

  public void addNode(Node node) {
    node.setParentGroup(this);
    nodes.add(node);
  }

  public Node getNodeAt(int x, int y) {
    for (Node node : nodes) {
      if (node.contains(x, y)) return node;
    }
    return null;
  }
  
  public void onKeyPressed() {
  }
  
  public void onMousePressed() {
     if(getNodeAt(mouseX, mouseY) == null) {
       addNode(new Node(mouseX, mouseY, this)); 
     }
  }

  public void draw() {
    for (Node node : nodes) {
      node.draw();
    }
  }

  public void update() {
    for (Node node : nodes) {
      node.update();
    }
  }
  
    public void drawSelector(boolean active, int offset) {
    stroke(active ? 0 : 100);
    
    noFill();
    rect(width - 5 - (5 + 30) * offset, height - 35, 30, 30);
    
    line(width - 5 - (5 + 30) * offset + 15, height - 30, width - 5 - (5 + 30) * offset + 15, height-10);
    
    line(width - 5 - (5 + 30) * offset + 5, height - 20, width - 5 - (5 + 30) * offset + 25, height-20);
  }
}
  public void settings() {  size(1280, 720); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "NodePlayground" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
