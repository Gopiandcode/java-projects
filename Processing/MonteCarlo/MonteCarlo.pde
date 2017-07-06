
float montecarlo() {
  // This essentially returns a probability equal to it's probability
    while(true) {
      float r1 = random(1);
      float probability = r1;
      float r2 = random(1);
      
      if(r2 < probability) {
        return r1;
      }
    }
}


void setup() {
  size(640, 360);
  background(255);
}
int time = 0;
void draw() {
  float value = montecarlo();
  
  float pixel_height = value * height;
  
  point((int)time, (int)pixel_height);
  
  time++;
  if(time > width)
  time = 0;
}