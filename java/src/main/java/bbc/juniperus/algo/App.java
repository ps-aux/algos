package bbc.juniperus.algo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class App {
	
	public static void main(String[] args) {
		int[] array = createArray(10, 100);
//		selectionSort(array);
		
//		print(Arrays.toString(array));
//		int[] a = {2,3,4,5};
//		int[] b = {2,3,7,8};
//		
//		
//		int[] sorted = mergeSort(a, b);
//		
//		System.out.println(Arrays.toString(sorted));
		
//		int[] ar = new int[]{1,2,3,4,5};
//		String[] res = createAnagrams(ar);

		
		String[] res = createAnagrams3("123");
		
		int i = 1;
		for (String s : res){
			System.out.println(i + ": " + s);
			i++;
		}
		
	}
	
	public static int[] createArray(int size, int maxVal){
		Random random = new Random(System.currentTimeMillis());

		int[] array = new int[size];
		for (int i = 0; i < size; i++) {
			array[i] = random.nextInt(maxVal);
		}
		
		return array;
	}
	
	public static void bubbleSort(int[] array){
		print("Starting bubble sort");
		print(Arrays.toString(array));
		
		for (int i = array.length - 1; i > 0; i--){
			for (int j = 0; j < i; j++)
				if (array[j] > array[j+1]){
					int tmp = array[j+1];
					array[j+1] = array[j];
					array[j] = tmp;
				}
			print(Arrays.toString(array));
		}
	}
	
	
	public static void insertionSort(int[] array){
		print("Starting insertion sort");
		print(Arrays.toString(array));
		
		for (int i = 1; i < array.length; i++){
	
			int previous = i - 1;
			int val = array[i];
			while (previous > -1 && val < array[previous]){
				array[previous + 1] = array[previous];
				previous--;
			}
			array[previous + 1] = val;
			print(Arrays.toString(array));
		}
	}
	
	
	public static void selectionSort(int[] array){
		print("Starting selection sort");
		print(Arrays.toString(array));
		
		for (int j = 0; j < array.length - 1; j++){
			int min = j;
			
			for (int i = j; i < array.length; i++){
				if (array[i] < array[min])
					min = i;
			}
	
			if (min != j){
				int tmp = array[j];
				array[j] = array[min];
				array[min] = tmp;
			}
		print(Arrays.toString(array));
		}
	}

	
	

	public static int[] mergeSort(int[] array, int[] array2){
		int a = 0;
		int b = 0;
	
		int[] sorted = new int[array.length+array2.length];
		int i = 0;
		while (a < array.length  && b < array2.length){
			
			int valA = array[a];
			int valB = array2[b];
			
			if (valA < valB){
				sorted[i] = valA;
				a++;
			}else if (valB < valA){
				sorted[i] = valB;
				b++;
			}else{
				sorted[i] = valA;
				a++;
				i++;
				sorted[i] = valB;
				b++;
			}
			i++;
		}
		
		while (a < array.length){
			sorted[i] = array[a];
			a++;
			i++;
		}
		
		while (b < array2.length){
			sorted[i] = array2[b];
			b++;
			i++;
		}
		
		return sorted;
	}
	
	
	public static String[] createAnagrams(int[] array){

		String[] result = new String[factorial(array.length)];
		if (array.length == 1){
			result[0] = array[0] + "";
			return result;
		}
		int length = result.length/ array.length;
		
		for (int i = 0; i < array.length; i++){
			int[] others = new  int[array.length - 1];
			int k = 0;
			//Copy all except the current
			for (int j = 0; j < array.length; j++){
				if (j != i){
					others[k] = array[j];
					k++;
				}
			}
			
			String[] combinations = createAnagrams(others);
			for (int j = 0; j < combinations.length; j++){
				StringBuilder sb = new StringBuilder();
				sb.append(array[i] + "").append(combinations[j]);
				result[i * length + j] = sb.toString();
			}
			
		}
		
		return result;
	}

	public static String[] createAnagrams2(String word){

		if (word.length() == 1){
			return new String[]{word};
		}
		List<String> anagrams = new ArrayList<String>();
		for (int j = 0; j < word.length(); j++){
			String first = word.substring(0, 1);
			String[] subAnagrams = createAnagrams2(word.substring(1));
			for (String s : subAnagrams){
				anagrams.add(first + s);
			}
			word = word.substring(1) + first; //Revert
		}
		
		return anagrams.toArray(new String[anagrams.size()]);
	}
	
	
	public static String[] createAnagrams3(String word){

		if (word.length() == 1){
			return new String[]{word};
		}

		String first = word.substring(0, 1);
		String[] subAnagrams = createAnagrams3(word.substring(1));

		List<String> anagrams = new ArrayList<String>();
	
		for (String subAnagram : subAnagrams){
			for (int i = 0; i <= subAnagram.length();i++){
				String anagram;
				if (i == 0)
					anagram = first + subAnagram;
				else
					anagram = subAnagram.substring(i - 1, i) + first + subAnagram.substring(i);
				anagrams.add(anagram);
			}
		}
		
		return anagrams.toArray(new String[anagrams.size()]);
	}
	
	
	public static int factorial(int n){
		if (n == 1)
			return 1;
		
		return n * factorial(n - 1);
	}
	
	public static void print(String msg){
		System.out.println(msg);
	}
}
