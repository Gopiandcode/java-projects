import java.util.List;
import java.util.Iterator;
class PathView {
  
  class InternalCarView {
    public PVector position;
    public Callback callback;
    
    void update() {
      float dist = min(PVector.dist(position, end), stepSize);
      PVector moveDir = PVector.mult(dir, dist);
      position.add(moveDir);
    }
    
    void draw() {
      drawCarAt(position.x, position.y);
    }
    
    public InternalCarView(Callback callback) {
       this.position = start.copy();
       this.callback = callback;
    }
  }
  private PVector start;
  private PVector end;
  private PVector dir;
  private float stepSize;
  private ArrayList<InternalCarView> completed = new ArrayList<InternalCarView>();
  private ArrayList<InternalCarView> ongoing = new ArrayList<InternalCarView>();
  private volatile boolean insertBufferInUse = false;
  private volatile boolean insertBufferInsert = false;
  private ArrayList<InternalCarView> insertBuffer = new ArrayList<InternalCarView>();
  
  public PathView(PVector start, PVector end) {
    this.start = start;
    this.end = end;
    this.dir = PVector.sub(end,start);
    this.stepSize = dir.mag() / 30;
    dir.normalize();
  }
  
  public synchronized void addCar(Callback c) {
    while(insertBufferInUse) Thread.yield();
    insertBufferInsert = true;
   this.insertBuffer.add(new InternalCarView(c)); 
   
    insertBufferInsert = false;
  }
  
  public void draw() {
    if(!insertBufferInsert) {
      insertBufferInUse = true;
      this.ongoing.addAll(insertBuffer);
      insertBuffer.clear();
      insertBufferInUse = false;
    }
    
    rectMode(CORNERS);
    line(start.x, start.y, end.x, end.y);
    for(InternalCarView v : ongoing) {
       v.draw(); 
    }
    for(InternalCarView v : completed) {
       v.draw(); 
    }
  }
  
  public void update() {
    Iterator<InternalCarView> iter = ongoing.iterator();
    while(iter.hasNext()) {
       InternalCarView v = iter.next();
        v.update();
        if(PVector.dist(v.position, end) < 0.01) {
          completed.add(v);
          iter.remove();
        }
    }
    iter = completed.iterator();
    while(iter.hasNext()) {
       InternalCarView v = iter.next();
        if(v.callback.callback()) {
          iter.remove();
        }
    }
  }
}

class CarParkView {
   private volatile int cars;
   private volatile float leftCornerX;
   private volatile float leftCornerY;
   
   private volatile float rightCornerX;
   private volatile float rightCornerY;
   
   public CarParkView(float leftCornerX, float leftCornerY, float rightCornerX, float rightCornerY) {
     this.cars = 0;
     this.leftCornerX = leftCornerX;
     this.leftCornerY = leftCornerY;
     
     this.rightCornerX = rightCornerX;
     this.rightCornerY = rightCornerY;
   }
   
   public synchronized void setCars(int cars) {
     this.cars = cars;
   }
   
   public void draw() {
     fill(255);
     rectMode(CORNERS);
     rect(leftCornerX, leftCornerY, rightCornerX, rightCornerY);
     String text = "Cars: " + cars;
     fill(0);
     text(text, (leftCornerX + rightCornerX)/2.0 - textWidth(text)/2.0, (leftCornerY + rightCornerY)/2.0);
   }
   
}