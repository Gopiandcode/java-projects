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


  void separate(ArrayList<Vehicle> vehicles) {
    PVector sum = new PVector(); 
    float desiredseparation = mass*10;
    int count = 0;
    for (Vehicle other : vehicles) {
      float d = PVector.dist(location, other.location);

      if ((d > 0) && (d < desiredseparation)) {
        PVector diff = PVector.sub(location, other.location);
        diff.normalize();
        diff.div(d);
        sum.add(diff);
        count++;
      }
    }
    
    if(count > 0) {
     sum.div(count);
     sum.setMag(maxspeed);
     PVector steer = PVector.sub(sum,velocity);
     applyForce(steer);
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