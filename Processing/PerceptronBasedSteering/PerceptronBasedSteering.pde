class Vehicle{ 
  PVector location;
  PVector velocity;
  PVector acceleration;
  float maxforce;
  float maxspeed;
  Perceptron brain;
  
  Vehicle(float x, float y, int n){
   location = new PVector(x,y);
   velocity = new PVector();
   acceleration = new PVector();
   
   maxspeed = 4;
   maxforce = 0.1;
   brain = new Perceptron(n, 0.001);
  }
 
  void step() {
   velocity.add(acceleration);
   acceleration.mult(0);
   velocity.limit(maxspeed);
   location.add(velocity);
   checkEdges();
  }
  
  
  void display() {
   float heading = velocity.heading();
   fill(0, 150);
   stroke(0);
   
   pushMatrix();
   translate(location.x, location.y);
   rotate(heading);
   triangle(-5,5, -5,-5, 5,0);
   popMatrix();
    
  }
  
  void checkEdges() {
   if(location.x > width) location.x = 0;
   else if(location.x < 0) location.x = width;
   
   if(location.y > height) location.y = 0;
   else if(location.y <0 ) location.y = height;
  }
  
  void seek(ArrayList<PVector> targets) {
    PVector[] forces = new PVector[targets.size()];

    PVector sum = new PVector();
    for(int i = 0; i<forces.length; i++) {
     forces[i] = seek(targets.get(i)); 
     sum.add(forces[i]);
    }
    
    PVector output = brain.process(forces);
    applyForce(output);
    
    PVector desired = new PVector(width/2, height/2);
    PVector error = PVector.sub(sum, location);
    brain.train(forces, error);
  }
  
  PVector seek(PVector target) {
   PVector desired = PVector.sub(target, location);
   desired.normalize();
   desired.mult(maxspeed);
   
   PVector desired_vel = PVector.sub(desired, velocity);
   desired_vel.limit(maxspeed);
   
   return desired_vel;
  }
  
  
  void applyForce(PVector f) {
   PVector force = f.copy();
   force.div(5);
   force.limit(maxforce);
   acceleration.add(force);
  }
  void update() {
   step();
   display();
  }
}

class Perceptron {
 PVector[] weights;
 float c = 0.01;
 
 Perceptron(int size, float learning_rate) {
  weights = new PVector[size];
  c = learning_rate;
  
  for(int i = 0; i<size; i++) {
   weights[i] = PVector.random2D(); 
  }
 }
 
 PVector process(PVector[] forces) {
  PVector sum = new PVector();
  for(int i = 0; i<weights.length; i++) {
   forces[i].dot(weights[i]);
   sum.add(forces[i]);
  }
  return sum;
 }
 
 void train(PVector[] forces, PVector error) {
  
   
   for(int i =0; i<weights.length; i++) {
    weights[i].x += c * error.x * forces[i].x; 
    weights[i].y += c * error.y * forces[i].y;
 }
 }
}


class Trainer {

  float[] inputs;
  int answer;
  
  Trainer(float x, float y, int a) {
   inputs = new float[3];
   inputs[0] = x;
   inputs[1] = y;
   inputs[2] = 1;
   answer = a;
  }
  
}

Vehicle vehicle;
ArrayList<PVector> targets;
void setup() {
 size(640,360);
 vehicle = new Vehicle(random(width), random(height), 20);
 targets = new ArrayList<PVector>();
 for(int i = 0; i<20; i++) {
  targets.add(new PVector(random(width), random(height))); 
 }
}


void draw() {
background(255);
stroke(0);
fill(0,0);
rectMode(CENTER);
for(PVector target: targets) {
  if(random(1) < 0.1) target.add(new PVector(random(-50,50), random(-50,50)));
  ellipse(target.x, target.y, 10,10);
}

vehicle.seek(targets);
vehicle.update();
}