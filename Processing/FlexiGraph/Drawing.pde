public class graph {
  private float x, y, graphHeight, graphWidth;
  private float scaleX, scaleY;
  private float minValue, maxValue;
  private float range;
  
  private float currentTime;
  private float drawStartTime;
  private float startTime;
  private float prevTime;
  private float timeInterval;
  
  private String label;
  
  private float timeLargeBar, timeThinBar, valueLargeBar, valueThinBar;
  
  private int dataValueCount;
  private int currentCount = 0;
  private int maxCount = 0;
  
  private FloatList valueStore, timeStore;
  
  public graph(float X, float Y, float Height, float Width, float Low, float High, float xTimeInterval, int noThick, int noThinBetween, String value, int entries){
    x = X; y = Y; graphHeight = Height; graphWidth = Width;
    minValue = Low; maxValue = High;
    range = maxValue - minValue;
    
    timeInterval = xTimeInterval;
    scaleX = graphWidth/(timeInterval);
    scaleY = graphHeight/range;
    
    label = value;
    
    timeLargeBar = timeInterval/noThick;
    timeThinBar = timeInterval/(noThick*noThinBetween);
    
    valueLargeBar = range/noThick;
    valueThinBar = range/(noThick*noThinBetween);
    
    
    dataValueCount = entries;
    valueStore = new FloatList();
    timeStore = new FloatList();
    
  };  
  
  private float xTimetoPixel(float xTime) {
    return x+(xTime-drawStartTime)*scaleX;
  }
  
  private float xPixeltoTime(float xPixel) {
    return (xPixel - x)/scaleX + drawStartTime;
  }
  
  private float yValuetoPixel(float yValue) {
    return y-(yValue-minValue)*scaleY;
  }  
  
  private float yPixeltoValue(float yPixel) {
     return (y-yPixel)/scaleY + minValue; 
    
  }
  
  public void startTiming() {
   startTime = millis();
   currentTime = startTime;
   prevTime = millis();
   drawStartTime = millis();
  }
  
  private void updateTime() {
    if(timeInterval < currentTime-drawStartTime) {
     drawStartTime += currentTime - prevTime;
    }
    prevTime = currentTime;
    currentTime = millis();
  }
  
  private void getValues() {
    valueStore.append(float(engduino.getValue()));
    timeStore.append(currentTime);
    if(valueStore.size() >dataValueCount) {valueStore.remove(0); timeStore.remove(0);}
    if (currentCount < dataValueCount-1) {
       currentCount++;
       if(maxCount < currentCount) {
        maxCount = currentCount;
       }
    }
    else {
       currentCount = 0; 
    }
  }
  
  
  private void plotValues() {
     strokeWeight(1);
    for(int counter = 0; counter < maxCount-1; counter++) {
        if(timeStore.get(counter) > drawStartTime) line(xTimetoPixel(timeStore.get(counter)), yValuetoPixel(valueStore.get(counter)),xTimetoPixel(timeStore.get((counter+1))), yValuetoPixel(valueStore.get((counter+1))));

    }
    strokeWeight(1);
  }
  
  private void drawAxis() {
    fill(#000000);
    text("Time(s)", (x+graphWidth)/2, y+graphHeight/10);
    text(label, X-graphWidth/6, (Y+graphHeight)/2);
    strokeWeight(3);
    line(x, y, x+graphWidth, y);                    //Draws the X Axis
    line(x,y, x, y-graphHeight);                    //Draws the Y Axis
    strokeWeight(1);
  }
   
   
   
   
   private void drawGridLines() {
     strokeWeight(1);
     for(float i = drawStartTime; i<drawStartTime+timeInterval; i+=timeThinBar) {
       line(xTimetoPixel(i),y,xTimetoPixel(i)%(x+graphWidth),y-graphHeight);
     }
     strokeWeight(2);
     for(float i = drawStartTime; i<=drawStartTime+timeInterval; i+=timeLargeBar) {
       line(xTimetoPixel(i),y+graphHeight/64,xTimetoPixel(i),y-graphHeight);
       fill(#000000);
       text((i)/1000,xTimetoPixel(i)-graphWidth/50,y+graphHeight/20); 
     }
     
     for(float i = minValue; i<maxValue; i+=valueThinBar) {
       line(x,yValuetoPixel(i),x+graphWidth,yValuetoPixel(i));
     }
     strokeWeight(2);
     for(float i = minValue; i<=maxValue; i+=valueLargeBar) {
       line(x-graphWidth/90,yValuetoPixel(i),x+graphWidth,yValuetoPixel(i));
       fill(#000000);
       text(i,x-graphWidth/16,yValuetoPixel(i)+graphHeight/100); 
     } 
     
     
     
     
     
     strokeWeight(1);
   }
   
   
   
   
   
   public void update() {
     updateTime();
     getValues();
     drawAxis();
     drawGridLines();
     plotValues();
   } 
}