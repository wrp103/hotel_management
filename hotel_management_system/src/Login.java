//*******************************************************************************//
//* Author:     1816477                                                         *//
//* Assignment: Element 011: Hotel Management System                            *//
//* Date:       10/05/20                                                        *//
//*                                                                             *//
//  Class Name: Login                                                           *//
//* Class Description: Handles the elements of users attempting to gain access. *//
//*                                                                             *//
//*******************************************************************************//

import javax.swing.*;
import java.util.ArrayList;

public class Login {

    //*************************Class instance variables**************************//

    private boolean validLogin = false; // valid login attempt
    private boolean admin = false; // admin access
    private ArrayList<User> testUsers; // array of users for testing
    private int error; // error code
    private static int badLoginCounter = 0; // failed login counter

    //***************************************************************************//

    //****************************Class constructors*****************************//

    /** constructor
     * creates a new login instance when a user attempts to login
     * @param username: {String} username entry
     * @param password: {String} password entry
     */
    public Login(String username, String password) {

        createTestUsers(); // create users for testing
        checkLoginDetails(username, password); // validate user login
    }

    //***************************************************************************//

    //********************************Class methods******************************//

    /** method buildMainMenuPanel()
     * checks users login entry against user accounts
     * @param username: {String} username entry
     * @param password: {String} password entry
     */
    private void checkLoginDetails(String username, String password) {

        // loop through each test user account
        for (User user : testUsers) {

            // if username is a match, proceed
            if (user.getUsername().equals(username)) {

                // if user password matches the one associated with username, proceed
                if (user.getPassword().equals(password)) {

                    // if account has admin privileges, set admin access
                    if (user.getAdmin()) {
                        admin = true; // set admin access
                    }

                    badLoginCounter = 0; // reset bad login counter
                    validLogin = true; // set valid login attempt as true
                    break; // exit

                // else, password incorrect, assign error code
                } else {
                    error = 1; // return error code 1
                    validLogin = false; // set valid login attempt as false
                    break; // exit
                }
            // else, username not found, assign error code
            } else {
                error = 2; // return error code 2
                validLogin = false; // set valid login attempt as false

            } // close user details check if statement

        } // close for loop

        // if valid login is false, display message according to error code
        if (!validLogin) {

            badLoginCounter++; // increase bad login attempt counter

            // if error code 1, display invalid password message
            if (error == 1) {
                JOptionPane.showMessageDialog(null,
                        "Invalid password");
            // else, error code 2, display invalid username message
            } else {
                JOptionPane.showMessageDialog(null,
                        "Invalid username");
            }

            // if bad login counter is greater than 2, shut down app
            if (badLoginCounter > 2) {
                JOptionPane.showMessageDialog(null,
                        "Too many wrong attempts!, Closing program");
                System.exit(-1);
            }

         } // close error message if statement

    } // close checkLoginDetails method


    /** method createTestUsers()
     * creates users for testing purposes and assigns to array list
     */
    private void createTestUsers() {

        testUsers = new ArrayList<>(); // create array list
        // add test users to array list
        testUsers.add(new UserCustomer("steve", "123"));
        testUsers.add(new UserCustomer("phil", "123"));
        testUsers.add(new UserCustomer("simon", "123"));
        testUsers.add(new UserCustomer("greg", "123"));
        testUsers.add(new UserCustomer("dustin", "123"));
        testUsers.add(new UserManager("abe", "123"));
        testUsers.add(new UserManager("ioannis", "123"));
        testUsers.add(new UserManager("tam", "123"));
        testUsers.add(new UserManager("paul", "123"));
        testUsers.add(new UserManager("wes", "123"));
    }

    //***************************************************************************//

    //*******************************Class getters*******************************//

    public boolean getValidLogin() {
        return validLogin;
    }

    public boolean getAdmin() {
        return admin;
    }

} // close class Login
