class CarPark {
  private final CarParkView v;
  private int cars = 0;
  public CarPark(CarParkView v) {
   this.v = v; 
  }

  public synchronized int enter() {
    cars++;
    notifyAll();
    v.setCars(cars);
    return cars;
  }

  public synchronized int exit() throws InterruptedException {
    while (cars <= 0) wait();
    cars--;
    v.setCars(cars);
    return cars;
  }
}

class InboundCar implements Runnable {
  private volatile boolean reached = false;
  private volatile boolean completed = false;
  private CarPark p;
  private PathView enterPath;
  public InboundCar(CarPark p, PathView enterPath) {
    this.p = p;
    this.enterPath = enterPath;
  }

  private class CarGeneratorCallback implements Callback {
    public boolean callback() {
      reached = true;
      return completed;
    }
  }
  public Callback getCallback() {
    return new CarGeneratorCallback();
  }
  public void run() {
    enterPath.addCar(getCallback());
    while (!reached) Thread.yield();
    p.enter();
    completed = true;
  }
}

class OutboundCar implements Runnable {
  private volatile boolean reached = false;
  private volatile boolean completed = false;
  private CarPark p;
  private PathView enterPath;
  public OutboundCar(CarPark p, PathView enterPath) {
    this.p = p;
    this.enterPath = enterPath;
  }

  private class CarGeneratorCallback implements Callback {
    public boolean callback() {
      reached = true;
      return completed;
    }
  }
  public Callback getCallback() {
    return new CarGeneratorCallback();
  }
  public void run() {
    try {
      p.exit();
    } 
    catch(InterruptedException e) {
    }
    
    enterPath.addCar(getCallback());
    while (!reached) Thread.yield();
    completed = true;
  }
}

class CarGenerator implements Runnable {

  private CarPark p;
  private PathView enterPath;
  public CarGenerator(CarPark p, PathView enterPath) {
    this.p = p;
    this.enterPath = enterPath;
  }

  public void run() {
    while (true) {
      try {
        long time = (long)(((Math.random() * 2.0) + 1.0) * 0.0 * 1000.0);  
        Thread.sleep(time);
        new Thread(new InboundCar(p, enterPath)).start();
      } 
      catch(InterruptedException e) {
      }
    }
  }
}


class CarExtractor implements Runnable {

  private CarPark p;
  private PathView enterPath;
  public CarExtractor(CarPark p, PathView enterPath) {
    this.p = p;
    this.enterPath = enterPath;
  }

  public void run() {
    while (true) {
      try {
        long time = (long)(((Math.random() * 2.0) + 1.0) * 5.0 * 1000.0);  
        Thread.sleep(time);
        new Thread(new OutboundCar(p, enterPath)).start();
      } 
      catch(InterruptedException e) {
      }
    }
  }
}