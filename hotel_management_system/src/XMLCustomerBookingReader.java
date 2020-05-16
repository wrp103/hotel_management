//*******************************************************************************//
//* Author:     1816477                                                         *//
//* Assignment: Element 011: Hotel Management System                            *//
//* Date:       10/05/20                                                        *//
//*                                                                             *//
//  Class Name: XMLCustomerBookingReader (Subclass of XMLReader)                *//
//* Class Description: Used to retrieve customer booking details                *//
//*                                                                             *//
//*******************************************************************************//

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class XMLCustomerBookingReader extends XMLReader {

    //*************************Class instance variables**************************//

    private Document bookingsData; // XML bookings data
    private String[][] tableData; // bookings table data
    private int rows = 0; // table row
    private int columns = 0; // table column
    private JTable bookingsTable; // bookings table
    private JScrollPane scroll;

    // customer bookings XML data
    private Document customerBookingsData;
    private NodeList nList;
    private String[] bookingInfo;

    // all confirm booking data
    ArrayList<String> allConfirmFiles;
    ArrayList<XMLCustomerBookingReader> allConfirmObjects;
    private String confirmationID;
    private String clientname;
    private String guests;
    private String startdate;
    private String enddate;
    private String apartmentname;
    private String bedrooms;
    private String livingroom;
    private String bathrooms;
    private String catering;
    private String totalprice;

    //***************************************************************************//

    //********************************Class methods******************************//

    /** method storeAllConfirmationInfo()
     * retrieves all confirmation data and puts in array list
     */
    public void storeAllConfirmationInfo() {

        // retrieve file list of confirmation XML files
        allConfirmFiles = new FileHandler().getFileList("xml_files/confirmations", ".xml");
        allConfirmObjects = new ArrayList<>(); // create new array list
        // loop through each file, get the confirmation details and add to array list
        for (String file : allConfirmFiles) {
            allConfirmObjects.add(getConfirmationInfo(file));
        }
    }

    /** method getConfirmationInfo()
     * retrieves a specific confirmation files information
     * @param newfilename: {String} confirmation file to extract data from
     * @returns:           {XMLCustomerBookingReader} returns object containing xml data of file provided
     */
    public XMLCustomerBookingReader getConfirmationInfo(String newfilename) {

        // create object to hold confirmation data
        XMLCustomerBookingReader confirmedData = new XMLCustomerBookingReader();

        // read file name provided
        bookingsData = confirmedData.readXML("xml_files/confirmations/" + newfilename);

        // get data by confirmation element
        NodeList nList = bookingsData.getElementsByTagName("confirmation");

        // loop through and assign confirmation data to instance variables
        for (int i = 0; i < nList.getLength(); i++) {

            Node nNode = nList.item(i);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                Element eElement = (Element) nNode;
                confirmedData.confirmationID = eElement.getAttribute("id");
                confirmedData.clientname = eElement.getElementsByTagName("clientname").item(0).getTextContent();
                confirmedData.guests = eElement.getElementsByTagName("guests").item(0).getTextContent();
                confirmedData.startdate = eElement.getElementsByTagName("startdate").item(0).getTextContent();
                confirmedData.enddate = eElement.getElementsByTagName("enddate").item(0).getTextContent();
                confirmedData.apartmentname = eElement.getElementsByTagName("apartmentname").item(0).getTextContent();
                confirmedData.bedrooms = eElement.getElementsByTagName("bedrooms").item(0).getTextContent();
                confirmedData.livingroom = eElement.getElementsByTagName("livingroom").item(0).getTextContent();
                confirmedData.bathrooms = eElement.getElementsByTagName("bathrooms").item(0).getTextContent();
                confirmedData.catering = eElement.getElementsByTagName("catering").item(0).getTextContent();
                confirmedData.totalprice = eElement.getElementsByTagName("totalprice").item(0).getTextContent();

            } // end if statement

        } // close for loop

        return confirmedData;
    }

    /** method confirmBookingMatch()
     * searches customerBookings.xml for matching bookingID & username
     * @param username:     {String} username of customer making search
     * @param bookingID:    {String}  bookingID customer searching for
     * @returns:            {boolean} if validation passes return true, else false
     */
    public boolean confirmBookingMatch(String username, String bookingID) {

        // read customerBookings.xml file
        customerBookingsData = readXML("xml_files/customerBookings.xml");

        // get data by customerBooking element
        nList = customerBookingsData.getElementsByTagName("customerBooking");

        // find booking based on ID provided
        getCustomerBookingInfo(bookingID);

        // if not found, display error message
        if (bookingInfo[2].equals("false")) {
            JOptionPane.showMessageDialog(null,
                    "Unknown booking ID");
            return false;
        // else if found & if users username matches booking username, return true;
        } else if (bookingInfo[1].equals(username)) {
            return true;
        } else {
            // else display erroe message
            JOptionPane.showMessageDialog(null,
                    "This is not your booking");
            return false;
        } // end if statement
    }

    /** method confirmBookingMatch()
     * searches customerBookings.xml for matching bookingID
     * @param bookingID:    {String}  bookingID manager searching for
     * @returns:            {boolean} if validation passes return true, else false
     */
    public boolean confirmBookingMatch(String bookingID) {

        // read customerBookings.xml file
        customerBookingsData = readXML("xml_files/customerBookings.xml");

        // get data by customerBooking element
        nList = customerBookingsData.getElementsByTagName("customerBooking");

        // find booking based on ID provided
        getCustomerBookingInfo(bookingID);

        // if not found, display error message
        if (bookingInfo[2].equals("false")) {
            JOptionPane.showMessageDialog(null,
                    "Unknown booking ID");
            return false;
            // else return true
        } else {
            return true;
        }
    }

    // this well return bookingID(0) & username(1) or empty if id not found
    /** method getCustomerBookingInfo()
     * attempt to find customerBooking based on ID, if cannot find set array to string as falsee
     * @param bookingID:    {String}  booking to get information from
     */
    private void getCustomerBookingInfo(String bookingID) {

        // loop through customerBooking.xml
        for (int i = 0; i < nList.getLength(); i++) {

            Node nNode = nList.item(i); // get current booking information
            Element element = (Element) nNode;
            String id = element.getAttribute("id");
            bookingInfo = new String[3]; // create new array
            bookingInfo[2] = "false"; // pre-set findings as false
            if (id.equals(bookingID)) {
                // assign findings to array
                bookingInfo[0] = element.getElementsByTagName("bookingID").item(0).getTextContent();
                bookingInfo[1] = element.getElementsByTagName("username").item(0).getTextContent();
                bookingInfo[2] = "true"; // set findings as true
                break;

            } // close if statement

        } // close for loop

    }

    /** method confirmationReader()
     * puts all booking confirmation information into an array to be used for a table
     * @returns:  {String[][]} returns table data in columns & rows
     */
    public String[][] confirmationReader() {

       // create new table data array
       tableData = new String[allConfirmObjects.size()][11];

        // for each confirmation, add data to array
       for (XMLCustomerBookingReader eachConfirmation : allConfirmObjects) {

           // add confirmation data to array
           tableData[rows][columns] = eachConfirmation.confirmationID;
           columns++;
           tableData[rows][columns] = eachConfirmation.clientname;
           columns++;
           tableData[rows][columns] = eachConfirmation.guests;
           columns++;
           tableData[rows][columns] = eachConfirmation.startdate;
           columns++;
           tableData[rows][columns] = eachConfirmation.enddate;
           columns++;
           tableData[rows][columns] = eachConfirmation.apartmentname;
           columns++;
           tableData[rows][columns] = eachConfirmation.bedrooms;
           columns++;
           tableData[rows][columns] = eachConfirmation.livingroom;
           columns++;
           tableData[rows][columns] = eachConfirmation.bathrooms;
           columns++;
           tableData[rows][columns] = eachConfirmation.catering;
           columns++;
           tableData[rows][columns] = eachConfirmation.totalprice;

           rows++; // next row
           columns = 0; // reset column counter

       } // close for loop

        return tableData;
    }

    /** method displayConfirmationsTable()
     * builds confirmation bookings table and adds to a given JPanel
     * @param tableData:    {String[][]} table data
     * @param displayPanel: {JPanel} GUI panel to add table to
     */
    public void displayConfirmationsTable (String[][] tableData, JPanel displayPanel) {

        // set column headers for table
        String columnHeaders[] = {"Confirmation ID","Name", "Guests","Start Date", "End Date",
                "Apartment", "Bedrooms", "Living Room", "Bathrooms", "Catering", "Total"};

        // create new JTable
        bookingsTable = new JTable(tableData, columnHeaders);

        // create JScrollPane, add table & set size
        scroll = new JScrollPane(bookingsTable);
        scroll.setPreferredSize(new Dimension(1000, 600));

        displayPanel.add(scroll); // add table to panel
    }

    //***************************************************************************//

    //*******************************Class getters*******************************//

    public String getClientname() {
        return clientname;
    }

    public String getGuests() {
        return guests;
    }

    public String getStartdate() {
        return startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public String getApartmentname() {
        return apartmentname;
    }

    public String getBedrooms() {
        return bedrooms;
    }

    public String getLivingroom() {
        return livingroom;
    }

    public String getBathrooms() {
        return bathrooms;
    }

    public String getCatering() {
        return catering;
    }

    public String getTotalprice() {
        return totalprice;
    }

} // close XMLCustomerBookingReader class
