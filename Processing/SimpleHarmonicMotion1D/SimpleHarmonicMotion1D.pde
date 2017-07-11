float amplitude = 100;
float period = 120;


void setup() {
  background(255);
  size(640, 360);
}

void draw() {
  background(255, 0.01);
  float x = amplitude * cos(TWO_PI * frameCount/period);
  
  stroke(10);
  fill(175);
  rectMode(CENTER);
  line(width/2, height/2, x + width/2, height/2);
  ellipse(x+width/2, height/2, height/8, height/8);

}