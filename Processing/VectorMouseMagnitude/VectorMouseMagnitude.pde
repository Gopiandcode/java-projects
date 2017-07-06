
int[] past;
int time;
void setup() {
  size(640, 360);
  past = new int[width];
}


void draw() {
  background(255, 10);
  PVector mouse = new PVector(mouseX, mouseY);
  PVector center = new PVector(width/2, height/2);
  mouse.sub(center);
  
  float m = mouse.mag();
  fill(0);
  rect(0,0,m,10);
  past[time] = (int)(height-m);
  
  for(int i = 0; i<time; i++) {
    point(i, past[i]);
    if(i+1 <=time)
    line(i, past[i], i+1, past[i+1]);
  }
  
  time++;
  
  
  if(time >= width) time = 0;
}