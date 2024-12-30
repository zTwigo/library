/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Ernesto
 */
public class FileManager {
    /**
     * Creates a brand new folder into the file system.
     * @param folderPath
     */
    public static void createFolder(String folderPath){
        // Create a new File object containing the specific path
        File folder = new File(folderPath);
        
        // Try to create the directory
        folder.mkdir();
    }
    
    /**
     * Check if a specific folder path exist within the file system.
     * 
     * @param folderPath
     * @return boolean
     */
    public static boolean isFolderPresent(String folderPath){
        return new File(folderPath).exists();
    }
    
    public static void createRoamingFolder(){
        // Get the path
        String appDataPath = System.getenv("APPDATA");
        
        // Create the directory in which data will be stored
        String folderPath = appDataPath + File.separator + "hello";
        File appFolder = new File(folderPath);
        
        // Create the directory if it doesn't exist
        if(!appFolder.exists()) appFolder.mkdirs();
        
    }
    
    public static void createFile(String filePath) throws IOException{
        try ( // Creates a new FileWriter object
                FileWriter fileWriter = new FileWriter(new File(filePath))) {
            fileWriter.write("<root></root>"); 
        }
    }
    
    public static void renameFolder(String oldFolderPath, String newFolderPath){
        // Create a new File object containing the specific path
        File folder = new File(oldFolderPath);
        
        // Try to create the directory
        folder.renameTo(new File(newFolderPath));
    }
    
    /**
     * Completely erase the content of the file.
     * @param filePath
     * @throws java.io.IOException
     */
    public static void clearFile(String filePath) throws IOException{
        FileWriter writer = new FileWriter(filePath, false);
        writer.close();
    }
    
    /**
     * Note: this method works exclusively with this program and in-fact
     * there are no parameter in the function's decoration.
     * @return String
     */
    public static String checkFolderTree(){
        // Get the app data folder
        File appDataFolder = getAppData();
        
        // Get all the sub folders/files paths
        String[] subPaths = appDataFolder.list();
        
        // Loop through the list in order to find a folder which contains
        // .rm at the end of the string
        for(String appDataChild : subPaths){
            System.out.println(appDataChild);
            // Check if it's a folder first
            if(!new File(appDataChild).isFile() && appDataChild.contains(".rm")) {
                // The file path has been found so return it
                return appDataChild.replaceAll(".rm", "");
            }
        }
        
        // If nothing was found then return ""
        return "";
    }
    
    /**
     * This method will return a file object which will
     * handle the appData/Roaming folder in which this program
     * save all the user's data.
     * 
     * @return file
     */
    public static File getAppData(){
        return new File(System.getenv("APPDATA") + "/zelLibrary");
    }
}
