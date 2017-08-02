void drawCircle(int x, int y, float radius) {
  stroke(0);
  noFill();
  ellipse(x, y, radius, radius);
 if(radius > 2) {
  drawCircle(int(x + radius/2), y, radius/2);
  drawCircle(int(x - radius/2), y, radius/2);
 }
  
}

void setup(){
  background(255);
size(640, 360);

rectMode(CENTER);

drawCircle(width/2, height/2,width/2);
}