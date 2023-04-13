/////////////// FILE HEADER //////////////////////////////////////////////////
//
// Title:    COVID TrackervTester
// Course:   CS 300 Fall 2020
//
// Author:   Ayan Deep Hazra
// Email:    ahazra2@wisc.edu
// Lecturer: Hobbes LeGault
//
///////////////////////// OUTSIDE HELP ////////////////////////////////////////
//
// Persons:         NONE
// Online Sources:  NONE
//
///////////////////////////////////////////////////////////////////////////////

/**
 * 
 * This class tests the methods defined in COVIDTracker.java. Expected outputs of
 * methods are calculated by hand and equated to the computer generated output,
 * and if there is a match, true is returned from each respective method. Else
 * false is returned and the method has failed the test.
 * 
 * @author Ayan Deep Hazra
 */

public class COVIDTrackerTester {

	/**
	 * Checks whether addTest() works as expected
	 * 
	 * @return true if method functionality is verified, false otherwise
	 */

	public static boolean testAddTest() {
		// two empty arrays -> true; also checking that arrays were updated properly
		String[] pos = new String[2];
		String[] neg = new String[2];
		if (!COVIDTracker.addTest(pos, neg, "AB1234", false) || neg[0] == null)
			return false;
		if (!COVIDTracker.addTest(pos, neg, "CD2345", true) || pos[0] == null)
			return false;
		// two arrays with space -> true
		if (!COVIDTracker.addTest(pos, neg, "CD2345", false) || neg[1] == null)
			return false;
		// one full array but adding to one with space -> true
		if (!COVIDTracker.addTest(pos, neg, "EF3456", true) || pos[1] == null)
			return false;
		// one array with space but adding to full one -> false
		String[] pos2 = new String[2];
		if (COVIDTracker.addTest(pos2, neg, "EF3456", false))
			return false;
		// two full arrays -> false
		if (COVIDTracker.addTest(pos, neg, "EF3456", true))
			return false;

		return true;
	}

	/**
	 * Checks whether removeIndividual() works as expected
	 * 
	 * @return true if method functionality is verified, false otherwise
	 */

	public static boolean testRemoveIndividual() {
		/*
		 * for (String element: pos) { System.out.println(element); }
		 */
		// two empty arrays -> true; also checking that arrays were updated properly
		String[] pos = { "AB1234", "CD2345", null, "EF3456", "EF3456", null, "AB1234", null };

		String[] neg = { null, "GH3456", null, "EF3456", "EF3456", null, null, "CD1234" };

		// adding elements using addTest() method

		// element EF3456 present in atleast one array -> true
		if (!COVIDTracker.removeIndividual(pos, neg, "EF3456"))
			return false;
		// element EF3456 present in atleast one array -> false
		if (COVIDTracker.removeIndividual(pos, neg, "EF3456"))
			return false;
		// element GH3456 present in atleast one array -> true
		if (!COVIDTracker.removeIndividual(pos, neg, "GH3456"))
			return false;
		// element GH3456 present in atleast one array -> false
		if (COVIDTracker.removeIndividual(pos, neg, "GH3456"))
			return false;
		// element CD1234 present in atleast one array -> true
		if (!COVIDTracker.removeIndividual(pos, neg, "CD1234"))
			return false;
		// element CD1234 present in atleast one array -> false
		if (COVIDTracker.removeIndividual(pos, neg, "CD1234"))
			return false;
		// element AB1234 present in atleast one array -> true
		if (!COVIDTracker.removeIndividual(pos, neg, "AB1234"))
			return false;
		// element AB1234 present in atleast one array -> false
		if (COVIDTracker.removeIndividual(pos, neg, "AB1234"))
			return false;

		return true;
	}

	/**
	 * Checks whether getPopStats() works as expected
	 * 
	 * @return true if method functionality is verified, false otherwise
	 */

	public static boolean testGetPopStats() {

		// DEFINING two random pos and neg arrays, with some degree of randomness
		String[] pos = { "AB1234", "CD2345", null, "EF3456",
				null, null, "CD5678", null, null, null };

		String[] neg = { null, "GH3456", null, "EF3456",
				null, null, "CD5678", "CD1234", "AB1234", "GH3456" };

		// Testing if required output matches, should return true
		if (!COVIDTracker.getPopStats(pos, neg).equals("Total tests: 10\n" 
		        + "Total individuals tested: 6\n" + "Percent positive tests: 40.0%\n" 
				+ "Percent positive individuals: 66.66666666666666%"))
			return false;

		// Testing with random junk values, should return false
		if (COVIDTracker.getPopStats(pos, neg)
				.equals("Total tests: 1\n" + "Total individuals tested: 1\n"
				+ "Percent positive tests: 0.0%\n" + "Percent positive individuals: 100.00%"))
			return false;

		// DEFINING two arrays with single entries of same individual

		String[] pos1 = { "AB1234", null };

		String[] neg1 = { "AB1234", null };

		// Testing if required output matches, should return true
		if (!COVIDTracker.getPopStats(pos1, neg1)
				.equals("Total tests: 2\n" + "Total individuals tested: 1\n"
				+ "Percent positive tests: 50.0%\n" + "Percent positive individuals: 100.0%"))
			return false;

		// Testing with random junk values, should return false
		if (COVIDTracker.getPopStats(pos1, neg1)
				.equals("Total tests: 1\n" + "Total individuals tested: 1\n"
				+ "Percent positive tests: 0.0%\n" + "Percent positive individuals: 100.0%"))
			return false;

		// DEFINING two empty arrays to gauge the programs capability of handling
		// division by zero errors & NaN errors

		String[] pos2 = {};

		String[] neg2 = {};

		// Testing if required output matches, should return true
		if (!COVIDTracker.getPopStats(pos2, neg2)
				.equals("Total tests: 0\n" + "Total individuals tested: 0\n"
				+ "Percent positive tests: 0.0%\n" + "Percent positive individuals: 0.0%"))
			return false;

		// Testing with random junk values, should return false
		if (COVIDTracker.getPopStats(pos2, neg2)
				.equals("Total tests: 1\n" + "Total individuals tested: 1\n"
				+ "Percent positive tests: 0.0%\n" + "Percent positive individuals: 100.0%"))
			return false;

		// DEFINING one empty pos array & one unique fully filled
		// neg array to check corner case handling
		String[] pos3 = {};

		String[] neg3 = { "AB1234" };

		// Testing if required output matches, should return true
		if (!COVIDTracker.getPopStats(pos3, neg3)
				.equals("Total tests: 1\n" + "Total individuals tested: 1\n"
				+ "Percent positive tests: 0.0%\n" + "Percent positive individuals: 0.0%"))
			return false;

		// Testing with random junk values, should return false
		if (COVIDTracker.getPopStats(pos3, neg3)
				.equals("Total tests: 1\n" + "Total individuals tested: 1\n"
				+ "Percent positive tests: 0.0%\n" + "Percent positive individuals: 100.0%"))
			return false;

		return true;
	}

	/**
	 * Checks whether getIndividualStats() works as expected
	 * 
	 * @return true if method functionality is verified, false otherwise
	 */

	public static boolean testGetIndividualStats() {
		// DEFINING two random pos and neg arrays, with some degree of randomness
		String[] pos = { "AB1234", "CD2345", null, "EF3456", "EF3456", 
				null, "AB1234", null, "GH3456", "AB1234" };

		String[] neg = { null, "GH3456", null, "EF3456", null, null, null,
				"CD1234", "AB1234", "GH3456" };

		// Testing for individual EF3456 who has 3 total, 2 pos and 1 neg
		if (!COVIDTracker.getIndividualStats(pos, neg, "EF3456")
				.equals("Total tests: 3\n" + "Positive: 2\n" + "Negative: 1"))
			return false;
		// Testing for individual AB1234 who has 4 total, 3 pos and 1 neg
		if (!COVIDTracker.getIndividualStats(pos, neg, "AB1234")
				.equals("Total tests: 4\n" + "Positive: 3\n" + "Negative: 1"))
			return false;
		// Testing for individual CD1234 who has 1 total, 0 pos and 1 neg
		if (!COVIDTracker.getIndividualStats(pos, neg, "CD1234")
				.equals("Total tests: 1\n" + "Positive: 0\n" + "Negative: 1"))
			return false;
		// Testing for individual AB5678 who has 0 total, 0 pos and 0 neg
		// ^ basically nil records, not part of data set
		if (!COVIDTracker.getIndividualStats(pos, neg, "AB5678")
				.equals("Total tests: 0\n" + "Positive: 0\n" + "Negative: 0"))
			return false;

		// DEFINING one empty array and one filled array
		String[] pos1 = {};

		String[] neg1 = { "GH3456", "GH3456", "AB1234" };
		// Testing for individual GH3456 who has 2 total, 0 pos and 2 neg
		if (!COVIDTracker.getIndividualStats(pos1, neg1, "GH3456")
				.equals("Total tests: 2\n" + "Positive: 0\n" + "Negative: 2"))
			return false;
		// Testing for individual AB1234 who has 1 total, 0 pos and 1 neg
		if (!COVIDTracker.getIndividualStats(pos1, neg1, "AB1234")
				.equals("Total tests: 1\n" + "Positive: 0\n" + "Negative: 1"))
			return false;
		// Testing for individual CD1234 who has 0 total, 0 pos and 0 neg
		// ^ basically nil records, not part of data set
		if (!COVIDTracker.getIndividualStats(pos1, neg1, "CD1234")
				.equals("Total tests: 0\n" + "Positive: 0\n" + "Negative: 0"))
			return false;

		// DEFINING two null arrays
		String[] pos2 = { null, null };

		String[] neg2 = { null, null };

		// Testing for individual CD1234 who has 0 total, 0 pos and 0 neg
		// ^ basically nil records, not part of data set
		if (!COVIDTracker.getIndividualStats(pos2, neg2, "CD1234")
				.equals("Total tests: 0\n" + "Positive: 0\n" + "Negative: 0"))
			return false;

		return true;
	}

	/**
	 * Calls the test methods implemented in this class and displays output
	 * 
	 * @param args input arguments if any
	 */

	public static void main(String[] args) {

		System.out.println("testAddTest(): " + testAddTest());

		System.out.println("testRemoveIndividual(): " + testRemoveIndividual());

		System.out.println("testGetPopStats(): " + testGetPopStats());

		System.out.println("testGetIndividualStats(): " + testGetIndividualStats());

	}

}
