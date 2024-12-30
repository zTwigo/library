/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package library;

/**
 *
 * @author Ernesto Gabriele Mattei
 * @version 1.0
 */
public class Book {
    /**
     * Unique identifier which will contain hyphens and numbers. This information
     * should be stored as a string since it's a identifier and not a number: no
     * arithmetic operation should be performed with ISBNs.
     */
    private String ISBN;
    
    /**
     * Title of the book, this could be not unique.
     */
    private String title;
    
    /**
     * Author of the book who could obviously have written more 
     * than one book or more than one edition of it.
     */
    private String author;
    
    /**
     * Exact year of publication of this book.
     */
    private int year;
    
    /**
     * Publisher's book.
     */
    private String publisher;
    
    /**
     * Default constructor.
     */
    public Book(){
        
    }

    /**
     * Full constructor which takes in all the parameters needed in order to
     * build a full book object.
     * @param ISBN
     * @param title
     * @param author
     * @param year
     * @param publisher 
     */
    public Book(String ISBN, String title, String author, int year, String publisher) {
        this.ISBN = ISBN;
        this.title = title;
        this.author = author;
        this.year = year;
        this.publisher = publisher;
    }
    
    /**
     * Sets the ISBN of the book.
     * @param ISBN
     */
    public void setISBN(String ISBN){
        this.ISBN = ISBN;
    }
    
    /**
     * Sets the title of the book.
     * @param title
     */
    public void setTitle(String title){
        this.title = title;
    }
    /**
     * Sets the ISBN of the book.
     * @param author
     */
    public void setAuthor(String author){
        this.author = author;
    }
    /**
     * Sets the ISBN of the book.
     * @param year
     */
    public void setYear(int year){
        this.year = year;
    }
    /**
     * Sets the ISBN of the book.
     * @param publisher
     */
    public void setPublisher(String publisher){
        this.publisher = publisher;
    }

    public String getISBN() {
        return ISBN;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getYear() {
        return year;
    }

    public String getPublisher() {
        return publisher;
    }

    /**
     * Returns the internal state of the object such as
     * all its attributes.
     * @return string
     */
    @Override
    public String toString() {
        return "Book{" + "ISBN=" + ISBN + ", title=" + title + ", author=" + author + ", year=" + year + ", publisher=" + publisher + '}';
    }
    
}
