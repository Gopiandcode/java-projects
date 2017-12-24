
class Rectangle {
  float x, y, w, h;

  Rectangle(float x, float y, float w, float h) {
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
  }

  void draw() {
    rectMode(CORNER);
    rect(x, y, w, h);
  }
}

class Wave { 
  Rectangle location;
  float sinx, cosx;
  float sinxx, cosxx;
  float t = 0;
  float timespan = 100;
  float max = -1;

  Wave(float x, float y, float w, float h, float sinx, float cosx, float sinxx, float cosxx) {
    location = new Rectangle(x, y, w, h);
    this.sinx = sinx;
    this.cosx = cosx;
    this.sinxx = sinxx;
    this.cosxx = cosxx;
  }

  Wave(float x, float y, float w, float h) {
    location = new Rectangle(x, y, w, h);
  }

  Wave(Rectangle position, float sinx, float cosx, float sinxx, float cosxx) {
    location = new Rectangle(position.x, position.y, position.w, position.h);
    this.sinx = sinx;
    this.cosx = cosx;
    this.sinxx = sinxx;
    this.cosxx = cosxx;
  }

  float calculate_value(float time_at_pixel) {
    float value =  sinx * sin(sinxx*time_at_pixel) + cosx * cos(cosxx* time_at_pixel);
    if(abs(value) > abs(max) || max == -1)
    max = value;
    return value;
   }

  float calculate_scaley() {
    return location.h/ (2 * max);
  }

  void draw() {
    location.draw();
    int i;
    float scalex = timespan / location.w;


    for (i = 0; i< location.w; i++) {
      float time_at_pixel = i * scalex + t;
      float value = calculate_value(time_at_pixel);
    }
    float scaley = calculate_scaley();
    beginShape();
    for (i = 0; i< location.w; i++) {
      float time_at_pixel = i * scalex + t;
      float value = calculate_value(time_at_pixel);
      float coordy = location.y + location.h/2;

      coordy += value * scaley;
      vertex(location.x + i, coordy);
    }
    endShape();
  }


  void update() {
    t += 0.01;
  }
}

class MergedWave extends Wave {
  float max = -1;
  Wave[] waves;
  MergedWave(float x, float y, float w, float h, Wave[] waves) {
    super(x, y, w, h);
    this.waves = waves;
  }

  float calculate_value(float time_at_pixel) {
    return combine_waves(waves, time_at_pixel);
  }

  float calculate_scaley() {
    return location.h/ (max * 2);
  }

  float combine_waves(Wave[] waves, float time_at_pixel) {
    float sum = 1;


    for (Wave w : waves) { 
      sum  *= w.calculate_value(time_at_pixel);
      if (sum > max || max == -1) max = sum;
    }
    return sum;
  }
}



Wave waveA, waveB, waveC, waveD;

void setup() {
  size(630, 640);
  background(255);
  waveA = new Wave(10, 10, 300, 200, 1, 1, 1, 1);
  waveB = new Wave(10, 220, 300, 200, 3, 1.5, 1.5, 0.5);
  waveD = new Wave(10, 430, 300, 200,    1, 2,    3.1,   0.3);
  //waveD = new MergedWave(10, 430, 300, 200, new Wave[] {waveA, waveB});
  waveC = new MergedWave(320, 120, 300, 200, new Wave[] {waveA, waveB, waveD});
}

boolean controllingA = true;

void mousePressed() {
  controllingA = !controllingA;
}

void draw() {
  if (controllingA) {
    waveA.sinx = map(mouseX, 0, width, -5, 5);
    waveA.cosx = map(mouseY, 0, height, -5, 5);
  } else {
     waveA.sinxx = map(mouseX, 0, width, -5, 5);
    waveA.cosxx = map(mouseY, 0, height, -5, 5);
  }
  background(255);
  waveA.draw();
  waveA.update();
  waveB.draw();
  waveB.update();

  waveD.draw();
  waveD.update();

  waveC.draw();
  waveC.update();
}