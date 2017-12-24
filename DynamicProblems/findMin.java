import java.util.Arrays;
public class findMin {
	public static int findMin(int[] array) {
		int G_max = 0;
		int[] chains = new int[array.length];
		for(int i = array.length-1; i>= 0; i--) {
			//System.out.println("Operating on index[" + i + "]:" + array[i]);
			//System.out.println(java.util.Arrays.toString(chains));
			int max = 0;
			for(int j = i; j<array.length; j++) {
				if(array[j] >= array[i]) {if (chains[j] + 1 > max) {max = chains[j] + 1;}}
			}
			chains[i] = max;
			if(chains[i] > G_max) G_max = chains[i];
		}
	return G_max;
	}
	

}