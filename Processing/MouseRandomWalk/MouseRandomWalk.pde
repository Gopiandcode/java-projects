class Walker {
  int x;
  int y;
  
  Walker() {
    x = width/2;
    y = height/2;
  }
  
  void draw() {
    stroke(0);
    point(x,y);
  }
  
  void update() {
     float probability = random(1);
     
     if(probability < 0.4) {
       if(x < mouseX) {
         x ++;
       } else {
         x--;
       }
     } else if(probability < 0.6) {
       if(x < mouseX) {
         x--;
       } else {
         x++;
       }
     } else if(probability < 0.8) {
       if(y < mouseY) {
         y++;
       } else {
         y--;
       }
     } else {
       if(y < mouseY) {
         y--;
       } else {
         y++;
       }
     }
  }
}

Walker main;

void setup() {
  main = new Walker();
  size(640, 360);
}

void draw() {
  main.update();
  main.draw();
}