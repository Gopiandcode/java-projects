import java.util.Scanner;
import java.io.File;
import java.util.Optional;
import java.util.Iterator;
import java.lang.Iterable;

WordSource source;


// we are trying to incorporate the following mapping
// (state, interpretation, data) -> (state, interpretation, data)
// and
// interpretation: (state, data) -> (state, interpretation, data)

interface InformationProcess<T,U,V> {
  T getState();
  U getInterpretation();
  V getData();
  InformationProcess<T,U,V> performTransition(T state, U interpretation, V data);
}



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