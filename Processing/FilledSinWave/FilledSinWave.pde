float startAngle = 0;
float angleVel = 0.1;

void setup() {
  background(255);
  size(640, 360);
}

void draw() {
  background(255, 50);
  float angle = startAngle;
  for(int x = 0; x <= width; x+= 5) {
    float y = map(sin(angle),-1,1,0,height);
    stroke(0);
    fill(0, 50);
    ellipse(x,y,48,48);
    angle+=angleVel;
  }
  startAngle+=angleVel/10;
}