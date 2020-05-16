//*******************************************************************************//
//* Author:     1816477                                                         *//
//* Assignment: Element 011: Hotel Management System                            *//
//* Date:       10/05/20                                                        *//
//*                                                                             *//
//  Class Name: GUI                                                             *//
//* Class Description: Super class for building/handling the applications GUI   *//
//*                                                                             *//
//*******************************************************************************//

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class GUI {

    //*************************Class instance variables**************************//

    // Graphical variables
    private JFrame mainFrame;
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private JPanel headerPanel;
    private JPanel footerPanel;
    private JPanel loginPanel;
    private JPanel customerPanel;
    private JPanel managerPanel;
    private JButton logout;

    // user variables (for access to certain areas of GUI)
    private String currentUser;
    private boolean adminAccess = false;
    private JTextField usernameField;
    private JPasswordField passwordField;

    // GUI user area variables
    private GUIUser managerGUI;
   // private GUICustomer customerGUI;

    //***************************************************************************//

    //********************************Class methods******************************//

    /** method buildMainGUI()
     * creates and builds main frame GUI layout
     */
    public void buildMainGUI() {

        mainFrame = new JFrame("Hotel Management"); // create main frame for GUI
        mainPanel = new JPanel(); // create main panel for GUI
        createLogoutButton(); // create logout button
        buildHeaderGUI(); // create header
        buildFooterGUI(); // create footer

        cardLayout = new CardLayout();
        mainPanel.setLayout(cardLayout); // set main panel to card layout
        mainPanel.add(loginPanel, "loginPanel"); // add login panel to a card
        cardLayout.show(mainPanel, "loginPanel"); // set login panel as showing

        // add everything created to the main frame, set size & visibility
        mainFrame.getContentPane().add(BorderLayout.NORTH, headerPanel);
        mainFrame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        mainFrame.getContentPane().add(BorderLayout.SOUTH, footerPanel);
        mainFrame.setSize(1400, 800);
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainFrame.setVisible(true);
    }

    /** method createLogoutButton()
     * creates and builds logout button & sets action
     */
    private void createLogoutButton() {
        // create button and set action upon click
        logout = new JButton("Logout");
        logout.addActionListener(new LogoutAction());
    }

    /** method buildHeaderGUI()
     * creates and builds header layout
     */
    private void buildHeaderGUI() {

        headerPanel = new JPanel(); // create new panel
        headerPanel.add(new JLabel("Hotel Management App")); // add title to header
        headerPanel.setBackground(Color.WHITE); // set colour
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0)); // set border
    }

    /** method buildFooterGUI()
     * creates and builds footer layout
     */
    private void buildFooterGUI() {

        footerPanel = new JPanel(); // create new panel
        footerPanel.add(new JLabel("SID: 1816477")); // add student ID to footer
        footerPanel.setBackground(Color.WHITE); // set colour
        footerPanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 20, 0)); // set border
    }

    /** method userLoginAccepted()
     * sets the correct GUI to build & display when valid user logs in
     */
    public void userLoginAccepted() {
        // if admin, build & display manager GUI
        if (adminAccess) {
            managerGUI = new GUIUser(logout, adminAccess); // create new manager GUI
            managerPanel = managerGUI.buildManagerGUI(); // build manager GUI
            mainPanel.add(managerPanel, "manager"); // add manager GUI to main GUI
            cardLayout.show(mainPanel, "manager"); // display manager GUI
        // if customer, build & display customer GUI
        } else {
            managerGUI = new GUIUser(logout, currentUser, adminAccess); // create new customer GUI
            customerPanel = managerGUI.buildCustomerGUI(); // build customer GUI
            mainPanel.add(customerPanel, "customer"); // add customer GUI to main GUI
            cardLayout.show(mainPanel, "customer"); // display customer GUI
        }
    }

    //***************************************************************************//

    //********************************Inner classes******************************//

    /** Inner class LogoutAction()
     * contains logout button action method
     */
    private class LogoutAction implements ActionListener {

        /** method actionPerformed()
         * resets login variables and displays login GUI
         */
        public void actionPerformed(ActionEvent a) {
            usernameField.setText(""); // reset username
            passwordField.setText(""); // reset password
            currentUser = ""; // reset current user
            adminAccess = false; // reset admin access
            cardLayout.show(mainPanel, "loginPanel"); // show login panel card
        }
    }

    //***************************************************************************//

    //*******************************Class getters*******************************//

    public JTextField getUsernameField() {
        return usernameField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }


    //***************************************************************************//

    //*******************************Class setters*******************************//

    public void setUsernameField(JTextField aUsernameField) {
        usernameField = aUsernameField;
    }

    public void setPasswordField(JPasswordField aPasswordField) {
        passwordField = aPasswordField;
    }

    public void setCurrentUser(String aCurrentUser) {
        currentUser = aCurrentUser;
    }

    public void setAdminAccess(boolean aAdminAccess) {
        adminAccess = aAdminAccess;
    }

    public void setLoginPanel(JPanel aLoginPanel) {
        loginPanel = aLoginPanel;
    }

} // close GUI class