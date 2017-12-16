import java.util.Iterator;
class Neuron {
  PVector location;

  float sum = 0;

  ArrayList<Connection> connections;
  Neuron(float x, float y) {
    location = new PVector(x, y);
    connections = new ArrayList<Connection>();
  }

  void addConnection(Connection c) {
    connections.add(c);
  }

  void feedforward(float input) {
    sum += input;

    if (sum > 1) {
      fire();
      sum = 0;
    }
  }

  void update() {
   for(Connection c : connections) {
    c.update(); 
   }
  }

  void fire() {
   for(Connection c: connections) {
    c.feedforward(sum); 
   }
    
  }

  void display() {
    for (Connection c : connections) {
      c.display();
    }

    stroke(0);
    fill(150);
    ellipse(location.x, location.y, 16, 16);
  }
}


class Connection {
  class Signal {
   float sum;
   PVector location;
  }
  
  ArrayList<Signal> signals;
  
  Neuron a;
  Neuron b;
  float weight;

  Connection(Neuron from, Neuron to, float w) {
    weight = w;
    a = from;
    b = to;
    signals = new ArrayList<Signal>();
  }

   void feedforward(float sum) {
     Signal sig = new Signal();
     sig.location = a.location.copy();
     sig.sum = sum * weight;
     signals.add(sig);
   }
   
   void update() {
     Iterator<Signal> it = signals.iterator();
    while(it.hasNext()) {
      Signal sig = it.next();
     PVector location = sig.location.copy();
     location.x = lerp(location.x, b.location.x, 0.1);
     location.y = lerp(location.y, b.location.y, 0.1);
     
     sig.location = location;
     
     if(PVector.dist(sig.location,b.location) < 4) {
       b.feedforward(sig.sum);
       it.remove(); 
     }
     
    }
   }

  void display() {
    stroke(0);
    strokeWeight(weight*4);
    line(a.location.x, a.location.y, b.location.x, b.location.y);
    for(Signal s : signals) {
       stroke(0);
       fill(120);
       ellipse(s.location.x, s.location.y, 8,8); 
    }
  }
}

class Network {
  ArrayList<Neuron> neurons;

  PVector location;

  Network(float x, float y) {
    location = new PVector(x, y);
    neurons = new ArrayList<Neuron>();
  }

  void addNeuron(Neuron n) {
    neurons.add(n);
  }

  void feedforward(float input) {
    Neuron start = neurons.get(0);
    start.feedforward(input);
  }

  void connect(Neuron a, Neuron b) {
    Connection c = new Connection(a, b, random(1));
    a.addConnection(c);
  }
  
  void update() {
    for(Neuron n : neurons) {
     n.update(); 
    }
  }
  void display() {
    pushMatrix();
    translate(location.x, location.y);
    for (Neuron n : neurons) {
      n.display();
    }
    popMatrix();
  }
  
  void debug() {
   for(Neuron n: neurons) {
    print("Neuron sum: " + n.sum + "\n"); 
   }
  }
}


Network network;

void setup() {
  size(640, 460);
  network = new Network(width/2, height/2);
  Neuron a = new Neuron(-200, 0);
  
  Neuron b = new Neuron(-100, 100);
  Neuron c = new Neuron(-100, -100);
  
  Neuron d = new Neuron(0, 100);
  Neuron e = new Neuron(0, -100);
  
  Neuron f = new Neuron(100, 100);
  Neuron g = new Neuron(100, -100);
  
  Neuron h = new Neuron(200, 0);

  network.connect(a, b);
  network.connect(a, c);
  
  network.connect(b,d);
  network.connect(c,e);
  
  network.connect(d,f);
  network.connect(e,g);

  network.connect(d,c);
  network.connect(e,b);
  
  network.connect(f,e);
  network.connect(g,d);
  
  network.connect(f,h);
  network.connect(g,h);
  

  network.addNeuron(a);
  network.addNeuron(b);
  network.addNeuron(c);
  network.addNeuron(d);
    network.addNeuron(e);
  network.addNeuron(f);
  network.addNeuron(g);
  network.addNeuron(h);

  network.feedforward(random(100));

}

void draw() {
  background(255);

  if(frameCount % 30 == 0) {
   network.feedforward(random(1)); 
  }

  network.display();
  network.update();
}