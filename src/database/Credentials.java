/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;


/**
 *
 * @author Ernesto Gabriele Mattei
 */
public class Credentials {
    /**
     * Username of the user.
     */
    private String username;
    
    /**
     * Password of the user.
     */
    private String password;

    public Credentials(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    private void cryptPassword(){
        
    }
}
