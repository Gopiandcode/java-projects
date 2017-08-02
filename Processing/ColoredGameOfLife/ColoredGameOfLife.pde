class Cell {
  float x, y;
  float wx, wy;

  int state;

  int previous;

  Cell(float x, float y, float wx, float wy, int state) {
    this.x = x;
    this.y = y;
    this.wx = wx;
    this.wy = wy;
    this.state = state;
    this.previous = 0;
  }

  void clearState(int value) {
   state = value; 
    previous = 0;
  }
  void setState(int value) {
   previous = state;
   state = value;
    
  }

  void display() {
    if (previous == 0 && state == 1) fill(0, 0, 255);
    else if (state == 1) fill(0);

    else if (previous == 1 && state == 0) fill(255, 0, 0);
    else fill(255);
    rect(x, y, wx, wy);
  }
}


Cell[][] ca;
int rows;
int columns;
float wx;
float wy;

boolean running = false;

void setup() {
  size(1280, 720);
  float w = 10;
  ca = new Cell[(int)(height/w)][(int)(width/w)];  

  rows = ca.length;
  columns = ca[0].length;

  wx = width/columns;
  wy = height/rows;

  for (int j = 0; j<rows; j++) {
    for (int i = 0; i<columns; i++) {
      ca[j][i] = new Cell(i*wx, j*wy, wx, wy, int(random(2)));
     if(!(i != 0 && j != 0 && i != columns-1 && j != rows -1))
     ca[j][i].clearState(0);
  }
  }
}

void mousePressed() {
  int mx = int(mouseX/wx); 
  int my = int(mouseY/wy);

  ca[my][mx].clearState(1 - ca[my][mx].state);
}

void keyPressed() {
  println("" + key);
  if (key == 's') {
    running = !running;
  } else if (key == 'c') {
    for (int j = 0; j<rows; j++) {
      for (int i = 0; i<columns; i++) {
        ca[j][i].clearState( 0);
      }
    }
  } else if (key == 'r') {
    for (int j = 0; j<rows; j++) {
      for (int i = 0; i<columns; i++) {
        ca[j][i].clearState (int(random(2)));
        
     if(!(i != 0 && j != 0 && i != columns-1 && j != rows -1))
     ca[j][i].clearState(0);
      }
    }
  }
}

void draw() {
  if(running) {
   for(int y = 1; y<rows-1; y++) {
    for(int x = 1; x<columns-1; x++) {
       
      int neighbors = 0;
      
      for(int j = -1; j <= 1; j++) {
       for(int i = -1; i <=1; i++) {
        neighbors += ca[y+j][x+i].previous;  
       }
      }
    neighbors -= ca[y][x].previous;
    
    
    if ((ca[y][x].state == 1) && (neighbors < 2)) ca[y][x].setState(0);
    else if((ca[y][x].state == 1) && (neighbors > 3)) ca[y][x].setState(0);
    else if((ca[y][x].state == 0) && (neighbors ==3)) ca[y][x].setState(1);
    }
  } 
 }
 
 for(int j = 0; j<rows; j++) {
    for(int i = 0; i<columns; i++) {
     ca[j][i].display();
     if(i != 0 && j != 0 && i != columns-1 && j != rows -1)
     ca[j][i].setState(ca[j][i].state);
   }
  }
  
  fill(255, 0, 0);
  textSize(15);
  text("Press 's' to " + (running ? "stop" : "start") + ".", 20, 20);
  text("Press 'c' to clear", 20, 40);
  text("Press 'r' to reset", 20, 60);
}