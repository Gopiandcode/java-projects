import java.util.List; //<>// //<>//
import java.util.*;

final int minSize = 10;


abstract class Quadruple {
  protected int startX;
  protected int startY;
  protected int width;
  protected int height;

  public void draw() {
    noFill();
    stroke(10);
    rectMode(CORNER);
    rect(startX, startY, this.width, this.height);
    drawSpecial();
  }


  public Quadruple(int startX, int startY, int width, int height) {
    this.startX = startX;
    this.startY = startY;
    this.width = width;
    this.height = height;
  }

  public boolean contains(PVector position) {
    return (position.x >= startX && position.x <= startX + this.width) && (position.y >= startY && position.y <= startY + this.height);
  }


  abstract public  List<Entity> update();
  abstract protected Quadruple compress();
  abstract protected void drawSpecial();
  abstract Quadruple addEntity(Entity e);
  abstract protected int getEntityCount();
}

class LeafQuadruple extends Quadruple {
  private List<Entity> entities;

  public LeafQuadruple(int startX, int startY, int width, int height) {
    super(startX, startY, width, height);
    entities = new ArrayList<Entity>();
  }

  protected void drawSpecial() {
    for (Entity entity : entities) 
      entity.draw();
  }
  public List<Entity> update() {
    for (int i = 0; i < entities.size()-1; i++) {
      Entity entity = entities.get(i);
      for (int j = i+1; j < entities.size(); j++) {
        entity.collide(entities.get(j));
      }
    }
    ArrayList<Entity> result = new ArrayList<Entity>();
    Iterator<Entity> iter = entities.iterator();
    while (iter.hasNext()) {
      Entity entity = iter.next(); 

      PVector newpos = entity.update();
      if (!contains(newpos)) {
        result.add(entity);
        iter.remove();
      }
    }
    return result;
  }

  protected Quadruple compress() {
    return this;
  }

  public Quadruple addEntity(Entity e) {
    if (this.width < minSize || this.height < minSize || this.entities.size() == 0) {
      this.entities.add(e);
      return this;
    } else {
      NodeQuadruple result = new NodeQuadruple(this.startX, this.startY, this.width, this.height);
      for (Entity entity : entities) {
        result =  result.addEntity(entity, true);
      }
      return result.addEntity(e);
    }
  }

  protected  int getEntityCount() {
    return entities.size();
  }
}

public class NodeQuadruple extends Quadruple {
  private Quadruple q1;
  private Quadruple q2;
  private Quadruple q3;
  private Quadruple q4;

  public NodeQuadruple(int startX, int startY, int width, int height) {
    super(startX, startY, width, height);
    q1 = new LeafQuadruple(startX, startY, width/2, height/2);
    q2 = new LeafQuadruple(startX + width/2, startY, width/2, height/2);
    q3 = new LeafQuadruple(startX, startY + height/2, width/2, height/2);
    q4 = new LeafQuadruple(startX + width/2, startY + height/2, width/2, height/2);
  }

  protected void drawSpecial() {
    text("" + getEntityCount(), startX +  width/2, startY + height/2);
    q1.draw();
    q2.draw();
    q3.draw();
    q4.draw();
  }


  protected Quadruple compress() {

    if (getEntityCount() == 0)
      return new LeafQuadruple(this.startX, this.startY, this.width, this.height);
    else 
    return this;
  }


  public List<Entity> update() {
    ArrayList<Entity> entities = new ArrayList<Entity>();
    ArrayList<Entity> result = new ArrayList<Entity>();
    entities.addAll(q1.update());

    entities.addAll(q2.update());

    entities.addAll(q3.update());

    entities.addAll(q4.update());

    for (Entity entity : entities) {
      PVector pos = entity.getPosition();
      if (!contains(pos))
        result.add(entity);
      else {
        if (q1.contains(pos))
          q1 = q1.addEntity(entity);
        else if (q2.contains(pos))
          q2 = q2.addEntity(entity);
        else if (q3.contains(pos))
          q3 = q3.addEntity(entity);
        else if (q4.contains(pos))
          q4 = q4.addEntity(entity);
      }
    }
    q1  = q1.compress();
    q2  = q2.compress();
    q3  = q3.compress();
    q4  = q4.compress();

    return result;
  }

  protected int getEntityCount() {
    int result = 0;
    result += q1.getEntityCount();
    result += q2.getEntityCount();
    result += q3.getEntityCount();
    result += q4.getEntityCount();
    return result;
  }


  public Quadruple addEntity(Entity entity) {
    if (getEntityCount() == 0) {
      LeafQuadruple quad = new LeafQuadruple(this.startX, this.startY, this.width, this.height);
      return quad.addEntity(entity);
    } else {
      PVector pos = entity.getPosition();
      if (q1.contains(pos))
        q1 = q1.addEntity(entity);
      else if (q2.contains(pos))
        q2 = q2.addEntity(entity);
      else if (q3.contains(pos))
        q3 = q3.addEntity(entity);
      else if (q4.contains(pos))
        q4 = q4.addEntity(entity);
      return this;
    }
  }

  public NodeQuadruple addEntity(Entity entity, boolean multiple) {

    PVector pos = entity.getPosition();
    if (q1.contains(pos))
      q1 = q1.addEntity(entity);
    else if (q2.contains(pos))
      q2 = q2.addEntity(entity);
    else if (q3.contains(pos))
      q3 = q3.addEntity(entity);
    else if (q4.contains(pos))
      q4 = q4.addEntity(entity);
    return this;
  }
}


Quadruple main;

void mousePressed() {
}

void setup() {
  size(720, 640);
  main = new LeafQuadruple(0, 0, width, height);
  main.addEntity(new Particle(width/2, height/2));
  main.addEntity(new Particle(width/2 - 20, height/2 + 10));
}

void draw() {
  background(255);
  main.update();
  main.draw();
  if (mousePressed) {

    main = main.addEntity(new Particle(mouseX, mouseY));
  }
}