package ch1_Basic;

/**
 * Exercises from chapter 1
 * 
 * @author johannes
 *
 */

public class Exercises {


	/**
	 * R-1.8
	 * Return true if n is a multiple of m, otherwise it returns false
	 * 
	 * @param n
	 * @param m
	 * @return boolean 
	 */
	public boolean isMultiple(long n, long m){

		boolean multiple = false;

		// return false if n is smaller than m
		if(n < m){
			return false;
		}
		else if(n % m == 0){
			return true;
		}
		return multiple;

	}	

	/**
	 * R-1.9
	 * Return true if i is odd, otherwise it returns false
	 * 
	 * @param i
	 * @return boolean 
	 */
	public boolean isOdd(int i){

		boolean multiple = false;
		
		if(i == 0) {
			return false;
		}
		
		else if(i == 1) {
			return true;
		}
		
		else if(isMultiple(i, 2) == false) {
			return true;
		}

			return multiple;

	}	

	/**
	 * factorial
	 */
	int total = 0;
	public int factorialRecursive(int n) {
						
		if(n == 0) {
			return total; 
		} else {
			total = total + n;
			return factorialRecursive(n-1); 

		}
	}
	
	/**
	 * R-1.10
	 * Sum of numbers up to but not including n i.e. smaller than n.
	 * @param n
	 * @return
	 */
	public int sumOfAllNumber(int n) {
		
		int sum = 0; 
		
		for(int x = 0; x < n; x++) {
			sum = sum + x;
		}
		
		return sum;
	}
	
	
	/**
	 * R-1.11
	 * Sum of all odd numbers up to but including n i.e. smaller than n.
	 * @param n
	 * @return
	 */
	public int sumOfAllOddNumber(int n) {
		
		int sum = 0; 
		
		for(int x = 0; x < n; x++) {
		
			if(x % 2 == 1) {
				sum = sum + x;
			}
		}
		
		return sum;
	}
	
	
	
	public static void main(String args[]) {

		Exercises sa = new Exercises(); 

		long n = 16l;
		long m = 1l;
		int i = 0;

		System.out.println(sa.isMultiple(n, m));
		System.out.println(sa.isOdd(i));
		System.out.println(sa.factorialRecursive(2));
		System.out.println(sa.sumOfAllNumber(4));
		System.out.println(sa.sumOfAllOddNumber(4));
	}

}


