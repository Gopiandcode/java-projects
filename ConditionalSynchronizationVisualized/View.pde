import java.utll.List;

class PathView {
  private class InternalCarView {
    public PVector position;
    public Callback callback;
    
    public InternalCarView(Callback callback) {
       this.position = start.copy();
       this.callback = callback;
    }
  }
  private PVector start;
  private PVector end;
  private List<InternalCarView> completed = new ArrayList<InternalCarView>();
  private List<InternalCarView> ongoing = new ArrayList<InternalCarView>();
  
}