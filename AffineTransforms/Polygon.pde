import java.util.List;

class Polygon {
  private ArrayList<PVector>  points;

  public Polygon(List<PVector> points) {
    this.points = new ArrayList<PVector>(points);
  }


  public void draw() {
    beginShape();
    for (PVector p : points) {
      vertex(p.x, p.y);
    }
    endShape();
  }

  public void draw(PMatrix transform) {
    beginShape();
    PVector result = new PVector();
    for (PVector p : points) {
      transform.mult(p, result);
      vertex(result.x, result.y);
      println("" + result.x + "/" + width + "," + result.y + "/" + height);
    }
    endShape();
  }

  public Polygon copy() {
     return new Polygon(this.points); 
  }
  public Polygon transform(PMatrix2D transform) {
    ArrayList<PVector> newPoints = new ArrayList<PVector>();
    for (PVector p : points) {
      newPoints.add(transform.mult(p, null));
    }
    return new Polygon(newPoints);
  }
}