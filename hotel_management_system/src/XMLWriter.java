//*******************************************************************************//
//* Author:     1816477                                                         *//
//* Assignment: Element 011: Hotel Management System                            *//
//* Date:       10/05/20                                                        *//
//*                                                                             *//
//  Class Name: XMLReader                                                       *//
//* Class Description: Used to create a new file & add data to it               *//
//*                                                                             *//
//*******************************************************************************//

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public abstract class XMLWriter {

    //*************************Class instance variables**************************//

    private DocumentBuilderFactory factory; // builder factory
    private DocumentBuilder builder; // document builder
    private Document xmlFile; // the document of data to write to xml
    private TransformerFactory transformerFactory; // transformer factory
    private Transformer transformer; // transformer
    private DOMSource source;
    private StreamResult result;

    /** method buildXML()
     * builds a document to be written to
     * @returns:        {Document} returns document of data read
     */
    public Document buildXML() {

        // create new document
        try {
            factory = DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();
            xmlFile = builder.newDocument();
        } catch(ParserConfigurationException e) {
            e.printStackTrace();
        }
        return xmlFile; // return document ready to be written to
    }

    /** method writeXML()
     * writes data to a document and creates new file
     * @param fileName: {String} file name to be created
     * @param xmlData:  {Document} file name to create
     */
    public void writeXML(String fileName, Document xmlData) {

        // add data provided to new file
        try {
            transformerFactory = TransformerFactory.newInstance();
            transformer = transformerFactory.newTransformer();
            source = new DOMSource(xmlData);
            result = new StreamResult(new File(fileName));
            transformer.transform(source, result);
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }

    /** method updateXML()
     * overwrites/updates XML file with new data
     * @param currentXMLData: {Document} updated data ready for adding
     * @param filename:       {String} file name to update
     */
    public void updateXML(Document currentXMLData, String filename) {

        // update file provided with data provided
        try {
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();
            DOMSource domSource = new DOMSource(currentXMLData);
            StreamResult streamResult = new StreamResult(new File(filename));
            transformer.transform(domSource, streamResult);
            new DOMSource(currentXMLData);
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }

} // close XMLWriter class