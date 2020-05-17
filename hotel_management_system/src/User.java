//*******************************************************************************//
//* Author:     1816477                                                         *//
//* Assignment: Element 011: Hotel Management System                            *//
//* Date:       10/05/20                                                        *//
//*                                                                             *//
//  Class Name: User                                                            *//
//* Class Description: Used to create a user account to access the system       *//
//*                                                                             *//
//*******************************************************************************//

import javax.swing.*;

public abstract class User {

    //*************************Class instance variables**************************//

    private String username; // users username
    private String password; // users password
    private boolean admin = false; // users access level

    //***************************************************************************//

    //****************************Class constructors*****************************//

    /** constructor
     * creates a new instance of a user
     */
    public User() {
        // display account created message
      //  userCreated(); message disabled to speed up testing process
    }

    //***************************************************************************//

    //********************************Class methods******************************//

    /** method userCreated()
     * displays message that account has been created
     */
    public void userCreated() {
        JOptionPane.showMessageDialog(null,
                "User account has been created"); // display message
    }

    //***************************************************************************//

    //*******************************Class setters*******************************//

    public void setUsername(String aUsername) {
        username = aUsername;
    }

    public void setPassword(String aPassword) {
        password = aPassword;
    }

    public void setAdmin(boolean aAdmin) {
        admin = aAdmin;
    }

    //*******************************Class getters*******************************//

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean getAdmin() {
        return admin;
    }

} // close User class