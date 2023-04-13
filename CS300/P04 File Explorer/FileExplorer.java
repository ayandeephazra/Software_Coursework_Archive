/////////////// FILE HEADER //////////////////////////////////////////////////
//
// Title:    FileExplorer
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

/**
 * The FileExplorer class is responsible for traversing through the file system
 * and returning files as dictated by the following methods:
 * 
 * @author Ayan Deep Hazra
 *
 */
public class FileExplorer {

	/**
	 * Returns a list of the names of all files and directories in the the given
	 * folder. Throws NotDirectoryException with a description error message if the
	 * provided currentFolder does not exist or if it is not a directory.
	 * 
	 * @param currentFolder the folder to display names of contents from
	 * @return listContents an ArrayList containing the name of all contents of the 
	 *                      file Object passed in as parameter
	 * @throws NotDirectoryException if directory is not found or doesn't exist
	 */
	public static ArrayList<String> listContents(File currentFolder) 
			throws NotDirectoryException {

		// fileContentsAL = files' content list in an Array List
		// fileContentsSA = files' content list in a String Array
		
		ArrayList<String> fileContentsAL = new ArrayList<String>();

		// NotDirectoryExcpetion Throwing
		if (!currentFolder.isDirectory() || !currentFolder.exists()) {
			throw new NotDirectoryException("Directory Not Found or doesn't exist");
		}

		// adds all names of files/folders in directory to a String Array
		String[] fileContentsSA = currentFolder.list();
		
		// functionality to add String Array elements to ArrayList
		for(int i = 0; i<fileContentsSA.length; i++)
		{
			fileContentsAL.add(fileContentsSA[i]);
		}

		return fileContentsAL;
	}

	/**
	 * Returns a list of the abstract pathnames of all files and directories in the the 
	 * given folder. Throws NotDirectoryException with a description error message if 
	 * the provided currentFolder does not exist or if it is not a directory.
	 * (PRIVATE HELPER METHOD)
	 * 
	 * @param currentFolder the folder to display names of contents from
	 * @return fileAbstractPathnames an ArrayList containing the abstract pathnames of all 
	 *                               contents of the file Object passed in as parameter
	 * @throws NotDirectoryException if directory is not found or doesn't exist
	 */
	private static ArrayList<File> listContentsPaths(File currentFolder)
			throws NotDirectoryException {

		ArrayList<File> fileAbstractPathnames = new ArrayList<File>();

		// NotDirectoryExcpetion Throwing
		if (!currentFolder.isDirectory() || !currentFolder.exists()) {
			throw new NotDirectoryException("Directory Not Found or doesn't exist");
		}

		// adds all abstract pathnames of files/folders in directory to a String Array
		File[] fileContentsPath = currentFolder.listFiles();

		// functionality to add File Array elements to ArrayList
		for (int i = 0; i < fileContentsPath.length; i++) {
			fileAbstractPathnames.add(fileContentsPath[i]);
		}

		return fileAbstractPathnames;
	}

	/**
	 * Recursive method that lists the names of all the files (not directories) in
	 * the given folder and its sub-folders. Throws NotDirectoryException with a
	 * descriptive error message if the provided currentFolder does not exist or if
	 * it is not a directory.
	 * 
	 * @param currentFolder the folder to display names of deep contents from
	 * @return deepListal an ArrayList containing the filenames of all deep contents
	 *                      of the file Object passed in as parameter
	 * @throws NotDirectoryException if directory is not found or doesn't exist
	 */
	public static ArrayList<String> deepListContents(File currentFolder)
			throws NotDirectoryException {

		// an ArrayList containing the filenames of all deep contents
		// of the file Object passed in as parameter
		ArrayList<String> deepListAL = new ArrayList<String>();

		//abstract pathnames of folder contents
		ArrayList<File> fileAbstractPathnames = listContentsPaths(currentFolder);

		for (int i = 0; i < fileAbstractPathnames.size(); i++) {
			// BASE CASE
			if (fileAbstractPathnames.get(i).isFile()) {
				deepListAL.add(fileAbstractPathnames.get(i).getName());
			} 
			// RECURSION CASE
			else {

				// NotDirectoryExcpetion Handler
				if (!fileAbstractPathnames.get(i).isDirectory() 
						|| !fileAbstractPathnames.get(i).exists()) {
					throw new NotDirectoryException("Directory Not Found or doesn't exist");
				} 
				// all elements added to the ArrayList
				deepListAL.addAll(deepListContents(fileAbstractPathnames.get(i)));
			}

		}
		return deepListAL;
	}
	

	/**
	 * Returns a list of the abstract pathnames of all files and directories in the
	 * the given folder and its sub-folders. Throws NotDirectoryException with a
	 * descriptive error message if the provided currentFolder does not exist or if
	 * it is not a directory.
	 * (PRIVATE HELPER METHOD)
	 * 
	 * @param currentFolder the folder to display names of contents from
	 * @return deepListAbstractPathnames an ArrayList containing the abstract
	 *                                   pathnames of all contents of the file 
	 *                                   Object passed in as parameters
	 * @throws NotDirectoryException if directory is not found or doesn't exist
	 */

	private static ArrayList<File> deepListAbstractPaths(File currentFolder) 
			throws NotDirectoryException
	{
		ArrayList<File> deepListAbstractPathnames = new ArrayList<File>();

		ArrayList<File> fileAbstractPathnames = listContentsPaths(currentFolder);

		for (int i = 0; i < fileAbstractPathnames.size(); i++) {
			// BASE CASE
			if (fileAbstractPathnames.get(i).isFile()) {
				deepListAbstractPathnames.add(fileAbstractPathnames.get(i));
			}
			// RECURSION CASE
			else {

				// NotDirectoryExcpetion Handler
				if (!fileAbstractPathnames.get(i).isDirectory() 
						|| !fileAbstractPathnames.get(i).exists()) {
					throw new NotDirectoryException("Directory Not Found or doesn't exist");
				} 
				// all elements added to the ArrayList
				deepListAbstractPathnames.addAll(deepListAbstractPaths(
						fileAbstractPathnames.get(i)));
			}

		}
		return deepListAbstractPathnames;
	}

	/**
	 * Searches the given folder and all of its sub-folders for an exact match to the
	 * provided fileName. This method must be recursive or must use a recursive
	 * private helper method to operate. This method returns a path to the file, if
	 * it exists. Throws NoSuchElementException with a descriptive error message if
	 * the search operation returns with no results found (including the case if
	 * fileName is null or currentFolder does not exist, or was not a directory)
	 * 
	 * @param currentFolder the main directory to deep search the filename in
	 * @param fileName the name of the file that is to be searched
	 * @return String containing the abstract pathname of the file
	 */

	public static String searchByName(File currentFolder, String fileName) {

		String path= "";

		ArrayList<File> allPathnames = null;
		
		if(fileName == null)
			throw new NoSuchElementException("input FileName was null");
		if(!currentFolder.exists())
			throw new NoSuchElementException("Directory does not exist");
		
		// Recursive method that returns all the abstracts paths of all files in all
		// sub folders in currentFolder
		
		// NotDirectoryExcpetion Handler
		try {
			allPathnames = deepListAbstractPaths(currentFolder);
		}
		catch (NotDirectoryException nde) {
			System.out.print("Not Directory exception encountered");
		}
		
		
		for(int i = 0; i<allPathnames.size(); i++)
		{
			// ASSIGNMENT based on condition
			if(fileName.equals(allPathnames.get(i).getName()))
				path = allPathnames.get(i).toString();
					
		}

		// if no such element was found path will remain assigned to ""
		// and then a NoSuchElementException exception will be thrown
		if(path.equals(""))
			throw new NoSuchElementException("FileName was not found");

		return path;
	}

	/**
	 * Recursive method that searches the given folder and its sub-folders for ALL
	 * files that contain the given key in part of their name. Returns An ArrayList
	 * of all the names of files that match and an empty ArrayList when the
	 * operation returns with no results found (including the case where
	 * currentFolder is not a directory).
	 * 
	 * @param currentFolder the folder to search the files and sub folders from
	 *                      for the files with the key in their names
	 * @param key the key to be searched against every file's name
	 * @return ArrayList containing the files that have the key in their name
	 */

	public static ArrayList<String> searchByKey(File currentFolder, String key) {

		// ArrayList of files that have the the identifier key in their name
		ArrayList<String> key_match_files = new ArrayList<String>();

		// Abstract pathnames in the particular directory
		ArrayList<File> fileAbstractPathnames = null;
		
		// NotDirectoryExcpetion Handler
		try {
		fileAbstractPathnames = listContentsPaths(currentFolder);
		}
		catch (NotDirectoryException nde) {
			System.out.print("Not Directory exception encountered");
		}

		try {

			for (int i = 0; i < fileAbstractPathnames.size(); i++) {
				// BASE CASE
				if (fileAbstractPathnames.get(i).isFile()) {
					if(fileAbstractPathnames.get(i).getName().indexOf(key) != -1)
						key_match_files.add(fileAbstractPathnames.get(i).getName());
				} 
				// RECURSIVE CASE
				else {

					// exception thrown if File object is not a directory or does not exist
					if (!fileAbstractPathnames.get(i).isDirectory() 
							|| !fileAbstractPathnames.get(i).exists()) {
						throw new NotDirectoryException("Directory Not Found or doesn't exist");
					}
					// all elements added to the ArrayList
					fileAbstractPathnames.addAll(deepListAbstractPaths
							(fileAbstractPathnames.get(i)));
				}

			}
			
		} catch (NotDirectoryException nde) {
			System.out.print("Not Directory exception encountered");
		}


		return key_match_files;
	}
	
	/**
	 * Recursive method that searches the given folder and its sub-folders for ALL
	 * files whose size is within the given maximum and minimum values, inclusive. Returns
	 * an array list of the names of all files whose size are within the boundaries
	 * and an empty ArrayList if the search operation returns with no results found
	 * (including the case where currentFolder is not a directory).
	 * 
	 * @param currentFolder the folder to search the files and sub folders from
	 *                      for the files that are in the size range
	 * @param sizeMin the minimum size of a file that will make the ArrayList
	 * @param sizeMax the maximum size of a file that will make the ArrayList
	 * @return ArrayList containing the files that have the key in their name
	 */
	public static ArrayList<String> searchBySize(File currentFolder, long sizeMin, long sizeMax) {
		
		// ArrayList of files that fall within the size range defined by sizeMin and sizeMax
		ArrayList<String> size_match_files = new ArrayList<String>();

		// Abstract pathnames in the particular directory
		ArrayList<File> fileAbstractPathnames = null;
		
		// NotDirectoryExcpetion Handler
		try {
			fileAbstractPathnames = listContentsPaths(currentFolder);
		} catch (NotDirectoryException nde) {
			System.out.print("Not Directory exception encountered");
		}

		try {

			for (int i = 0; i < fileAbstractPathnames.size(); i++) {
				
				// BASE CASE
				if (fileAbstractPathnames.get(i).isFile()) {
					if( (fileAbstractPathnames.get(i).length() >= sizeMin) && 
							(fileAbstractPathnames.get(i).length() <= sizeMax))
						size_match_files.add(fileAbstractPathnames.get(i).getName());
				} 
				// RECURSIVE CASE
				else {

					// exception thrown if File object is not a directory or does not exist
					if (!fileAbstractPathnames.get(i).isDirectory() 
							|| !fileAbstractPathnames.get(i).exists()) {
						throw new NotDirectoryException("Directory Not Found or doesn't exist");
					}
					// all elements added to the ArrayList
					fileAbstractPathnames.addAll(deepListAbstractPaths(
							fileAbstractPathnames.get(i)));
				}

			}
			
			// NotDirectoryExcpetion Handler
		} catch (NotDirectoryException nde) {
			System.out.print("Not Directory exception encountered");
		}

		return size_match_files;
	}

}
