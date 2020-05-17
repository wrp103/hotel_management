//*******************************************************************************//
//* Author:     1816477                                                         *//
//* Assignment: Element 011: Hotel Management System                            *//
//* Date:       10/05/20                                                        *//
//*                                                                             *//
//  Class Name: UserManager (Subclass of User)                                  *//
//* Class Description: Used to create a manager user account                    *//
//*                                                                             *//
//*******************************************************************************//

import javax.swing.*;

public class UserManager extends User {

    //****************************Class constructors*****************************//

    /** constructor
     * creates a new instance of a manager user
     * @param username: {String} manager username
     * @param password: {String} manager password
     */
    public UserManager(String username, String password) {
        setUsername(username); // set username to super class instance variable
        setPassword(password); // set password to super class instance variable
        setAdmin(true); // set admin access to super class instance variable
    }

    //***************************************************************************//

    //********************************Class methods******************************//

    /** method userCreated()
     * displays message that account has been created
     */
    @Override
    public void userCreated() {
        JOptionPane.showMessageDialog(null,
                "Manager account has been created"); // display message
    }

} // close UserManager class


