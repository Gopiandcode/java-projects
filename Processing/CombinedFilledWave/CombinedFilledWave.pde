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
    float y = map(sin(angle) + 2*cos(angle) + 3.5*sin(2*angle) + 0.5*cos(0.5*angle),-7,7,0,height);
    stroke(0);
    fill(0, 50);
    ellipse(x,y,48,48);
    angle+=angleVel;
  }
  startAngle+=angleVel/10;
}