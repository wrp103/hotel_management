//*******************************************************************************//
//* Author:     1816477                                                         *//
//* Assignment: Element 011: Hotel Management System                            *//
//* Date:       10/05/20                                                        *//
//*                                                                             *//
//  Class Name: GUILogin (Subclass of GUI)                                      *//
//* Class Description: Builds/handles login menu                                *//
//*                                                                             *//
//*******************************************************************************//

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUILogin extends GUI {

    //*************************Class instance variables**************************//

    // Graphical variables
    private JPanel loginPanel; // login panel
    private JPanel loginForm; // login form
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JButton loginButton;
    private GridLayout formLayout;
    private TitledBorder loginBorder;

    // User Login variables
    private Login newLogin;
    private String usernameEntry;
    private String passwordEntry;

    //***************************************************************************//

    //****************************Class constructors*****************************//

    /** constructor
     * creates a new instance of a login GUI (for starting app)
     */
    public GUILogin() {

        buildLoginGUI(); // build login GUI
        buildMainGUI(); // build main GUI
    }

    //***************************************************************************//

    //********************************Class methods******************************//

    /** method buildLoginGUI()
     * creates and builds login GUI layout
     */
    private void buildLoginGUI() {

        loginPanel = new JPanel(); // create login panel
        loginForm = new JPanel(); // create login form panel

        // create grid layout for form & set gaps between grid squares
        formLayout = new GridLayout(4, 2);

        // change login form panel settings
        loginForm.setLayout(formLayout);  // set login form to use grid layout
        loginForm.setBackground(Color.green); // set colour
        loginBorder = new TitledBorder("User Login"); // create border
        loginBorder.setTitleFont(new Font("Arial", Font.BOLD,17)); // set font
        loginForm.setBorder(loginBorder); // add border

        // create username & password label and field
        usernameLabel = new JLabel("Username");
        setUsernameField(new JTextField("", 15));
        passwordLabel = new JLabel("Password");
        setPasswordField(new JPasswordField("", 15));

        // create login button and set action upon click
        loginButton = new JButton("Login");
        loginButton.addActionListener(new userLoginAction());
        loginButton.setPreferredSize(new Dimension(10,8)); // set login buttons size

        // create blank row for appearance purposes
        JLabel emptySquare1 = new JLabel("");
        JLabel emptySquare2 = new JLabel("");
        loginForm.add(emptySquare1);
        loginForm.add(emptySquare2);
        loginForm.add(usernameLabel);
        loginForm.add(getUsernameField());
        loginForm.add(passwordLabel);
        loginForm.add(getPasswordField());
        loginForm.add(loginButton);
        loginPanel.add(loginForm); // add form to login panel

        setLoginPanel(loginPanel); // set login panel to GUI to super class
    }

    //********************************Inner classes******************************//

    /** Inner class userLoginAction()
     * contains login button action method
     */
    private class userLoginAction implements ActionListener {

        /** method actionPerformed()
         * checks username and password entry
         */
        public void actionPerformed(ActionEvent a) {

            // get entered test
            usernameEntry = getUsernameField().getText();
            passwordEntry = getPasswordField().getText();

            // create new login object to validate entry
            newLogin = new Login(usernameEntry, passwordEntry);

            // if login details are correct call method to load user GUI
            if (newLogin.getValidLogin()) {
                // if user is admin, set admin access true
                if (newLogin.getAdmin()) {
                    setAdminAccess(true);
                }
                setCurrentUser(usernameEntry); // set username
                userLoginAccepted(); // load GUI
            } // close if statement

        } // close actionPerformed method

    } // close userLoginAction inner class

} // end GUILogin class