
void drawPVector(float x, float y, float side_length, PVector vector) {
    PVector inv = vector.copy();
    inv.mult(-1);
  float angle = inv.heading();
    fill(150);
    stroke(0,0);
    rectMode(CENTER);
    
    pushMatrix();
    translate(x,y);
    rotate(angle);
    
    triangle(-side_length/4, 0,
             0, side_length/8,
             0, -side_length/8);
     rectMode(CORNERS);
    rect((float)0,(float) -side_length/16,
          (float)side_length/2, (float)side_length/16);
    
    popMatrix();
}