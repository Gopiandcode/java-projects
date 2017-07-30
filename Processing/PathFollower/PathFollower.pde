Path path;
Vehicle car;
void setup() {
  size(640, 360);
  car = new Vehicle(new PVector(width/2, height/2), 2, 5,1);
  path = new Path();
}

void draw() {
  background(255);
  
  path.display();
  if(mousePressed) {
    PVector mouse = new PVector(mouseX, mouseY);
     car.seek(mouse);
  } else {
    car.followPath(path);
  }
  car.update();
}