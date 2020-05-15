//*******************************************************************************//
//* Author:     1816477                                                         *//
//* Assignment: Element 011: Hotel Management System                            *//
//* Date:       10/05/20                                                        *//
//*                                                                             *//
//  Class Name: XMLReader                                                       *//
//* Class Description: Used to retrieve data from an given file                 *//
//*                                                                             *//
//*******************************************************************************//

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public abstract class XMLReader {

    //*************************Class instance variables**************************//

    private File xmlFile; // the xml file to read
    private DocumentBuilder dBuilder; // document builder
    private DocumentBuilderFactory dbFactory; // builder factory
    private Document dataRetrieved; // document data read

    //***************************************************************************//

    //********************************Class methods******************************//

    /** method readXML()
     * retrieves a specific files information
     * @param fileName: {String} file name to read
     * @returns:        {Document} returns document of data read
     */
    public Document readXML(String fileName) {

        try {
            xmlFile = new File(fileName); // create new File using filename provided
            dbFactory = DocumentBuilderFactory.newInstance(); // create new document builder factory
            dBuilder = dbFactory.newDocumentBuilder(); // create new document builder
            dataRetrieved = dBuilder.parse(xmlFile); // build document using File
            dataRetrieved.getDocumentElement().normalize();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataRetrieved; // return document of data read
    }

} // close XMLReader class

