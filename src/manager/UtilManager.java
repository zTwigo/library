/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manager;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Ernesto Gabriele Mattei
 */
public class UtilManager {
    
    private JFrame frame;
    
    public static int checkPasswordStrength(String password){
        int score = 0;
        
        if(password.length()>=0){
            score++;
        }
        
        boolean hasUpperCase = !password.equals(password.toLowerCase());
        if(hasUpperCase){
            score++;
        }
        
        boolean hasLowerCase = !password.equals(password.toUpperCase());
        if(hasLowerCase){
            score++;
        }
        boolean hasDigit = password.matches(".*\\d.*");
        if(hasDigit){
            score++;
        }
        
        boolean hasSpecialChars = !password.matches("[A-Za-z0-9]*");
        if(hasSpecialChars){
            score++;
        }
        
        if(score < 3) {
            return 1;
        } else if(score < 5){
            return 2;
        }
        else {
            return 3;
        }
    }
    
    public static String capitalizeFirstLetter(String string){
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }
    
    /**
     * This method will cast the string given as a parameter into
     * a primitive type, through the its Wrapper class and return it
     * as an object.
     * 
     * @param string
     * @param clazz
     * @return Object
     */
    public static Object castElement(String string, Class clazz){
        switch(clazz.getTypeName()){
            case "int" -> {
                return Integer.valueOf(string);
            }
            default -> {
                return string;
            }
        }
    }
    
    /**
     * This method checks wether or not the object
     * belongs to a primitive type of variable.
     * 
     * @param object
     * @return boolean
     */
    public static boolean isPrimitive(Object object){
        return object instanceof Byte ||
                object instanceof Short ||
                object instanceof Integer ||
                object instanceof Long ||
                object instanceof Float ||
                object instanceof Double ||
                object instanceof Boolean ||
                object instanceof Character ||
                object instanceof String;
    }
    
    /**
     * This method controls wether the object that is going to be added to the
     * file has integrity, which means:
     * - it has not to be pointing to null;
     * - it should have at least one property or field.
     * 
     * @param object
     * @throws java.lang.Exception
     */
    public static void checkObjectIntegrity(Object object) throws Exception{
        // Check if the instance of the object is not null
        if(object == null) throw new Exception("The object given as a parameter is null.");
        
        // Utilize reflection in order to analyze the object
        
        // This control will prevent anyone to insert objects with no properties
        if(object.getClass().getDeclaredFields().length == 0) throw new Exception ("The object given as a parameter contains no properties.");
    }
    
    /**
     * 
     * @param simpleClassName
     * @return
     */
    public static Object createDinamicClass(String simpleClassName){
        // Scan the entire classpath without restricting to specific packages
        try(ScanResult scanResult = new ClassGraph()
                .enableClassInfo()
                .scan())
        {
            return scanResult.getAllClasses().
                    stream()
                    .filter(classInfo -> classInfo.getSimpleName().equals(simpleClassName))
                    .findFirst()
                    .map(classInfo -> {
                        try {
                            // Load the class using the full class name
                            Class<?> clazz = Class.forName(classInfo.getName());
                            
                            // Create an instance using the default constructor
                            return clazz.getDeclaredConstructor().newInstance();
                        } catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
                            return null;
                        }
                    })
                    .orElse(null);
        }
    }
    
    /**
     * This method will add an on change event listener to the text field
     * given as a parameter through the DocumentListener class.
     * 
     * Additionally the consumer parameter let the caller implement a
     * function that is going to be called whenever the document listener
     * gets triggered through a lambda function.
     * 
     * @param textField
     * @param consumer 
     */
    public static void addOnChangeListener(JTextField textField, Consumer<JTextField> consumer){
        // Add the event listener
        textField.getDocument().addDocumentListener(new DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
                consumer.accept(textField);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                consumer.accept(textField);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                consumer.accept(textField);
            }
            
        });
    }
   
}
