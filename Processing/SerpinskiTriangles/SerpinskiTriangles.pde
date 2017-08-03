class Triangle {
  
  PVector A,B,C;
  
  Triangle(PVector A, PVector B, PVector C) {
   this.A = A.copy();
   this.B = B.copy();
   this.C = C.copy();
  }
  
  
  void display() {
    fill(0);
    triangle(A.x, A.y, B.x, B.y, C.x, C.y);
  }
  
  PVector TriangleAB() {
   PVector ab = PVector.sub(B, A);
   ab.div(2);
   ab.add(A);
   return ab;
  }
  
  PVector TriangleAC() {
   PVector ac = PVector.sub(C,A);
   ac.div(2);
   ac.add(A);
   return ac;
  }
  
  PVector TriangleBC() {
   PVector bc = PVector.sub(C,B);
   bc.div(2);
   bc.add(B);
   return bc;
  }
}



ArrayList<Triangle> triangles;

void generate() {
 ArrayList<Triangle> next = new ArrayList<Triangle>();
 
 for(Triangle t : triangles) {
  next.add(new Triangle(t.A, t.TriangleAB(), t.TriangleAC()));
  next.add(new Triangle(t.TriangleAB(), t.B, t.TriangleBC()));
  next.add(new Triangle(t.TriangleAC(), t.TriangleBC(), t.C));
   
 }
  triangles = next;
}


void mousePressed() {
 generate(); 
}

void setup() {
 size(640, 360);
 background(255);
 
 triangles = new ArrayList<Triangle>();
  
  PVector center = new PVector(width/2, height/2);
  
  PVector up = new PVector(0, height/2);
  
  PVector left = up.get();
  left.rotate(radians(120));
  
  PVector right = left.get();
  right.rotate(radians(120));
  
  up.add(center);
  left.add(center);
  right.add(center);
  
  triangles.add(new Triangle(up, left, right));
  
  
}



void draw() {
 background(255);
 for(Triangle t : triangles)  {
   
  t.display(); 
 }
  
}