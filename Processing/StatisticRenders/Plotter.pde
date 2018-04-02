
Plotter plotCenteredAt(float plot_x, float plot_y, float plot_width, float plot_height) {
  return new Plotter(plot_x - plot_width/2, plot_y - plot_height/2, plot_width, plot_height);
}

class Plotter {
  static final float X_MIN = -10, 
    Y_MIN = -10, 
    X_MAX = 10, 
    Y_MAX = 10;
  static final int X_TICK_COUNT = 10;
  static final int Y_TICK_COUNT = 10; 
  static final boolean DRAW_GRIDLINES = true;
  ArrayList<PositionEventHandler> positionListeners = new ArrayList<PositionEventHandler>();

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

  void addPositionListener(PositionEventHandler listener) {
   positionListeners.add(listener); 
  }

  void drawBorder() {
    rectMode(CENTER);
    fill(0);
    strokeWeight(1.0);
    stroke(0);
    line(plot_x, plot_y, plot_x + plot_width, plot_y);
    line(plot_x + plot_width, plot_y, plot_x + plot_width, plot_y + plot_height);
    line(plot_x, plot_y + plot_height, plot_x + plot_width, plot_y + plot_height);
    line(plot_x, plot_y, plot_x, plot_y + plot_height);
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
    for (int i = 1; i < X_TICK_COUNT; i++) {

      float value = i * value_delta_ratio + X_MIN;
      float position = screen_delta_ratio * i + plot_x;

      if (DRAW_GRIDLINES) {
        strokeWeight(0.5);
        stroke(200);
        line(position, plot_y, position, plot_y + plot_height);
      }


      strokeWeight(1.0);
      stroke(0);
      line(position, plot_y + plot_height, position, plot_y + plot_height + 5);
      String label = "" + value;
      text(label, position - textWidth(label)/2, plot_y + plot_height + 20);

      strokeWeight(0.5);
      stroke(200);
    }

    screen_delta_ratio = plot_height / Y_TICK_COUNT;
    value_delta_ratio = (Y_MAX - Y_MIN) / Y_TICK_COUNT;
    for (int i = 1; i < Y_TICK_COUNT; i++) {

      float value = Y_MAX -  i * value_delta_ratio;
      float position = screen_delta_ratio * i + plot_y;
      if (DRAW_GRIDLINES) {
        strokeWeight(0.5);
        stroke(200);
        line(plot_x, position, plot_x + plot_width, position);
      }
      strokeWeight(1.0);
      stroke(0);
      line(plot_x, position, plot_x - 5, position);
      String label = "" + value;
      text(label, plot_x - 5 - textWidth(label) * 1.5, position);
    }
  }

  private PVector toScreenSpace(float x, float y) {
    float screen_x = plot_x + ((max(min(x, X_MAX), X_MIN) - X_MIN) / (X_MAX - X_MIN)) * (plot_width);
    float screen_y = plot_y + plot_height - ((max(min(y, Y_MAX), Y_MIN) - Y_MIN) / (Y_MAX - Y_MIN)) * (plot_height);

    return new PVector(screen_x, screen_y);
  }

  private PVector toWorldSpace(float x, float y) {
    float world_x = (x - plot_x)  * ((X_MAX - X_MIN)/(plot_width)) + X_MIN;
    float world_y = (plot_height - (y - plot_y))  * ((Y_MAX - Y_MIN)/(plot_height)) + Y_MIN;
    return new PVector(world_x, world_y);
  }

  void drawLine(float x1, float y1, float x2, float y2) {
    PVector p1 = toScreenSpace(x1, y1);
    PVector p2 = toScreenSpace(x2, y2);
    rectMode(CORNERS);
    line(p1.x, p1.y, p2.x, p2.y);
  }

  private boolean checkBounds(float x, float y) {
    return x > plot_x && x < plot_x + plot_width && y > plot_y && y < plot_y + plot_height;
  }
  void drawPoint(float x, float y) {
    PVector p1 = toScreenSpace(x, y);
    if (checkBounds(p1.x, p1.y)) {
      rectMode(CORNERS);
      point(p1.x, p1.y);
    }
  }

  void drawRectangle(float x1, float y1, float x2, float y2) {
    PVector p1 = toScreenSpace(x1, y1);
    PVector p2 = toScreenSpace(x2, y2);
    rectMode(CORNERS);
    rect(p1.x, p1.y, p2.x, p2.y);
  }

  void drawEllipse(float x1, float y1, float x2, float y2) {
    PVector p1 = toScreenSpace(x1, y1);
    PVector p2 = toScreenSpace(x2, y2);
    rectMode(CORNERS);
    ellipse(p1.x, p1.y, p2.x, p2.y);
  }
  
  
  void onMousePressed() {  
    if((mouseX > plot_x && mouseX < plot_x + plot_width) && (mouseY > plot_y && mouseY < plot_y + plot_height)) {
      PVector worldPos = toWorldSpace(mouseX, mouseY);
      for(PositionEventHandler event : positionListeners) {
        event.onPositionEvent(worldPos.copy());
      }
    }
  }
}