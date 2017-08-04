class Rule {
 char character;
 String sequence;
 
 Rule(char c, String s) {
   character = c;
   sequence = s;
 }
  
}

class Ruleset {
 ArrayList<Rule> rules;
 
 Ruleset() {
  rules = new ArrayList<Rule>();
 }
 
 Ruleset(ArrayList<Rule> rules) {
  this.rules = rules;
 }
 
 void add(Rule rule) {
  rules.add(rule); 
 }
  
  Rule findRule(char c) {
   for(Rule r : rules) {
    if(r.character == c) return r; 
   }
   return null;
  }
  
 String applyToString(String s) {
  String str = "";
  for(int i = 0; i<s.length(); i++) {
    char c = s.charAt(i);
    Rule r = findRule(c);
     if(r != null)
    str += r.sequence;
    else
    str += c;
  }
  
  return str;
 }
}


class Turtle {
  float len; float theta;
  
  Turtle(float x, float y, float theta, float len) {
    translate(x,y);
    this.theta = theta;
    this.len = len;
  }

 
 void interpret(char c) {
  switch(c) {
   case 'F': {
     line(0,0, len,0);
     translate(len,0);
     break;
   }
   case'G': {
     translate(len,0);
     break;
   }
   case '+': {
     rotate(theta);
     break;
   }
   case '-':
   rotate(-theta);
   break;
   case '[':
   pushMatrix();
   break;
   case ']':
   popMatrix();
   break;
  }
}

 void interpret(String s) {
   for(int i = 0; i<s.length(); i++) {
    char c = s.charAt(i); 
     interpret(c);
   }
   
 }
 
 void changeLen(float multiplier) {
  len *= multiplier; 
 }
 
}


String sequence = "F";
Ruleset rules;

Turtle turtle;
float len = width/4;

void mousePressed() {
 sequence = rules.applyToString(sequence); 
 print(sequence + "\n");
 
 translate(width/2, height);
 rotate(radians(-90));
  background(255);
  turtle.interpret(sequence);
  turtle.changeLen(0.5);
}

void setup() {
 size(640, 360);
 strokeWeight(1);
  background(255);
  turtle = new Turtle(width/2, height/2, radians(25), width/12);
  rules = new Ruleset();
  rules.add(new Rule('F', "FF+[+F-F-F]-[-F+F+F]"));
}

void draw() {
  //turtle.clear();
  
}