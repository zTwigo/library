/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import xml.XMLElement;

/**
 *
 * @author Ernesto Gabriele Mattei
 */

public class User {
    /**
     * Username of the user.
     */
    @XMLElement(name = "username")
    private String username;
    
    /**
     * Password of the user.
     */
    private String password;
    
    /**
     * Gender of the user, which is a boolean variable and its two values means:
     * - true, male;
     * - false, female;
     */
    @XMLElement(name = "gender")
    private boolean gender;
    
    /**
     * First name of the user.
     */
    @XMLElement(name = "firstName")
    private String firstName;
    
    /**
     * Second name of the user.
     */
    @XMLElement(name = "secondName")
    private String secondName;
    
    /**
     * Default constructor.
     */
    public User(){
        
    }

    /**
     * Full constructor which takes in all the parameters needed in order to
     * build a complete User object.
     * @param username
     * @param password
     * @param gender
     * @param firstName
     * @param secondName
     */
    public User(String username, String password, boolean gender, String firstName, String secondName) {
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.firstName = firstName;
        this.secondName = secondName;
    }
    
    /**
     * Get the username of the user.
     * @return username
     */
    public String getUsername(){
        return username;
    }
    
    /**
     * Get the password of the user.
     * @return password
     */
    public String getPassword(){
        return password;
    }
    
    /**
     * Get the gender of the user. A small reminder that:
     * - true, male;
     * - false, female;
     * @return gender
     */
    public boolean getGender(){
        return gender;
    }
    
    /**
     * Get the first name of the user.
     * @return first name
     */
    public String getFirstName(){
        return firstName;
    }
    
    /**
     * Get the second name of the user.
     * @return second name
     */
    public String getSecondName(){
        return secondName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    @Override
    public String toString() {
        return "User{" + "username=" + username + ", password=" + password + ", gender=" + gender + ", firstName=" + firstName + ", secondName=" + secondName + '}';
    }
    
}
