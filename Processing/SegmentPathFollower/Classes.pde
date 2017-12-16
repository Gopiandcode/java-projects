class Path {
  ArrayList<PVector> points;
  
  float radius;
  Path() {
    radius = 20;
    points = new ArrayList<PVector>();
  }

  void addPoint(float x, float y) {
   PVector point = new PVector(x,y);
   points.add(point);
  }

  void display() {
    strokeWeight(radius*2);
    stroke(0, 100);
    
    noFill();
    beginShape();
    for(PVector v : points) {
     vertex(v.x, v.y); 
    }
    endShape();
    
    
    strokeWeight(1);
    stroke(0);
    
    
    stroke(0);
    noFill();
    beginShape();
    for(PVector v : points) {
     vertex(v.x, v.y); 
    }
    endShape();
    
  }
}


class Vehicle {
  PVector location;
  PVector velocity;
  PVector acceleration;
  float mass;
  float maxspeed;
  float maxforce;

  void applyForce(PVector action) {
    PVector force = action.copy();
    force.limit(maxforce);
    force.div(mass);
    acceleration.add(force);
  }

  void step() {
    checkEdges();
    velocity.add(acceleration);

    velocity.limit(maxspeed);

    location.add(velocity);

    acceleration.mult(0);
  }

  void display() {
    float angle = velocity.heading();

    fill(0, 150);
    stroke(0);
    rectMode(CENTER);

    pushMatrix();

    translate(location.x, location.y);
    rotate(angle);

    triangle(mass*5, 0, -mass*5, mass*5, -mass*5, -mass*5);

    popMatrix();
  }

  void update() {
    step(); 
    display();
  }


  void checkEdges() {
    if (location.x < 0) {
      PVector desired = new PVector(maxspeed, velocity.y);
      desired.limit(maxspeed);

      PVector steer = PVector.sub(desired, velocity);
      applyForce(steer);
    } else if (location.x > width) {

      PVector desired = new PVector(-maxspeed, velocity.y);
      desired.limit(maxspeed);

      PVector steer = PVector.sub(desired, velocity);
      applyForce(steer);
    }

    if (location.y < 0) {

      PVector desired = new PVector(velocity.x, maxspeed);
      desired.limit(maxspeed);

      PVector steer = PVector.sub(desired, velocity);
      applyForce(steer);
    } else if (location.y > height) {
      PVector desired = new PVector(velocity.x, -maxspeed);
      desired.limit(maxspeed);

      PVector steer = PVector.sub(desired, velocity);
      applyForce(steer);
    }
  }

  void followPath(Path path) {
   PVector predict = velocity.get();
   predict.normalize();
   predict.mult(25);
   PVector predictLoc = PVector.add(location, predict);
   
   float record = Float.MAX_VALUE;
   PVector target = new PVector();
   PVector bb = new PVector();
   for(int i = 0; i<path.points.size()-1; i++) {
    PVector a = path.points.get(i).copy();
    PVector b = path.points.get(i+1).copy();
    
    PVector normalPoint = getNormalPoint(predictLoc,a,b);
    if(normalPoint.x < min(a.x,b.x) || normalPoint.x > max(b.x,a.x)) {
     normalPoint = b.get(); 
    }
    
    float distance = PVector.dist(predictLoc, normalPoint);
    if(distance < record) {
     record = distance;
     target = normalPoint.get();
     bb =b;
    }
   }
   
   
   if(record > path.radius) {
      bb.normalize();
      bb.mult(25);
      target = PVector.add(target, bb);
     
     seek(target); 
   }
   
   
  }

  void seek(PVector pos) {
   PVector postion = pos.copy();
   PVector desired = PVector.sub(pos, location);
   applyForce(desired);
  }
  
  
  Vehicle(PVector location, float mass, float maxspeed, float maxforce) {
    this.location = location;
    this.velocity = new PVector();
    this.acceleration = new PVector();
    this.mass = mass;
    this.maxspeed = maxspeed;
    this.maxforce = maxforce;
  }
}