Lift lift;
Building renderer;

void setup() {
  size(400, 640);
  lift = new Lift(9);
  renderer = new Building(lift, 50);
}

void keyPressed(KeyEvent e) {
  if (key == CODED) {
    if (keyCode == UP) {
      renderer.moveLiftUp();
    } else if (keyCode == DOWN) {
      renderer.moveLiftDown();
    } else if(keyCode == LEFT) {
      renderer.enterLift(); 
    } else if(keyCode == RIGHT) {
     renderer.leaveLift(); 
    }
  } else if (key == 'r') {
    renderer.randomPerson();
  } else if(key == 'c') {
     renderer.removeSatisifiedCustomers();
  }
}
void draw() {
  background(255);
  pushMatrix();
  translate(width/2-25, 30);
  renderer.draw();
  popMatrix();
  drawOptions();
  drawLabels();
}


void drawOptions() {
   String rButtonPrompt = "Press r to simulate a person arriving at the lift";
   String cButtonPrompt = "Press 'c' to send all people who are at the floor they want home";
   String moveButtonPrompt = "Press 'UP/DOWN' to move the lift";
   String changeButtonPrompt = "Press 'LEFT/RIGHT' to push people off / on the lift";
    fill(0);
   text(rButtonPrompt, width-textWidth(cButtonPrompt), height-70);
text(cButtonPrompt, width-textWidth(cButtonPrompt), height-50);
text(moveButtonPrompt, width-textWidth(cButtonPrompt), height-30);
text(changeButtonPrompt, width-textWidth(cButtonPrompt), height-10);
}

void drawLabels() {
    fill(0);
  text("People on Lift", width/2-125, 20);
   text("People on Building", width/2+50, 20);
}