/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package login;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import components.PasswordStrengthStatus;
import database.Database;
import database.User;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import manager.FormsManager;
import net.miginfocom.swing.MigLayout;
import org.xml.sax.SAXException;

/**
 *
 * @author Ernesto
 */
public class Register extends javax.swing.JPanel {

    /**
     * Database object which is essential for the registration by the user. This
     * will check and insert several things such as:
     * - checking wether the username was already used or not;
     * - inserting all the user data into the "database";
     */
    private final Database database;
    
    private JPanel mainPanel;
    
    /**
     * Creates new form Register
     * @throws javax.xml.parsers.ParserConfigurationException
     * @throws org.xml.sax.SAXException
     * @throws java.io.IOException
     */
    public Register() throws ParserConfigurationException, SAXException, IOException {
        this.database = new Database();
        initComponents();
        init();
    }
    

    private void init(){
        setLayout(new MigLayout("fill,insets 20","[center]","[center]"));
        
        instantiateComponents();
        
        mainPanel = new JPanel(new MigLayout("wrap,fillx,insets 35 45 30 45", "[fill,360]", "[]"));
        mainPanel.putClientProperty(FlatClientProperties.STYLE, "" +
                "arc:20;" +
                "[light]background:darken(@background,3%);" +
                "[dark]background:lighten(@background,3%)"
        );
        
        JLabel lbTitle = new JLabel("Welcome to our library application!");
        JLabel description = new JLabel("Join us to save books you have read. Sign up now and start saving your books!");
        lbTitle.putClientProperty(FlatClientProperties.STYLE, "" + 
                "font:bold +10"
        );
        description.putClientProperty(FlatClientProperties.STYLE, 
                "[light]foreground:lighten(@foreground,30%);" +
                "[dark]foreground:darken(@foreground,30%)"
        );
        passwordStrengthStatus.initPasswordField(txtPassword);
        mainPanel.add(lbTitle, "wrap");
        mainPanel.add(description, "wrap");
        mainPanel.add(new JLabel("Full Name"),"gapy 10");
        mainPanel.add(textFirstName, "split 2");
        mainPanel.add(textSecondName);
        mainPanel.add(createGenderPanel());
        mainPanel.add(new JSeparator(), "gapy 5 5");
        mainPanel.add(new JLabel("Username or email"));
        mainPanel.add(txtUsername);
        mainPanel.add(new JLabel("Password"), "gapy 8");
        mainPanel.add(txtPassword);
        mainPanel.add(passwordStrengthStatus, "gapy 0");
        mainPanel.add(new JLabel("Confirm password"), "gapy 8");
        mainPanel.add(txtConfirmPassword);
        mainPanel.add(cmdRegister, "gapy 20");
        mainPanel.add(createLoginLabel(), "gapy 10");    
        add(mainPanel); 
    }
    
    private Component createGenderPanel(){
        JPanel panel = new JPanel(new MigLayout("insets 0"));
        
        panel.putClientProperty(FlatClientProperties.STYLE, 
                "background:null"
         );
        jrMale = new JRadioButton("Male");
        jrFemale = new JRadioButton("Female");
        groupGender = new ButtonGroup();
        groupGender.add(jrMale);
        groupGender.add(jrFemale);
        jrMale.setSelected(true);
        panel.add(jrMale);
        panel.add(jrFemale);
        return panel;
    }
    
    private Component createLoginLabel(){
       JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        panel.putClientProperty(FlatClientProperties.STYLE, 
                "background:null"
         );
        
        textFirstName.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "First name");
        textSecondName.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Second name");
        txtUsername.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter your username");
        txtPassword.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter your password");
        txtConfirmPassword.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Re-enter your password");
        cmdRegister.putClientProperty(FlatClientProperties.STYLE, 
                "[light]background:darken(@background,10%);" +
                "[dark]background:lighten(@background,10%);" +
                "borderWidth:0;" +
                "focusWidth:0;" +
                "innerFocusWidth:0"
         );
        cmdRegister.addActionListener(e -> {
            if(areInputFieldsFilled()) try {
                performRegistration();
            } catch (Exception ex) {
                Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        txtPassword.putClientProperty(FlatClientProperties.STYLE, 
                "showRevealButton:true"
         );
        txtConfirmPassword.putClientProperty(FlatClientProperties.STYLE, 
                "showRevealButton:true"
         );
        JButton cmdLogin = new JButton("<html><a href=\"#\">Sign in</a></html>");
        cmdLogin.putClientProperty(FlatClientProperties.STYLE, 
                "border:3,3,3,3"
        );
        cmdLogin.setContentAreaFilled(false);
        cmdLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cmdLogin.addActionListener(e -> {
            FormsManager.getInstance().showForm(new Login());
        });
        JLabel label = new JLabel("Already have an account?");
        label.putClientProperty(FlatClientProperties.STYLE, 
                "[light]foreground:lighten(@foreground,30%);" +
                "[dark]foreground:darken(@foreground,30%)"
         );
        panel.add(label);
        panel.add(cmdLogin);
        return panel;
    }
    
    /**
     * This method will instantiate all the components which will be displayed on the form.
     */
    private void instantiateComponents(){
        textFirstName = new JTextField();
        textSecondName = new JTextField();
        txtUsername = new JTextField();
        txtPassword = new JPasswordField();
        txtConfirmPassword = new JPasswordField();
        cmdRegister = new JButton("Sign up");
        passwordStrengthStatus = new PasswordStrengthStatus();
    }
    
    /**
     * Perform a registration.
     */
    private void performRegistration() throws Exception{
        // Create a user object
        User user = createUser();
        
        // Store its information in the database
        database.createUser(user);
    }
    
    /**
     * Create a user object which will be then inserted into the "database".
     */
    private User createUser(){
        return new User(txtUsername.getText(), Arrays.toString(txtPassword.getPassword()),
        true,textFirstName.getText(),textSecondName.getText());
    }
    
    /**
     * This method will check if every input was filled with the data needed.
     * If the returned value is greater than zero means that at least one input
     * field was not filled with data.
     * @return int
     */
    private boolean areInputFieldsFilled(){
        // Return value
        boolean flag = true;
        
        // Check if the first name was inserted
        if(!checkFirstName()) {
            flag = false;
            displayErrorMessages(textFirstName);
        }
        
        // Check if the second name was inserted
        if(!checkSecondName()) {
            flag = false;
            displayErrorMessages(textSecondName);
        }
        
        // Check if the username was inserted and also if it already exist.
        if(txtUsername.equals("") || !database.getUsername(txtUsername.getText())) {
            flag = false;
            displayErrorMessages(txtUsername);
        }
        
        // Check if the password was inserted and also if the minimum length was right
        if(txtPassword.equals("") || !checkPassword()) {
            flag = false;
            displayErrorMessages(txtPassword);
        }
        
        // Return true which means that all the controls were passed withot any trouble
        return flag;
    }
    
    /**
     * This method will check over the first name. It should have at least 3 characters.
     */
    private boolean checkFirstName(){
        return textFirstName.getText().length() >= 3;
    }
    
    /**
     * This method will check over the second name. It should have at least 3 characters.
     */
    private boolean checkSecondName(){
        return textSecondName.getText().length() >= 3;
    }
    
    /**
     * Even though the user is suggested to create a strong password and password
     * strength status display is given, in this Library implementation, the user must only
     * insert at least 8 characters and can insert whatever string it likes.
     */
    private boolean checkPassword(){
        return txtPassword.getPassword().length >= 8;
    }
    
    /**
     * 
     * @param component 
     */
    private void displayErrorMessages(JComponent component){
        component.putClientProperty(FlatClientProperties.STYLE, 
                "borderColor:@borderColor"
        );
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Input field in which first name will be inserted.
     */
    private JTextField textFirstName;
    
    /**
     * Input field in which second name will be inserted.
     */
    private JTextField textSecondName;
    
    /**
     * Radio button which could be chosen as for gender information.
     */
    private JRadioButton jrMale;
    
    /**
     * Radio button which could be chosen as for gender information.
     */
    private JRadioButton jrFemale;
    
    /**
     * Input field in which username will be inserted.
     */
    private JTextField txtUsername;
    
    /**
     * Input field in which password (which is completely hidden unless you press the
     * reveal button) will be inserted.
     */
    private JPasswordField txtPassword;
    
    /**
     * Input field in which password have to be confirmed and so re-entered again.
     */
    private JPasswordField txtConfirmPassword;
    
    /**
     * Button group which will contain both male and female gender radio button.
     */
    private ButtonGroup groupGender;
    
    /**
     * Button useful in order to make the registration possible. Note this will cause
     * an event which will be caught and analyzed by a listener which will eventually
     * decide if everything was inserted correctly.
     */
    private JButton cmdRegister;
    
    /**
     * In order to make password safer an algorithm will be triggered when
     * the user is inserting the password to see if the password is secure or not.
     */
    private PasswordStrengthStatus passwordStrengthStatus;
    
     

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
