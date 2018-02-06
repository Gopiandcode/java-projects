import java.util.Scanner;
import java.io.File;
import java.util.Optional;
import java.util.Iterator;
import java.lang.Iterable;

WordSource source;



void setup() {
  size(1280, 720);
  background(255);
  try {

    source = new WordSource(new Scanner(createInput("corpus.txt")));
  } 
  catch (Exception e) {
    println(e);
  }

  Iterator<String>iter = source.iterator();
  fill(0);

  for (int i = 0; i < 100; i++) {
    text(iter.next(), random(width), random(height));
  }
}