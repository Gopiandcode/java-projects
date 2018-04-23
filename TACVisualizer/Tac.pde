import java.util.List;
import java.util.ArrayList;


//
//

public class TACLexer {
  public static enum TACType {
    
  }
  
  public static class TACToken {
     public final TACType t;
     public final String c;
     
     public TACToken(TACType t, String c) {
      this.t = t;
      this.c = c;
     }
     
     public String toString() {
        if(t == TACType.ATOM) {
          return "ATOM<" + c + ">";
        }
        return t.toString();
     }
  }
  
  public static String getAtom(String s, int i) {
   int j = i;
   for(; j < s.length(); ){
     if(Character.isLetter(s.charAt(j))){
       j++;
     } else {
      return s.substring(i, j); 
     }
   }
   return s.substring(i,j);
  }
  
  
  public static List<TACToken> lex(String input) {
   List<TACToken> result = new ArrayList<TACToken>();
   
   for(int i = 0; i < input.length();) {
    switch(input.charAt(i)) {
      case '(':
      result.add(new TACToken(TACType.LPAREN, "("));
      i++;
      break;
      case ')':
      result.add(new TACToken(TACType.RPAREN, ")"));
      i++;
      break;
      default:
      if(Character.isWhitespace(input.charAt(i))) {
        i++;
      } else {
        String atom = getAtom(input, i);
        i += atom.length();
        result.add(new TACToken(TACType.ATOM, atom));
      }
      break;
   }
  }
  
   return result;
  }
}