//*******************************************************************************//
//* Author:     1816477                                                         *//
//* Assignment: Element 011: Hotel Management System                            *//
//* Date:       10/05/20                                                        *//
//*                                                                             *//
//  Class Name: GUIUser (Subclass of GUI)                                       *//
//* Class Description: Builds/handles customer & managers GUI                   *//
//*                                                                             *//
//*******************************************************************************//

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIUser extends GUI  {

    //*************************Class instance variables**************************//

    // panels
    private JPanel contentPanel = new JPanel();
    private JPanel searchPanel = new JPanel();
    private JPanel manageBookingPanel = new JPanel();
    private JPanel makeBookingPanel = new JPanel();
    private JPanel customerDetailsPanel = new JPanel();
    private JPanel allBookingsPanel = new JPanel();
    private JPanel mainManagerPanel = new JPanel();
    private JPanel mainCustomerPanel = new JPanel();
    private JPanel mainMenuPanel = new JPanel();

    // text fields
    private JTextField apartmentIdField;
    private JTextField clientNameField;
    private JTextField guestsNoField;
    private JTextField startDateField;
    private JTextField endDateField;
    private JTextField cateringField;
    private JTextField bookingIDField;

    // labels
    private JLabel apartmentIdLabel;
    private JLabel clientNameLabel;
    private JLabel guestsNoLabel;
    private JLabel startDateLabel;
    private JLabel endDateLabel;
    private JLabel cateringLabel;
    private JLabel enterBookingLabel;

    // buttons
    private JButton viewAllBookingsButton;
    private JButton manageBookingsButton;
    private JButton updateBookingButton;
    private JButton logoutButton;
    private JButton searchButton;
    private JButton makeBookingButton;
    private JButton bookApartmentButton;

    // layouts & borders
    private BoxLayout menuLayout;
    private BorderLayout mainUserLayout;
    private FlowLayout manageBookingsLayout;
    private FlowLayout viewBookingsLayout;
    private BorderLayout contentPanelLayout;
    private TitledBorder managerPanelBorder;
    private TitledBorder customerPanelBorder;

    // form entry variables
    private String apartmentIdEntry;
    private String clientnameEntry;
    private String guestsEntry;
    private String startdateEntry;
    private String enddateEntry;
    private String cateringEntry;

    // other
    private boolean isManager; // used to differentiate between manager & customer access
    private String currentCustomer; // current customer username for booking purposes
    private String bookingToUpdate;
    private XMLCustomerBookingReader bookings;
    private Booking booking;
    private String[] apartment;
    private XMLCustomerBookingReader bookingReader;
    private XMLApartmentReader apartments;

    //***************************************************************************//

    //****************************Class constructors*****************************//

    /** constructor1
     * creates a new GUI user instance for a customer manager
     * @param logout: {JButton} button with action to return to login screen
     * @param access: {boolean} user access level, true for admin
     */
    GUIUser(JButton logout, boolean access) {

        isManager = access; // assign access
        logoutButton = logout; // assign logout button with logout action
    }

    /** constructor2
     * creates a new GUI user instance for a customer
     * @param logout:   {JButton} button with action to return to login screen
     * @param customer: {String}  customer username
     * @param access:   {boolean} user access level, true for admin
     */
    GUIUser(JButton logout, String customer, boolean access) {

        isManager = access; // assign access
        currentCustomer = customer; // assign customer username
        logoutButton = logout; // assign logout button with logout action
    }

    //***************************************************************************//

    //********************************Class methods******************************//

    /** method buildCustomerGUI()
     * creates, builds & returns main customer GUI panel & buttons
     * @returns: {JPanel} main customer panel
     */
    public JPanel buildCustomerGUI() {

        // create layout and set to main customer panel
        mainUserLayout = new BorderLayout();
        mainCustomerPanel.setLayout(mainUserLayout);
        mainCustomerPanel.setBackground(Color.gray); // set colour

        // create border and set to main customer panel
        customerPanelBorder = new TitledBorder("Main Customer Menu"); // title text
        customerPanelBorder.setTitleFont(new Font("Arial", Font.BOLD,17)); // title font
        customerPanelBorder.setTitleColor(Color.white); // text colour
        mainCustomerPanel.setBorder(customerPanelBorder);

        // create menu buttons and add action listeners
        makeBookingButton = new JButton("Make Booking");
        makeBookingButton.addActionListener(new makeBookingButtonAction());
        manageBookingsButton = new JButton("Manage Bookings");
        manageBookingsButton.addActionListener(new manageBookingButtonAction());

        buildMainMenuPanel(); // call build main menu panel method
        buildContentPanel(); // call build content panel method

        return mainCustomerPanel;
    }


    /** method buildManagerGUI()
     * creates, builds & returns main manager GUI panel & buttons
     * @returns: {JPanel} main manager panel
     */
    public JPanel buildManagerGUI() {

        // create layout and set to main manager panel
        mainUserLayout = new BorderLayout();
        mainManagerPanel.setLayout(mainUserLayout);
        mainManagerPanel.setBackground(Color.gray); // set colour

        // create border and set to main manager panel
        managerPanelBorder = new TitledBorder("Main Manager Menu"); // title text
        managerPanelBorder.setTitleFont(new Font("Arial", Font.BOLD,17));
        managerPanelBorder.setTitleColor(Color.white); // text colour
        mainManagerPanel.setBorder(managerPanelBorder);

        // create menu buttons and add action listeners
        viewAllBookingsButton  = new JButton("View All Bookings");
        viewAllBookingsButton.addActionListener(new viewAllBookingsButtonAction());
        manageBookingsButton = new JButton("Manage Bookings");
        manageBookingsButton.addActionListener(new manageBookingButtonAction());

        buildMainMenuPanel(); // call build main menu panel method
        buildContentPanel(); // call build content panel method

        return mainManagerPanel;
    }

    /** method buildMainMenuPanel()
     * creates and builds main menu GUI panel
     */
    private void buildMainMenuPanel() {

        // create layout and set to main menu panel
        menuLayout = new BoxLayout(mainMenuPanel, BoxLayout.X_AXIS);
        mainMenuPanel.setLayout(menuLayout);

        // if user is a manager, add menu to main manager panel
        if (isManager) {
            mainMenuPanel.add(viewAllBookingsButton); // add view bookings button
            mainMenuPanel.add(manageBookingsButton); // add manage bookings button
            mainMenuPanel.add(logoutButton); // add logout button
            mainManagerPanel.add(mainMenuPanel, BorderLayout.NORTH);
        // else add menu to main customer panel
        } else {
            mainMenuPanel.add(makeBookingButton); // make booking button
            mainMenuPanel.add(manageBookingsButton); // add manage bookings button
            mainMenuPanel.add(logoutButton); // add logout button
            mainCustomerPanel.add(mainMenuPanel, BorderLayout.NORTH);
        }
    }

    /** method buildContentPanel()
     * creates and builds content GUI panel
     */
    private void buildContentPanel() {

        // create layout and set to content panel
        contentPanelLayout = new BorderLayout();
        contentPanel.setLayout(contentPanelLayout);
        contentPanel.setBackground(Color.gray); // set colour

        // if user is a manager, add content panel to main manager panel
        if (isManager) {
            mainManagerPanel.add(contentPanel, BorderLayout.CENTER);
        // else add content panel to main customer panel
        } else {
            mainCustomerPanel.add(contentPanel, BorderLayout.CENTER);
        }
    }

    /** method buildMakeBookingPanel()
     * creates and builds make a booking panel (displays when customer clicks make booking on main menu)
     */
    private void buildMakeBookingPanel() {

        // create temp button and remove all to prevent duplicating panel with multiple clicks of button
        makeBookingPanel.add(new JButton());
        makeBookingPanel.removeAll();

        makeBookingPanel.setBackground(Color.gray); // set colour

        // create apartment reader and build table of all apartments
        apartments = new XMLApartmentReader();
        apartments.apartmentsTableDisplay(apartments.apartmentsTableCreator(), makeBookingPanel);

        // add table of apartments to content panel
        contentPanel.add(makeBookingPanel, BorderLayout.CENTER);
        buildCustomerDetailsPanel(); // call method to build the panel that holds customer booking form
    }

    /** method buildCustomerDetailsPanel()
     * creates and builds customer details panel, used for entering booking form details
     */
    private void buildCustomerDetailsPanel() {

        // create and assign layout to customer details panel
        viewBookingsLayout = new FlowLayout();
        customerDetailsPanel.setLayout(viewBookingsLayout);
        customerDetailsPanel.setBackground(Color.pink); // set colour
        customerDetailsPanel.setBorder(BorderFactory.createEmptyBorder(10,20,10,20));
        customerDetailsPanel.setPreferredSize(new Dimension(160,100));

        createFormLayout(); // call create booking form method

        // add created booking form labels and fields to customer details panel
        customerDetailsPanel.add(apartmentIdLabel);
        customerDetailsPanel.add(apartmentIdField);
        customerDetailsPanel.add(clientNameLabel);
        customerDetailsPanel.add(clientNameField);
        customerDetailsPanel.add(guestsNoLabel);
        customerDetailsPanel.add(guestsNoField);
        customerDetailsPanel.add(startDateLabel);
        customerDetailsPanel.add(startDateField);
        customerDetailsPanel.add(endDateLabel);
        customerDetailsPanel.add(endDateField);
        customerDetailsPanel.add(cateringLabel);
        customerDetailsPanel.add(cateringField);

        // add book apartment button and action
        bookApartmentButton = new JButton("Book Apartment");
        bookApartmentButton.addActionListener(new bookApartmentButtonAction());
        customerDetailsPanel.add(bookApartmentButton);

        // add customer details panel to content panel
        contentPanel.add(customerDetailsPanel, BorderLayout.WEST);

    }

    /** method buildAllBookingsPanel()
     * creates and builds all bookings panel (displays when manager clicks view all bookings on main menu)
     */
    private void buildAllBookingsPanel() {

        allBookingsPanel.setBackground(Color.gray); // set colour

        // create XMLCustomerBookingReader object
        bookings = new XMLCustomerBookingReader();

        // retrieve all bookings information
        bookings.storeAllConfirmationInfo();

        // put booking information in a table panel
        bookings.displayConfirmationsTable(bookings.confirmationReader(), allBookingsPanel);

        // add table to content panel
        contentPanel.add(allBookingsPanel, BorderLayout.CENTER);
    }

    /** method buildSearchPanel()
     * creates and builds search panel layout (displays when user clicks manage bookings on main menu)
     */
    private void buildSearchPanel() {

        // create layout and set to search panel
        viewBookingsLayout = new FlowLayout();
        searchPanel.setLayout(viewBookingsLayout);
        searchPanel.setBackground(Color.pink); // set colour
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10,20,10,20));
        searchPanel.setPreferredSize(new Dimension(160,100)); // set size

        // create enter booking id label and field
        enterBookingLabel = new JLabel("Enter Booking ID");
        bookingIDField = new JTextField("", 15);

        // create search button and action
        searchButton = new JButton("Search");
        searchButton.addActionListener(new searchButtonAction());

        // add label, field and button to search panel
        searchPanel.add(enterBookingLabel);
        searchPanel.add(bookingIDField);
        searchPanel.add(searchButton);

        // add search panel to content panel
        contentPanel.add(searchPanel, BorderLayout.WEST);
    }

    /** method buildManageBookingPanel()
     * creates and builds manage booking GUI layout (displays when user searches for a booking)
     */
    private void buildManageBookingPanel() {

        // create layout and set to manage booking panel
        manageBookingsLayout = new FlowLayout();
        manageBookingPanel.setLayout(manageBookingsLayout);

        // set manage booking panel visual settings
        manageBookingPanel.setBackground(Color.pink); // set colour
        manageBookingPanel.setBorder(BorderFactory.createEmptyBorder(10,20,10,20));
        manageBookingPanel.setPreferredSize(new Dimension(160,100)); // set size

        // add form labels and fields to manage booking panel
        manageBookingPanel.add(apartmentIdLabel);
        manageBookingPanel.add(apartmentIdField);
        manageBookingPanel.add(clientNameLabel);
        manageBookingPanel.add(clientNameField);
        manageBookingPanel.add(guestsNoLabel);
        manageBookingPanel.add(guestsNoField);
        manageBookingPanel.add(startDateLabel);
        manageBookingPanel.add(startDateField);
        manageBookingPanel.add(endDateLabel);
        manageBookingPanel.add(endDateField);
        manageBookingPanel.add(cateringLabel);
        manageBookingPanel.add(cateringField);

        // create update booking button and action
        updateBookingButton = new JButton("Update Booking");
        updateBookingButton.addActionListener(new updateBookingButtonAction());
        manageBookingPanel.add(updateBookingButton);

        // add manage booking panel to content panel
        contentPanel.add(manageBookingPanel, BorderLayout.WEST);
    }

    /** method createFormLayout()
     * creates and builds booking form labels and empty text fields
     */
    private void createFormLayout() {

        // create and assign label text and empty fields
        apartmentIdLabel = new JLabel("Apartment ID");
        apartmentIdField = new JTextField("", 15);
        clientNameLabel = new JLabel("Your name");
        clientNameField = new JTextField("", 15);
        guestsNoLabel = new JLabel("No. of guests");
        guestsNoField = new JTextField("", 15);
        startDateLabel = new JLabel("Start date");
        startDateField = new JTextField("", 15);
        endDateLabel = new JLabel("End date");
        endDateField = new JTextField("", 15);
        cateringLabel = new JLabel("Catering required");
        cateringField = new JTextField("", 15);
    }

    /** method createFormLayout()
     * creates and builds booking form labels and filled text fields (with searched for booking info)
     * @param fillApartmentID:   {String} apartment ID to fill text field with
     * @param fillClientname:    {String} customers name to fill text field with
     * @param fillGuests:        {String} no. of guests to stay to fill text field with
     * @param fillStartdate:     {String} date to check in to fill text field with
     * @param fillEnddate:       {String} date to check out to fill text field with
     * @param fillCatering:      {String} requested catering or not (yes/no) to fill text field with
     */
    private void createFormLayout(String fillApartmentID, String fillClientname, String fillGuests,
                                 String fillStartdate, String fillEnddate, String fillCatering) {

        // create and assign label text and filled fields
        apartmentIdLabel = new JLabel("Apartment ID");
        apartmentIdField = new JTextField(fillApartmentID, 15);
        clientNameLabel = new JLabel("Customer name");
        clientNameField = new JTextField(fillClientname, 15);
        guestsNoLabel = new JLabel("No. of guests");
        guestsNoField = new JTextField(fillGuests, 15);
        startDateLabel = new JLabel("Start date");
        startDateField = new JTextField(fillStartdate, 15);
        endDateLabel = new JLabel("End date");
        endDateField = new JTextField(fillEnddate, 15);
        cateringLabel = new JLabel("Catering required");
        cateringField = new JTextField(fillCatering, 15);
    }

    /** method resetView()
     * resets GUI panels settings and visibility
     */
    private void resetView() {

        // remove all panel settings
        searchPanel.removeAll();
        manageBookingPanel.removeAll();
        customerDetailsPanel.removeAll();
        allBookingsPanel.removeAll();

        // set visibility back to false
        allBookingsPanel.setVisible(false);
        customerDetailsPanel.setVisible(false);
        searchPanel.setVisible(false);
        makeBookingPanel.setVisible(false);
        manageBookingPanel.setVisible(false);

        // re-build main menu
        buildMainMenuPanel();
        buildContentPanel();
    }

    //***************************************************************************//

    //********************************Inner classes******************************//

    /** Inner class makeBookingButtonAction()
     * contains make booking button action method
     */
    private class makeBookingButtonAction implements ActionListener {

        /** method actionPerformed()
         * builds and displays make booking panel
         */
        public void actionPerformed(ActionEvent a) {

            resetView(); // reset panels
            buildMakeBookingPanel();
            customerDetailsPanel.setVisible(true); // set visible
            makeBookingPanel.setVisible(true); // set visible

        } // close actionPerformed method

    } // close makeBookingButtonAction class

    /** Inner class bookApartmentButtonAction()
     * contains book apartment button action method
     */
    private class bookApartmentButtonAction implements ActionListener {

        /** method actionPerformed()
         * retrieves text from booking form fields and creates booking
         */
        public void actionPerformed(ActionEvent a) {

            // retrieve and assign booking form field text
            apartmentIdEntry = apartmentIdField.getText();
            clientnameEntry = clientNameField.getText();
            guestsEntry = guestsNoField.getText();
            startdateEntry = startDateField.getText();
            enddateEntry = endDateField.getText();
            cateringEntry = cateringField.getText();

            // create new booking object
            booking = new Booking(currentCustomer, apartmentIdEntry, clientnameEntry, guestsEntry, startdateEntry,
                    enddateEntry, cateringEntry);

            // if apartment found proceed to validating booking form entries
            if (booking.findApartment()) {
                // if validation checks pass, create booking
                if (booking.checkBookingForm()) {
                    booking.createBooking();
                    resetView(); // reset panels
                }

            } // close if statement

        } // close actionPerformed method

    } // close bookApartmentButtonAction class

    /** Inner class viewAllBookingsButtonAction()
     * contains view all bookings button action method
     */
    private class viewAllBookingsButtonAction implements ActionListener {

        /** method actionPerformed()
         * builds and displays make all bookings panel
         */
        public void actionPerformed(ActionEvent a) {

            resetView(); // reset panels
            buildAllBookingsPanel(); // build all bookings panel
            allBookingsPanel.setVisible(true); // set visible

        } // close actionPerformed method

    } // close viewAllBookingsButtonAction class

    /** Inner class manageBookingButtonAction()
     * contains manage booking button action method
     */
    private class manageBookingButtonAction implements ActionListener {

        /** method actionPerformed()
         * builds and displays search panel
         */
        public void actionPerformed(ActionEvent a) {

            resetView(); // reset panels
            buildSearchPanel(); // build search panel
            searchPanel.setVisible(true); // set visible

        } // close actionPerformed method

    } // close manageBookingButtonAction class

    /** Inner class searchButtonAction()
     * contains search button action method
     */
    private class searchButtonAction implements ActionListener {

        /** method actionPerformed()
         * retrieves booking ID from search field and displays manage booking panel
         */
        public void actionPerformed(ActionEvent a) {

            resetView(); // reset panels

            // retrieve booking ID from search field
            bookingToUpdate = bookingIDField.getText();

            // create XMLCustomerBookingReader object
            bookingReader = new XMLCustomerBookingReader();

            // confirm the booking exists & belongs to customer
            boolean resultOfSearch;
            // if user is a manager, confirm booking exists
            if (isManager) {
                resultOfSearch = new XMLCustomerBookingReader().confirmBookingMatch(bookingToUpdate);
            // else, confirm booking exists AND the booking belongs to current customer
            } else {
                resultOfSearch = bookingReader.confirmBookingMatch(currentCustomer, bookingToUpdate);
            }

            // if search result is confirmed true, retrieve booking details to fill manage form fields with
            if (resultOfSearch) {

                // retrieve booking confirmation information from XML file
                bookingReader = bookingReader.getConfirmationInfo(bookingToUpdate + ".xml");

                // create new apartment reader object and retrieve information based on bookings apartment name
                apartment = new XMLApartmentReader().getBasicApartmentInfo(bookingReader.getApartmentname());

                // build booking form with fields filled with current booking information
                createFormLayout(apartment[0], bookingReader.getClientname(), bookingReader.getGuests(),
                        bookingReader.getStartdate(), bookingReader.getEnddate(), bookingReader.getCatering());

                // build manage booking panel and set visible
                buildManageBookingPanel();
                manageBookingPanel.setVisible(true);

            } // close if statement

        } // close actionPerformed method

    } // close searchButtonAction class

    /** Inner class updateBookingButtonAction()
     * contains update booking button action method
     */
    private class updateBookingButtonAction implements ActionListener {

        /** method actionPerformed()
         * retrieves booking ID from search field and displays manage booking panel
         */
        public void actionPerformed(ActionEvent a) {

            // retrieve changed booking details information from form fields
            apartmentIdEntry = apartmentIdField.getText();
            clientnameEntry = clientNameField.getText();
            guestsEntry = guestsNoField.getText();
            startdateEntry = startDateField.getText();
            enddateEntry = endDateField.getText();
            cateringEntry = cateringField.getText();

            // create new booking object with changed booking details
            booking = new Booking(apartmentIdEntry, clientnameEntry, guestsEntry, startdateEntry,
                    enddateEntry, cateringEntry);

            // if apartment id entered is found proceed to form validation
            if (booking.findApartment()) {
                // if form validation is acceptable, overwrite booking with new booking details
                if (booking.checkBookingForm()) {
                    booking.updateBooking(bookingToUpdate); // update booking
                    resetView(); // reset panels
                }

            } // close if statement

        } // close actionPerformed method

    } // close updateBookingButtonAction class

} // close GUIUser class
