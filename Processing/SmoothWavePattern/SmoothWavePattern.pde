float amplitude = height/2;
float angle = 0;
float startAngle = 0;
float angleVel = 0.02;

void setup() {
  background(255);
  size(640, 360);
}

void draw() {
  background(255);
  beginShape();
  angle = startAngle;
  for(int x = 0; x <= width; x+=5) {
      float y = map(cos(angle), -1,1,0,height);
      vertex(x,y);
      angle += angleVel;
  }
  startAngle += angleVel;
  endShape();
}