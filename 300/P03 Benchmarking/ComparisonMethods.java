/////////////// FILE HEADER //////////////////////////////////////////////////
//
// Title:    ComparisonMethods
// Course:   CS 300 Fall 2020
//
// Author:   Ayan Deep Hazra
// Email:    ahazra2@wisc.edu
// Lecturer: Hobbes LeGault
//
///////////////////////// OUTSIDE HELP ////////////////////////////////////////
//
// Persons:         Prof. Hobbes Legault - General Troubleshooting
// Online Sources:  NONE
//
///////////////////////////////////////////////////////////////////////////////

/**
 * Implements the two Comparison Methods which are bruteForce() and
 * constantTime(). Does not have a main in the ComparisonMethods class.
 * 
 * @author Ayan Deep Hazra
 *
 */
public class ComparisonMethods {

	/*
	 * Calculates and returns the sum of all integers 1 to n. Uses a loop and running
	 * total to calculate the same.
	 * 
	 * @param n The last value to be added in the sum
	 * @return long the sum calculated using a for loop
	 */
	public static long bruteForce(long n) {
		long sum = 0L;
		
		// i is declared as long to accommodate for large values of n that 
		// might very well be out of the scope of the int primitive in java
		for (long i = 1; i <= n; i++) {
			
			// for every iteration sum is added to the current i value
			sum = sum + i;
		}

		return sum;
	}

	/*
	 * Calculates and returns the sum of all integers 1 to n. Uses a formula to
	 * calculate the same.
	 * 
	 * @param n The last value to be added in the sum
	 * @return long the sum calculated using a formula
	 */
	public static long constantTime(long n) {
		
		// triangle formula directly applied to code and value returned
		return n * (n + 1) / 2;
	}
}
