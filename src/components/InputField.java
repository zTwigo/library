/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package components;

import com.formdev.flatlaf.FlatClientProperties;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.geom.RoundRectangle2D;

/**
 *
 * @author Ernesto
 */
public class InputField extends javax.swing.JTextField {
    /**
     * The placeholder will be displayed when the user hasn't typed in the field yet.
     */
    private String placeholder;
    
    private InputField(){
        setVisible(true);
        setOpaque(false);
        setBorder(new RoundedBorder(20));
        putClientProperty(FlatClientProperties.STYLE, 
                "minimumWidth:400"
        );
        putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Find your book");
    }
    
    public InputField(String placeholder){
        this();
        this.placeholder = placeholder;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
         Graphics2D g2D = (Graphics2D) g;
         GradientPaint gP = new GradientPaint(0, 0, new Color(38, 38, 38), getHeight(), 0, new Color(46, 46, 46));
        
         // Create a RoundRectangle2D with border radius
         RoundRectangle2D roundRectangle2D = new RoundRectangle2D.Double(0, 0, 100, 100, 40, 40);
         g2D.setPaint(gP);
         g2D.fill(roundRectangle2D);
         
         super.paintComponent(g);
    }
}
