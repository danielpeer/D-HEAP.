import java.util.Random;

public class Tester {
	
	public static final int BASE_SIZE=1000;
	
	public static void main(String [] args) {

		int[][]sortResults = testDHeapSort();
		printMatrix(sortResults);
		
		 
		}
	
	public static int[] createArray(int mult) {
		int[] randArray = new int[(int)Math.pow(10, mult)*BASE_SIZE];
		Random rand = new Random();
		for(int i=0;i<randArray.length;i++) {
			randArray[i]= rand.nextInt(1001);
		}
		return randArray;
	}
	
	public static int[][] testDHeapSort() {
		int[][] results = new int[3][3]; 
		for(int d=2;d<=4;d++) {
			for(int mult=0;mult<=2;mult++) {
				int count = 0;
				for(int i=0;i<10;i++) {
					 int[]arr = createArray(mult);
					 count+=DHeap.DHeapSort(arr, d);
					 }
				 results[d-2][mult] = count/10;
				 
			 }
		 }
		return results;
	}
	
	public static int testIncreaseKey() {
		int sum=0;
		for(int i=0;i<=10;i++) {
			for(int d=2;d<=4;d++) {
				int[]randArray = createArray(2);
				DHeap_Item[] itemArray = new DHeap_Item[randArray.length];
				DHeap heap = new DHeap(d,randArray.length);
				for(int j=0;i<randArray.length;j++) {
					itemArray[j]=new DHeap_Item(Integer.toString(randArray[j]),randArray[j]);
				}
				sum+=heap.arrayToHeap(itemArray);
				}
		}
		return sum/10;
	}
	
	
	public static void printMatrix(int[][]mat) {
		for(int i=0;i<mat.length;i++) {
			for(int j=0;j<mat[i].length;j++) {
				System.out.print(mat[i][j] + " ");
			}
			System.out.println();
		}
	}
}

