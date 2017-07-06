void setup() {
  size(640, 360);
}

float time = 0.0;

void draw() {
  float n = noise(time+=0.01);
  float x = map(n,0,1,0,width);
  
  ellipse(x,180,16,16);
}