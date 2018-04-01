color RED = color(200, 50, 12);
color BLUE = color(30, 10, 200);
Plotter plot;
Sample sample = new Sample();
void setup() {
  size(1280, 720);
  background(255);
  plot = plotCenteredAt(width/2, height/2, 500, 500);
}

void keyPressed() {
  if(key == 'r') {
    sample.regenerate();
  }
}

void draw() {
  background(255);
  strokeWeight(1.0);
  stroke(0);
  plot.drawSelf();
  sample.draw(plot);
}