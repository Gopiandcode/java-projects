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

class Box {
  float x, y;
  float w,h;
  Body body;
  
  Box(float x, float y) {
    this(x,y,16,16,false);
  }
  
  Box(float x, float y, float w, float h) {
    this(x,y,w,h,true);
  }
  
  Box(float x, float y, float w, float h, boolean isStatic) {
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
    
    BodyDef bd = new BodyDef();
    if(isStatic)
      bd.type = BodyType.STATIC;
    else
    bd.type = BodyType.DYNAMIC;
    bd.position.set(box2d.coordPixelsToWorld(x,y));
    body = box2d.createBody(bd);
    
    PolygonShape ps = new PolygonShape();
    float box2dW = box2d.scalarPixelsToWorld(w/2);
    float box2dH = box2d.scalarPixelsToWorld(h/2);
    ps.setAsBox(box2dW, box2dH);
    
    FixtureDef fd = new FixtureDef();
    fd.shape = ps;
    fd.density = 1;
    fd.friction = 0.3;
    fd.restitution = 0.5;
    body.createFixture(fd);
  }
  
  void display() {
    Vec2 pos = box2d.getBodyPixelCoord(body);
    float a = body.getAngle();
    
    pushMatrix();
    translate(pos.x, pos.y);
    rotate(-a);
    fill(175);
    stroke(0);
    rectMode(CENTER);
    rect(0,0,w,h);
    popMatrix();
  }
  
  void killBody() {
    box2d.destroyBody(body);
  }
  
}
ArrayList<Box> statics;
ArrayList<Box> boxes;
void setup() {
  size(400, 300);
  statics = new ArrayList<Box>();
  boxes = new ArrayList<Box>();
  box2d = new Box2DProcessing(this);
  box2d.createWorld();
  Box a = new Box(10, 250, 100, 10);
  statics.add(a);
  a = new Box(300, 200, 300, 10);
  statics.add(a);

}


void draw() {
  box2d.step();
  background(255);
  if(mousePressed) {
    Box p = new Box(mouseX, mouseY, random(10, 16), random(10,16), false);
    boxes.add(p);
  }
  
  Iterator<Box> it = boxes.iterator();
  
  while(it.hasNext()) {
    Box b = it.next();
    b.display();
    PVector loc = box2d.getBodyPixelCoordPVector(b.body);
    if(loc.x > width || loc.x < 0 || loc.y > height || loc.y < 0) {
      b.killBody();
      it.remove();
    }
  }
  for(Box b : statics) {
    b.display();
  }
}