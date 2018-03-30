color RED = color(200,50,12);
color BLUE = color(30, 10, 200);
Plotter plot;
ArrayList<Float> gaussianX = new ArrayList<Float>();
ArrayList<Float> gaussianY = new ArrayList<Float>();

void setup() {
  size(1280, 720);
   background(255);
   plot = plotCenteredAt(width/2, height/2, 500, 500);
   
for(int i = 0; i < 100; i++) {
 gaussianX.add(randomGaussian() * 10); 
 
 gaussianY.add(randomGaussian() * 10); 
}
}



void draw() {
  plot.drawSelf();
  strokeWeight(10);
for(int i = 0; i < 100; i++) {
 plot.drawPoint(gaussianX.get(i), gaussianY.get(i));
}
}