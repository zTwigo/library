/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package library;

import xml.JAXB;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.TransformerException;

/**
 *
 * @author Ernesto
 */
public class Library {
    /**
     * Data structure in which all the books will be stored, obviously making the program
     * more fast since everything is read from the ram rather than the rom and also making
     * the code more readable.
     */
    private ArrayList<Book> books = new ArrayList<>();
    
    /**
     * Data structure which will permit to make key-value pairs in order to
     * make the research of a specific book more fast. Since we need a unique identifier
     * in order to make this happen the ISBN will be used as the key.
     */
    private HashMap<String, Book> booksMap = new HashMap<>();
    
    /**
     * Instance of the class which will handle everything that concerns saving, updating or
     * deleting data in the XML file.
     */
    private JAXB xml = new JAXB("./saves/books.xml");
    
    /**
     * Default constructor with 0 parameters.
     */
    public Library(){
        init();
    }
    
    public Library(ArrayList<Book> books){
        this.books = books;
    }
    
    /**
     * Alternative constructor which takes in as a parameter a list of books. Two
     * options are offered from this method depending on a flag given as a parameter:
     * - true, all the books which were stored in the file will be completely erased and
     * only the new ones will be saved;
     * - false, all the books, the ones which were in the file before and the ones which needs
     * to be added, will be in the same file.
     * Note: in every case given a control will be performed since two books could have the same ISBN
     * which is not possible since an identifier is unique. 
     * @param books
     * @param overwrite 
     */
    public Library(ArrayList<Book> books, boolean overwrite){
        // Check over the flag
        if(overwrite){
            try {
                // Erase all the content which could cause an Exception due to transforming options
                xml.eraseDocument();
                
                // Find the duplicates and then remove them
                deleteDuplicatedBooks(books);
                
                // Add the content to the file
                
            } catch (TransformerException ex) {
                Logger.getLogger(Library.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * This method will initialize the library, meaning that the books will be read from
     * the file and then saved in both the list and the hashmap.
     */
    private void init(){
        // Get the books from the XML file
        
        
        // Loop through the list and create a key-value pair for each book
        for (Book book : books) {
            booksMap.put(book.getISBN(), book);
        }
    }
    
    /**
     * Return a specific book at a certain index given as
     * parameter.
     * @param index
     * @return book
     */
    public Book getBook(int index){
        // Return value
        Book book = null;
        
        // Check over the index
        if(index > 0 && index < books.size()) book = books.get(index);
        
        // Return the book
        return book;
    }
    
    /**
     * Get the size, in term of books, of the current library object.
     * @return integer
     */
    public int getSize(){
        return books.size();
    }
    
    /**
     * Remove a specific book at a certain index.
     * @param index
     */
    public void removeBook(int index){
        // Check over the index
        if(index > 0 && index < books.size()) books.remove(index);
    }
    
    public void addBook(Book book){
        books.add(book);
    }
    
    /**
     * This method will find duplicates in this case books. All of this in terms of ISBNs
     * since a book could have the same title as another one but different author, indeed
     * the unique identifier is the ISBN.
     * There's no need to return anything since the parameter books is a pointer.
     * @param library
     */
    private void deleteDuplicatedBooks(ArrayList<Book> books){
        // Within this approach a HashSet will be used
        HashSet<String> set = new HashSet<>();
        
        // Get the length
        int length = books.size();
        
        // Loop through the list
        for (int i = 0; i < length; i++) {
            // If the ISBN (In this case a String) is not in the set yet that means that it
            // can be added to the set, otherwise the book will be removed from the list
            if(!set.add(books.get(i).getISBN())){
                books.remove(i);
                length--;
            }
        }
    }
}
