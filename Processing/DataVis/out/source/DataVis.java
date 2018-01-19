import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.List; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class DataVis extends PApplet {


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


    public void moveTo(PVector newPos) {
        this.position = newPos.copy();
    }

    public void applyForce(PVector force) {
        PVector f = force.copy();
        f.div(mass);
        this.acceleration.add(f);
    }

    public PVector getPosition() {
        return this.position.copy();
    }

    public boolean intersectsWith(DataPoint other) {
        return (PVector.dist(position, other.position) < this.radius + other.radius);
    }

    public PVector repel(DataPoint other) {
        PVector repelForce = PVector.sub(position, other.position);
        return ((repelForce));
    }

    public void draw() {
        rectMode(CENTER);
        fill(mass);
        ellipse(position.x, position.y, radius, radius);
        text(this.string, position.x + radius, position.y);
    }

    public void update() {
        this.velocity.add(acceleration);
        this.velocity.limit(10);
        this.acceleration.limit(0);
        this.position.add(velocity);
    }

}


class DataSet {
    List<DataPoint> dataset = new ArrayList<DataPoint>();

    public void addDataPoint(DataPoint datapoint) {
        dataset.add(datapoint);
    }


    public void draw() {
        for(DataPoint p : dataset) {
            p.draw();
        }
    }

    public void update() {
        for(DataPoint p : dataset) {
            for (DataPoint other : dataset) {
                if(p.intersectsWith(other)) {
                    other.applyForce(p.repel(other));
                }
            }
        }

        for(DataPoint p : dataset) {
            if(random(1) > 0.5f) {
                p.applyForce(new PVector(random(-0.5f, 0.5f), random(-0.5f, 0.5f)));
            }
            p.update();
       }


    }
}

DataSet dataset = new DataSet();

public void setup() {
    


    table = loadTable("public-transport-crime-london-data.csv", "header"); 
    for(TableRow row : table.rows()) {
        println(row + " ");
        
        String date = row.getString("Month");
println(row.getString("Bus"));
println(row.getString("London Underground (includes DLR up to Mar-17)"));
println(row.getString("London Overground"));
println(row.getString("London Tramlink"));
println(row.getString("TfL Rail"));
println(row.getString("Docklands Light Railway (combined with LU before Apr-17)"));
        float value = row.getFloat("Bus");
        dataset.addDataPoint(new DataPoint(width/2, height/2, value * 10, value, "Bus  - " + date));

    }
}

public void draw() {
    dataset.update();
    dataset.draw();
}

  public void settings() {  size(640, 360); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "DataVis" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
