package neural_network;

import java.util.Arrays;

public class NetworkTools {
	
	public static double[] createArray(int size, double init_value) {
		if(size < 1) {
			return null;
		}
		double[] arr = new double[size];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = init_value;
		}
		return arr;
	}
	
	public static double[] createRandomArray(int size, double lower_bound, double upper_bound) {
		if(size < 1) {
			return null;
		}
		double[] arr = new double[size];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = randomValue(lower_bound, upper_bound);
		}
		return arr;
	}
	
	public static double[][] createRandomArray(int sizeX, int sizeY, double lower_bound, double upper_bound) {
		if(sizeX < 1 || sizeY < 1) {
			return null;
		}
		double[][] arr = new double[sizeX][sizeY];
		for (int i = 0; i < sizeX; i++) {
			arr[i] = createRandomArray(sizeY, lower_bound, upper_bound);
		}
		return arr;
	}

	private static double randomValue(double lower_bound, double upper_bound) {
		return Math.random() * (upper_bound - lower_bound) + lower_bound;
	}
	
	public static Integer[] randomValues(int lower_bound, int upper_bound, int amount) {
		lower_bound--;
		
		if(amount > upper_bound - lower_bound) {
			return null;
		}
		
		Integer[] values = new Integer[amount];
		for(int i = 0; i < amount; i++) {
			int n = (int)(Math.random()*(upper_bound - lower_bound + 1) + lower_bound);
			while(containsValue(values, n)) {
				n = (int)(Math.random()*(upper_bound - lower_bound + 1) + lower_bound);
			}
			values[i] = n;
		}
		return values;
	}

	public static <T extends Comparable<T>> boolean containsValue(T[] arr, T value) {
		for (int i = 0; i < arr.length; i++) {
			if(arr[i] != null) {
				if(value.compareTo(arr[i]) == 0) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static int indexOfHighestValue(double[] values) {
		int index = 0;
		for (int i = 0; i < values.length; i++) {
			if(values[i] > values[index]) {
				index = i;
			}
		}
		return index;
	}

	public static void main(String[] args) {
		double[] arr = createRandomArray(3, 0.5, 0.7);
		System.out.println(Arrays.toString(arr));
	}

}
