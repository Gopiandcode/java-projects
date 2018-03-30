class Sample {
  static final int SIZE = 100;
  ArrayList<Float> gaussianX = new ArrayList<Float>();
  ArrayList<Float> gaussianY = new ArrayList<Float>();

  Sample() {
    for (int i = 0; i < SIZE; i++) {
      gaussianX.add(randomGaussian() * 10); 
      gaussianY.add(randomGaussian() * 10);
    }
  }

  void regenerate() {
    gaussianX.clear();
    gaussianY.clear();
    for (int i = 0; i < SIZE; i++) {
      gaussianX.add(randomGaussian() * 10); 
      gaussianY.add(randomGaussian() * 10);
    }
  }

  void draw(Plotter plot) {
    strokeWeight(10);
    for (int i = 0; i < 100; i++) {
      plot.drawPoint(gaussianX.get(i), gaussianY.get(i));
    }
  }
}