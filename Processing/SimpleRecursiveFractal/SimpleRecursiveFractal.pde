void drawCircle(int x, int y, float radius) {
 ellipse(x, y, radius, radius);
 if(radius > 2) {
  radius *= 0.75;
  drawCircle(x,y,radius);
   
 }
  
}

void setup(){
  
size(640, 360);

rectMode(CENTER);

drawCircle(width/2, height/2,width);
}