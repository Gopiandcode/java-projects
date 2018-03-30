color RED = color(200,50,12);
color BLUE = color(30, 10, 200);
Plotter plot;
void setup() {
  size(1280, 720);
   background(255);
   plot = plotCenteredAt(width/2, height/2, 500, 500);
}

void draw() {
  plot.drawSelf();
}