import toxi.physics2d.*;
import toxi.physics2d.behaviors.*;
import toxi.geom.*;

VerletPhysics2D physics;

class Particle extends VerletParticle2D {
  
  Particle(Vec2D pos) {
   super(pos); 
  }
  
  
  void display() {
   fill(0, 150);
   stroke(0);
   ellipse(x, y, 16,16);
  }
}


Particle[][] points;
int num = 30;
int startx = 250;
int starty = 0;
int len = 10;
float strength = 0.5;

void setup() {
 size(640, 360);
 physics = new VerletPhysics2D();
 physics.setWorldBounds(new Rect(0,0, width, height));
 physics.addBehavior(new GravityBehavior2D(new Vec2D(0, 0.5)));
 
 
 points = new Particle[num][num];
 for(int i = 0; i<num; i++) points[i] = new Particle[num];
 
 for(int i = 0; i<num; i++) {
  
   for(int j = 0; j < num; j++){
     points[i][j] = new Particle(new Vec2D(startx + j*len, starty + i*len));
     
     // Check if i != 0, connect to prev in row
     if(i != 0) {
      Particle prev = points[i-1][j];
      VerletSpring2D spring = new VerletSpring2D(points[i][j], prev, len, strength);
      physics.addSpring(spring);
     }
     
     if(j != 0) {
      Particle prev = points[i][j-1];
      VerletSpring2D spring = new VerletSpring2D(points[i][j], prev, len, strength);
      physics.addSpring(spring);
     }
     
     physics.addParticle(points[i][j]);
   }
   points[0][0].lock();
   points[0][num-1].lock();
 }
  
  
  
}


void draw() {
 background(255);

 if(mousePressed) {
  Particle p = points[num-1][num/2];
  p.lock();
  p.set(new Vec2D(mouseX, mouseY));
  p.unlock();
   
 }
 
 physics.update();
 
 // draw the rows
 for(int i = 0; i<num; i++) {
  noFill();
  stroke(0);
  beginShape();
   for(int j = 0; j<num; j++) {
    Particle p = points[i][j];
    vertex(p.x, p.y);
     
   }
   
   endShape();
 }
 
 for(int i = 0; i<num; i++) {
  noFill();
  stroke(0);
  beginShape();
   for(int j = 0; j<num; j++) {
    Particle p = points[j][i];
    vertex(p.x, p.y);
      
     
   }
   
   endShape();
 }
 
 
  
}