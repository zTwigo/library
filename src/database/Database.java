/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import xml.JAXB;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.xml.parsers.ParserConfigurationException;
import manager.FileManager;
import org.xml.sax.SAXException;

/**
 *
 * @author Ernesto
 */
public class Database {
    private JAXB xml;
    
    public Database() throws ParserConfigurationException, SAXException, IOException{
        xml = new JAXB();
    }
    
    public boolean getUsername(String username){
        // Briefily exit the function if no username was given
        if(username.equals("")) return false;
        
        // Create a path in order to make a check
        Path userFolderPath = Paths.get("roaming/" + username);
        
        // Check if the folder exists
        return Files.exists(userFolderPath) && Files.isDirectory(userFolderPath);
    }
    
    public boolean getPassword(String username, String password) throws Exception{
        // Briefily exit the function if no password or username was given
        if(username.equals("") || password.equals("")) return false;
        
        // Get the user object
        User user = new User();
        
        // Prepare the xml object and parse the element
        xml.parseFile("roaming/" + username + "/user.xml");
        xml.parseDocument();
        xml.parseSingleElement(user);
        
        
        // Get the password and check if they're equal
        return password.equals(user.getPassword());
    }
    
    /**
     * Creates a new user that will be stored into the "database": a folder will be created within the
     * roaming folder with the username of the user which will contain two specific files:
     * - user, which will contain everything that concerns the user such as credentials
     * and information;
     * - books, which will contain information regarding every book the user decided to
     * store into his library.
     * Each one of the files will be written using the XML format.
     * 
     * Note: this approach is not safe since anyone could either disassemble the jar file
     * or unzipping the .zip file (if it's given to the user), accessing the roaming folder and
     * reading sensitive data about a specific user. This library implementation's done only
     * for educational purposes.
     * 
     * @param user
     * @throws java.lang.Exception
     */
    public void createUser(User user) throws Exception{
        // Control over the user
        if(user == null) throw new Exception("Couldn't create the user, the object is null.");
        
        // Create its dedicated folder
        FileManager.createFolder("roaming/" + user.getUsername());
        
        // Create the files in which data will be stored
        FileManager.createFile("roaming/" + user.getUsername() + "/books.xml");
        FileManager.createFile("roaming/" + user.getUsername() + "/user.xml");
        
        // Write user's information into the dedicated file
        xml.parseFile("roaming/" + user.getUsername() + "/user.xml");
        xml.parseDocument();
        xml.createSingleElement(user);
    }
    
    /**
     * This method will rename the folder in which the user's data is stored
     * in order to remember that it has already logged in.
     * 
     * Note: again this approach is not secure and inefficient since anyone
     * that could get access to the project folder
     * 
     * @param username 
     */
    public void rememberUser(String username){
        FileManager.renameFolder("roaming/" + username, "roaming/" + username + ".rm");
    }
}
