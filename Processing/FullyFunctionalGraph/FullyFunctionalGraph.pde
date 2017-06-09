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
  

  private int dataValueCount;
  private int currentCount = 0;
  private int maxCount = 0;
  
  private float[] valueStore, timeStore;
  
  public graph(float X, float Y, float Height, float Width, float Low, float High, float xTimeInterval, int entries){
    
    
    x = X;                                                                                                                                                // Sets the x coord
    y = Y;                                                                                                                                                // Sets the base y coord
    
    
    
    
    graphHeight = Height;                                                                                                                                  // sets the width and height of the graph
    graphWidth = Width;
    
    
    
    
    minValue = Low;                                                                                                                                         // sets the highest and lowest value inputs
    maxValue = High;
    
    
    
    
    range = maxValue - minValue;                                                                                                                            // Finds the range
    
    
    
    timeInterval = xTimeInterval;                                                                                                                          // Its the range of time's to display on the screen
                                                                                                                                                           // When the *adjusted* time reaches this value, it spans the whole graph
    scaleX = graphWidth/(timeInterval);
    scaleY = graphHeight/range;


    dataValueCount = entries;
    valueStore = new float[entries];
    timeStore = new float[entries];
    
  };  
  
  
  
  
  private float xTimetoPixel(float xTime) {
    return x+(xTime-drawStartTime)/scaleX;
  }
  
  
  private float yValuetoPixel(float yValue) {
    return y-(yValue-minValue)*scaleY;
  }  
  

  
  public void startTiming() {
   startTime = millis();
   currentTime = startTime;
   prevTime = millis();
   drawStartTime = millis();
  }
  
  private void updateTime() {
    if(drawStartTime+ timeInterval < currentTime) {
     drawStartTime += currentTime - prevTime;
    }
    prevTime = currentTime;
    currentTime = millis();
  }
  
  private void getValues() {
    valueStore[currentCount] = float(engduino.getValue());
    timeStore[currentCount] = currentTime;
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

    for(int counter = 0; counter < maxCount-1; counter++) {
       line(xTimetoPixel(timeStore[counter]), yValuetoPixel(valueStore[counter]),xTimetoPixel(timeStore[counter+1]), yValuetoPixel(valueStore[counter+1])); 
    }

  }

   
   
   public void update() {
     updateTime();
     getValues();

     plotValues();
   } 
}