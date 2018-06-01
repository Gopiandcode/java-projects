PathView enterPath;
PathView exitPath;
CarParkView carPark;

Thread carGenerator;
Thread carExtractor;

CarPark carparkModel;

void setup() {
  size(1280, 720);
  
  carPark = new CarParkView(width/2.0 - 50.0, height/2.0 - 50.0, width/2.0 + 50.0, height/2.0 + 50.0);
  enterPath = new PathView(
    new PVector(width/2.0 -  400.0, height/2.0),
    new PVector(width/2.0 -  50.0, height/2.0)
    );
  
  exitPath = new PathView(
    new PVector(width/2.0 +  50.0, height/2.0),
    new PVector(width/2.0 +  400, height/2.0)
    );
    
   carparkModel = new CarPark(carPark);
   carGenerator = new Thread(new CarGenerator(carparkModel, enterPath));
   carExtractor = new Thread(new CarExtractor(carparkModel, exitPath));
   carGenerator.start();
   carExtractor.start();
}

void draw() {
  background(255);
    
  enterPath.update();
  exitPath.update();
  
  enterPath.draw();
  exitPath.draw();
  carPark.draw();
}