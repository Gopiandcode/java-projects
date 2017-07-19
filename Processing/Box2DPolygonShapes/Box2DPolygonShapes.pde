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

ArrayList<Vec2> deflt;
 
Box2DProcessing box2d;

class Surface {
  ArrayList<Vec2> surface;
  
  Surface(ArrayList<Vec2> array) {
    surface = array;
    ChainShape chain = new ChainShape();
    Vec2[] vertices = new Vec2[surface.size()];
    for(int i = 0; i<vertices.length; i++)
      vertices[i] = box2d.coordPixelsToWorld(surface.get(i));
     chain.createChain(vertices,vertices.length);
     BodyDef bd = new BodyDef();
     Body body = box2d.world.createBody(bd);
     body.createFixture(chain,1);
  }
  Surface() {
    this(deflt);
  }
  void display() {
   strokeWeight(1);
   stroke(0);
   noFill();
   beginShape();
   for(Vec2 v: surface) {
    vertex(v.x, v.y); 
   }
   endShape();
  }
  
}

interface Entity {
 void display(); 
 void killBody();
 
 PVector getPosition();
}



class Circle implements Entity {
 float x,y;
 float r;
 Body body;
 
 Circle(float x, float y, float r) {
   this(x,y,r,false);
 }
 
 Circle(float x, float y, float r, boolean isStatic) {
   this.x = x;
   this.y = y;
   this.r = r;
   BodyDef bd = new BodyDef();
   bd.setPosition(box2d.coordPixelsToWorld(new Vec2(x,y)));
   if(isStatic)
     bd.type = BodyType.STATIC;
   else
     bd.type = BodyType.DYNAMIC;
   body = box2d.world.createBody(bd);
   
   CircleShape cs = new CircleShape();
   float box2dR = box2d.scalarPixelsToWorld(r);
   cs.setRadius(box2dR);
   
   FixtureDef fd = new FixtureDef();
   fd.shape = cs;
   fd.restitution = 0.5;
   fd.friction = 0.3;
   fd.density = 1;
   
   body.createFixture(fd);
 }
   
 void display() {
   PVector pos = box2d.getBodyPixelCoordPVector(body);
   fill(175);
   stroke(0);
   rectMode(CENTER);
   ellipse(pos.x, pos.y, r*2,r*2);
 }
 
 void killBody() {
    box2d.destroyBody(body); 
 }
 
 PVector getPosition() {
   return box2d.getBodyPixelCoordPVector(body);
 }
}


class Box implements Entity{
  float x,y;
  float w,h;
  Body body;
  
  Box(float x, float y) {
    this(x,y,16,16,false);
  }
  
  Box(float x, float y,  boolean isStatic, ArrayList<Vec2> vertices) {
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
    float bx = bd.position.x;
    float by = bd.position.y;
    PolygonShape ps = new PolygonShape();
    Vec2[] vc = new Vec2[vertices.size()];
    for(int i = 0; i<vc.length; i++) {
      Vec2 pos = //box2d.coordPixelsToWorld(vertices.get(i));
      box2d.vectorPixelsToWorld(vertices.get(i));
       vc[i] = (pos); 
    }
    ps.set(vc, vc.length);
    
    FixtureDef fd = new FixtureDef();
    fd.shape = ps;
    fd.density = 1;
    fd.friction = 0.3;
    fd.restitution = 0.5;
    
    body.createFixture(fd);
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
    float angle = body.getAngle();
    PVector pos = box2d.getBodyPixelCoordPVector(body);
    
    Fixture f = body.getFixtureList();
    PolygonShape ps =(PolygonShape) f.getShape();
    
    
    pushMatrix();
    translate(pos.x, pos.y);
    rotate(-angle);
    
    fill(175);
    stroke(0);
    rectMode(CENTER);
    
    beginShape();
    for(int i = 0; i<ps.getVertexCount(); i++){
       Vec2 v = box2d.vectorWorldToPixels(ps.getVertex(i));
       vertex(v.x, v.y);
    }
    
    endShape(CLOSE);
    popMatrix();
  }
  
  PVector getPosition() {
    return box2d.getBodyPixelCoordPVector(body);
  }
  
  void killBody() {
    box2d.destroyBody(body);
  }
}

ArrayList<Entity> boxes = new ArrayList<Entity>();

ArrayList<Vec2> genSinWave() {
  ArrayList<Vec2> result = new ArrayList<Vec2>();
  float t = 0;
  for(int i = 0; i<width; i+= 5) {
    print("The value of sin(" + t + ") is " + sin(t) + "\n");
    float val = map(sin(t), -1, 1, height, height/2);
    print(val + "\n");
      Vec2 pos = new Vec2(i, val);
      t += 0.1;
      print(pos);
     result.add(pos);
  }
  
  return result;
}


ArrayList<Vec2> genPerlin() {
  ArrayList<Vec2> result = new ArrayList<Vec2>();
  float t = 0;
  for(int i = 0; i<width; i+= 5) {
    print("The value of sin(" + t + ") is " + sin(t) + "\n");
    float val = map(noise(t), -1, 1, height, height/2);
    print(val + "\n");
      Vec2 pos = new Vec2(i, val);
      t += 0.1;
      print(pos);
     result.add(pos);
  }
  
  return result;
}

ArrayList<Vec2> shape = new ArrayList<Vec2>();

Surface surface;
void setup() {
  size(640, 480);
  deflt = new ArrayList<Vec2>();
  deflt.add(new Vec2(0, height/2+50));
  deflt.add(new Vec2(width/2, height/2+50));
  deflt.add(new Vec2(width, height/2));

  shape.add(new Vec2(-15,25));
  shape.add(new Vec2(15, 0));
  shape.add(new Vec2(20, -15));
  shape.add(new Vec2(-10,-10));
  
  box2d = new Box2DProcessing(this);
  box2d.createWorld();
  surface = new Surface(genPerlin());
}


void draw() {
  background(255);
  box2d.step();
  if(mousePressed) {
    Entity b;
    float prob = random(0,1);
    if(prob < 0.33333)
     b = new Box(mouseX, mouseY);
     else if(prob <0.66666)
     b = new Circle(mouseX, mouseY, random(10,16));
     else
     b = new Box(mouseX, mouseY, false, shape);
     boxes.add(b);
  }
  surface.display();
  
  Iterator<Entity> it = boxes.iterator();
  
  while(it.hasNext()) {
    Entity b = it.next();
    b.display();
    PVector pos = b.getPosition();
    if(pos.x > width || pos.x < 0 || pos.y > height || pos.y < 0){
      b.killBody(); 
      it.remove();
    }
  }
}