

NodeSystem nodes;

void setup() {
  size(1280, 720);
  nodes = new NodeSystem();
}

void keyPressed() {
  nodes.onKeyPressed();
}
void mousePressed() {
  nodes.onMousePressed();
}

void draw() {
  background(255);
  nodes.draw();
  nodes.update();

}