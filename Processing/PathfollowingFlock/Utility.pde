PVector getNormalPoint(PVector p, PVector a, PVector b) {
  PVector ap = PVector.sub(p,a);
  PVector ab = PVector.sub(b,a);
  
  ab.normalize();
  ab.mult(ap.dot(ab));
  PVector normalPoint = PVector.add(a,ab);
  return normalPoint;
}