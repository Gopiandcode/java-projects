import java.util.Calendar;
PMatrix2D positionMatrix;
PMatrix2D hourScaleMatrix = getScaleMatrix(1, 5.5);
PMatrix2D minuteScaleMatrix = getScaleMatrix(1, 7.3);

void drawClockTimes() {
  PVector center = new PVector(width/2, height/2);
   for(float i = 1; i <= 12; i++) {
       PVector point = PVector.add(center, new PVector(90 * cos((i/12.0) * PI * 2 - PI/2), 90 * sin((i/12.0) * PI * 2 - PI/2)));
       text("" + i, point.x - textWidth("" + i), point.y);
   }
}

Calendar rightnow = Calendar.getInstance();
float hour = rightnow.get(Calendar.HOUR);
float minutes = rightnow.get(Calendar.MINUTE);

void setup() {
  size(640, 640);
  background(255);
  positionMatrix = getMovementMatrix(new PVector(width/2, height/2));
}

void draw() {
  background(255);
  text("" + hour + ":" + minutes, 10, 10);
  minutes += 1;
  if(minutes >= 60) {
     minutes = 0;
     hour += 1;
  }
  if(hour >= 12) {
     hour = 0; 
  }
  
  PMatrix2D hourMatrix = getMovementMatrix(new PVector(0,0));

  hourMatrix.apply(positionMatrix);
  hourMatrix.apply(getRotationMatrix((hour/12.0) * 2 * PI -  PI));
    hourMatrix.apply(hourScaleMatrix);
  fill(0);
  hand.draw(hourMatrix);



  PMatrix2D minuteMatrix = getMovementMatrix(new PVector(0,0));
  minuteMatrix.apply(positionMatrix);
  minuteMatrix.apply(getRotationMatrix((minutes/60.0) * 2 * PI - PI));
  minuteMatrix.apply(minuteScaleMatrix);
  fill(0);
  hand.draw(minuteMatrix);
  
  drawClockTimes();
}