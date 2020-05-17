//*******************************************************************************//
//* Author:     1816477                                                         *//
//* Assignment: Element 011: Hotel Management System                            *//
//* Date:       10/05/20                                                        *//
//*                                                                             *//
//  Class Name: UserCustomer (Subclass of User)                                 *//
//* Class Description: Used to create a customer user account                   *//
//*                                                                             *//
//*******************************************************************************//

import javax.swing.*;

public class UserCustomer extends User {

    //****************************Class constructors*****************************//

    /** constructor
     * creates a new instance of a customer user
     * @param username: {String} customer username
     * @param password: {String} customer password
     */
    public UserCustomer(String username, String password) {
        setUsername(username); // set username to super class instance variable
        setPassword(password); // set password to super class instance variable
    }

    //***************************************************************************//

    //********************************Class methods******************************//

    /** method userCreated()
     * displays message that account has been created
     */
    @Override
    public void userCreated() {
        JOptionPane.showMessageDialog(null,
                "Customer account has been created"); // display message
    }

} // close UserCustomer class



