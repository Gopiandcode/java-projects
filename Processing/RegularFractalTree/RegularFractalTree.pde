void branch(float len) {
  strokeWeight(len*0.1);
  line(0,0,0,-len);
  translate(0,-len);
  
  len *= 0.66;
  
  if(len > 2) {
   pushMatrix();
   rotate(theta);
   branch(len);
   popMatrix();
   
   pushMatrix();
   rotate(-theta);
   branch(len);
   popMatrix();
  }

}  

float theta = radians(60);


void setup() {size(640,360);}


void draw() {
  background(255);
  theta = map(mouseX, 0, width, 0, PI/2);
  translate(width/2, height);
  stroke(0);
  branch(60);
}