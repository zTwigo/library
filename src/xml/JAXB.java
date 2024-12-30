/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package xml;

import java.util.List;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.OutputKeys;
import library.Book;
import manager.FileManager;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import manager.UtilManager;

/**
 * This particular library let the user completely manipulate an XML file by editing everything within.
 * @author Ernesto Gabriele Mattei
 * @version 1.0
 */
public final class JAXB {
    /**
     * Path of the file in which data will either added, updated, modified or deleted.
     * Note that this particular field won't be in any constructor since its necessary to
     * parse the document that could throw an Exception if the file doesn't contain a root yet.
     * This will prevent the object to stop the program at the very beginning of its declaration and
     * instantiation.
     */
    private String path;
    
    private FileManager fileManager = new FileManager();
    
    /**
     * Document which is crucial in order to make any modify to the XML file. This will be used
     * for creating the root and the corresponding nodes that will be appended.
     * 
     * Note: after setting the filepath such as during the instantiation of the object with the correct
     * constructor or with the setFilePath() method, before performing any action which involves
     * the modification of the XML file the parseDocument() must be called one time.
     */
    private Document document;
    
    /**
     * Default constructor which takes in 0 parameters.
     */
    public JAXB(){
        
    }

    public JAXB(String path){
        this.path = path;
    }
    
    /**
     * Note that this method will completely erase what was in the file
     * before in order to create a new root to append children to.
     * @param rootName
     * @throws IOException 
     */
    public void createRoot(String rootName) throws IOException{
        // Clear the file
        FileManager.clearFile(path);
        
        System.out.println(rootName);
        
        // Write the root as text
        try (FileWriter writer = new FileWriter(path)) {
            writer.write("<" + rootName + "></" + rootName + ">");
        }
    }
    
    /**
     * This method will append a new child to the root.
     * 
     * @param object
     * @throws java.lang.Exception
     */
    public void appendChildToRoot(Object object) throws Exception{
        // Check object's integrity (this could cause exceptions)
        UtilManager.checkObjectIntegrity(object);
        
        // Get the fields out of the object
        Field[] objectFields = object.getClass().getDeclaredFields();
        
        // Create an HashSet in order to store every field's name and value
        ArrayList<String> fieldValues = new ArrayList<>();
        ArrayList<String> fieldNames = new ArrayList<>();
        
        // Loop through the list of fields and gather all their names into the set
        for(Field field : objectFields){
            field.setAccessible(true);
            fieldValues.add(field.get(object).toString());
            fieldNames.add(field.getName());
        }
        
        // Create a new element
        Element newElement = document.createElement(object.getClass().getSimpleName());
        
        // Loop through each array in order to the name of the fields and their values
        // and create the corresponding nodes
        for (int i = 0; i < objectFields.length; i++) {
            Element element = document.createElement(fieldNames.get(i));
            element.appendChild(document.createTextNode(fieldValues.get(i)));
            newElement.appendChild(element);
        }
        
         // Remove all unnecessary white spaces
         removeWhiteSpaces(document.getDocumentElement());
        
        document.getDocumentElement().appendChild(newElement);
        
        // Apply the changes to the file
        saveToXML();
    }
     
    /**
     * Create a single element into the file which will handle all the data, which means
     * that this will be the root itself and no other object like the one given as a parameter
     * of this function will be found in the XML file.
     * This method will completely erase the content which was stored before in the file.
     * 
     * Several notes of this method will be now given:
     * - a primitive type of variable cannot be passed as a parameter to this function:
     * only classes with declared fields are meant to be transformed in an XML file;
     * - the root's name will be the name of the class to lower case in case the class
     * doesn't have a @XMLRootElement annotation;
     * - the declared fields within a class will be considered part of the transformation
     * into the XML file only if a @XMLElement annotation is written above their declaration:
     * this both provides the choice of deciding which fields are going to be child nodes of the root
     * and prevents from storing into the file useless information.
     * 
     * @param object
     * @throws java.lang.Exception
     */
    public void createSingleElement(Object object) throws Exception{
        // Check if the object given as a paremeter is a primitive
        if(UtilManager.isPrimitive(object)) throw new Exception("A primitive type of variable can't be transformed.");
        
        // Check object's integrity (this could cause exceptions)
        UtilManager.checkObjectIntegrity(object);
        
        // Get the fields out of the object
        Field[] objectFields = object.getClass().getDeclaredFields();
        
        // Delete everything
        FileManager.clearFile(path);
        
        createRoot(object.getClass().getSimpleName().toLowerCase());
        
        // Create a new element: in this case it's called root since it's the only present
        Element root = document.getDocumentElement();
        
        // Loop through each array in order to the name of the fields and their values
        // and create the corresponding nodes
        // Loop through the list of fields and gather all their names into the set
        for(Field field : objectFields){
            field.setAccessible(true);
            if(addElement(field, object) != null) root.appendChild(addElement(field, object));
        }
        
        removeWhiteSpaces(root);
        
        // Apply the changes to the file
        saveToXML();
    }
    
    /**
     * Parse a single element from within the file into a new object given as
     * parameter of the procedure. This method works only if certain conditions
     * have been met.
     * 
     * The conditions will be listed and explained down below:
     * - the object has to contain at least one declared field;
     * - every tag name which contains either a String or a list of child nodes
     * must have its name set to the name of the corresponding declared field.
     * In the case of primitive variables it is enough if the declared field value corresponds
     * to the <fieldName>...</fieldName>. In the case of classes (excluding java.lang.String which
     * in this implementation is considered as a primitive type of variable even if it's not) the name
     * of the declared field MUST be the same as the simple name of the class, if the declared field
     * is instance of the class Cat the <fieldName>...</fieldName> MUST be either cat or Cat.
     * 
     * Note: this method will extract all of the root's child nodes and consider them
     * as the declared fields of the object given as a parameter no matter what the
     * name of the root is.
     * 
     * @param object
     * @throws java.lang.Exception
     */
    public void parseSingleElement(Object object) throws Exception{
        // Check if the object given as a paremeter is a primitive
        if(UtilManager.isPrimitive(object)) throw new Exception("A primitive type of variable can't be parsed.");
        
        // Check object's integrity (this could throw exceptions)
        UtilManager.checkObjectIntegrity(object);
        
        // Get the child nodes of the root element
        NodeList nodeList = document.getDocumentElement().getChildNodes();
        
        // Get the fields of the class which the object belongs to
        Field[] objectFields = object.getClass().getDeclaredFields();
        
        foo(nodeList, objectFields, object);
    }
    
    /**
     *
     * @param <T>
     * @param list 
     * @throws java.lang.Exception
     */
    public <T> void parseListOfElements(List<T> list) throws Exception{
        // Check object's integrity (this could throw exceptions)
        UtilManager.checkObjectIntegrity(list);
        
        // Get the child nodes of the root element
        NodeList nodeList = document.getDocumentElement().getChildNodes();
        
        // Loop through the list
        for (int i = 0; i < nodeList.getLength(); i++) {
            // Get the node item
            Node node = nodeList.item(i);
            
            if(node.getNodeType() == Node.ELEMENT_NODE){
                // Get the actual element
                Element element = (Element) node;
                
                // Get the object and its simple name
                Object dinamicObject = UtilManager.createDinamicClass(UtilManager.capitalizeFirstLetter(element.getTagName()));

                // Get the child nodes and the fields
                NodeList childNodes = element.getChildNodes();
                Field[] fields = dinamicObject.getClass().getDeclaredFields();
                
                foo(childNodes, fields, dinamicObject);
                
                list.add((T) dinamicObject);
            }
        }
    }
    
    private void foo(NodeList nodeList, Field[] objectFields, Object object) throws Exception{
        // Loop through the list in order to get each element
        for (int i = 0; i < nodeList.getLength(); i++) {
            // Get the node
            Node node = nodeList.item(i);
            
            // Check its integrity
            if(node.getNodeType() == Node.ELEMENT_NODE){
                Element element = (Element) node;
                
                // Find the field which has to be set
                Field field = findField(objectFields, element.getTagName());
                
                // Check if the element is primitive
                if(node.getChildNodes().getLength() <= 1){
                    setAttribute(field, element.getTextContent(), object);
                } else {
                    // Get the object and its simple name
                    Object dinamicObject = UtilManager.createDinamicClass(UtilManager.capitalizeFirstLetter(element.getTagName()));
                    
                    // Get the child nodes and the fields
                    NodeList childNodes = element.getChildNodes();
                    Field[] fields = dinamicObject.getClass().getDeclaredFields();
                    
                    foo(childNodes, fields, dinamicObject);
                    
                    setAttribute(field, dinamicObject, object);
                }
            }
        }
    }
    
    private Field findField(Field[] objectFields, String fieldName){
        for (Field objectField : objectFields) {
            if(objectField.getName().equals(fieldName)) return objectField;
        }
        return null;
    }
    
    /**
     * This method will set the value of the specific declared field
     * of the object that is going to be parsed.
     * 
     * Note: this could cause an exception due to the missing
     * setter method.
     * 
     * @param setterMethods
     * @param fieldName
     * @param fieldValue
     * @param object 
     */
    private void setAttribute(Field field, Object fieldValue, Object object) throws Exception{
        // Make the field accessible
        field.setAccessible(true);
        
         if(UtilManager.isPrimitive(field.get(object))){
             field.set(object, UtilManager.castElement((String) fieldValue, field.getType()));
         } else {
             field.set(object,fieldValue);
         }
        
        // If the setter method was nowhere to be found an exception will be thrown
//        if(!isSetterMethodPresent){
//            throw new Exception("No setter method was found for the " + fieldName + " field into the " + object.getClass().getSimpleName() + " object");
//        }
    }
    
    private Element addElement(Field field, Object object) throws IllegalArgumentException, IllegalAccessException{
        if(!checkAnnotation(field)) return null;
        else if(isPrimitive(field, object)) {
            return buildElement(field, object);
        }
        else {
            Field[] fields = field.get(object).getClass().getDeclaredFields();
            
            Element element = document.createElement(field.get(object).getClass().getSimpleName().toLowerCase());
            
            for (Field field1 : fields) {
                field1.setAccessible(true);
                if(addElement(field1, field.get(object))  != null)element.appendChild(addElement(field1, field.get(object)));
            }

            return element;
        }
    }
    
    /**
     * Since the nodes that will be added and appended to the root
     * of the XML structure are only fields with the @XMLElement
     * annotation, a control must be performed.
     * 
     * @return boolean
     */
    private boolean checkAnnotation(Field field){
        return field.isAnnotationPresent(XMLElement.class);
    }
    
    /**
     * This method will build a brand new element that will appended
     * to either the root or a node of the document.
     * 
     * <field>value</field>
     * @param element
     * @param field 
     */
    private Element buildElement(Field field, Object object) throws IllegalArgumentException, IllegalAccessException{
        // Create the element
        Element element = document.createElement(field.getAnnotation(XMLElement.class).name());
        
        // Add the value
        element.appendChild(document.createTextNode(field.get(object).toString()));
        
        // Return the element
        return element;
    }
    
    public boolean isPrimitive(Field field, Object object) throws IllegalArgumentException, IllegalAccessException{
        field.setAccessible(true);
        return field.get(object) instanceof Byte ||
                field.get(object) instanceof Short ||
                field.get(object) instanceof Integer ||
                field.get(object) instanceof Long ||
                field.get(object) instanceof Float ||
                field.get(object) instanceof Double ||
                field.get(object) instanceof Boolean ||
                field.get(object) instanceof Character ||
                field.get(object) instanceof String;
    }
    
    
    
    /**
     * Private method in which the document (in this case an XML file) will be
     * parsed. This function could cause some exception due to input and parsing
     * procedures.
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public void parseDocument() throws ParserConfigurationException, SAXException, IOException{
        // Creates a new DocumentBuilderFactory
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        
        // Creates a new DocumentBuilder
        DocumentBuilder builder = factory.newDocumentBuilder();
        
        // Finally parse the document related to the specified path
        document = builder.parse(path);
    }
    
    /**
     * This method will set the filepath in which modifications will be performed.
     * 
     * Note: this will also trigger the parseDocument().
     * @param filePath
     */
    public void parseFile(String filePath){
        this.path = filePath;
        try {
            parseDocument();
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(JAXB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Save all the changes applied to the XML file in order to store all
     * the data.
     * @param doc 
     */
    private void saveToXML() throws TransformerConfigurationException, TransformerException {
        // Save the modified content back to the original file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        
        // Enable pretty printing or identation
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount" , "3");
        
        // Get the source of the document and its result
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(new File(path));
        transformer.transform(source, result);
    }
    
    /**
     * This method ensures that unnecessary text, like white spaces, are removed
     * from the element in order to make the document cleaner.
     * @param node
     */
    private void removeWhiteSpaces(Node node){
        // Get the first child of the list
        Node child = node.getFirstChild();
        
        // Loop through each node which in this case are represented by each book
        // If there are no child this loop will be skipped
        while(child != null){
            // Get the next node
            Node next = child.getNextSibling();
            
            // If the child appears to be only blank text then remove it
            if(child.getNodeType() == Node.TEXT_NODE && child.getNodeValue().trim().isEmpty()){
                node.removeChild(child);
            } else {
                // Recusirve approach
                removeWhiteSpaces(child);
            }
            child = next;
        }
    }
    
    /**
     * This method will completely delete the content of the file including every book stored within, leaving the root
     * element <books></books> the only text saved without nothing in it. This action could be fatal, indeed this could
     * be performed in two distinguished and precise situations:
     * - when creating a XML instance an option to overwrite the content of the file in which books are saved can be
     * performed and that's when this method comes in;
     * - when the user decide to delete all the books.
     * @throws javax.xml.transform.TransformerException
     */
    public void eraseDocument() throws TransformerException{
        // Get the root element from the documet and erase everything
        Element root = document.getDocumentElement();
        // While the root has child, remove the first child and continue the loop
        while(root.hasChildNodes()){
            root.removeChild(root.getFirstChild());
        }
        // Save changes to the XML file
        saveToXML();
    }
    
    
    
}
