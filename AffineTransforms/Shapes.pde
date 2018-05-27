import java.util.Arrays;
import java.util.List;

//   |        x     --- 6      
//   |      x | x   --- 5         
//   |        |           
//   |        |           
//   |        |           
//   |        |           
//   |-----x--|--x--------
//        -2  0  2


Polygon hand = new Polygon(Arrays.asList(
    new PVector(2, 0), 
    new PVector(1,5), 
    new PVector(0,6),
    new PVector(-1,5), 
    new PVector(-2,0)
));