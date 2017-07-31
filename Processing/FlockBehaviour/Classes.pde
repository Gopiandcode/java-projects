class Vehicle {

  PVector location;
  PVector velocity;
  PVector acceleration;
  float mass;
  float maxspeed;
  float maxforce;

  Vehicle(PVector location, float mass, float maxspeed, float maxforce) {
    this.location = location.copy();
    this.velocity = new PVector();
    this.acceleration = new PVector();
    this.mass = mass;
    this.maxspeed = maxspeed;
    this.maxforce = maxforce;
  }

  Vehicle(float x, float y, float mass, float maxspeed, float maxforce) {
    this.location = new PVector(x, y);
    this.velocity = new PVector();
    this.acceleration = new PVector();
    this.mass = mass;
    this.maxspeed = maxspeed;
    this.maxforce = maxforce;
  }


  void display() {
    fill(0, 100);
    stroke(0);
    float angle = velocity.heading();

    pushMatrix();

    translate(location.x, location.y);
    rotate(angle);

    triangle(mass*3.5, 0, 
      -3.5*mass, 3.5*mass, 
      -3.5*mass, -3.5*mass);
    popMatrix();
  }

  void checkEdges() {
    if (location.x > width) {
      location.x = 0;
    } else if (location.x < 0) {
      location.x = width;
    }

    if (location.y > height) {
      location.y = 0;
    } else if (location.y < 0) {
      location.y = height;
    }
  }

  PVector separate(ArrayList<Vehicle> flock) {
    PVector sum = new PVector();
    int count = 0;
    float desired_distance = 6*mass;

    for (Vehicle v : flock) {
      PVector vec = PVector.sub(location, v.location);
      float d = vec.mag();
      if ((d > 0) && (d < desired_distance)) {
        vec.normalize();
        vec.div(d);
        sum.add(vec);
        count++;
      }
    }

    if (count != 0) {
      sum.div(count);
      sum.normalize();
      sum.mult(maxspeed);

      PVector steer = PVector.sub(sum, velocity);
      steer.limit(maxforce);

      return steer;
    }
    return sum;
  }

  PVector align(ArrayList<Vehicle> flock) {
    PVector sum = new PVector();
    float neighbor_dist = mass * 12;
    int count = 0;
    for (Vehicle v : flock) {
           float d = PVector.dist(location, v.location);
      if ((d > 0) && (d < neighbor_dist)) {
        sum.add(v.velocity); 
        count++;
      }
    }
    if(count > 0) {
      sum.div(count);
      sum.normalize();
      
    sum.setMag(maxspeed);

    PVector steer = PVector.sub(sum, velocity);
    steer.limit(maxforce);
    return steer;
    } else {
       return new PVector(); 
    }
  }
  
  PVector cohesion(ArrayList<Vehicle> flock) {
   float neighbor_dist = mass * 30;
   PVector sum = new PVector();
   int count = 0;
   
   for(Vehicle v: flock) {
     PVector dir = PVector.sub(v.location, location);
    float d = dir.mag();
    if((d > 0) && (d < neighbor_dist)) {
     sum.add(v.location);
     count++;
    }
     
   }
   
   if(count > 0) {
    sum.div(count);
    return seek(sum);
   } else {
     return new PVector();
   }
  }

  void flock(ArrayList<Vehicle> flock) {
    PVector sep = separate(flock);
    PVector ali = align(flock);
    PVector coh = cohesion(flock);

    sep.mult(5.8);
    ali.mult(1.2);
    coh.mult(2.0);

    applyForce(sep);
    applyForce(ali);
    applyForce(coh);
  }
  void step() {
    checkEdges();
    velocity.add(acceleration);
    velocity.limit(maxspeed);
    location.add(velocity);  

    acceleration.mult(0);
  }

  void applyForce(PVector force) {
    PVector action = force.copy();
    action.div(mass);
    acceleration.add(action);
  }

  PVector seek(PVector target) {
    PVector desired = PVector.sub(target, location);
    desired.normalize();
    desired.mult(maxspeed);
    PVector steer = PVector.sub(desired, velocity);
    steer.limit(maxforce);
    return steer;
  }

  void seekTo(PVector target) {
    applyForce(seek(target));
  }


  void update() {
    step();
    display();
  }
}