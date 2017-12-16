class Perceptron {
 float[] weights;
 float c = 0.01;
 
 Perceptron(int n){ 
  weights = new float[n];
  for(int i = 0; i< weights.length; i++) {
   weights[i] = random(-1,1); 
  }
 }
 
 float feedforward(float[] inputs) {
  float sum = 0;
  for(int i = 0; i<weights.length; i++) {
   sum += inputs[i]*weights[i]; 
  }
  return activate(sum);
 }
 
 float activate(float sum){
   //return (1/(1 + exp(-sum)));
   if(sum > 0) return 1; else return -1;
 }
 
  void train(float[] inputs, int desired) {
   float guess = feedforward(inputs);
   
   float error = desired - guess;
   for(int i = 0; i<weights.length; i++) {
     weights[i] += c * error * inputs[i];
   }
  }
  
}


class Trainer {

  float[] inputs;
  int answer;
  
  Trainer(float x, float y, int a) {
   inputs = new float[3];
   inputs[0] = x;
   inputs[1] = y;
   inputs[2] = 1;
   answer = a;
  }
  
}


float f(float x) {
 return 2*x+1; 
}

Perceptron ptron;
Trainer[] training_set = new Trainer[2000];
int count = 0;

void setup() {
 size(640,360);
 ptron = new Perceptron(3);
 for(int i = 0; i<training_set.length; i++) {
   float x = random(-width/2, width/2);
   float y = random(-height/2, height/2);
   int answer = 1;
   if(y < f(x)) answer = -1;
   training_set[i] = new Trainer(x,y,answer);
 }
}


void draw() {
 background(255);
 translate(width/2, height/2);
 ptron.train(training_set[count].inputs, training_set[count].answer);
 count = (count+1) % training_set.length;
 stroke(0);
 line(-width/2, f(-width/2), width/2, f(width/2));
 for(int i = 0; i<count; i++) {
  stroke(0);
  float guess = ptron.feedforward(training_set[i].inputs);
  if(guess > 0) noFill();
  else fill(0);
  ellipse(training_set[i].inputs[0],training_set[i].inputs[1], 8, 8);
 }
}