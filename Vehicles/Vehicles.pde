interface Vehicle {
  PVector getNextPosition(float dt); 
  void resetUpdate();
}
void setup() {
  size(640, 360);
  x = width/2;
  y = height/2;
  bearing = 0;
}

float x;
float y;
float bearing;
float leftSpeed = 0.1;
float rightSpeed = 0.1;
float dt = 0.1;

void keyPressed() {
  println("" + key);
  if (key == 'a') {
    leftSpeed -= 0.1;
  } 
  if (key == 'd') {
    rightSpeed -= 0.1;
  } 
  if (key == 'w') {
    dt += 0.1;
  } 
  if (key == 's') {
    dt -= 0.1;
  } 
  if (key == 'q') {

    leftSpeed += 0.1;
  } 
  if (key == 'e') {

    rightSpeed += 0.1;
  }
}
void draw() {
  background(255);

  float dt = 5;
  float wid = 20;
  float leftX = x + cos(PI/2 - bearing) * wid;
  float leftY = y + sin(PI/2 - bearing) * wid;
  float rightX = x + -cos(PI/2 - bearing) * wid;
  float rightY = y + -sin(PI/2 - bearing) * wid;
  float newLeftX = leftX + sin(PI/2 + bearing) * leftSpeed * dt;
  float newLeftY = leftY + cos(PI/2 + bearing) * leftSpeed * dt;

  float newRightX = rightX + sin(PI/2 + bearing) * rightSpeed * dt;
  float newRightY = rightY + cos(PI/2 + bearing) * rightSpeed * dt;

  float dx = newRightX - newLeftX;
  float dy = newRightY - newLeftY;
  float newBearing = atan2(-dx, -dy);

  text("" + bearing, x + 20, y + 20);
  text("" + newBearing, x + 20, y + 30);
  fill(25);
  stroke(10);

  rectMode(CORNERS);
  line(leftX, leftY, rightX, rightY);
  line(leftX, leftY, mouseX, mouseY);
  line(rightX, rightY, mouseX, mouseY);
  line(leftX, leftY, newLeftX, newLeftY);
  line(rightX, rightY, newRightX, newRightY);
  line(newLeftX, newLeftY, newRightX, newRightY);

  rectMode(CENTER);
  ellipse(x, y, 3, 3);

  ellipse(leftX, leftY, 3, 3);
  ellipse(rightX, rightY, 3, 3);
  x = (newRightX + newLeftX)/2;
  y = (newRightY + newLeftY)/2;
  bearing = newBearing;
}