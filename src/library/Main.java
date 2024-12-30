/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package library;

import database.Database;
import database.User;
import xml.JAXB;
import java.util.ArrayList;
import java.util.function.Function;
import javax.xml.transform.TransformerException;
import manager.FileManager;
import test.Breed;
import test.Cat;
import manager.UtilManager;
import xml.XMLElement;

/**
 *
 * @author Ernesto
 */
public class Main {

    /**
     * @param args the command line arguments
     * @throws javax.xml.transform.TransformerException
     */
    public static void main(String[] args) throws TransformerException, Exception {
         JAXB jaxb = new JAXB("roaming/test/test.xml");
         jaxb.parseDocument();
         ArrayList<User> users = new ArrayList<>();
         jaxb.parseListOfElements(users);
         for (User user : users) {
             System.out.println(user);
        }
    }
    
}
