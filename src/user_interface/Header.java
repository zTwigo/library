/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package user_interface;

import com.formdev.flatlaf.FlatClientProperties;
import components.InputField;
import components.SearchBar;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Ernesto Gabriele Mattei
 */
public class Header extends javax.swing.JPanel {
    
    public Header(){
        init();
    }
    
    private void init(){
         // Set the layout of the panel
        setLayout(new MigLayout("fill,gap 0", "[center]", "[center]"));
        add(new InputField(""));
    }
    
}
