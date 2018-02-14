import java.util.Iterator;

class Message {
    int progress;
    Data data; 
    
    Message(Data data) {
     this.data = data;
     this.progress = 0;
    }
    
    void draw(PVector from, PVector to) {
      PVector position = PVector.sub(to, from).mult((float)progress / (float)100.0f).add(from);
      fill(0);
      stroke(0);
      ellipse(position.x, position.y, 10,10);
    }
    
    void update() {
      if(progress < 100)
      progress += 1;
    }
    
    boolean hasCompleted() {
      return progress == 100;
    }
}

class Edge {
    Node parentA;
    Node parentB;
    ArrayList<Message> messagesToA;
    ArrayList<Message> messagesToB;


    Edge(Node parentA, Node parentB) {
        messagesToA = new ArrayList<Message>();
        messagesToB = new ArrayList<Message>();
        this.parentA = parentA;
        this.parentB = parentB;
    }
    
    void sendMessageFrom(Node source, Data data) {
       if(source == parentA) {
         messagesToA.add(new Message(data));
       } else if(source == parentB) {
         messagesToB.add(new Message(data));
       }
    }

    void draw() {
        strokeWeight(1);
        fill(0);
        line(parentA.position.x, parentA.position.y, parentB.position.x, parentB.position.y);
        strokeWeight(5);
        fill(0, 10);
        line(parentA.position.x, parentA.position.y, parentB.position.x, parentB.position.y);

        for(Message message : messagesToA) {
          message.draw(parentB.position, parentA.position);
        }

        for(Message message : messagesToB) {
          message.draw(parentA.position, parentB.position);
        }
    }
    
    void update() {
      Iterator<Message> iter = messagesToA.iterator();
      
      while(iter.hasNext()) {
        Message msg = iter.next();
        msg.update();
        
        if(msg.hasCompleted()) {
         parentA.acceptData(msg.data);
         iter.remove();
        }
      }
      
      
      
      iter = messagesToB.iterator();
      
      while(iter.hasNext()) {
        Message msg = iter.next();
        msg.update();
        
        if(msg.hasCompleted()) {
         parentB.acceptData(msg.data);
         iter.remove();
        }
      }
    }
}