/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import javax.swing.border.Border;

/**
 *
 * @author Ernesto
 */
public class RoundedBorder implements Border{
    
    /**
     * The radius of the arch.
     */
    private int radius;

    /**
     * Full constructor which takes in all the parameter needed
     * in order to build a full object.
     * @param radius 
     */
    public RoundedBorder(int radius) {
        this.radius = radius;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2D = (Graphics2D) g.create();
        
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2D.setColor(Color.GRAY);
        g2D.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        
        g2D.dispose();
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(radius + 1, radius + 1, radius +2, radius);
    }

    @Override
    public boolean isBorderOpaque() {
        return true;
    }
    
}
