/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geologia;

import geologia.vista.LoginIGU;
import java.awt.EventQueue;

/**
 *
 * @author josue
 */
public class Inicio {
    
    public static void main(String args[]) {
        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : 
                    javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | 
                IllegalAccessException | 
                javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(
                    Inicio.class.getName()).log(java.util.logging.Level.SEVERE, 
                            null, ex
                    );
        }
       
        EventQueue.invokeLater(LoginIGU::new);           
    }
}