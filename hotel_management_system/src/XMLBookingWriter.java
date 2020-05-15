//*******************************************************************************//
//* Author:     1816477                                                         *//
//* Assignment: Element 011: Hotel Management System                            *//
//* Date:       10/05/20                                                        *//
//*                                                                             *//
//  Class Name: XMLBookingWriter (Subclass of XMLWriter)                        *//
//* Class Description: Used to create a new booking XML file                    *//
//*                                                                             *//
//*******************************************************************************//

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.swing.*;

public class XMLBookingWriter extends XMLWriter {

    //*************************Class instance variables**************************//

    private String fileName = "xml_files/bookings/"; // file path
    private Document bookingXML; // XML document
    private Element bookingsRoot; // root node
    private Element bookingElement; // child node
    // booking XML file elements
    private Attr idAttr;
    private Element clientnameElement;
    private Element guestsElement;
    private Element startdateElement;
    private Element enddateElement;
    private Element cateringElement;

    //***************************************************************************//

    //********************************Class methods******************************//

    /** method createBookingXML()
     * creates a booking XML file with provided booking elements
     * @param bookingID:  {String} bookingID used as attribute & filename
     * @param clientname: {String} customer name
     * @param guests:     {String} no. of guests
     * @param startdate:  {String} date to check in
     * @param enddate:    {String} date to check out
     * @param catering:   {String} catering requested
     */
    public void createBookingXML(String bookingID, String clientname, String guests, String startdate,
                                 String enddate, String catering) {

            fileName += bookingID + ".xml"; // set filename
            bookingXML = buildXML(); // build XML document

            bookingsRoot = bookingXML.createElement("bookings"); // set bookings node
            bookingXML.appendChild(bookingsRoot); // add to XML document

            bookingElement = bookingXML.createElement("booking"); // set child node
            bookingsRoot.appendChild(bookingElement); // add to XML document

            // create attribute (id) and assign under booking node
            idAttr = bookingXML.createAttribute("id");
            idAttr.setValue(bookingID);
            bookingElement.setAttributeNode(idAttr);

            // create client name element and assign under booking node
            clientnameElement = bookingXML.createElement("clientname");
            clientnameElement.appendChild(bookingXML.createTextNode(clientname));
            bookingElement.appendChild(clientnameElement);

            // create no. of guests element and assign under booking node
            guestsElement = bookingXML.createElement("guests");
            guestsElement.appendChild(bookingXML.createTextNode(guests)); // add to XML document
            bookingElement.appendChild(guestsElement);

            // create start date element and assign under booking node
            startdateElement = bookingXML.createElement("startdate");
            startdateElement.appendChild(bookingXML.createTextNode(startdate));
            bookingElement.appendChild(startdateElement);

            // create end date element and assign under booking node
            enddateElement = bookingXML.createElement("enddate");
            enddateElement.appendChild(bookingXML.createTextNode(enddate));
            bookingElement.appendChild(enddateElement);

            // create catering element and assign under booking node
            cateringElement = bookingXML.createElement("catering");
            cateringElement.appendChild(bookingXML.createTextNode(catering));
            bookingElement.appendChild(cateringElement);

            // create/write XML file and show success message
            writeXML(fileName, bookingXML);
            JOptionPane.showMessageDialog(null,"Booking created");
    }

} // close XMLBookingWriter class
