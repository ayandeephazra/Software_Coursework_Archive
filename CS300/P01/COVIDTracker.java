//////////////// FILE HEADER //////////////////////////////////////////////////
//
// Title:    COVID Tracker
// Course:   CS 300 Fall 2020
//
// Author:   Ayan Deep Hazra
// Email:    ahazra2@wisc.edu
// Lecturer: Hobbes LeGault
//
///////////////////////// OUTSIDE HELP ////////////////////////////////////////
//
// Persons:         Prof. Hobbes Legault - General Troubleshooting
// Online Sources:  from https://www.geeksforgeeks.org/ - How to find the
//                  unique elements in an Array 
//
///////////////////////////////////////////////////////////////////////////////

/**
 * 
 * This class contains 4 methods relating to COVID Tracking. 
 * - addTest() which adds new Tests by Individuals 
 * - removeIndividual() which removes the record of an Individual from both Arrays
 * - getPopStats() which displays the statistics of the population in a formatted String
 * - getIndividualStats() which displays the statistics of an Individual in a formatted String
 * Also there are additional helper methods that contain code functionality 
 * that is frequently used
 * - uniqueElementsCount() which returns the number of unique elements in an array
 * - pushNull() which arranges array by pushing null references to the end
 * - testsCount_pos_neg() number of elements in the positive and negative arrays
 * 
 * @author Ayan Deep Hazra
 *
 */
public class COVIDTracker {

	/**
	 * Adds ID to the appropriate test array if there is room.
	 * 
	 * @param pos   The current array of positive tests
	 * @param neg   The current array of negative tests
	 * @param id    The tested individual's unique identifier String
	 * @param isPos True if individual has tested positive, else not
	 * @return true if the new record was added, false otherwise
	 */
	public static boolean addTest(String[] pos, String[] neg, String id, boolean isPos) {
		if (isPos) {
			for (int i = 0; i < pos.length; i++) {
				if (pos[i] == null) {
					pos[i] = id;
					// exit the method if record was added
					return true;
				}
			}
		}
		if (!isPos) {
			for (int i = 0; i < neg.length; i++) {
				if (neg[i] == null) {
					neg[i] = id;
					// exit the method if record was added
					return true;
				}
			}
		}

		// if none of the branches were taken, this default is executed.
		// --> record was not updated.

		return false;
	}

	/**
	 * Prompts user for the identifier and removes them from both the test arrays if
	 * there is a match. Even one deletion would return a true.
	 * 
	 * @param pos The current array of positive tests
	 * @param neg The current array of negative tests
	 * @param id  The id to search and possibly remove
	 * @return true if the new record was deleted, false otherwise
	 */

	public static boolean removeIndividual(String[] pos, String[] neg, String id) {
		
		// if even one record is deleted, ifDel is assigned true
		boolean ifDel = false;

		pushNull(pos);
		pushNull(neg);

		// loop for the positive array

		for (int i = 0; i < pos.length; i++) {
			// match found
			if (pos[i] == null)
				continue;
			if (pos[i].equals(id)) {
				for (int j = i + 1; j < pos.length; j++) {
					// each element is assigned the value of the latter element
					pos[j - 1] = pos[j];
				}
				// assigning last element to null
				pos[pos.length - 1] = null;
				ifDel = true;
				// after the deletion we want to check element i again because it could
				// also be a target
				i--;
			}
		}

		// loop for the negative array

		for (int i = 0; i < neg.length; i++) {
			// match found
			if (neg[i] == null)
				continue;
			if (neg[i].equals(id)) {
				for (int j = i + 1; j < neg.length; j++) {
					// each element is assigned the value of the latter element
					neg[j - 1] = neg[j];
				}
				// assigning last element to null
				neg[neg.length - 1] = null;
				ifDel = true;
				// after the deletion we want to check element i again because it could
				// also be a target
				i--;
			}
		}

		pushNull(pos);
		pushNull(neg);

		if (ifDel)
			return true;
		else
			return false;
	}

	/**
	 * Prompts user for the array and removes instances of Null elements that fall
	 * in between two Non-Null elements and pushes it to the back.
	 * 
	 * ADDITIONAL HELPER METHOD
	 * 
	 * @param arr The current array passed in
	 * @return void
	 */

	public static void pushNull(String[] arr) {
		for (int i = 0; i < arr.length; i++) {
			// the next code block effectively takes a null element and places
			// it at the end of the array.
			if (arr[i] == null) {
				for (int j = i + 1; j < arr.length; j++) {
					// if non null element is found, replace and break
					if (arr[j] != null) {
						arr[i] = arr[j];
						arr[j] = null;
						break;
					}
				}
			}
		}
	}

	/**
	 * Prompts user for the arrays and returns the number of elements that appear in
	 * both.
	 * 
	 * ADDITIONAL HELPER METHOD
	 * 
	 * @param pos The current array of positive tests
	 * @param neg The current array of negative tests
	 * @return arr the number of elements in positive and negative arrays in the 0th
	 *         and 1st index of the array returned respectively
	 */

	public static int[] testsCount_pos_neg(String[] pos, String[] neg) {
		pushNull(pos);
		pushNull(neg);
		int valPos = 0, valNeg = 0;
		int[] arr = new int[2];
		// iterate through both loops and find number of total tests
		for (int i = 0; i < pos.length && pos[i] != null; i++)
			valPos++;
		for (int i = 0; i < neg.length && neg[i] != null; i++)
			valNeg++;
		arr[0] = valPos;
		arr[1] = valNeg;
		return arr;
	}

	/**
	 * Prompts user for an array and returns the number of Unique elements contained
	 * within it
	 * 
	 * ADDITIONAL HELPER METHOD
	 * 
	 * @param arr The current array to find unique elements in
	 * @return int The number of unique elements
	 */

	public static int uniqueElementsCount(String[] arr) {
		int uniqueEleCount = 0;
		for (int i = 1; i < arr.length && arr[i] != null; i++) {
			int j = 0;
			for (j = 0; j < i; j++)
				if (arr[i] == arr[j])
					break;

			// If not printed earlier,
			// then print it
			if (i == j)
				uniqueEleCount++;
		}
		return uniqueEleCount;
	}

	/**
	 * Prompts user for the positive and negative arrays and returns the statistics
	 * of the population in a formatted String.
	 * 
	 * @param pos The current array of positive tests
	 * @param neg The current array of negative tests
	 * @return String The statistics of the population
	 */

	public static String getPopStats(String[] pos, String[] neg) {
		pushNull(pos);
		pushNull(neg);

		/**
		 * the 0 index element has number of positive cases the 1 index element has
		 * number of negative cases
		 */

		int[] numTest = testsCount_pos_neg(pos, neg);
		// test count is sum of unique positive test and unique negative tests
		int test_count = numTest[0] + numTest[1];
		// unassigned as of now
		double posTestsPercent;
		double posIndivsPercent;
		// we do not know the contents of the array, so by default they are 0
		int uniqueIndivsCount = 0;
		int uniquePosIndivsCount = 0;

		// the content of the two arrays are easier to traverse
		// if a consolidated array containing elements of both is constructed
		String[] arrayPosNeg = new String[pos.length + neg.length];

		for (int i = 0; i < pos.length; i++) {
			arrayPosNeg[i] = pos[i];
		}
		for (int i = 0; i < neg.length; i++) {
			arrayPosNeg[pos.length + i] = neg[i];
		}

		// arrange null elements to the back of consolidated array
		pushNull(arrayPosNeg);

		// empty positive arrays take the if branch, all others take the else branch

		if (numTest[0] == 0)
			// percentage of something with respect to a whole will always be 0 if its value
			// is 0
			posTestsPercent = 0.0;
		else
			posTestsPercent = ((double) numTest[0] / ((double) numTest[0] + 
					(double) numTest[1])) * 100;

		if (numTest[0] == 0) {
			posIndivsPercent = 0.0;
			if (numTest[1] != 0) {
				// at least one person is there in the negative array
				uniqueIndivsCount = 1;

				uniqueIndivsCount = uniqueIndivsCount + uniqueElementsCount(arrayPosNeg);
			}

		} else {
			// at least one individual is tested, thus value is updated to 1 before loop
			// in method executes
			uniqueIndivsCount = 1;
			uniquePosIndivsCount = 1;

			uniqueIndivsCount = uniqueIndivsCount + uniqueElementsCount(arrayPosNeg);

			uniquePosIndivsCount = uniquePosIndivsCount + uniqueElementsCount(pos);

			/*
			 * so, percent positive individuals is the number of all the unique people who
			 * have tested positive at least one divided by the amount of unique people who
			 * have given a test at least once
			 */
			posIndivsPercent = ((double) uniquePosIndivsCount / (double) uniqueIndivsCount) * 100;
		}

		return "Total tests: " + test_count + "\n" + "Total individuals tested: " +
		       uniqueIndivsCount + "\n" + "Percent positive tests: " + posTestsPercent +
		       "%\n" + "Percent positive individuals: " + posIndivsPercent + "%";
	}

	/**
	 * Prompts user for the positive and negative arrays and String to
	 *  search and returns the statistics of the Individual in a formatted String.
	 * 
	 * @param pos The current array of positive tests
	 * @param neg The current array of negative tests
	 * @param id  The String of the individual to search
	 * @return String The statistics of the Individual
	 */
	
	public static String getIndividualStats(String[] pos, String[] neg, String id) {
		int testCountNeg = 0;
		int testCountPos = 0;
		for (int i = 0; i < pos.length; i++) {
			if (id.equals(pos[i]))
				testCountPos++;
		}
		for (int i = 0; i < neg.length; i++) {
			if (id.equals(neg[i]))
				testCountNeg++;
		}

		// sum of the two variables gives total count for individual
		return "Total tests: " + (testCountPos + testCountNeg) + "\n" +
		       "Positive: " + testCountPos + "\n" +
			   "Negative: " + testCountNeg;
	}

}