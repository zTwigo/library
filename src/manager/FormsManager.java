/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manager;

import com.formdev.flatlaf.extras.FlatAnimatedLafChange;
import main.Application;
import java.awt.EventQueue;
import javax.swing.JComponent;

/**
 *
 * @author Ernesto
 */
public class FormsManager {
    private Application application;
    private static FormsManager instance;
    public static FormsManager getInstance(){
        if(instance==null){
            instance = new FormsManager();
        }
        return instance;
    }
    
    private FormsManager(){
        
    }
    
    public void initApplication(Application application){
        this.application = application;
    }
    
    public void showForm(JComponent form){
        EventQueue.invokeLater(() -> {
            FlatAnimatedLafChange.showSnapshot();
            application.setContentPane(form);
            application.revalidate();
            application.repaint();
            FlatAnimatedLafChange.hideSnapshotWithAnimation();
        });
    }
}
