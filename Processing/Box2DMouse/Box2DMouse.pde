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
  float w, h;
  Body body;

  Box(float x, float y, float w, float h) {
    this.w = w;
    this.h = h;

    BodyDef bd = new BodyDef();
    bd.position.set(box2d.coordPixelsToWorld(x, y));
    bd.type = BodyType.DYNAMIC;
    body = box2d.createBody(bd);

    float box2dW = box2d.scalarPixelsToWorld(w);
    float box2dH = box2d.scalarPixelsToWorld(h);
    PolygonShape ps = new PolygonShape();
    ps.setAsBox(w, h);

    body.createFixture(ps, 1.0);
  }

  void display() {
    Vec2 pos = box2d.getBodyPixelCoord(body);
    float angle  = body.getAngle();

    stroke(10);
    fill(175);
    rectMode(CENTER);
    pushMatrix();
    translate(pos.x, pos.y);
    rotate(-angle);
    rect(0, 0, w, h);
    popMatrix();
  }

  void applyForce(Vec2 force) {
    Vec2 pos = body.getWorldCenter();
    body.applyForce(force, pos);
  }
}

class Spring {
  MouseJointDef md;
  MouseJoint mj;

  void configureMd() {
    md.maxForce = 23000.0;
    md.frequencyHz = 1.0;
    md.dampingRatio = 0.2;
  }

  Spring() {
    md  = new MouseJointDef();
    configureMd();
  }

  void destroy() {
    if (mj != null) {
      box2d.world.destroyJoint(mj);
      MouseJoint.destroy(mj);
      mj = null;
    }
  }

  void bind(float x, float y, Box b) {
    md.bodyA = box2d.getGroundBody();
    md.bodyB = b.body;
    mj = (MouseJoint) box2d.world.createJoint(md);
    Vec2 mouse = box2d.coordPixelsToWorld(x, y);
    mj.setTarget(mouse);
  }
}
Box b;
Spring s;

void setup() {
  size(640, 360);
  box2d = new Box2DProcessing (this);
  box2d.createWorld();
  b = new Box(width/2, height/2, 16, 16);
  s = new Spring();
}

void mousePressed() {
 s.bind(mouseX, mouseY, b); 
}

void mouseReleased() {
 s.destroy(); 
}



void draw() {
  background(255);
  box2d.step();
  b.display();
  Vec2 pos = box2d.getBodyPixelCoord(b.body);
  if (mousePressed) {
    line(pos.x, pos.y, mouseX, mouseY);
  }
}