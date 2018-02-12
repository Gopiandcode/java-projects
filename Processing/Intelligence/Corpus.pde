class WordSource implements Iterable<String> {
  
  class WordSourceIterator implements Iterator<String> {
      
      Optional<String> last; 
      
      WordSourceIterator() {
         last = WordSource.this.nextWord(); 
      }
      
      boolean hasNext() {
       return last.isPresent(); 
      }
    
      String next() {
       String result = last.get();
       last = WordSource.this.nextWord();
        return result; 
      }
  }
  
  
  Scanner source;
  Optional<String[]> currentLine = Optional.empty();
  Optional<Integer> currentIndex = Optional.empty();
  
  WordSource(Scanner scanner) {
    this.source = scanner;
  }
  
  WordSourceIterator iterator() {
     return new WordSourceIterator(); 
  }
  
  Optional<String> nextWord() {
        if(currentLine.isPresent() && currentIndex.isPresent()) {
          int index = currentIndex.get();
          int newIndex = index + 1;
          String[] words = currentLine.get();
          
          if(newIndex >= words.length || index >= words.length) {
             currentLine = Optional.empty();
             currentIndex = Optional.empty();
          } else {
           currentIndex = Optional.of(newIndex); 
          }
          
          if(index < words.length)
            return Optional.of(words[index]);
          else 
           return nextWord();
        }
      
      if(source.hasNextLine()) {
        String line = source.nextLine();
        currentLine = Optional.of(line.split(" "));
        currentIndex = Optional.of(0);
        return nextWord();
      } else {
        return Optional.empty();
      }
  }
  
}