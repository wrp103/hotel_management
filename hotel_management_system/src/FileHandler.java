//*******************************************************************************//
//* Author:     1816477                                                         *//
//* Assignment: Element 011: Hotel Management System                            *//
//* Date:       10/05/20                                                        *//
//*                                                                             *//
//  Class Name: FileHandler                                                     *//
//* Class Description: Retrieve file information from a given directory         *//
//*                                                                             *//
//*******************************************************************************//

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class FileHandler {

    //*************************Class instance variables**************************//

    private File folder;
    private File[] fileList;
    private ArrayList<String> filesFound;
    private String nextFileName;
    private String fileName;

    //***************************************************************************//

    //********************************Class methods******************************//

    /** method getFileList()
     * retrieves a list of files from a given folder
     * @param newFolder: {String} directory to search
     * @param extension: {String} file extension type to find
     * @returns:         {ArrayList<String>} files found
     */
    public ArrayList<String> getFileList(String newFolder, String extension) {

        // create new File object using given directory
        folder = new File (newFolder);

        // add ALL files in directory to File array
        fileList = folder.listFiles();

        // create new ArrayList to hold wanted files
        filesFound = new ArrayList<>();

        // search File array for files with extension from parameter
        for (File file : fileList) {

            fileName = file.getName(); // get files name

            // if file ends with extension provided, add to ArrayList
            if (fileName.endsWith(extension)) {
                filesFound.add(fileName);
            }

        }

        return filesFound; // return files found
    }

    /** method nextXMLFile()
     * searches ArrayList of file names, finds the latest file (highest number) and adds 1,
     * this is used when creating a booking to get the next file name or bookingID
     * @param files: {ArrayList<String>} list of files to search
     * @returns:     {String} next file name or bookingID
     */
    public String nextXMLFile(ArrayList<String> files) {

        // create temporary variable to hold highest number
        int fileNo;

        // convert the list of strings to integers and find the highest number
        fileNo = (Integer.parseInt(Collections.max(files).replace(".xml", "")));

        // add 1 to the number found and convert back to string
        nextFileName = (Integer.toString(fileNo+1));

        return nextFileName; // return next file name or bookingID
    }

} // close FileHandler class

