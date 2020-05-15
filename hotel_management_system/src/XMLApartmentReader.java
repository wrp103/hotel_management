//*******************************************************************************//
//* Author:     1816477                                                         *//
//* Assignment: Element 011: Hotel Management System                            *//
//* Date:       10/05/20                                                        *//
//*                                                                             *//
//  Class Name: XMLApartmentReader (Subclass of XMLReader)                      *//
//* Class Description: Used to retrieve apartment information                   *//
//*                                                                             *//
//*******************************************************************************//

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.swing.*;
import java.awt.*;

public class XMLApartmentReader extends XMLReader {

    //*************************Class instance variables**************************//

    private Document apartmentsData; // all apartment data
    private String[] apartmentInfo; // specific apartment information
    private NodeList nList;
    private int rows = 0; // table row
    private int columns = 0; // table column
    private String[][] tableData; // table data
    private JTable apartmentsTable; // built apartment table
    private JScrollPane scroll;

    //***************************************************************************//

    //****************************Class constructors*****************************//

    /** constructor
     * creates a new instance of an XML apartment reader
     */
    public XMLApartmentReader() {
        // read XML file from given path
        apartmentsData = readXML("xml_files/apartments.xml");
        // organise data by node
        nList = apartmentsData.getElementsByTagName("apartment");
    }

    //***************************************************************************//

    //********************************Class methods******************************//

    /** method getAllApartmentInfo()
     * retrieves a specific apartments information based on the apartment ID
     * @param apartmentID: {String} apartment to locate
     * @returns:           {String[]} found apartments information
     */
    public String[] getAllApartmentInfo(String apartmentID) {

        // loop through each apartments information
        for (int i = 0; i < nList.getLength(); i++) {

            Node nNode = nList.item(i); // assign current apartments details
            Element element = (Element) nNode; // convert to element
            String id = element.getAttribute("id"); // get apartment id
            apartmentInfo = new String[8]; // create array to hold apartment information

            // if current apartment id matches the apartment searched for, add all info to array
            if (id.equals(apartmentID)) {

                apartmentInfo[0] = element.getElementsByTagName("apartmentname").item(0).getTextContent();
                apartmentInfo[1] = element.getElementsByTagName("price").item(0).getTextContent();
                apartmentInfo[2] = element.getElementsByTagName("startdate").item(0).getTextContent();
                apartmentInfo[3] = element.getElementsByTagName("enddate").item(0).getTextContent();
                apartmentInfo[4] = element.getElementsByTagName("maxguests").item(0).getTextContent();
                apartmentInfo[5] = element.getElementsByTagName("bedrooms").item(0).getTextContent();
                apartmentInfo[6] = element.getElementsByTagName("bathrooms").item(0).getTextContent();
                apartmentInfo[7] = element.getElementsByTagName("livingroom").item(0).getTextContent();
                break;

            } // end if statement

        } // end for loop

        return apartmentInfo;
    }

    /** method getBasicApartmentInfo()
     * retrieves a specific apartments information based on the apartment name
     * @param apartmentName: {String} apartment to locate
     * @returns:             {String[]} returns found apartments ID & name
     */
    public String[] getBasicApartmentInfo(String apartmentName) {

        // create array to hold apartment info
        String[] singleApartmentInfo = new String[2];
        singleApartmentInfo[0] = "";
        singleApartmentInfo[1] = "";

        // loop through each apartment in XML file
        for (int i = 0; i < nList.getLength(); i++) {

            Node nNode = nList.item(i); // assign current apartments details

            // if node is correct type assign apartment information
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode; // convert to element
                // retrieve apartment name of current apartment
                String tempApartmentName = eElement.getElementsByTagName("apartmentname").item(0).getTextContent();
                // if apartment matches the searched for apartment, assign information to array
                if (tempApartmentName.equals(apartmentName)) {
                    singleApartmentInfo[0] = eElement.getAttribute("id");
                    singleApartmentInfo[1] = eElement.getElementsByTagName("price").item(0).getTextContent();
                }

            } // close if statement

        } // close for loop

        return singleApartmentInfo;
    }

    /** method apartmentsTableCreator()
     * puts all apartment information into an array to be used for a table
     * @returns:             {String[][]} returns table data in columns & rows
     */
    public String[][] apartmentsTableCreator() {

        // create new table data array
        tableData = new String[nList.getLength()][9];

        // for each apartment, add data to array
        for (int i = 0; i < nList.getLength(); i++) {

            Node nNode = nList.item(i); // assign current apartments details

            // if node is correct type assign apartment information
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode; // convert to element

                // assign apartment information to string variables
                String apartmentID = eElement.getAttribute("id");
                String apartmentName = eElement.getElementsByTagName("apartmentname").item(0).getTextContent();
                String apartmentPrice = eElement.getElementsByTagName("price").item(0).getTextContent();
                String apartmentStart = eElement.getElementsByTagName("startdate").item(0).getTextContent();
                String apartmentEnd = eElement.getElementsByTagName("enddate").item(0).getTextContent();
                String apartmentMaxGuests = eElement.getElementsByTagName("maxguests").item(0).getTextContent();
                String apartmentBedrooms = eElement.getElementsByTagName("bedrooms").item(0).getTextContent();
                String apartmentBathrooms = eElement.getElementsByTagName("bathrooms").item(0).getTextContent();
                String apartmentLivingRoom = eElement.getElementsByTagName("livingroom").item(0).getTextContent();

                // add apartment data to array
                tableData[rows][columns] = apartmentID;
                columns++;
                tableData[rows][columns] = apartmentName;
                columns++;
                tableData[rows][columns] = apartmentPrice;
                columns++;
                tableData[rows][columns] = apartmentStart;
                columns++;
                tableData[rows][columns] = apartmentEnd;
                columns++;
                tableData[rows][columns] = apartmentMaxGuests;
                columns++;
                tableData[rows][columns] = apartmentBedrooms;
                columns++;
                tableData[rows][columns] = apartmentBathrooms;
                columns++;
                tableData[rows][columns] = apartmentLivingRoom;

            } // close if statement

            rows++; // next row
            columns = 0; // reset column counter

        } // close for loop

        return tableData;
    }

    /** method apartmentsTableDisplay()
     * builds apartments table and adds to a given JPanel
     * @param dataTable:    {String[][]} table data
     * @param displayPanel: {JPanel} GUI panel to add table to
     */
    public void apartmentsTableDisplay (String[][] dataTable, JPanel displayPanel) {

        // set column headers for table
        String columnHeaders[] = {"Apartment ID","Name", "Price (Per Night)","Start Date", "End Date", "Max Guests",
                "Beds", "Baths", "Living Room"};
        // create new JTable & adjust size
        apartmentsTable = new JTable(dataTable, columnHeaders);
        apartmentsTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        // create JScrollPane, add table & set size
        scroll = new JScrollPane(apartmentsTable);
        scroll.setPreferredSize(new Dimension(1000, 600));
        displayPanel.add(scroll); // add table to panel

    }

} // close XMLApartmentReader class