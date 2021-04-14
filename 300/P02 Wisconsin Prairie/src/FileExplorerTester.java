
import java.io.File;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.List;
import java.util.Arrays;

/**
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
							+ " is missing from the output of the list contents of grades folder.");
					return false;
				}
			}

		} catch (Exception e) {
			System.out.println(
					"Problem detected: Your FileExplorer.listContents() has thrown" + " a non expected exception.");
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
			String[] contents = new String[] { "ExceptionHandling.txt", "proceduralProgramming.txt",
					"UsingObjectsAndArrayLists.txt", "AlgorithmAnalysis.txt", "Recursion.txt",
					"COVIDTracker.java", "COVIDTrackerTester.java", "WisconsinPrairie.java",
					"Benchmarker.java", "ComparisonMethods.java", "Program01.pdf", "Program02.pdf",
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
							+ " is missing from the output of the list contents of cs300 folder.");
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
			File f2 = new File(f.getPath() + File.separator + "programs" + File.separator + "p02");
			listContent = FileExplorer.deepListContents(f2);
			if (listContent.size() != 1 || !listContent.contains("WisconsinPrairie.java")) {
				System.out.println(
						"Problem detected: p02 folder must contain only one file named "
				                 + "WisconsinPrairie.java.");
				return false;
			}

		} catch (Exception e) {
			System.out.println(
					"Problem detected: Your FileExplorer.listContents() has thrown" + " a non expected exception.");
			e.printStackTrace();
			return false;
		}

		return true;
	}
	
	public static boolean testSearchByName(File f) {
		try {

			// Scenario 1
			// test the search by name method for the todo.txt file
			ArrayList<String> listContent = FileExplorer.deepListContents(f);
			// expected output content
			String[] contents = new String[] { "ExceptionHandling.txt", "proceduralProgramming.txt",
					"UsingObjectsAndArrayLists.txt", "AlgorithmAnalysis.txt", "Recursion.txt", "COVIDTracker.java",
					"COVIDTrackerTester.java", "WisconsinPrairie.java", "Benchmarker.java", "ComparisonMethods.java",
					"Program01.pdf", "Program02.pdf", "Program03.pdf", "codeSamples.java", "outline.txt",
					"zyBooksCh1.txt", "zyBooksCh2.txt", "zyBooksCh3.txt", "zyBooksCh4.txt", "syllabus.txt",
					"todo.txt" };

			List<String> expectedList = Arrays.asList(contents);
			// check the size and the contents of the output
			if (listContent.size() != 21) {
				System.out.println("Problem detected: grades folder must contain 21 elements.");
				return false;
			}
			String filename = "todo.txt";

			String path1 = f.getPath() + File.separator + filename;
			if (!path1.equals(FileExplorer.searchByName(f, filename))) {
				System.out.println("Problem detected: " + filename + " did not have the"
						+ " same path as expected or does not exist");
				return false;
			}
	

		
		}

		catch (Exception e) {
			System.out
					.println("Problem detected: Your FileExplorer.listContents() has thrown a non expected exception.");
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
	 */

	public static void main(String[] args) {
		System.out.println("testListContents: " + testListContents(new File("cs300")));
		System.out.println("testDeepListBaseCase: " 
		    + testDeepListBaseCase(new File("cs300/grades")));
		System.out.println("testDeepListRecursiveCase: " 
		    + testDeepListRecursiveCase(new File("cs300")));
		System.out.println("testSearchByName: " 
			    + testSearchByName(new File("cs300")));
		
	}

}
