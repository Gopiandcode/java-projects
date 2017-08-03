void recursivetree(float x, float y, float len) {
 if(len < 1) return;
  
  pushMatrix();
 
 translate(x,y);
 line(-len, 0, len, 0);
 line(-len,len/2, -len, -len/2);
 line(len, len/2, len, -len/2);
 popMatrix();
  
  recursivetree(x-len,y+len/2, len/2.5);
  recursivetree(x-len, y-len/2, len/2.5);
  recursivetree(x+len, y+len/2, len/2.5);
  recursivetree(x+len, y-len/2, len/2.5);
  
}

void setup(){
  background(255);
  size(1280, 720);
  recursivetree(width/2, height/2, width/3);
}