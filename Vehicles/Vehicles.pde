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

int x;
int y;
int bearing;

void keyPressed() {
  println("" + key);
  if(key == 'a') {
    x--; 
  } else if(key == 'd') {
   x++; 
  } else if(key == 'w') {
   y--; 
  } else if(key == 's') {
   y++; 
  } else if(key == 'q') {
    bearing += 0.1;
  } else if(key == 'e') {
    bearing -= 0.1;
  }
}
void draw() {
    background(255);
    
    float wid = 20;
    float leftX = cos(PI/2 - bearing) * wid;
    float leftY = sin(PI/2 - bearing) * wid;
    float rightX = -cos(PI/2 - bearing) * wid;
    float rightY = -sin(PI/2 - bearing) * wid;
    
    fill(25);
    stroke(10);
    rectMode(CENTER);
    line(leftX, leftY, rightX, rightY);
    ellipse(x,y, 3, 3);
    
    ellipse(leftX, leftY, 3,3);
    ellipse(rightX, rightY, 3,3);
    
    
}