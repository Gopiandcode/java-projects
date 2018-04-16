color RED = color(200, 50, 12);
color BLUE = color(30, 10, 200);
boolean insert = false;
Plotter plot;
Sample sample1 = new Sample();
TheilSen sample2 = new TheilSen();
void setup() {
  size(1280, 720);
  background(255);
  plot = plotCenteredAt(width/2, height/2, 500, 500);
  plot.addPositionListener(sample1);
  plot.addPositionListener(sample2);
}

void keyPressed() {
  if (key == 'r') {
    if (!insert) {
      sample2.regenerate();
      sample1.regenerate();
    }
  }
  if (key == 'i') {
    if (insert) {
      insert = false;
      sample2.regenerate();
      sample1.regenerate();
    } else {
      insert = true;
      sample2.emptyEntries();
      sample1.emptyEntries();
    }
  }
}

void mousePressed() {
  if (insert)
    plot.onMousePressed();
}

void draw() {
  background(255);
  strokeWeight(1.0);
  stroke(0);
  plot.drawSelf();
  sample1.draw(plot);
  sample2.draw(plot);

  int basex = 10;
  int basey = 40;
  int yinc = 30;
  String sx = "Sx = " + sample1.sx;
  String sy = "Sy = " + sample1.sy ;
  String mux = "mUx = " + sample1.mux ;
  String muy = "mUy = " + sample1.muy ;
  String sxx = "Sxx = " + sample1.sxx, 
    syy = "Syy = " + sample1.syy, 
    sxy = "Syx = " + sample1.sxy;
  String varx = "Varx = " + sample1.varx ;
  String vary = "Vary = " + sample1.vary ;
  String covxy = "Covxy = " + sample1.covxy ;
  String row = "Row = " + sample1.row ;
  String bhat = "Bhat = " + sample1.bhat ;
  String ahat = "Ahat = " + sample1.ahat ;
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