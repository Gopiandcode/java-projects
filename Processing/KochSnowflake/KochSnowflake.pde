class KochLine {
  PVector start;
  PVector end;
  
  KochLine(PVector a, PVector b) {
   start = a.get();
   end = b.get();
  }
  
  void display() {
 
    stroke(0);
    line(start.x, start.y, end.x, end.y);
  }

  PVector kochA() {
   return start.copy(); 
  }
  
  PVector kochB() {
    PVector v = PVector.sub(end,start);
    v.div(3);
    v.add(start);
    return v;
    
  }

  PVector kochC() {
    PVector a = start.get();
    PVector v = PVector.sub(end,start);
    v.div(3);
    a.add(v);
    v.rotate(-radians(60));
    a.add(v);
    return a;
  }
  
  PVector kochD() {
    PVector v = PVector.sub(end,start);
    v.mult(2/3.0);
    v.add(start);
    return v;
  }
  
  PVector kochE() {
   return end.copy(); 
  }

}

ArrayList<KochLine> lines;

void setup() {
  size(600, 300);
  
  
  
  lines = new ArrayList<KochLine>();
  
  PVector center = new PVector(width/2, height/2);
  
  PVector up = new PVector(0,height/2);
  PVector left = up.copy();
  left.rotate(radians(120));

  
  PVector right = left.copy();
  right.rotate(radians(120));
  
  
  up.add(center);
  left.add(center);
  right.add(center);
 
 
  lines.add(new KochLine(up, left));
  lines.add(new KochLine(left,right));
  lines.add(new KochLine(right, up));
}


void mousePressed() {
 generate(); 
}

void draw() {
  background(255);
  for(KochLine l : lines) {
    l.display();
  }
  
}


void generate() {
  ArrayList<KochLine> next = new ArrayList<KochLine>();
  
  for(KochLine l : lines) {
    PVector a = l.kochA();
    PVector b = l.kochB();
    PVector c = l.kochC();
    PVector d = l.kochD();
    PVector e = l.kochE();
    
    
    next.add(new KochLine(a,b));
    next.add(new KochLine(b,c));
    next.add(new KochLine(c,d));
    next.add(new KochLine(d,e));
  }
  
  lines = next;
}
    
  