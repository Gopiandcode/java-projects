void setup() {
  size(640,360);
}

float time = 0;

void draw() {
  float value = noise(time);
  
  point(time*10, value*height);
  time+=0.01;
  
  if(time > width) time = 0;
}