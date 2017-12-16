float theta = 0;
float dtheta = 0.01;
float radius = 1;

void setup() {
  size(640, 360);
  background(255);
}

void draw(){
   theta += dtheta;
   radius += 0.05;
  float y = radius * sin(theta);
  float x = radius * cos(theta);
  
  noStroke();
  fill(0);
  rectMode(CENTER);
  ellipse(x + width/2, y + height/2, 16,16);
}