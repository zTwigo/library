/*
Login package lol.
 */
package login;

import com.formdev.flatlaf.FlatClientProperties;
import database.Database;
import database.User;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.xml.parsers.ParserConfigurationException;
import manager.FileManager;
import manager.FormsManager;
import manager.UtilManager;
import net.miginfocom.swing.MigLayout;
import org.xml.sax.SAXException;
import raven.glasspanepopup.GlassPanePopup;
import user_interface.UserInterface;

/**
 *
 * @author Ernesto Gabriele Mattei
 */
public class Login extends javax.swing.JPanel {

    /**
     * Database object essential in order to check if the credentials exist or not.
     * Note that since its initialization could throw an Exception its instantiation is
     * moved down within the constructor.
     */
    private Database database;
    
    /**
     * Message component (JPanel) which will be useful in order to display error
     * messages on the screen. It replaces the function of a JOptionPane due to
     * its possibility of being customized.
     */
    private Message message;
    
    private JPanel mainPanel;
    
    /**
     * Creates new form Login
     */
    public Login() {
        try {
            database = new Database();
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        initComponents();
        init();
    }
    
    /**
     * This method will handle several things which are going to be added
     * to the panel, such as:
     * - instantiate every component which will be displayed on the screen;
     * - style every component which needs to be restyled;
     * - add the on change listeners to the input labels.
     */
    private void init(){
        // Set the layout of the panel
        setLayout(new MigLayout("fill,insets 20", "[center]", "[center]"));
        
        // Instantiate every component
        instantiateComponents();
        
        // Initialize a new panel in which every component will be added in
        mainPanel = new JPanel(new MigLayout("wrap,fillx,insets 35 45 30 45","fill,250:280"));
        
        // Style and add the components
        styleComponents();
        addComponents();
        
        // Add the main panel and all the event listeners
        add(mainPanel);
        addOnChangeListeners();
    }
    
    /**
     * This method will instantiate all the components that will
     * be either displayed instantly or later on.
     */
    private void instantiateComponents(){
        txtUsername = new JTextField();
        txtPassword = new JPasswordField();
        chRememberMe = new JCheckBox("Remember me");
        cmdLogin = new JButton("Login");
        message = new Message();      
    }
    
    /**
     * This method will style every component which needs to be restyled
     * for readability and design purposes.
     */
    private void styleComponents(){
        mainPanel.putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:20;" +
                "[light]background:darken(@background,3%);" +
                "[dark]background:lighten(@background,3%)"
        );
        cmdLogin.putClientProperty(FlatClientProperties.STYLE, 
                "[light]background:darken(@background,10%);" +
                "[dark]background:lighten(@background,10%);" +
                "borderWidth:0;" +
                "focusWidth:0;" +
                "innerFocusWidth:0"
         );
        cmdLogin.addActionListener(e -> {
            try {
                cmdLoginActionPerformed();
            } catch (Exception ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        txtUsername.putClientProperty(FlatClientProperties.STYLE, 
                "borderColor:@defaultBorderColorTextField"
         );
        txtPassword.putClientProperty(FlatClientProperties.STYLE, 
                "showRevealButton:true;" +
                "borderColor:@defaultBorderColorTextField"
         );
        
        // Placeholders in order to facilitate the user
        txtUsername.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter your username");
        txtPassword.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter your password");
    }
    
    /**
     * This method will add every component which has been instantiated
     * in the main panel.
     */
    private void addComponents(){
        JLabel lbTitle = new JLabel("Welcome back!");
        lbTitle.putClientProperty(FlatClientProperties.STYLE, 
                "font:bold +10"
         );
        JLabel description = new JLabel("Please sign in to access your account");
        description.putClientProperty(FlatClientProperties.STYLE, 
                "[light]foreground:lighten(@foreground,30%);" +
                "[dark]foreground:darken(@foreground,30%)"
        );
        mainPanel.add(lbTitle);
        mainPanel.add(description);
        mainPanel.add(new JLabel("Username"),"gapy 8");
        mainPanel.add(txtUsername);
        mainPanel.add(new JLabel("Password"),"gapy 8");
        mainPanel.add(txtPassword);
        mainPanel.add(chRememberMe,"grow 0");
        mainPanel.add(cmdLogin,"gapy 10");
        mainPanel.add(createSignUpLabel(), "gapy 10");
    }
    
    private Component createSignUpLabel(){
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        panel.putClientProperty(FlatClientProperties.STYLE, 
                "background:null"
         );
        JButton cmdRegister = new JButton("<html><a href=\"#\">Sign up</a></html>");
        cmdRegister.putClientProperty(FlatClientProperties.STYLE, 
                "border:3,3,3,3"
        );
        cmdRegister.setContentAreaFilled(false);
        cmdRegister.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cmdRegister.addActionListener(e -> {
            try {
                FormsManager.getInstance().showForm(new Register());
            } catch (ParserConfigurationException | SAXException | IOException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        JLabel label = new JLabel("Don't have an account?");
        label.putClientProperty(FlatClientProperties.STYLE, 
                "[light]foreground:lighten(@foreground,30%);" +
                "[dark]foreground:darken(@foreground,30%)"
         );
        panel.add(label);
        panel.add(cmdRegister);
        return panel;
    }
    
    /**
     * This method will check if the the login operation was done correctly.
     * Different values will be returned depending on the outcome of the checks
     * that will be performed.
     * 
     * The explanation of the results will be shown down below:
     * - 0, not all the input fields were filled;
     * - 1, the user inserted all the data needed but the user
     * was not found or the password was not correct;
     * - 2, everything was done correctly.
     */
    private int checkLogin() throws Exception{
        // First check if the input fields are filled
        if(!areInputFieldsFilled()) return 0;
        
        // Check if the username exist
        if(!database.getUsername(txtUsername.getText())) return 1;
        else {
            // If the user exist check if the password inserted is correct
            if(!database.getPassword(txtUsername.getText(), txtPassword.getText())) return 1;
            else return 2;
        }
    }
    
    /**
     * This method will check if every input field was filled with the
     * corresponding data. Other than notifying the user that it has
     * to insert all its credentials, this check will prevent the program
     * to query the "database" uselessly.
     */
    private boolean areInputFieldsFilled(){
        // Return value
        boolean flag = true;
        
        // Check if the username was inserted
        if(txtUsername.getText().equals("")){
            flag = false;
            setBorderColor(txtUsername, "errorBorderColorTextField");
        }
        
        // Check if the password was inserted
        if(txtPassword.getText().equals("")){
            flag = false;
            setBorderColor(txtPassword, "errorBorderColorTextField");
        }
        
        // Return the value
        return flag;
    }
    
     /**
     * This method, as the name already says, let the caller change the
     * border colour of any component, if a borderColor name (stored as
     * variables in the properties file) is given.
     * 
     * This procedure is very important in two specific situations:
     * - when the user may have done something wrong during the login
     * procedure such as not filling the input fields or inserting an incorrect
     * password or username (the colour will be red);
     * - right after the user starts typing on the input field which border colour
     * is set to red, in order to set the border colour back to the default one.
     * 
     * @param component 
     */
    private void setBorderColor(JComponent component, String borderColor){
        component.putClientProperty(FlatClientProperties.STYLE, 
                "borderColor:@" + borderColor
        );
    }
    
    /**
     * This method will be immediately called whenever the action listener
     * of cmdLogin component will be triggered.
     */
    private void cmdLoginActionPerformed() throws Exception{
        // Depending on the value returned by the checkLogin() function
        // different actions will be performed
        switch(checkLogin()){
            // A full description of each case and its meaning will be provided in the
            // checkLogin() decoration
            case 0 -> {
                // Notify the user that not all input fields were filled
                message.setDescription("Not all input fields were filled.");
                
                // Show the popup
                GlassPanePopup.showPopup(message);
            }
            case 1 -> {
                // Notify the user that the given credentials are incorrect
                message.setDescription("Username or password incorrect.");
                
                // Reset the text
                resetInputFields();
                
                // Show the popup
                GlassPanePopup.showPopup(message);
            }
            case 2 -> {
                FormsManager.getInstance().showForm(new UserInterface(txtUsername.getText(), false));
            }
        }
    }
    
    /**
     * This method will add on change listeners to the input fields.
     * Since whenever the user fails the login's procedure the border colour
     * of the input fields turn red in order to notify the user that was either missing or
     * was not correct, it is necessary to remove this style property after the user has started
     * to type since he could think that something is going wrong again.
     */
    private void addOnChangeListeners(){
        UtilManager.addOnChangeListener(txtUsername, t -> {
            setBorderColor(t, "defaultBorderColorTextField");
        });
        
        UtilManager.addOnChangeListener(txtPassword, t -> {
            setBorderColor(t, "defaultBorderColorTextField");
        });
    }
    
    /**
     * When the user has filled all the text fields and has failed the login
     * procedure, after the popup shows up and the user clicks OK it is
     * necessary to notify the user that something has gone wrong.
     * 
     * In this case all the text that it has inserted earlier will be erased
     * and the border colour of the input fields will turn into red.
     */
    private void resetInputFields(){
        txtUsername.setText("");
        txtPassword.setText("");
        
        setBorderColor(txtUsername, "errorBorderColorTextField");
        setBorderColor(txtPassword, "errorBorderColorTextField");
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
     * Text field in which username will be inserted.
     */
    private JTextField txtUsername;
    
    /**
     * Text field in which password will be inserted.
     */
    private JPasswordField txtPassword;
    private JCheckBox chRememberMe;
    
    /**
     * This component, if clicked, will trigger the login's procedure.
     */
    private JButton cmdLogin;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
