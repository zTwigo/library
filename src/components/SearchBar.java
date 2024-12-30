/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;

/**
 *
 * @author Ernesto
 */
public class SearchBar extends javax.swing.JTextField{

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2D = (Graphics2D) g.create();
        
        setOpaque(false);
        g2D.setColor(Color.red);
        g2D.fill(new RoundRectangle2D.Double(0, 0, 100, 100, 10, 10));
        g2D.dispose();
        
        super.paintComponents(g);
    }
    
}
