class CA {
  int[][] board;
  int columns;
  int rows;
  
  CA(int rows, int columns) {
    this.rows = rows;
    this.columns = columns;
     board = new int[columns][rows];
    for(int x = 0; x < columns; x++) {
     for(int y =0; y< rows; y++) {
       board[x][y] = int (random(2)); 
     }
    }
  }
  
  
  int rules(int value, int x, int y) {
    int neighbors = 0;
    for(int i = x-1; i< x+2; i++) {
     for(int j = y-1; j<y+2; j++) {
      if((i!=x || j != y) && board[i][j] == 1) neighbors ++;
     }
    }
    if(value == 1 && neighbors < 2) return 0;
    
    if(value == 1 && neighbors > 3) return 0;
    
    if(value == 0 && neighbors == 3) return 1;
    
    else return value;
    
    
  }
  
  void generate() {
   int[][] next = new int[columns][rows];
   
   for(int x = 1; x < columns-1; x++) {
    for(int y = 1; y < rows-1; y++) {
     next[x][y] = rules(board[x][y], x,y); 
    }
     
   }
    board = next;
  }
  
  
  void display() {
   int dimx = width/columns;
   int dimy = height/rows;
   
   for(int i = 0; i<columns;i++) {
    for(int j = 0; j<rows; j++) {
     if(board[i][j] == 1) fill(0);
     else fill(255);
     
     rect(i * dimx, j*dimy, dimx, dimy); 
    }
   }
    
  }
}

CA auto;

void setup() {
  size(1280, 720);
  auto = new CA(60, 80);
  
}


boolean running = false;

void keyPressed(){
 if(key == 's') running = !running;
  else if(key == 'r') {
   for(int x = 0; x < auto.columns; x++) {
     for(int y =0; y< auto.rows; y++) {
       auto.board[x][y] = int (random(2)); 
     }
    }
    
    
  } else if(key == 'c') {
    
   for(int x = 0; x < auto.columns; x++) {
     for(int y =0; y< auto.rows; y++) {
       auto.board[x][y] = 0; 
     }
    }
    
  }
}

void mousePressed() {
 int posX = mouseX*auto.columns/width ;
 int posY = mouseY*auto.rows/height;

 auto.board[posX][posY] = 1 - auto.board[posX][posY];
  
}

void draw() {
  
  
  if(running) auto.generate();
  
 auto.display();
 
 
 fill(255,10,02);
 textSize(20);
 text("Press 's' to " + (running ? "stop" : "start"), 20,20);
 text("Press 'r' to reload", 20, 40);
 text("Press 'c' to clear",  20, 60);
}