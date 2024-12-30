/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test;

import xml.XMLElement;

/**
 *
 * @author Ernesto
 */
public class Breed {
    @XMLElement(name = "origin")
    private String origin;
    
    @XMLElement(name = "year")
    private int year;

    public Breed() {
    }
    
    public Breed(String origin, int year){
        this.origin = origin;
        this.year = year;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Breed{" + "origin=" + origin + ", year=" + year + '}';
    }
    
    
}
