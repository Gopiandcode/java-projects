class Walker{
  int x; 
  int y;
  Walker() {
    x = width/2;
    y = height/2;
  }
  
  void update() {
    float r = random(1);
    float xstep, ystep;
    if(r < 0.01) {
      xstep = random(-100,100);
      ystep = random(-100,100);
    } else {
      xstep = random(-1,1);
      ystep = random(-1,1);
    }
    
    x += (float)xstep;
    y += (float)ystep;
  }
  
  void draw() {
    stroke(1);
    point(x,y);
  }
  
}

Walker main;

void setup() {
  main = new Walker();
  size(640,360);
}

void draw() {
  main.update();
  main.draw();
}