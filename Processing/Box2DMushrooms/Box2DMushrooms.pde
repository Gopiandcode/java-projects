import shiffman.box2d.*;
import org.jbox2d.common.Transform;
import org.jbox2d.common.*;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.*;
import org.jbox2d.collision.*;
import org.jbox2d.collision.shapes.*;
import org.jbox2d.dynamics.joints.JointDef;
import java.util.Iterator;

Box2DProcessing box2d;

class Dongle {
    float x, y;
    float scale;
    Body body;
    
    Dongle(float x, float y, float scale) {
      this.x = x;
      this.y = y;
      this.scale = scale;
      BodyDef bd = new BodyDef();
      bd.type = BodyType.DYNAMIC;
      bd.position.set(box2d.coordPixelsToWorld(new PVector(x,y)));
      body = box2d.createBody(bd);
      PolygonShape ps = new PolygonShape();
      float box2dW = box2d.scalarPixelsToWorld(scale*3/4);
      float box2dH = box2d.scalarPixelsToWorld(scale/2);
      ps.setAsBox(box2dW,box2dH);
      CircleShape cs = new CircleShape();
      cs.m_radius = box2d.scalarPixelsToWorld(scale);
      Vec2 offset = new Vec2(0, -scale/2);
      offset = box2d.vectorPixelsToWorld(offset);
      cs.m_p.set(offset);
      body.createFixture(ps,1.0);
      body.createFixture(cs,1.0);
    }
    
    void display() {
      Vec2 pos = box2d.getBodyPixelCoord(body);
      float a = body.getAngle();
      rectMode(CENTER);
      pushMatrix();
      translate(pos.x, pos.y);
      rotate(-a);
      fill(175);
      stroke(0);
      rect(0,0,scale*3/2,scale);
      ellipse(0,-scale/2, scale*2, scale*2);
      popMatrix();
      
    }
}
 
  ArrayList<Dongle> items;
  

void setup() {
  size(640, 360);
  items = new ArrayList<Dongle>();
  box2d = new Box2DProcessing(this);
  box2d.createWorld();
}
  
void draw() {
  background(255);
 box2d.step();
  if(mousePressed) {
  items.add(new Dongle(mouseX, mouseY, random(5,10))); 
 }
  Iterator<Dongle> it = items.iterator();
  while(it.hasNext()) {
    Dongle d = it.next();
     Vec2 ps = box2d.getBodyPixelCoord(d.body);
      if(ps.x > width || ps.x < 0 || ps.y > height || ps.y < 0) {
        it.remove();
      } else {
       d.display(); 
      }
  }
  
}