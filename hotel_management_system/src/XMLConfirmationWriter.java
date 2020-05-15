//*******************************************************************************//
//* Author:     1816477                                                         *//
//* Assignment: Element 011: Hotel Management System                            *//
//* Date:       10/05/20                                                        *//
//*                                                                             *//
//  Class Name: XMLConfirmationWriter (Subclass of XMLWriter)                   *//
//* Class Description: Used to create a new confirmation XML file               *//
//*                                                                             *//
//*******************************************************************************//

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLConfirmationWriter extends XMLWriter {

    //*************************Class instance variables**************************//

    private String fileName = "xml_files/confirmations/"; // file path
    private Document confirmationXML; // XML document
    private Element confirmationRoot; // root node
    private Element confirmationElement; // child node

    // confirmation XML file elements
    private Attr idAttr;
    private Element clientnameElement;
    private Element guestsElement;
    private Element startdateElement;
    private Element enddateElement;
    private Element apartmentnameElement;
    private Element bedroomsElement;
    private Element livingroomElement;
    private Element bathroomsElement;
    private Element cateringElement;
    private Element totalpriceElement;

    //***************************************************************************//

    //********************************Class methods******************************//

    /** method createConfirmationXML()
     * creates a booking XML file with provided booking elements
     * @param confirmationID:   {String} confirmationID used as attribute & filename
     * @param clientname:       {String} customer name
     * @param guests:           {String} no. of guests
     * @param startdate:        {String} date to check in
     * @param enddate:          {String} date to check out
     * @param apartmentname:    {String} name of apartment
     * @param bedrooms:         {String} no. of bedrooms
     * @param livingroom:       {String} seperate living room
     * @param bathrooms:        {String} no. of bathrooms
     * @param catering:         {String} catering requested
     * @param totalprice :      {String} total price of booking
     */
    public void createConfirmationXML(String confirmationID, String clientname, String guests, String startdate,
                                 String enddate, String apartmentname, String bedrooms, String livingroom,
                                 String bathrooms, String catering, String totalprice) {

        fileName += confirmationID + ".xml"; // set filename
        confirmationXML = buildXML(); // build XML document

        confirmationRoot = confirmationXML.createElement("confirmations"); // set confirmation node
        confirmationXML.appendChild(confirmationRoot); // add to XML document

        confirmationElement = confirmationXML.createElement("confirmation"); // set child node
        confirmationRoot.appendChild(confirmationElement); // add to XML document

        // create attribute (id) and assign under confirmation node
        idAttr = confirmationXML.createAttribute("id");
        idAttr.setValue(confirmationID);
        confirmationElement.setAttributeNode(idAttr);

        // create client name element and assign under confirmation node
        clientnameElement = confirmationXML.createElement("clientname");
        clientnameElement.appendChild(confirmationXML.createTextNode(clientname));
        confirmationElement.appendChild(clientnameElement);

        // create no. of guests element and assign under confirmation node
        guestsElement = confirmationXML.createElement("guests");
        guestsElement.appendChild(confirmationXML.createTextNode(guests));
        confirmationElement.appendChild(guestsElement);

        // create start date element and assign under confirmation node
        startdateElement = confirmationXML.createElement("startdate");
        startdateElement.appendChild(confirmationXML.createTextNode(startdate));
        confirmationElement.appendChild(startdateElement);

        // create end date element and assign under confirmation node
        enddateElement = confirmationXML.createElement("enddate");
        enddateElement.appendChild(confirmationXML.createTextNode(enddate));
        confirmationElement.appendChild(enddateElement);

        // create apartment name element and assign under confirmation node
        apartmentnameElement = confirmationXML.createElement("apartmentname");
        apartmentnameElement.appendChild(confirmationXML.createTextNode(apartmentname));
        confirmationElement.appendChild(apartmentnameElement);

        // create no. of bedrooms element and assign under confirmation node
        bedroomsElement = confirmationXML.createElement("bedrooms");
        bedroomsElement.appendChild(confirmationXML.createTextNode(bedrooms));
        confirmationElement.appendChild(bedroomsElement);

        // create living room element and assign under confirmation node
        livingroomElement = confirmationXML.createElement("livingroom");
        livingroomElement.appendChild(confirmationXML.createTextNode(livingroom));
        confirmationElement.appendChild(livingroomElement);

        // create no. of bathrooms element and assign under confirmation node
        bathroomsElement = confirmationXML.createElement("bathrooms");
        bathroomsElement.appendChild(confirmationXML.createTextNode(bathrooms));
        confirmationElement.appendChild(bathroomsElement);

        // create catering element and assign under confirmation node
        cateringElement = confirmationXML.createElement("catering");
        cateringElement.appendChild(confirmationXML.createTextNode(catering));
        confirmationElement.appendChild(cateringElement);

        // create total price element and assign under confirmation node
        totalpriceElement = confirmationXML.createElement("totalprice");
        totalpriceElement.appendChild(confirmationXML.createTextNode(totalprice));
        confirmationElement.appendChild(totalpriceElement);

        writeXML(fileName, confirmationXML); // create/write XML file

    }

} // close XMLConfirmationWriter class