import java.util.List;
import java.util.Iterator;
class PathView {
  
  class InternalCarView {
    public PVector position;
    public Callback callback;
    
    void update() {
      float dist = min(PVector.dist(position, end), stepSize);
      PVector moveDir = dir.mult(dist);
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
  
  public PathView(PVector start, PVector end) {
    this.start = start;
    this.end = end;
    this.dir = end.sub(start);
    this.stepSize = dir.mag() / 30;
    dir = dir.normalize();
  }
  
  public void draw() {
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