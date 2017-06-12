int[] temps = {6248, 17272, 14774, 10324, 13657, 11339, 10202, 18783, 16715, 11158, 17762, 9010, 13711, 17835, 7201, 9539, 19681, 7610, 13543, 13090, 6545, 5315, 9093, 10447, 15802, 9911, 15421, 11815, 9745, 18580, 12267, 13935, 7246, 6143, 18031, 13465, 12556, 5881, 15032, 15468, 5694, 11971, 5946, 7207, 14195, 16704, 17415, 16426, 15869, 18767, 15783, 11089, 9444, 18201, 10823, 6651, 19066, 16865, 15046, 8695, 10510, 9228, 15384, 18127, 13896, 16958, 13861, 18713, 11743, 5954, 12447, 8210, 7034, 17903, 10185, 15139, 10666, 11497, 15004, 17548, 12552, 17693, 9009, 17984, 17154, 18665, 15764, 18514, 11500, 13991, 12658, 18981, 5072, 16079, 19725, 10257, 14453, 15659, 7917, 5647};


public class OutPut {
  private int[] nums;
  private int index = 0;
  public OutPut (int[] values) {
    nums = values;
  }
  
  public int getValue(){
    int output = nums[index];
    
    if(index <nums.length-1) index++;
    else index = 0;
    
    
  return output;
  };
}

OutPut engduino = new OutPut(temps);