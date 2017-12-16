class CA {
  int[] cells;
  int[] ruleset;
  int generation = 0;
  
  CA() {
   cells = new int[width/10];
   ruleset = new int[] {0,1,0,1,1,0,1,0};
   
   
   for(int i = 0; i<cells.length; i++) {
    cells[i] = 0;      
   }
   cells[(int)cells.length/2] = 1;
  }
  
  void generate() {
  
    int[] nextgen = new int[cells.length];
   for(int i  = 1; i < cells.length-1; i++) {
    int left = cells[i-1];
    int middle = cells[i];
    int right = cells[i+1];
    nextgen[i] = rules(left, middle, right);

   }
    cells = nextgen;
    generation++;
   
  }
  
  int rules(int a, int b, int c) {
   String s = "" + a + "" + b + "" + c;
   int index = Integer.parseInt(s, 2);
   return ruleset[index];
  }
 
  void draw() {
    rectMode(CORNER);
    for(int i  =0; i<cells.length;i++) {
    if(cells[i] == 1) fill(0);
    else fill(255);
    rect(i*10,generation*10, 10,10); 
   }
  }
}

CA ca;
void setup() {
  size(1280, 720);
  ca = new CA();
  
}

void draw() {
 if(ca.generation * 10 > height) {
   delay(5000);
  background(255);
  ca.ruleset = new int[9];
  for(int i  =0; i<9; i++) {
   float prob = random(0,1);
   if(prob > 0.5) {
     ca.ruleset[i] = 1;
     
   }
   else   ca.ruleset[i] = 0;
     
    
  }
  ca.generation = 0;
   
   
 }
  
  ca.draw();
   ca.generate();
 
  
}