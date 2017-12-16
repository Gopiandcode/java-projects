class Path {
  PVector start;
  PVector end;
  float radius;
  Path() {
    radius = 20;
    start = new PVector(10, height/3);
    end = new PVector(width-10, 2*height/3);
  }


  void display() {
    strokeWeight(radius*2);
    stroke(0, 100);
    line(start.x, start.y, end.x, end.y);
    strokeWeight(1);
    stroke(0);
    line(start.x, start.y, end.x, end.y);
    ellipse(start.x, start.y, 2, 2);
    ellipse(end.x, end.y, 2, 2);
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
   
   PVector a = PVector.sub(predictLoc, path.start);
   PVector b = PVector.sub(path.end, path.start);
   
   b.normalize();
   b.mult(a.dot(b));
   PVector normalPoint = PVector.add(path.start, b);
   
   float distance = PVector.dist(predictLoc, normalPoint);
   if(distance > path.radius) {
      b.normalize();
      b.mult(25);
      PVector target = PVector.add(normalPoint, b);
     
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