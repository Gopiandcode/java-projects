
Plotter plotCenteredAt(float plot_x, float plot_y, float plot_width, float plot_height) {
  return new Plotter(plot_x - plot_width/2,  plot_y - plot_height/2, plot_width, plot_height);
}

class Plotter {
  static final float X_MIN = -10,
                      Y_MIN = -10,
                      X_MAX = 10,
                      Y_MAX = 10;
  static final int X_TICK_COUNT = 10;
  static final int Y_TICK_COUNT = 10;      
                
  float plot_x;
  float plot_y;
  float plot_width;
  float plot_height;


  Plotter(float plot_x, float plot_y, float plot_width, float plot_height) {
    this.plot_x = plot_x;
    this.plot_y = plot_y;
    this.plot_width = plot_width;
    this.plot_height = plot_height;
  }

  void drawSelf() {
    fill(255);
    rectMode(CORNER);
    rect(plot_x, plot_y, plot_width, plot_height);
    drawBorder();
    drawAxis();
    drawAxisTicks();
  }
  
  void drawBorder() {
    rectMode(CENTER);
    fill(0);
    line(plot_x, plot_y, plot_x + plot_width, plot_y);
    line(plot_x + plot_width, plot_y, plot_x + plot_width, plot_y + plot_height);
    line(plot_x, plot_y + plot_height, plot_x + plot_width, plot_y + plot_height);
    line(plot_x , plot_y, plot_x, plot_y + plot_height);

  }
  
  void drawAxis() {
    //
    //line(plot_x + plot_width/2, plot_y, plot_x + plot_width/2, plot_y + plot_height);
    //line(plot_x , plot_y + plot_height, plot_x + plot_width, plot_y + plot_height/2);

  }
  
  void drawAxisTicks() {
    
    rectMode(CENTER);
    fill(0);
    float screen_delta_ratio = plot_width / X_TICK_COUNT;
    float value_delta_ratio = (X_MAX - X_MIN) / X_TICK_COUNT;
    for(int i = 0; i < X_TICK_COUNT; i++) {
      float value = i * value_delta_ratio + X_MIN;
      float position = screen_delta_ratio * i + plot_x;
      line(position, plot_y + plot_height ,position, plot_y + plot_height + 5);
    }
    
    screen_delta_ratio = plot_height / Y_TICK_COUNT;
    value_delta_ratio = (Y_MAX - Y_MIN) / Y_TICK_COUNT;
    for(int i = 0; i < Y_TICK_COUNT; i++) {
      float value = Y_MAX -  i * value_delta_ratio;
      float position = screen_delta_ratio * i + plot_y;
      line(plot_x, position, plot_x - 5, position);
      
    }
    
  }
  
  void drawLine(float x, float y) {
  }
  
  void drawPoint(float x, float y) {
    
  }
  
  void drawRectangle(float x, float y) {
    
  }
  
  void drawCircle(float x, float y, float w, float h) {
    
  }
}