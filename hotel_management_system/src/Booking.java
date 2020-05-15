//*******************************************************************************//
//* Author:     1816477                                                         *//
//* Assignment: Element 011: Hotel Management System                            *//
//* Date:       10/05/20                                                        *//
//*                                                                             *//
//  Class Name: Booking                                                         *//
//* Class Description: Handles all aspects of a customer booking an apartment.  *//
//*                                                                             *//
//*******************************************************************************//

import javax.swing.*;
import java.util.ArrayList;

public class Booking {

    //*************************Class instance variables**************************//

    // class variables for accessing methods
    private XMLBookingWriter booking;
    private XMLConfirmationWriter confirmation;
    private XMLApartmentReader apartmentFinder;
    private XMLCustomerBookingEditor customerBookingWriter;
    private FileHandler fManager;
    private ArrayList<String> currentBookingFiles;

    // booking variables
    private String nextID;
    private String clientname;
    private String guests;
    private String startdate;
    private String enddate;
    private String catering;

    // confirmation variables
    private String apartmentID;
    private String[] apartmentInfo;
    private String apartmentname;
    private String bedrooms;
    private String livingroom;
    private String bathrooms;
    private String price;
    private String totalprice;

    // customerBooking variable
    private String customerUsername;

    //***************************************************************************//

    //****************************Class constructors*****************************//

    /** constructor1
     * creates a new instance for a CUSTOMER user and sets variables from parameters
     * @param username:         {String} current users username
     * @param newApartmentID:   {String} apartment ID to book
     * @param newClientname:    {String} customers name
     * @param newGuests:        {String} no. of guests to stay
     * @param newStartdate:     {String} date to check in
     * @param newEnddate:       {String} date to check out
     * @param newCatering:      {String} requested catering or not (yes/no)
     */
    public Booking(String username, String newApartmentID, String newClientname, String newGuests,
                     String newStartdate, String newEnddate, String newCatering) {

        // set user detail instance variables
        customerUsername = username;
        apartmentID = newApartmentID;
        clientname = newClientname;
        guests = newGuests;
        startdate = newStartdate;
        enddate = newEnddate;
        catering = newCatering;
    }

    /** constructor2
     * creates a new instance for a MANAGER user and sets variables from parameters
     * @param newApartmentID:   {String} apartment ID to book
     * @param newClientname:    {String} customers name
     * @param newGuests:        {String} no. of guests to stay
     * @param newStartdate:     {String} date to check in
     * @param newEnddate:       {String} date to check out
     * @param newCatering:      {String} requested catering or not (yes/no)
     */
    public Booking(String newApartmentID, String newClientname, String newGuests,
                   String newStartdate, String newEnddate, String newCatering) {

        // set user detail instance variables
        apartmentID = newApartmentID;
        clientname = newClientname;
        guests = newGuests;
        startdate = newStartdate;
        enddate = newEnddate;
        catering = newCatering;
    }

    //***************************************************************************//

    //********************************Class methods******************************//

    /** method createBooking()
     * creates or overwrites a new booking xml
     */
    public void createBooking() {

        // file handler finds the next bookingID to use
        fManager = new FileHandler();
        currentBookingFiles = fManager.getFileList("xml_files/bookings/", ".xml"); // search folder
        nextID = fManager.nextXMLFile(currentBookingFiles); // set id

        // xml booking writer creates the new booking xml file
        booking = new XMLBookingWriter();
        booking.createBookingXML(nextID, clientname, guests, startdate, enddate, catering); // create new booking xml

        // call method to create new confirmation xml
        createConfirmation();
    }

    /** method createConfirmation()
     * creates or overwrites a new confirmation xml
     */
    private void createConfirmation() {

        // xml confirmation writer creates the new confirmation xml file
        confirmation = new XMLConfirmationWriter();

        // calculate total cost of booking
        calcTotal();

        // create new confirmation xml
        confirmation.createConfirmationXML(nextID, clientname, guests, startdate, enddate, apartmentname,
                bedrooms, livingroom, bathrooms, catering, totalprice);

        // call method to create new customerBooking xml
        newCustomerBooking();
    }

    /** method calcTotal()
     * calculates the total cost of a booking
     */
    private void calcTotal() {

        // create temporary variables to hold converted string entries of int or double
        double tempCateringTotal;
        double tempRoomBookingTotal;
        int tempStartdate;
        int tempEnddate;
        int tempDaysBooked;

        // convert field entries to ints to calculate no. of days booked using convertDate() method
        tempStartdate = convertDate(startdate);
        tempEnddate = convertDate(enddate);

        // days booked calculation assumes customer does not pay for end day (checks out in morning)
        tempDaysBooked = tempEnddate - tempStartdate;

        // check if customer wants catering
        if (catering.equals("yes")) {
            tempCateringTotal = 15.0 * Double.parseDouble(guests); // multiply catering cost by no. of guests
        } else {
            tempCateringTotal = 0; // no catering cost
        }

        // get daily total by multiplying cost of apartment by no. of guests
        tempRoomBookingTotal = Double.parseDouble(price) * Double.parseDouble(guests);
        tempRoomBookingTotal = tempCateringTotal + tempRoomBookingTotal;

        // get final booking cost by multiplying daily total by no. of days booked
        tempRoomBookingTotal *= tempDaysBooked;

        // convert total back to string and set to totalprice instance variable
        totalprice = Double.toString(tempRoomBookingTotal);
    }

    /** method convertDate()
     * converts a date string of dd/mm/yy to an int of yymmdd
     * @param date: {String} date to convert
     * @returns:    {int} converted date of yymmdd
     */
    private int convertDate(String date) {

        // create temporary array to hold date
        String[] tempDateSplit;
        String tempDateReversed;
        // split date into day, month, year and assign to array
        tempDateSplit = date.split("/");
        // reverse the date and asign to tempoary variable
        tempDateReversed = "";
        for (int i = tempDateSplit.length-1; i >= 0; i--) {
            tempDateReversed += tempDateSplit[i];
        }
        // convert reversed date to integer and return it
        return Integer.parseInt(tempDateReversed);
    }

    /** method newCustomerBooking()
     * updates customerBooking XML file with new customer booking
     */
    private void newCustomerBooking() {

        // xml customerBooking writer is used to update customerBookings.xml file
        customerBookingWriter = new XMLCustomerBookingEditor();
        customerBookingWriter.updateCustomerBookingXML(customerUsername, nextID);
    }

    /** method updateBooking()
     * overwrites a confirmation xml file with new information
     * @param bookingID: {String} bookingID to overwrite
     */
    public void updateBooking(String bookingID) {

        // xml confirmation writer overwrites the current confirmation xml file
        confirmation = new XMLConfirmationWriter();

        // re-calculate total cost from changes
        calcTotal();

        // overwrite file
        confirmation.createConfirmationXML(bookingID, clientname, guests, startdate, enddate, apartmentname,
                bedrooms, livingroom, bathrooms, catering, totalprice);
        JOptionPane.showMessageDialog(null,
                "Booking information updated"); // display success message
    }

    /** method findApartment()
     * searches for apartment details & if found, sets variables
     * @returns: {boolean} true if apartment info found, false if not
     */
    public boolean findApartment() {

        // create new apartment reader to get apartment details
        apartmentFinder = new XMLApartmentReader();
        apartmentInfo = apartmentFinder.getAllApartmentInfo(apartmentID);
        // check if array is not empty (apartment found)
        if (apartmentInfo[0] != null) {
            // set required variables
            apartmentname = apartmentInfo[0];
            price = apartmentInfo[1];
            bedrooms = apartmentInfo[5];
            livingroom = apartmentInfo[7];
            bathrooms = apartmentInfo[6];
            return true;
            // else display error message and return false
        } else {
            JOptionPane.showMessageDialog(null,
                    "Invalid apartment id entry");
            return false;
        }
    }

    /** method checkBookingForm()
     * validates booking form, checking for errors from user entry
     * @returns: {boolean} true if no errors found, false if errors found
     */
    public boolean checkBookingForm() {

        // create temp variables used for validation checking
        int enteredStartdate;
        int enteredEnddate;
        int entereduests;
        int aptStartdate;
        int aptEnddate;
        int aptMaxGuests;

        // convert field entry dates and no. of guests to int and assign to temp variables
        enteredStartdate = convertDate(startdate);
        enteredEnddate = convertDate(enddate);
        entereduests = Integer.parseInt(guests);

        // get apartment details
        apartmentFinder = new XMLApartmentReader();
        apartmentInfo = apartmentFinder.getAllApartmentInfo(apartmentID);

        // convert apartment available dates and max guests to int and assign to temp variables
        aptStartdate = convertDate(apartmentInfo[2]);
        aptEnddate = convertDate(apartmentInfo[3]);
        aptMaxGuests = Integer.parseInt( apartmentInfo[4]);

        // check user requested dates with actual available apartment dates
        if (enteredStartdate < aptStartdate || enteredStartdate > aptEnddate) { // validate start date
            JOptionPane.showMessageDialog(null,
                    "Invalid start date entry"); // display error
            return false;
        } else if (enteredEnddate > aptEnddate || enteredEnddate < aptStartdate) { // validate end date
            JOptionPane.showMessageDialog(null,
                    "Invalid end date entry"); // display error
            return false;
        } else if (enteredStartdate >= enteredEnddate) { // validate start date is before end date
            JOptionPane.showMessageDialog(null,
                    "Invalid entry, start date must be less than end date"); // display error
            return false;
        } else {
            // if dates entered are valid, check no. of guests entry
            if (entereduests < 1) { // must be at least 1 guest
                JOptionPane.showMessageDialog(null,
                        "Invalid guests entry, must be at least 1 guest"); // display error
                return false;
            } else if (entereduests > aptMaxGuests) { // cannot exceed max guests
                JOptionPane.showMessageDialog(null,
                        "Invalid guest entry, too many guests"); // display error
                return false;
            } else {
                return true; // if all checks pass, return true
            }
        } // end if statement

    } // end method

} // close Booking class
