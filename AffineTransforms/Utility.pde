PMatrix2D getRotationMatrix(float angle) {
   return new PMatrix2D(cos(angle), -sin(angle), 0, sin(angle), cos(angle), 0);   
}

PMatrix2D getMovementMatrix(PVector position) {
   return new PMatrix2D(1, 0, position.x, 0, 1, position.y); 
}

PMatrix2D getScaleMatrix(float xScale, float yScale) {
   return new PMatrix2D(xScale, 0, 0, 0, yScale, 0); 
  
}