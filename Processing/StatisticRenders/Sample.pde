class Sample implements PositionEventHandler {
  int SIZE = 100;
  ArrayList<Float> gaussianX = new ArrayList<Float>();
  ArrayList<Float> gaussianY = new ArrayList<Float>();
  float sx;
  float sy;
  float mux;
  float muy;
  float sigma;
  float sxx, syy, sxy;
  float varx;
  float vary;
  float covxy;
  float row;
  float bhat;
  float ahat;

  void emptyEntries() {
   SIZE = 0;
   gaussianX.clear();
   gaussianY.clear();
  }
  
   
 void onPositionEvent(PVector position) {
   SIZE++;
   gaussianX.add(position.x);
   gaussianY.add(position.y);
   calculateStatistics();
 }

  void calculateStatistics() {
    if(SIZE < 0)
      return;
    sx = 0;
    sy = 0;
    for (int i = 0; i < SIZE; i++) {
      float x = gaussianX.get(i);
      float y = gaussianY.get(i);
      sx += x;
      sy += y;
    }
    mux = sx / SIZE;
    muy = sy / SIZE;
    sxx = 0;
    syy = 0;
    sxy = 0;
    for(int i = 0; i < SIZE; i++) {
      float x = gaussianX.get(i);
      float y = gaussianY.get(i);
      float x_err = (x-mux);
      float y_err = (y-muy);
      
      sxx += x_err * x_err;
      syy += y_err * y_err;
      sxy += x_err * y_err;
    }
    varx = sxx / SIZE;
    vary = syy / SIZE;
    covxy = sxy / SIZE;
    row = covxy /  (sqrt(varx * vary));
    bhat = sxy / sxx;
    ahat = muy - bhat * mux;
  }

  Sample() {
    regenerate();
  }

  void regenerate() {
    gaussianX.clear();
    gaussianY.clear();
    for (int i = 0; i < SIZE; i++) {
      gaussianX.add(randomGaussian() / 1.1); 
      gaussianY.add(randomGaussian() / 1.1);
    }
    calculateStatistics();
  }

  void draw(Plotter plot) {
      stroke(200, 0, 0);
      strokeWeight(0.5);
    plot.drawLine(mux, plot.Y_MIN, mux, plot.Y_MAX);
    plot.drawLine(plot.X_MIN, muy, plot.X_MAX, muy);
          stroke(0, 0, 200);
      strokeWeight(1.5);
    plot.drawLine(plot.X_MIN, ahat + bhat * plot.X_MIN, plot.X_MAX, ahat + bhat * plot.X_MAX);
    strokeWeight(10);
    for (int i = 0; i < SIZE; i++) {
      float x = gaussianX.get(i);
      float y = gaussianY.get(i);
      int red = 125;
      int green = 125;
      int blue = 125;
      if(x > mux) {
        red += 100;
      }
      else {
        blue += 100;
      }
      if(y > muy) {
        green += 100;
        red -= 50;
        blue -= 50;
      } else {
        green -= 100;
        red += 50;
        blue += 50;
      }

      stroke(red, green, blue);
      plot.drawPoint(x, y);
    }
  }
}