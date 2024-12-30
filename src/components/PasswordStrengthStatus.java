/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package components;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.ui.FlatUIUtils;
import com.formdev.flatlaf.util.UIScale;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import net.miginfocom.swing.MigLayout;
import manager.UtilManager;
/**
 *
 * @author Ernesto
 */
public class PasswordStrengthStatus extends JPanel{
    
    private JPasswordField passwordField;
    private DocumentListener documentListener;
    private JLabel label;
    private int type;
    
    public PasswordStrengthStatus(){
        init();
    }
    
    private void init(){
        putClientProperty(FlatClientProperties.STYLE, "background:null");
        setLayout(new MigLayout("fill,insets 0", "3[100,fill,grow0][]","fill,grow,0"));
        label = new JLabel("none");
        label.setVisible(false);
        add(new LabelStatus());
        add(label);
    }
    
    private Color getColor(int type){
        return switch (type) {
            case 1 -> Color.decode("#FF4D4D");
            case 2 -> Color.decode("#FFB04D");
            default -> Color.decode("#58C359");
        };
    }
    
    private void checkPassword(String password){
        this.type =  password.isEmpty()? 0: UtilManager.checkPasswordStrength(password);
        if(type == 0){
            label.setText("none");
            label.setVisible(false);
        } else {
            label.setVisible(true);
            switch (type) {
                case 1 -> label.setText("Too weak");
                case 2 -> label.setText("Medium");
                default -> label.setText("Strong");
            }
            label.setForeground(getColor(type));
        }
        repaint();
    }
    
    public void initPasswordField(JPasswordField txt){
        if(documentListener==null){
            documentListener = new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    checkPassword(String.valueOf(txt.getPassword()));
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    checkPassword(String.valueOf(txt.getPassword()));
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    checkPassword(String.valueOf(txt.getPassword()));
                }
            };
        }
        if(passwordField != null) {
            passwordField.getDocument().removeDocumentListener(documentListener);
        }
        txt.getDocument().addDocumentListener(documentListener);
        passwordField = txt;
    }
    
    private class LabelStatus extends JLabel {

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int width = getWidth();
            int height = getHeight();
            int size  = (int)(height*0.3f);
            Graphics2D g2D = (Graphics2D) g.create();
            FlatUIUtils.setRenderingHints(g2D);
            int gap = UIScale.scale(5);
            int w = (width-gap*2)/3;
            int y = (height-size)/2;
            Color disableColor = Color.decode(FlatLaf.isLafDark()?"#404040":"CECECE");
            if(type >= 1){
                g2D.setColor(getColor(1));
            } else {
                g2D.setColor(disableColor);
            }
            FlatUIUtils.paintComponentBackground(g2D, 0, y, w, size, 0, 999);
            if(type >= 2){
                g2D.setColor(getColor(2));
            } else {
                g2D.setColor(disableColor);
            }
            FlatUIUtils.paintComponentBackground(g2D, w + gap, y, w, size, 0, 999);
            
            if(type >= 3){
                g2D.setColor(getColor(3));
            } else {
                g2D.setColor(disableColor);
            }
            FlatUIUtils.paintComponentBackground(g2D, (w + gap)*2, y, w, size, 0, 999);
            g2D.dispose();
        }
        
    }
}
