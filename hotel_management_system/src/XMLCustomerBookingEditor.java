//*******************************************************************************//
//* Author:     1816477                                                         *//
//* Assignment: Element 011: Hotel Management System                            *//
//* Date:       10/05/20                                                        *//
//*                                                                             *//
//  Class Name: XMLCustomerBookingEditor (Subclass of XMLReader)                *//
//* Class Description: Used to update customerBookings.xml                      *//
//*                                                                             *//
//*******************************************************************************//

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLCustomerBookingEditor extends XMLReader {

    //*************************Class instance variables**************************//

    private String fileName = "xml_files/customerBookings.xml"; // file path
    private Document customerBookingsXML; // XML document
    private Element customerBookingsRoot; // root node
    private Element customerBookingElement; // child node

    // customer booking XML file elements
    private Attr idAttr;
    private Element bookingIDElement;
    private Element usernameElement;

    //***************************************************************************//

    //********************************Class methods******************************//

    /** method createBookingXML()
     * updates customer booking XML
     * @param username:  {String} bookingID used as attribute & filename
     * @param bookingID: {String} customer name
     */
    public void updateCustomerBookingXML(String username, String bookingID) {

        customerBookingsXML = readXML(fileName); // get current XML data
        customerBookingsRoot = customerBookingsXML.getDocumentElement(); // assign root
        customerBookingElement = customerBookingsXML.createElement("customerBooking"); // create new element

        // create attribute (id) and assign under confirmation node
        idAttr = customerBookingsXML.createAttribute("id");
        idAttr.setValue(bookingID);
        customerBookingElement.setAttributeNode(idAttr);

        // add elements to new booking confirmation entry
        customerBookingsRoot.appendChild(customerBookingElement);
        bookingIDElement = customerBookingsXML.createElement("bookingID");
        usernameElement = customerBookingsXML.createElement("username");
        bookingIDElement.appendChild(customerBookingsXML.createTextNode(bookingID));
        usernameElement.appendChild(customerBookingsXML.createTextNode(username));
        customerBookingElement.appendChild(bookingIDElement);
        customerBookingElement.appendChild(usernameElement);

        // update current file
        new EditCustomerBookingXML().updateXML(customerBookingsXML,fileName);

    }

    // inner class used to retrieve XMLWriter methods and variables
    private class EditCustomerBookingXML extends XMLWriter {}

} // close XMLCustomerBookingEditor class


