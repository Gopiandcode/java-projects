class Walker {
  int x;
  int y;
  int dx;
  int dy;
  
  Walker() {
    x = width/2;
    y = height/2;
  }
  
  void draw() {
    stroke(10);
    point(this.x, this.y);
  }
  
  void step() {
    int choice = int(random(9));
    switch(choice) {
      case 0:
        dx--;
        dy++;
        break;
      case 1:
        dy++;
        break;
      case 2:
        dy++;
        dx++;
        break;
      case 3:
        dx--;
        break;
      case 5:
        dx++;
        break;
      case 6:
        dy--;
        dx--;
        break;
      case 7:
        dy--;
        break;
      case 8:
        dy--;
        dx++;
        break;
      
    }
  }
  
  void update() {
   this.x += dx;
   this.y += dy;
   
   if(x > width) {
     x = width;
     dx = -dx;
   }
   if(x < 0) {
     x = 0;
     dx = -dx;
   }
   if(y > height) {
     y = height;
     dy = -dy;
   }
   if(y < 0) {
     y = 0;
     dy = -dy;
   }
  }
}

Walker main;
void setup() {
  main = new Walker();
  size(640,36:0);
  background(255);
}

void draw() {
   main.step();
   main.update();
   main.draw();
}