import toxi.physics2d.*;
import toxi.physics2d.behaviors.*;
import toxi.geom.*;

VerletPhysics2D physics;

class Node extends VerletParticle2D {
 
  Node(Vec2D pos) {
   super(pos); 
  }
  
  void display() {
   fill(0,150);
   stroke(0);
   ellipse(x,y, 16,16);
    
  }
  
}


class Cluster {
  ArrayList<Node> nodes;
  float diameter;
  Cluster(int n, float d, Vec2D center) {
   nodes = new ArrayList<Node>();
   diameter = d;
   
   for(int  i =0; i<n; i++){
    nodes.add(new Node(center.add(Vec2D.randomVector()))); 
   }
    for(int i = 0; i < nodes.size() - 1; i++) {
     VerletParticle2D ni = nodes.get(i);
     for(int j = i+1; j<nodes.size(); j++) {
      VerletParticle2D nj = nodes.get(j);
      physics.addSpring(new VerletSpring2D(ni,nj, diameter, 0.01));
     }
      
    }
  }
  
  
  void display() {
    
   for(int i = 0; i< nodes.size()-1;i++) {
     if(drawParticles)
       nodes.get(i).display();
       if(drawLines)
    for(int j =i+1; j< nodes.size(); j++) {
      Node a = nodes.get(i);
      Node b = nodes.get(j);
      line(a.x, a.y, b.x, b.y);
      
    }
    if(drawParticles)
    nodes.get(nodes.size()-1).display();
     
   }
    
  }
}

boolean drawParticles = true;
boolean drawLines     = true;
Cluster cluster;

void setup() {
 size(640, 360);
 physics = new VerletPhysics2D();
 cluster = new Cluster(8, 100, new Vec2D(width/2, height/2));
  
}

void keyPressed() {
 if(key == 'p') drawParticles = !drawParticles;
 if(key == 'c') drawLines     = !drawLines;
 if(key == 'n') cluster = new Cluster((int)random(1,50), random(80,120), new Vec2D(width/2, height/2));
}

void draw() {
  physics.update();
  background(255);
  fill(0);
  
  text("'p' to display or hide particles", 10, 20);
  text("'c' to display or hide connections", 10, 40);
  text("'n' for a new graph", 10, 60);
  
  cluster.display();
}