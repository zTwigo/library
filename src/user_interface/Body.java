/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package user_interface;

import com.formdev.flatlaf.FlatClientProperties;
import components.InputField;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Ernesto
 */
public class Body extends javax.swing.JPanel {
    
    private int oldResizedWidth = 0;
    
    public Body(){
        init();
    }
    
    private void init(){
        setBorder(BorderFactory.createLineBorder(Color.BLUE));
         // Set the layout of the panel
        setLayout(new GridLayout(0,1));
        putClientProperty(FlatClientProperties.STYLE, 
                "background:@testColor"
        );
        addComponentListener(new ComponentAdapter(){
            @Override
            public void componentResized(ComponentEvent e) {
                int width = getWidth();
                if(oldResizedWidth != width){
                    int cardWidth = 100;
                    int columns = Math.max(1, width / cardWidth);
                    ((GridLayout) getLayout()).setColumns(columns);
                    oldResizedWidth = width;
                    revalidate();
                    System.out.println("fatoo");
                    
                }
            }
        });
        add(new BookCard(),"wrap");
        add(new BookCard(),"wrap");
        add(new BookCard());
        add(new BookCard());
        add(new BookCard());
        add(new BookCard());
        add(new BookCard());
        add(new BookCard());
    }
}
