/**
 * 
 */


import java.io.File;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * @author Ayan Deep Hazra
 *
 */
public class FileExplorer {

	/*
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

		// fileContentsAL = files list in an Array List
		// fileContentsSA = files list in a String Array
		
		ArrayList<String> fileContentsAL = new ArrayList<String>();

		if (!currentFolder.isDirectory() || !currentFolder.exists()) {
			throw new NotDirectoryException("Directory Not Found or doesn't exist");
		}

		String[] fileContentsSA = currentFolder.list();
		
		for(int i = 0; i<fileContentsSA.length; i++)
		{
			fileContentsAL.add(fileContentsSA[i]);
		}

		return fileContentsAL;
	}

	/*
	 * Returns a list of the abstract pathnames of all files and directories in the the 
	 * given folder. Throws NotDirectoryException with a description error message if 
	 * the provided currentFolder does not exist or if it is not a directory.
	 * 
	 * @param currentFolder the folder to display names of contents from
	 * @return listContents an ArrayList containing the abstract pathnames of all contents
	 *                      of the file Object passed in as parameter
	 * @throws NotDirectoryException if directory is not found or doesn't exist
	 */
	private static ArrayList<File> listContentsPaths(File currentFolder)
			throws NotDirectoryException {

		ArrayList<File> fileAbstractPathnames = new ArrayList<File>();

		if (!currentFolder.isDirectory() || !currentFolder.exists()) {
			throw new NotDirectoryException("Directory Not Found or doesn't exist");
		}

		File[] fileContentsPath = currentFolder.listFiles();

		for (int i = 0; i < fileContentsPath.length; i++) {
			fileAbstractPathnames.add(fileContentsPath[i]);
		}

		return fileAbstractPathnames;
	}

	/*
	 * Recursive method that lists the names of all the files (not directories) in
	 * the given folder and its sub-folders. Throws NotDirectoryException with a
	 * description error message if the provided currentFolder does not exist or if
	 * it is not a directory.
	 * 
	 * @param currentFolder the folder to display names of deep contents from
	 * @return deepListal an ArrayList containing the filenames of all deep contents
	 *                      of the file Object passed in as parameter
	 * @throws NotDirectoryException if directory is not found or doesn't exist
	 */
	public static ArrayList<String> deepListContents(File currentFolder)
			throws NotDirectoryException {

		ArrayList<String> deepListAL = new ArrayList<String>();

		ArrayList<File> fileAbstractPathnames = listContentsPaths(currentFolder);

		for (int i = 0; i < fileAbstractPathnames.size(); i++) {
			if (fileAbstractPathnames.get(i).isFile()) {
				deepListAL.add(fileAbstractPathnames.get(i).getName());
			} else {

				if (!fileAbstractPathnames.get(i).isDirectory() 
						|| !fileAbstractPathnames.get(i).exists()) {
					throw new NotDirectoryException("Directory Not Found or doesn't exist");
				} 
				deepListAL.addAll(deepListContents(fileAbstractPathnames.get(i)));
			}

		}
		return deepListAL;
	}

	/*
	 * Searches the given folder and all of its subfolders for an exact match to the
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

		ArrayList<File> fileAbstractPathnames = null;

		try {
			fileAbstractPathnames = listContentsPaths(currentFolder);
		} catch (NotDirectoryException nde) {
			System.out.print("Not Directory exception encountered");
		}
		

		if (!currentFolder.exists() || !currentFolder.isDirectory())
			throw new NoSuchElementException("File object does not exist" + " or is not a directory");
		if (fileName == null)
			throw new NoSuchElementException("FileName is not a valid string (null)");

		for (int i = 0; i < fileAbstractPathnames.size(); i++) {
			

			if (fileAbstractPathnames.get(i).isFile()) {
				if (fileName.equals(fileAbstractPathnames.get(i).getName())) {
					path = fileAbstractPathnames.get(i).getAbsolutePath();
					//System.out.print(path);
					return path;
				}

			} else {

				try {
					if (!fileAbstractPathnames.get(i).isDirectory()) {
						throw new NotDirectoryException("Directory Not Found" + " or doesn't exist");
					}
					path = searchByName(fileAbstractPathnames.get(i), fileName);
		
				} catch (NotDirectoryException nde) {
					System.out.print("Not Directory exception encountered");
				}
			}
		}
		return path;
	}

	/*
	 * Recursive method that searches the given folder and its subfolders for ALL
	 * files that contain the given key in part of their name. Returns An arraylist
	 * of all the names of files that match and an empty arraylist when the
	 * operation returns with no results found (including the case where
	 * currentFolder is not a directory).
	 */

	public static ArrayList<String> searchByKey(File currentFolder, String key) {
		/////
		ArrayList<String> key_match_files = new ArrayList<String>();
		
		//////////
		
		
		
	
		
		//////////
		
		return key_match_files;
	}

	
	/*
	 * Recursive method that searches the given folder and its subfolders for ALL
	 * files whose size is within the given max and min values, inclusive. Returns
	 * an array list of the names of all files whose size are within the boundaries
	 * and an empty arraylist if the search operation returns with no results found
	 * (including the case where currentFolder is not a directory).
	 */
	public static ArrayList<String> searchBySize(File currentFolder, long sizeMin, long sizeMax) {
		///////

		ArrayList<String> size_match_files = new ArrayList<String>();
		
		ArrayList<File> fileAbstractPathnames = null;

		try {
			fileAbstractPathnames = listContentsPaths(currentFolder);
		} catch (NotDirectoryException nde) {
			System.out.print("Not Directory exception encountered");
		}

		if (!currentFolder.exists() || !currentFolder.isDirectory())
			throw new NoSuchElementException("File object does not exist" 
		          + " or is not a directory");
		
		for(int i = 0; i<fileAbstractPathnames.size(); i++)
		{
			
		}

		return size_match_files;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
