color RED = color(200, 50, 12);
color BLUE = color(30, 10, 200);
boolean insert = false;
Plotter plot;
Sample sample = new Sample();
void setup() {
  size(1280, 720);
  background(255);
  plot = plotCenteredAt(width/2, height/2, 500, 500);
  plot.addPositionListener(sample);
}

void keyPressed() {
  if (key == 'r') {
    if(!insert)
      sample.regenerate();
  }
  if(key == 'i') {
    if(insert) {
      insert = false;
      sample.regenerate();
    } else {
      insert = true;
      sample.emptyEntries();
    }
  }
}

void mousePressed() {
  if(insert)
  plot.onMousePressed();
}

void draw() {
  background(255);
  strokeWeight(1.0);
  stroke(0);
  plot.drawSelf();
  sample.draw(plot);

  int basex = 10;
  int basey = 40;
  int yinc = 30;
  String sx = "Sx = " + sample.sx;
  String sy = "Sy = " + sample.sy ;
  String mux = "mUx = " + sample.mux ;
  String muy = "mUy = " + sample.muy ;
  String sxx = "Sxx = " + sample.sxx, 
    syy = "Syy = " + sample.syy, 
    sxy = "Syx = " + sample.sxy;
  String varx = "Varx = " + sample.varx ;
  String vary = "Vary = " + sample.vary ;
  String covxy = "Covxy = " + sample.covxy ;
  String row = "Row = " + sample.row ;
  String bhat = "Bhat = " + sample.bhat ;
  String ahat = "Ahat = " + sample.ahat ;
  text(sx, basex, basey + yinc * 0);
  text(sy, basex, basey + yinc * 1);
  text(mux, basex, basey + yinc * 2);
  text(muy, basex, basey + yinc * 3);
  text(sxx, basex, basey + yinc * 4);
  text(syy, basex, basey + yinc * 5);
  text(sxy, basex, basey + yinc * 6);
  text(varx, basex, basey + yinc * 7);
  text(vary, basex, basey + yinc * 8);
  text(covxy, basex, basey + yinc * 9);
  text(row, basex, basey + yinc * 10);
  text(bhat, basex, basey + yinc * 11);
  text(ahat, basex, basey + yinc * 12);
}