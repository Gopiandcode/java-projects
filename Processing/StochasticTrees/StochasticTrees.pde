void branch(float len) {
  strokeWeight(len*0.1);
  line(0,0,0,-len);
  translate(0,-len);
  
  len *= 0.66;
  
  if(len > 2) {
 
    int n = int(random(1,4));
    
    for(int i = 0; i<n; i++) {
  
      float beta = random(-PI/2, PI/2);
      pushMatrix();
   rotate(beta);
   branch(len);
   popMatrix();
   
    }
  }

}  

float theta = radians(60);


void setup() {size(640,360);

  background(255);
  theta = map(mouseX, 0, width, 0, PI/2);
  translate(width/2, height);
  stroke(0);
  branch(60);

}


void mousePressed() {
   background(255);
  theta = map(mouseX, 0, width, 0, PI/2);
  translate(width/2, height);
  stroke(0);
  branch(60);
 
  
}

void draw() {
}