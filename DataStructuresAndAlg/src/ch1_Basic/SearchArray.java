package ch1_Basic;

import java.util.Random;


/**
 * Methods and classes to manipulate Arrays
 * 
 * @author johannes
 *
 */

/**
 * Test cases for methods that sort an array of integers
 * 
 *  0. Null passed in.
 * 	1. The array has zero length (no elements) length == 0
 * 	2. The elements are integers // instanceof
 * 	3. The array has one element length == 1
 * 	4. All elements of the array are the same [1,1,1,1,1]
 *  5. The array is already sorted
 *  6. The array is reverse sorted
 * 	7. out of boud exceptions i.e. requested index greater than length-1
 * 
 * 
 * @author johannes
 *
 */


public class SearchArray {


	/**
	 * Counts the number of occurrences of an integer k in an array
	 * 
	 * @param integerArray
	 * @param k
	 * @return count of occurrences of k in integerArray
	 */
	public int findCount(int[] integerArray, int k) {

		// throw a IllegalArgumentException if the array pointer passed as an argument points to null
		if(integerArray == null){
			throw new IllegalArgumentException("The argument for findCount cannot be null");
		}
		// if integerArray is empty return 0
		else if(integerArray.length == 0){
			return 0; 
		}

		int count = 0; // local variable to keep count of occurrences of k.
		for(int x = 0; x < integerArray.length; x++) {

			if(integerArray[x] == k) {
				count++;
			}
		}
		return count;
	}


	/**
	 * C-1.1
	 * Write a short java function that takes an array of int values and determines if there is a pair of numbers in the array whose product is odd
	 * 
	 * @param integerArray
	 * @return boolean, true is a pair of elements sum to an odd number
	 */
	public boolean findPairThatAddToAnOddNumber(int[] integerArray) {

		// throw a IllegalArgumentException if the array pointer passed as an argument points to null
		if(integerArray == null){
			throw new IllegalArgumentException("The argument for findPairThatAddToAnOddNumber cannot be null");
		}
		else if(integerArray.length == 1) {
			throw new IllegalArgumentException("The array cannot have just one element, not possible to calcualte the sum of two elements.");
		}
		// if integerArray is empty return 0
		else if(integerArray.length == 0){
			throw new IllegalArgumentException("The array has no elements, not possible to calcualte the sum of two elements.");
		}

		for(int x = 0; x < integerArray.length-1; x++) {

			if((integerArray[x] + integerArray[x+1]) % 2 == 1) {
				return true;
			}	
		}
		return false;
	}


	/**
	 * C-1.2
	 * Write a short java function that takes an array of int values and determines if there is a pair of numbers in the array whose product is odd
	 * 
	 * Uses only the original passed in Array.
	 * 
	 * @param integerArray
	 * @return boolean, true is a pair of elements sum to an odd number
	 */
	public boolean arrAllElementsDistict(int[] integerArray) {

		// throw a IllegalArgumentException if the array pointer passed as an argument points to null
		if(integerArray == null){
			throw new IllegalArgumentException("The argument cannot be null");
		}
		// if integerArray is empty return exception
		else if(integerArray.length == 0){
			throw new IllegalArgumentException("The array has no elements.");
		}
		else if(integerArray.length == 1) {
			return true;		
		}

		//iterate through all elements in the array
		for(int x = 0; x < integerArray.length; x++) {

			// iterate though all elements in the array
			for(int y = 0; y < integerArray.length; y++) {

				// make sure the element itself is not counted as a duplicate entry
				if(x != y) {

					if(integerArray[x] == integerArray[y]) {
						return false;
					}

				}

			}

		}
		// if this code is reached then no duplicate entries were found
		return true;
	}


	/**
	 * C-1.2
	 * Write a short java function that takes an array of int values and determines if there is a pair of numbers in the array whose product is odd
	 * 
	 * Makes a clone 
	 * 
	 * @param integerArray
	 * @return boolean, if all elements are distinct no 
	 */
	public boolean arrAllElementsDistict2(int[] integerArray) {

		// throw a IllegalArgumentException if the array pointer passed as an argument points to null
		if(integerArray == null){
			throw new IllegalArgumentException("The argument cannot be null");
		}
		// if integerArray is empty return exception
		else if(integerArray.length == 0){
			throw new IllegalArgumentException("The array has no elements.");
		}
		else if(integerArray.length == 1) {
			return true;		
		}

		// make a clone of the array, to compare to. 

		int[] cloneArray = integerArray.clone();

		//iterate through all elements in the array
		for(int x = 0; x < integerArray.length; x++) {

			// iterate though all elements in the array
			for(int y = 0; y < cloneArray.length; y++) {

				// make sure the element itself is not counted as a duplicate entry
				if(x != y) {

					if(integerArray[x] == cloneArray[y]) {
						return false;
					}

				}

			}

		}
		// if this code is reached then no duplicate entries were found
		return true;
	}

	/**
	 * C-1.3
	 * Write a short java function that takes an array of 52 int values and shuffles them with equal probability
	 * 
	 * @param integerArray
	 * @return boolean, true is a pair of elements sum to an odd number
	 */
	public void shuffle52(int[] integerArray) {

		// throw a IllegalArgumentException if the array pointer passed as an argument points to null
		if(integerArray == null){
			throw new IllegalArgumentException("The argument cannot be null");
		}
		// if integerArray is empty return exception
		else if(integerArray.length == 0){
			throw new IllegalArgumentException("The array has no elements.");
		}
		else if(integerArray.length != 52) {
			throw new IllegalArgumentException("The array has not 52 elements.");
		}


		Random rand = new Random();

		//iterate through all elements in the array
		for(int x = 0; x < integerArray.length; x++) {

			// 0.5 probability the current element will be swapped with another
			if(rand.nextInt(10) > 5){

				// temp local variable to hold the value of the current index
				int temp = integerArray[x];
				int swappedWithIndex = rand.nextInt(51);

				integerArray[x] = integerArray[swappedWithIndex];
				integerArray[swappedWithIndex] = temp;
			}
		}
	}


	public int factorial(int n) {

		int result;
		if(n == 0 || n == 1) {
			return 1;
		}

		result = factorial(n-1) * n;

		return result;

	}


	/**
	 * C-1.4
	 * Write a short java function that takes an array of 52 int values and shuffles them with equal probability
	 * 
	 * @param integerArray
	 * @return boolean, true is a pair of elements sum to an odd number
	 */
	public void shuffleLetters(char[] charArray) {

		// throw a IllegalArgumentException if the array pointer passed as an argument points to null
		if(charArray == null){
			throw new IllegalArgumentException("The argument cannot be null");
		}
		// if integerArray is empty return exception
		else if(charArray.length == 0){
			throw new IllegalArgumentException("The array has no elements.");
		}
		else if(charArray.length != 3) {
			throw new IllegalArgumentException("The array has not 6 elements.");
		}


		// permutations - total combinations are number of characters factorial
		int solutions = factorial(3);

		char[] tempArray = new char[charArray.length+1]; 

		String output = "";
		
//		for(int x = 0; x < charArray.length; x++) {
//			tempArray[x] = charArray[x];
//			
//		}
		
		// make sure we have all the solutions
		for(int x = 0; x < solutions; x++) {
			
			// go through each character in the array
			outerLoop:for(int z = 0; z < charArray.length; z++) {
				
				// add the current character to the front
				tempArray[0] = charArray[z];
				System.out.println("front of temp " + String.valueOf(tempArray[0]));
				
			
				
				
				
				// start from the first index
				int y = 1;
				
				// move forward to the end and keep swapping them.
				while(y != charArray.length-1) {

					char tempChar = charArray[y];
					tempArray[y] = charArray[y+1];
					System.out.println(tempArray[y]);
				
					tempArray[y+1] = tempChar;		
					System.out.println(tempArray[y+1]);

					y++;

					System.out.println(tempArray);
				}

			//	break outerLoop;
				
			}
			
		}
		
		
//			
//		for(char ch : tempArray) {
//			output = output + ch;
//		}
//	
//		System.out.println(output);
//		System.out.println(tempArray.length);
//
		

		
		
		
		
		/**
		String output = "";
		//
		for(char ch : charArray) {
			output = output + ch;
		}
		System.out.println(output);

		for(int z = 0; z < charArray.length; z++) {
			System.out.println(z);

			outerLoop:for(int x = z; x < charArray.length; x++) {

				char tempChar;			

				int y = x;

				if(x == charArray.length-1) {

					// move back the way when the end is reached.
					while(y != 0) {

						tempChar = charArray[y];
						charArray[y] = charArray[y-1];
						charArray[y-1] = tempChar;		
						y--;

						System.out.println("innerWhileLoop = " + String.valueOf(charArray));
					}


					// move forward to the original place.
					while(y != z) {

						tempChar = charArray[y];
						charArray[y] = charArray[y+1];
						charArray[y+1] = tempChar;		
						y++;

						System.out.println("innerWhileLoop = " + String.valueOf(charArray));
					}

					break outerLoop;

				}

				tempChar = charArray[x];
				charArray[x] = charArray[x+1];
				charArray[x+1] = tempChar;		

				System.out.println("outerForLoop = " + String.valueOf(charArray));

			}		

		}

		 */

		/**
		String temp;
		char tempChar;
		int index = 0;

		do {

			temp = "";

			for(char ch : charArray) {
				temp = temp + ch;
			}

			System.out.println(temp);

			if(index+1 == charArray.length){
				index = 0;
			}

			tempChar = charArray[index];
			charArray[index] = charArray[index+1];
			charArray[index+1] = tempChar;		
			index++;

			temp = "";

			for(char ch : charArray) {
				temp = temp + ch;
			}

		}while(!output.equalsIgnoreCase(temp));

		 */		


		//iterate through all elements in the array
		/**		for(int x = 0; x < charArray.length; x++) {

			// so this holds the original index of the current character
			int startingIndec = x;
			char temp = charArray[x];

			for(int y = startingIndec; y < charArray.length-1; y++) {

				System.out.println(y);

				output = "";

				charArray[y] = charArray[y+1];
				charArray[y+1] = temp;

				for(char ch : charArray) {
					output = output + ch;
				}

				System.out.println(output);

			}
		}
		 */	}



	public static void main(String args[]) {

		// Another trick to make sure the index will not throw an null pointer exception
		// if( (i >= 0) && (i < array.length) ){}

		SearchArray sa = new SearchArray(); 

		//char[] charArray = {'c','a','r','b','o','n'};
		char[] charArray = {'a','b','c'};


		int[] integerArray = {1,2,3};
		int k = 1;

		System.out.println(sa.findCount(integerArray, k));
		//System.out.println(sa.findPairThatAddToAnOddNumber(integerArray));
		System.out.println(sa.arrAllElementsDistict(integerArray));
		System.out.println(sa.arrAllElementsDistict2(integerArray));

		//		sa.shuffle52(integerArray);
		//		for(int x : integerArray) {
		//			System.out.println(x);
		//		}

		sa.shuffleLetters(charArray);
	}

}


