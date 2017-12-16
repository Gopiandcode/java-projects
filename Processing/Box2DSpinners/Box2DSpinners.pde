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
    Body bodyA, bodyB;
    
    Dongle(float x, float y, float scale) {
      this.x = x;
      this.y = y;
      this.scale = scale;
      
      // Body 1
      BodyDef bd = new BodyDef();
      bd.type = BodyType.DYNAMIC;
      bd.position.set(box2d.coordPixelsToWorld(new PVector(x,y)));
      bodyA = box2d.createBody(bd);
      
      CircleShape cs = new CircleShape();
      cs.m_radius = box2d.scalarPixelsToWorld(scale);
      bodyA.createFixture(cs,1.0);
      
      // Body 1
      bd = new BodyDef();
      bd.type = BodyType.DYNAMIC;
      bd.position.set(box2d.coordPixelsToWorld(new PVector(x+scale,y+scale)));
      bodyB = box2d.createBody(bd);
      
      cs = new CircleShape();
      cs.m_radius = box2d.scalarPixelsToWorld(scale);
      bodyB.createFixture(cs,1.0);
      
      
      RevoluteJointDef rjd = new RevoluteJointDef();
      rjd.initialize(bodyA,bodyB, bodyA.getWorldCenter());
      rjd.enableMotor = true;
      rjd.motorSpeed = PI*100;
      rjd.maxMotorTorque = 100000.0;
      box2d.world.createJoint(rjd);
      
    }
    
    void display() {
      Vec2 posA = box2d.getBodyPixelCoord(bodyA);
      float a = bodyA.getAngle();
      rectMode(CENTER);
      pushMatrix();
      translate(posA.x, posA.y);
      rotate(-a);
      fill(175);
      stroke(0);
      ellipse(0,0, scale*2, scale*2);
      popMatrix();

      Vec2 posB = box2d.getBodyPixelCoord(bodyB);
      float b = bodyB.getAngle();
      rectMode(CENTER);
      pushMatrix();
      translate(posB.x, posB.y);
      rotate(-b);
      fill(175);
      stroke(0);
      ellipse(0,0, scale*2, scale*2);
      popMatrix();
      
      line(posA.x, posA.y, posB.x, posB.y);
      
    }
}
 
  ArrayList<Dongle> items;
  

void setup() {
  size(640, 360);
  items = new ArrayList<Dongle>();
  box2d = new Box2DProcessing(this);
  box2d.createWorld();
  
  BodyDef bd = new BodyDef();
  bd.type = BodyType.STATIC;
  bd.position.set(box2d.coordPixelsToWorld(width/2, height-10));
  Body body = box2d.createBody(bd);
  
  PolygonShape ps = new PolygonShape();
  float box2dW = box2d.scalarPixelsToWorld(width/2);
  float box2dH = box2d.scalarPixelsToWorld(10);
  ps.setAsBox(box2dW, box2dH);
  
  body.createFixture(ps, 1.0);
}
  
void draw() {
  background(255);
  pushMatrix();
  translate(width/2, height-10);
  rectMode(CENTER);
  rect(0,0,width, 10);
  popMatrix();
 box2d.step();
  if(mousePressed) {
  items.add(new Dongle(mouseX, mouseY, random(5,10))); 
 }
  Iterator<Dongle> it = items.iterator();
  while(it.hasNext()) {
    Dongle d = it.next();
     Vec2 ps = box2d.getBodyPixelCoord(d.bodyA);
     ps.add(box2d.getBodyPixelCoord(d.bodyB));
     ps.mul(0.5);
      if(ps.x > width || ps.x < 0 || ps.y > height || ps.y < 0) {
        it.remove();
      } else {
       d.display(); 
      }
  }
  
}