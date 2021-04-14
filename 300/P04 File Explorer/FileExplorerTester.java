/////////////// FILE HEADER //////////////////////////////////////////////////
//
// Title:    FileExplorerTester
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

import java.io.File;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.List;
import java.util.Arrays;

/**
 * This class contains the test methods of all the methods in the FileExplorer file.
 * All the test methods are executed in the main() method and a true is printed if the 
 * implementation runs without any errors or exceptions. Else, a false is printed out.
 * 
 * testListContents() - checks the implementation of listContents().
 * testDeepListBaseCase() - just checks the implementation of base case in deepListContents().
 * testDeepListRecursiveCase() - just checks the implementation of recursive case in 
 *                               deepListContents().
 * testSearchByName() - checks the implementation of searchByName() with corner cases and 
 *                      right and wrong inputs to check the robustness of the method.
 * testSearchByKey() - checks the implementation of searchByKey() with corner cases and 
 *                     right and wrong inputs to check the robustness of the method.
 * testSearchBySize() - checks the implementation of searchBySize() with corner cases and 
 *                      right and wrong inputs to check the robustness of the method.
 * 
 * @author Ayan Deep Hazra
 *
 */

public class FileExplorerTester {

	/**
	 * Checks whether listContents() works as expected
	 * 
	 * @return true if method functionality is verified, false otherwise
	 */

	public static boolean testListContents(File folder) {
		try {
			// Scenario 1
			// list the basic contents of the cs300 folder
			ArrayList<String> listContent = FileExplorer.listContents(folder);
			// expected output content
			String[] contents = new String[] { "grades", "lecture notes", "programs", 
					"quizzes preparation", "reading notes", "syllabus.txt", "todo.txt" };
			List<String> expectedList = Arrays.asList(contents);
			// check the size and the contents of the output
			if (listContent.size() != 7) {
				System.out.println("Problem detected: cs300 folder must contain 7 elements.");
				return false;
			}
			for (int i = 0; i < expectedList.size(); i++) {
				if (!listContent.contains(expectedList.get(i))) {
					System.out.println("Problem detected: " + expectedList.get(i)
						  + " is missing from the output of the list contents of cs300 folder.");
					return false;
				}
			}

			// Scenario 2 - list the contents of the grades folder
			File f = new File(folder.getPath() + File.separator + "grades");
			listContent = FileExplorer.listContents(f);
			if (listContent.size() != 0) {
				System.out.println("Problem detected: grades folder must be empty.");
				return false;
			}
			// Scenario 3 - list the contents of the p02 folder
			f = new File(folder.getPath() + File.separator + "programs" + File.separator + "p02");
			listContent = FileExplorer.listContents(f);
			if (listContent.size() != 1 || !listContent.contains("WisconsinPrairie.java")) {
				System.out.println(
						"Problem detected: p02 folder must contain only one file named "
				                 + "WisconsinPrairie.java.");
				return false;
			}
			// Scenario 4 - Try to list the contents of a file
			f = new File(folder.getPath() + File.separator + "todo.txt");
			try {
				listContent = FileExplorer.listContents(f);
				System.out.println("Problem detected: Your FileExplorer.listContents() must "
						+ "throw a NotDirectoryException if it is provided an input which is not"
						         + "a directory.");
				return false;
			} catch (NotDirectoryException e) { // catch only the expected exception
				// Expected behavior -- no problem detected
			}
			// Scenario 5 - Try to list the contents of not found directory/file
			f = new File(folder.getPath() + File.separator + "music.txt");
			try {
				listContent = FileExplorer.listContents(f);
				System.out.println("Problem detected: Your FileExplorer.listContents() must "
						+ "throw a NotDirectoryException if the provided File does not exist.");
				return false;
			} catch (NotDirectoryException e) {
				// catch only the expected exception to be thrown -- no problem detected
			}
		} catch (Exception e) {
			System.out.println(
					"Problem detected: Your FileExplorer.listContents() has thrown" 
			               + " a non expected exception.");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * Checks whether deepListContents() base case  works as expected
	 * 
	 * @return true if method functionality is verified, false otherwise
	 */
	
	public static boolean testDeepListBaseCase(File f) {
		try {

			// Scenario 1
			// test the deep contents of the cs300 folder
			ArrayList<String> listContent = FileExplorer.deepListContents(f);
			// expected output content
			String[] contents = new String[] { };
			
			List<String> expectedList = Arrays.asList(contents);
			// check the size and the contents of the output
			if (listContent.size() != 0) {
				System.out.println("Problem detected: grades folder must contain 0 elements.");
				return false;
			}
			for (int i = 0; i < expectedList.size(); i++) {
				if (!listContent.contains(expectedList.get(i))) {
					System.out.println("Problem detected: " + expectedList.get(i)
							+ " is missing from the output of the list contents of "
							+ "grades folder.");
					return false;
				}
			}

		} catch (Exception e) {
			System.out.println(
					"Problem detected: Your FileExplorer.deepListContents() has thrown a "
					+ "non expected exception.");
			e.printStackTrace();
			return false;
		}

		return true;
	}
	

	/**
	 * Checks whether deepListContents() recursive case  works as expected
	 * 
	 * @return true if method functionality is verified, false otherwise
	 */
	
	public static boolean testDeepListRecursiveCase(File f) {
		try {

			// Scenario 1
			// test the deep contents of the cs300 folder
			ArrayList<String> listContent = FileExplorer.deepListContents(f);
			// expected output content
			String[] contents = new String[] { "ExceptionHandling.txt", 
					"proceduralProgramming.txt", "UsingObjectsAndArrayLists.txt", 
					"AlgorithmAnalysis.txt", "Recursion.txt", "COVIDTracker.java", 
					"COVIDTrackerTester.java", "WisconsinPrairie.java", "Benchmarker.java", 
					"ComparisonMethods.java", "Program01.pdf", "Program02.pdf",
					"Program03.pdf", "codeSamples.java", "outline.txt", "zyBooksCh1.txt",
					"zyBooksCh2.txt", "zyBooksCh3.txt", "zyBooksCh4.txt", "syllabus.txt",
					"todo.txt" };
			
			List<String> expectedList = Arrays.asList(contents);
			// check the size and the contents of the output
			if (listContent.size() != 21) {
				System.out.println("Problem detected: cs300 folder must contain 21 elements.");
				return false;
			}
			for (int i = 0; i < expectedList.size(); i++) {
				if (!listContent.contains(expectedList.get(i))) {
					System.out.println("Problem detected: " + expectedList.get(i)
							+ " is missing from the output of the list contents of "
							+ "cs300 folder.");
					return false;
				}
			}

			// Scenario 2 - list the deep contents of the grades folder
			File f1 = new File(f.getPath() + File.separator + "grades");
			listContent = FileExplorer.deepListContents(f1);
			if (listContent.size() != 0) {
				System.out.println("Problem detected: grades folder must be empty.");
				return false;
			}
			// Scenario 3 - list the deep contents of the p02 folder
			File f2 = new File(f.getPath() + File.separator + "programs" 
			                         + File.separator + "p02");
			listContent = FileExplorer.deepListContents(f2);
			if (listContent.size() != 1 || !listContent.contains("WisconsinPrairie.java")) {
				System.out.println(
						"Problem detected: p02 folder must contain only one file named "
				                 + "WisconsinPrairie.java.");
				return false;
			}

		} catch (Exception e) {
			System.out.println(
					"Problem detected: Your FileExplorer.deepListContents() has "
					+ "thrown a non expected exception.");
			e.printStackTrace();
			return false;
		}

		return true;
	}
	
	/**
	 * Checks whether searchByName() method works as expected
	 * 
	 * @return true if method functionality is verified, false otherwise
	 */
	
	public static boolean testSearchByName(File f) {
		try {

			// Scenario 1
			// test the search by name method for the todo.txt file
			
			String filename1 = "todo.txt";
			
			String path1 = f.getPath() + File.separator + filename1;
			if(!path1.equals(FileExplorer.searchByName(f, filename1)))
				{System.out.println("Problem detected: " + filename1 + " did not have the"
						+ " same path as expected or does not exist");
				return false;
			}
			
			// Scenario 2
			// test the search by name method for the Recursion.txt file
		
			String filename2 = "Recursion.txt";
			
			String path2 = f.getPath() + File.separator + "lecture notes" + File.separator
					+ "unit2" + File.separator + filename2;
			if(!path2.equals(FileExplorer.searchByName(f, filename2)))
				{System.out.println("Problem detected: " + filename2 + " did not have the"
						+ " same path as expected or does not exist");
				return false;
			}

			// Scenario 3
			// test the search by name method for the outine.txt file
		
			String filename3 = "outline.txt";
			String path3 = f.getPath() + File.separator + "quizzes preparation" + File.separator 
					+ "cquiz1" + File.separator + filename3;
			if (!path3.equals(FileExplorer.searchByName(f, filename3))) {
				System.out.println("Problem detected: " + filename3 + " did not have the"
						+ " same path as expected or does not exist");
				return false;
			}
			
			// Scenario 4
			// test the search by name method for a file that does not exist
		
			// the below block of code returns an exception as filename that does not exist
			// was passed in
			/*
			String filename4 = "doesnotexist.txt";
			String path4 = "";
			if (!path4.equals(FileExplorer.searchByName(f, filename4))) {
				System.out.println("Problem detected: " + filename4 + " did not have the"
						+ " same path as expected or does not exist");
				return false;
			}
			*/

		} catch (Exception e) {
			System.out.println(
					"Problem detected: Your FileExplorer.searchByName() has "
					+ "thrown a non expected exception.");
			e.printStackTrace();
			return false;
		}

		return true;
	}
	
	/**
	 * Checks whether searchByKey() base case works as expected
	 * 
	 * @return true if method functionality is verified, false otherwise
	 */
	
	public static boolean testSearchByKeyBaseCase(File f) {
		try {
			
			// Scenario 1
			// testing searchByKey() for the files containing "u" in their names
	
			ArrayList<String> listContent1 = FileExplorer.searchByKey(f, "u");
			// expected output content
			String[] contents1 = new String[] {"syllabus.txt", "outline.txt", 
					"Recursion.txt", "proceduralProgramming.txt"};
			
			List<String> expectedList1 = Arrays.asList(contents1);
			// check the size and the contents of the output
			if (listContent1.size() != contents1.length) {
				System.out.println("Problem detected: cs300 folder must contain 4 files"
						+ " that contain \".txt\" in their names.");
				return false;
			}
			for (int i = 0; i < expectedList1.size(); i++) {
				if (!listContent1.contains(expectedList1.get(i))) {
					System.out.println("Problem detected: " + expectedList1.get(i)
							+ " is missing from the output of the list contents of "
							+ "cs300 folder.");
					return false;
				}
			}
			

			// Scenario 2
			// testing searchByKey() for the files containing "Ch1" in their names
		
			ArrayList<String> listContent2 = FileExplorer.searchByKey(f, "Ch1");
			// expected output content
			String[] contents2 = new String[] {"zyBooksCh1.txt"};
			
			List<String> expectedList2 = Arrays.asList(contents2);
			// check the size and the contents of the output
			if (listContent2.size() != contents2.length) {
				System.out.println("Problem detected: cs300 folder must contain 1 file"
						+ " that contain \".txt\" in their names.");
				return false;
			}
			for (int i = 0; i < expectedList2.size(); i++) {
				if (!listContent2.contains(expectedList2.get(i))) {
					System.out.println("Problem detected: " + expectedList2.get(i)
							+ " is missing from the output of the list contents of "
							+ "cs300 folder.");
					return false;
				}
			}

			// Scenario 3
			// testing searchByKey() for the files containing "QWERTY" in their names
			// 0 elements case

			ArrayList<String> listContent3 = FileExplorer.searchByKey(f, "QWERTY");
			// expected output content
			String[] contents3 = new String[] {};

			List<String> expectedList3 = Arrays.asList(contents3);
			// check the size and the contents of the output
			if (listContent3.size() != contents3.length) {
				System.out.println("Problem detected: cs300 folder must contain 0 files"
						+ " that contain \".txt\" in their names.");
				return false;
			}
			for (int i = 0; i < expectedList3.size(); i++) {
				if (!listContent3.contains(expectedList3.get(i))) {
					System.out.println("Problem detected: " + expectedList3.get(i)
							+ " is missing from the output of the list contents of "
							+ "cs300 folder.");
					return false;
				}
			}

		} catch (Exception e) {
			System.out.println(
					"Problem detected: Your FileExplorer.searchByKey() has"
					+ " thrown a non expected exception.");
			e.printStackTrace();
			return false;
		}

		return true;
	}
	
	/**
	 * Checks whether searchByKey() recursive works as expected
	 * 
	 * @return true if method functionality is verified, false otherwise
	 */
	
	public static boolean testSearchByKeyRecursiveCase(File f) {
		try {

			
			// Scenario 1
			// testing searchByKey() for the files containing ".txt" in their names
			ArrayList<String> listContent1 = FileExplorer.searchByKey(f, ".txt");
			// expected output content
			String[] contents1 = new String[] { "ExceptionHandling.txt", 
					"proceduralProgramming.txt", "UsingObjectsAndArrayLists.txt",
					"AlgorithmAnalysis.txt", "Recursion.txt", "outline.txt", 
					"zyBooksCh1.txt", "zyBooksCh2.txt", "zyBooksCh3.txt", 
					"zyBooksCh4.txt", "syllabus.txt", "todo.txt" };
			
			List<String> expectedList1 = Arrays.asList(contents1);
			// check the size and the contents of the output
			if (listContent1.size() != contents1.length) {
				System.out.println("Problem detected: cs300 folder must contain 12 files"
						+ " that contain \".txt\" in their names.");
				return false;
			}
			for (int i = 0; i < expectedList1.size(); i++) {
				if (!listContent1.contains(expectedList1.get(i))) {
					System.out.println("Problem detected: " + expectedList1.get(i)
							+ " is missing from the output of the list contents of cs300 folder.");
					return false;
				}
			}
			
			// Scenario 2
			// testing searchByKey() for a random string that is not there in the names of 
			// the files in cs300
			ArrayList<String> listContent2 = FileExplorer.searchByKey(f, "notinside");
			// expected output content
			String[] contents2 = new String[] { };
			
			List<String> expectedList2 = Arrays.asList(contents2);
			// check the size and the contents of the output
			if (listContent2.size() != contents2.length) {
				System.out.println("Problem detected: cs300 folder must contain 0 files"
						+ " that contain \"notinside\" in their names.");
				return false;
			}
			for (int i = 0; i < expectedList2.size(); i++) {
				if (!listContent2.contains(expectedList2.get(i))) {
					System.out.println("Problem detected: " + expectedList2.get(i)
							+ " is missing from the output of the list contents of cs300 folder.");
					return false;
				}
			}
			
			// Scenario 3
			// testing searchByKey() for the files containing ".pdf" in their names
			ArrayList<String> listContent3 = FileExplorer.searchByKey(f, ".pdf");
			// expected output content
			String[] contents3 = new String[] {"Program01.pdf", "Program02.pdf",
					"Program03.pdf"};
			List<String> expectedList3 = Arrays.asList(contents3);
			// check the size and the contents of the output
			if (listContent3.size() != contents3.length) {
				System.out.println("Problem detected: cs300 folder must contain 3 files"
						+ " that contain \".pdf\" in their names.");
				return false;
			}
			for (int i = 0; i < expectedList3.size(); i++) {
				if (!listContent3.contains(expectedList3.get(i))) {
					System.out.println("Problem detected: " + expectedList3.get(i)
							+ " is missing from the output of the list contents of cs300 folder.");
					return false;
				}
			}

		} catch (Exception e) {
			System.out.println(
					"Problem detected: Your FileExplorer.searchByKey() has"
					+ " thrown a non expected exception.");
			e.printStackTrace();
			return false;
		}

		return true;
	}
	
	/**
	 * Checks whether searchBySize() works as expected
	 * 
	 * @return true if method functionality is verified, false otherwise
	 */
	
	public static boolean testSearchBySize(File f) {
		try {

			// Scenario 1
			// testing searchByKey() for the files within 180 B to 200 B 
			ArrayList<String> listContent1 = FileExplorer.searchBySize(f, 180L, 200L);
			
			// expected output content
			String[] contents1 = new String[] {"todo.txt"};
			
			List<String> expectedList1 = Arrays.asList(contents1);
			// check the size and the contents of the output
			if (listContent1.size() != contents1.length) {
				System.out.println("Problem detected: cs300 folder must contain 1 file"
						+ " that is between 180B to 200B.");
				return false;
			}
			for (int i = 0; i < expectedList1.size(); i++) {
				if (!listContent1.contains(expectedList1.get(i))) {
					System.out.println("Problem detected: " + expectedList1.get(i)
							+ " is missing from the output of the list contents of cs300 folder.");
					return false;
				}
			}

			// Scenario 2
			// testing searchByKey() for the files within 129600 B to 129700 B
			ArrayList<String> listContent2 = FileExplorer.searchBySize(f, 129600L, 129700L);

			// expected output content
			String[] contents2 = new String[] {"Program03.pdf"};

			List<String> expectedList2 = Arrays.asList(contents2);
			// check the size and the contents of the output
			if (listContent2.size() != contents2.length) {
				System.out.println("Problem detected: cs300 folder must contain 1 file"
						+ " that is between 129600B to 129700B.");
				return false;
			}
			for (int i = 0; i < expectedList2.size(); i++) {
				if (!listContent2.contains(expectedList2.get(i))) {
					System.out.println("Problem detected: " + expectedList2.get(i)
							+ " is missing from the output of the list contents of cs300 folder.");
					return false;
				}
			}
			
			// Scenario 3
			// testing searchByKey() for the files with wrong negative long inputs 
			// file size cannot be a negative number
			ArrayList<String> listContent3 = FileExplorer.searchBySize(f, -129700L, -129600L);

			// expected output content
			String[] contents3 = new String[] {};

			List<String> expectedList3 = Arrays.asList(contents3);
			// check the size and the contents of the output
			if (listContent3.size() != contents3.length) {
				System.out.println("Problem detected: cs300 folder must contain 0 files"
						+ " that is between -129600B to -129700B.");
				return false;
			}
			for (int i = 0; i < expectedList3.size(); i++) {
				if (!listContent3.contains(expectedList3.get(i))) {
					System.out.println("Problem detected: " + expectedList3.get(i)
							+ " is missing from the output of the list contents of cs300 folder.");
					return false;
				}
			}

		} catch (Exception e) {
			System.out.println(
					"Problem detected: Your FileExplorer.searchBySize() has"
					+ " thrown a non expected exception.");
			e.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * Implements the test methods and displays true or false depending on whether they 
	 * passed or failed.
	 * 
	 * @param args
	 * @return void
	 */

	public static void main(String[] args) {
		System.out.println("testListContents: " + testListContents(new File("cs300")));
		
		System.out.println("testDeepListBaseCase: " 
		    + testDeepListBaseCase(new File("cs300/grades")));
		
		System.out.println("testDeepListRecursiveCase: " 
		    + testDeepListRecursiveCase(new File("cs300")));
		
		System.out.println("testSearchByName: " 
			    + testSearchByName(new File("cs300")));
		
		System.out.println("testSearchByKeyBaseCase: " 
			    + testSearchByKeyBaseCase(new File("cs300")));
		
		System.out.println("testSearchByKeyRecursiveCase: " 
			    + testSearchByKeyRecursiveCase(new File("cs300")));
		
		System.out.println("testSearchBySize: " 
			    + testSearchBySize(new File("cs300")));
		
	}

}
