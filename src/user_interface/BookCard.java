/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package user_interface;

import com.formdev.flatlaf.FlatClientProperties;
import java.awt.Color;
import java.awt.Dimension;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Ernesto
 */
public class BookCard extends javax.swing.JPanel{
    
    public BookCard(){
        init();
    }

    private void init() {
        // Set the layout of the panel
        setSize(new Dimension(400, 600));
        setBackground(Color.red);
        setLayout(new MigLayout("fill,insets 60", "[center]", "[center]"));
        putClientProperty(FlatClientProperties.STYLE, 
                "arc:20"
        );
    }
    
}
