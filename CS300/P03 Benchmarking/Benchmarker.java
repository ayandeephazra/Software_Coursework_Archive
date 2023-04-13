/////////////// FILE HEADER //////////////////////////////////////////////////
//
// Title:    Benchmarker
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

import java.util.NoSuchElementException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.File;
import java.io.IOException;

/**
 * This class contains the compare(), createResultsFile() and the main()
 * methods. 
 * 
 * compare() compares the implementation of the bruteForce() and constantTime()
 * methods in the ComparisonMethods.java file.
 * 
 * createResultsFile() takes a File object and an array of longs and inputs
 * the results of compare() on each element of the array, with sufficient 
 * exception handling, into the File Object as text.
 * 
 * main() in this Program just tests the methods in this class and tries different
 * variations of array inputs, along with corner cases, to test the Exception
 * Handling Capabilities of the code.
 * 
 * @author Ayan Deep Hazra
 *
 */

public class Benchmarker {

	/*
	 * Runs both methods from ComparisonMethods.java on the same n. Tracks the time
	 * spent in milliseconds to complete each method Returns a formatted string with
	 * n and the elapsed times. Throws a NoSuchElementException with a descriptive
	 * error message if the return values of the two comparison methods are
	 * different.
	 * 
	 * @param n the long value to compare time of execution for the two methods
	 * 
	 * @return String Formatted String that contains the long element n and the
	 * execution times of the two ComparisonMethods with parameter n
	 * 
	 * @throws NoSuchElementException if the value returned from the two
	 * ComparisonMethods are not equivalent
	 */
	
	public static String compare(long n) throws NoSuchElementException {

		// bruteForceTime is calculated as a difference of the time between the method
		// call of ComparisonMethods.bruteForce() and the assignment operation to
		// bruteVal
		long bruteStartTime = System.currentTimeMillis();
		long bruteVal = ComparisonMethods.bruteForce(n);
		long bruteEndTime = System.currentTimeMillis();

		long bruteForceTime = bruteEndTime - bruteStartTime;

		// formulaTime is calculated as a difference of the time between the method
		// call of ComparisonMethods.constantTime() and the assignment operation to
		// formulaVal
		long formulaStartTime = System.currentTimeMillis();
		long formulaVal = ComparisonMethods.constantTime(n);
		long formulaEndTime = System.currentTimeMillis();

		long formulaTime = formulaEndTime - formulaStartTime;

		// checking that the returned value from both methods are the same.
		if (bruteVal != formulaVal) {
			throw new NoSuchElementException("No Such Element with different "
		                  + "results from each method");
		}

		return n + "\t" + bruteForceTime + "\t" + formulaTime + "\n";

	}

	/*
	 * Calls compare(n) using each of an array of values. Writes the results to a
	 * file specified by the parameter. Handles any exceptions raised by the methods
	 * it uses.
	 * 
	 * @param f The File object that needs to be output to
	 * 
	 * @param queryNs The long array that contains the elements that will be output
	 * to the file.
	 * 
	 * @return void
	 */
	
	public static void createResultsFile(File f, long[] queryNs) {

		String filename = f.getName();
		FileWriter fout = null;

		// error checking the FileWriter opening
		try {
			fout = new FileWriter(filename);
		} catch (IOException ioe) {
			System.out.print("Exception encountered, unable to complete method.");
		}

		// error checking per element of queryNs
		for (int i = 0; i < queryNs.length; i++) {
			try {
				fout.write(compare(queryNs[i]));
			} catch (NoSuchElementException nsee) {

			} catch (IOException ioe) {
				System.out.println("Exception encountered while writing for value N = "
			          + queryNs[i]);
			}
		}

		// error checking while closing
		try {

			fout.close();
		} catch (IOException ioe) {
			System.out.print("Exception encountered while closing file.");
		}

	}

	/**
	 * Testing the compare() and createResultsFile() methods with values. Noticing
	 * the brute and formula times.
	 * 
	 * @param args
	 */

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//outputting a simple long value passed through compare()
		System.out.println(compare(214766L));
		
		// File object to test the createResultsFile() method
		File f = new File("rand.txt");
		long[] q = new long[] { 89787L, 99999L };
		createResultsFile(f, q);
		
		// same file object but different array in which the
		// Last element gives a NoSuchElementException
		q = new long[] { 100000L, 1000000L, 10000000L, 100000000L, 1000000000L, 10000000000L};
		createResultsFile(f, q);

	}

}
