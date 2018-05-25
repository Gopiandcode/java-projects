final int CarWidth = 10;
final int CarHeight = 30;

public class Car implements Vehicle {
 private PVector position;
 private PVector wheelSpeeds;
 private float bearing;
 private PVector prevPosition;
 
 PVector getNextPosition(float dt) {
   PVector lWheel = position.add(new PVector(CarWidth/2 * cos(bearing), CarWidth/2 * sin(bearing)));
   PVector rWheel = position.add(new PVector(-CarWidth/2 * cos(bearing), CarWidth/2 * sin(bearing)));
   return null;
 }
 void resetUpdate() {
   position = prevPosition;
 }
 
 
}