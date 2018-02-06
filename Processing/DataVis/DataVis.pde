import java.util.List;
import java.util.Iterator;

XML xml;
Table table;

class DataPoint {
    float radius;
    float mass;
    PVector position;
    PVector velocity;
    PVector acceleration;
    String string;

    DataPoint(float x, float y, float radius, float mass, String name) {
        position = new PVector(x, y);
        velocity = new PVector();
        acceleration = new PVector();
        this.radius = radius;
        this.mass = mass;
        this.string = name;
    }


    void moveTo(PVector newPos) {
        this.position = newPos.copy();
    }

    void applyForce(PVector force) {
        PVector f = force.copy();
        f.div(mass);
        this.acceleration.add(f);
    }

    PVector getPosition() {
        return this.position.copy();
    }

    boolean intersectsWith(DataPoint other) {
        return (PVector.dist(position, other.position) < this.radius + other.radius);
    }

    void repel(DataPoint other) {
        PVector repelForce = PVector.sub(other.position, position);
        //repelForce.mult(this.radius + other.radius - PVector.dist(position, other.position));
        other.applyForce(repelForce);
        
    }

    void draw() {
        rectMode(CENTER);
        
        fill(velocity.mag() * 10, map(radius, 0, 10, 0, 256), map(mass, 0, 10, 0, 256), 55);
        ellipse(position.x, position.y, radius*2, radius*2);
        fill(mass);
        text(this.string, position.x + radius, position.y);
    }

    void checkPosition() {
     if(this.position.x > width || this.position.x < 0) {
          this.velocity.x *= -1;
        }
        if(this.position.y > height || this.position.y < 0) {
          this.velocity.y *= -1;
        } 
    }
    
    
    void update() {
        this.velocity.add(acceleration);
        this.velocity.limit(10);
        this.acceleration.limit(0);
        this.position.add(velocity);
        this.checkPosition();    
        
    }

}


class DataSet {
    List<DataPoint> dataset = new ArrayList<DataPoint>();

    void addDataPoint(DataPoint datapoint) {
        dataset.add(datapoint);
    }


    void draw() {
        for(DataPoint p : dataset) {
            p.draw();
        }
    }

    void update() {
        for(DataPoint p : dataset) {
            for (DataPoint other : dataset) {
                if(p.intersectsWith(other)) {
                    p.repel(other);
                }
            }
        }

        for(DataPoint p : dataset) {
            if(random(1) > 0.5) {
                p.applyForce(new PVector(random(-0.5, 0.5), random(-0.5, 0.5)));
            }
            p.update();
       }


    }
}

DataSet dataset = new DataSet();
Iterator<TableRow> dataSource;
String currentDate = "";

void populateDataset() {
    
        dataset = new DataSet();
        if(!dataSource.hasNext()) {
          dataSource = table.rows().iterator();  
        }
          TableRow row = dataSource.next();
          
          println(row + " ");
        
        String date = row.getString("Month");
          println(row.getString("Bus"));
          println(row.getString("London Underground (includes DLR up to Mar-17)"));
          println(row.getString("London Overground"));
          println(row.getString("London Tramlink"));
          println(row.getString("TfL Rail"));
          println(row.getString("Docklands Light Railway (combined with LU before Apr-17)"));
 
          float value = row.getFloat("Bus"); //<>//
          dataset.addDataPoint(new DataPoint(width/2, height/2, exp(value/3)/3, value, "Bus"));
          
          value = row.getFloat("London Underground (includes DLR up to Mar-17)");
          dataset.addDataPoint(new DataPoint(width/2, height/2, exp(value/3)/3, value, "London Underground"));
          
          value = row.getFloat("London Tramlink");
          dataset.addDataPoint(new DataPoint(width/2, height/2, exp(value/3)/3, value, "London Tramlink"));
          
          value = row.getFloat("TfL Rail");
          dataset.addDataPoint(new DataPoint(width/2, height/2, exp(value/3)/3, value, "TfL Rail"));
          
          value = row.getFloat("Docklands Light Railway (combined with LU before Apr-17)");
          dataset.addDataPoint(new DataPoint(width/2, height/2, exp(value/3)/3, value, "Docklands Light Railway"));
          
          
          currentDate = date;

}

void mousePressed() {
    populateDataset();
 
}

void setup() {
    size(1280, 720);


    table = loadTable("public-transport-crime-london-data.csv", "header"); 
    dataSource = table.rows().iterator();
    populateDataset();

}

void draw() {
  background(255);
    dataset.update();
    dataset.draw();
    text(currentDate, width-textWidth(currentDate)-10, height - 30);
}