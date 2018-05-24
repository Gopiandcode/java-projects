
interface Entity {
  void collide(Entity entity);
  PVector update();
  PVector getPosition();
  void draw();
}
float spd = 1;
float accel = 0.5;
float size = 10;
class Particle implements Entity {
   PVector position;
   PVector velocity;
   PVector acceleration;
   
   void collide(Entity other) {
     if(PVector.dist(other.getPosition(), position) < size) {
       PVector dir = PVector.sub(position,other.getPosition());
       dir.normalize();
       dir.mult(5);
       velocity.add(dir);
       
     }
   }
   
   Particle(int startX, int startY) {
      position = new PVector(startX, startY);
      velocity = new PVector((float)Math.random() * spd - spd/2,(float)Math.random() * spd - spd/2);
      acceleration = new PVector((float)Math.random() * accel - accel/2,(float)Math.random() * accel - accel/2);
   }
  
  
  PVector getPosition() {
   return position.copy(); 
  }
  
  PVector update() {
    this.velocity.add(acceleration);
    this.position.add(velocity);
    this.acceleration.mult(0.9);
    return position.copy();
  }
  
  void draw() {
    int red = (int)(velocity.mag() /10 * 255);
    int green = (int)(acceleration.mag() /10 * 255);
    
    int blue = (int)(acceleration.mag() * velocity.mag()/(10*10) * 255);
    fill(red, green, blue);
    rectMode(CENTER);
    noStroke();
    ellipse(position.x, position.y, size, size);
  }
}