class TheilSen implements PositionEventHandler {
  int SIZE = 100;
  ArrayList<Float> gaussianX = new ArrayList<Float>();
  ArrayList<Float> gaussianY = new ArrayList<Float>();
  ArrayList<Float> ahatList = new ArrayList<Float>();
  ArrayList<Float> bhatList = new ArrayList<Float>();
  
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
   
    ahatList.clear();
    bhatList.clear();
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
    ahat = getMedian(this.ahatList);
    bhat = getMedian(this.bhatList);
  }
  
  float getMedian(ArrayList<Float> values) {
     int size = values.size();
   if(size % 2 == 0) {
     int midPointS = size/2;
     int midPointE = midPointS + 1;
     return (values.get(midPointS) + values.get(midPointE))/2;
   } else {
    int midpoint = size/2;
    return (values.get(midpoint));
   }
  }

  TheilSen() {
    regenerate();
  }

  void regenerate() {
    gaussianX.clear();
    gaussianY.clear();
    ahatList.clear();
    bhatList.clear();
    for (int i = 0; i < SIZE; i++) {
      onPositionEvent(new PVector(randomGaussian() / 1.1,randomGaussian() / 1.1);
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

    }
  }
}