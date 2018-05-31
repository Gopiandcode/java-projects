interface Callback {
 boolean callback(); 
}

void drawCarAt(float x, float y) {
  float scale = 3.5;
    beginShape();
  vertex(x - 10 * scale, y + 5* scale);
  vertex(x - 10* scale, y);
  vertex(x - 5* scale, y);
  vertex(x - 3* scale, y - 2* scale);
  vertex(x + 3* scale, y - 2* scale);
  vertex(x + 5* scale, y);
  vertex(x + 10* scale, y);
  vertex(x + 10* scale, y + 5* scale);
  vertex(x - 10 * scale, y + 5* scale);
  endShape();
}