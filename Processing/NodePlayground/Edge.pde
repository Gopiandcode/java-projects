class Message {
    int progress;
    Data data; 
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

    void draw() {
        strokeWeight(1);
        fill(0);
        line(parentA.position.x, parentA.position.y, parentB.position.x, parentB.position.y);
        strokeWeight(5);
        fill(0, 10);
        line(parentA.position.x, parentA.position.y, parentB.position.x, parentB.position.y);

        for(Message message : messagesToA) {

        }

        for(Message message : messagesToB) {
            
        }
    }
}