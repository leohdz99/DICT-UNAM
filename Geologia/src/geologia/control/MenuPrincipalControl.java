/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geologia.control;

/**
 *
 * @author josue
 */
import java.awt.event.*;
import javax.swing.*;

import geologia.vista.HorarioIGU;
import geologia.vista.ConHorarioIGU;
import geologia.vista.ConProfesorIGU;
import geologia.vista.MenuPrincipalIGU;
import geologia.vista.ProfesorIGU;
import java.beans.PropertyVetoException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MenuPrincipalControl implements ActionListener{	//sólo implementa ActionListener

	//escritorio del menú principal
	private JDesktopPane escritorio;
	private JInternalFrame ventana = null;
        private JInternalFrame ventanaCon = null;
        private String semestre;
	
        public MenuPrincipalControl(MenuPrincipalIGU menu){	//recibe la IGU para poder manipular sus componentes
		this.escritorio = menu.getEscritorio();
                this.semestre = menu.getSemestre();
	}
	
        
	/**
	 * agrega ventanas (JInternalFrame) al escritorio dependiendo de la opción seleccionada
	 */
	@Override
	public void actionPerformed(ActionEvent evento) {
		
            	//define objeto ventana

            switch(evento.getActionCommand()){
                    case "Horarios"			: ventana = HorarioIGU.getVentana(semestre);	//obtiene ventana de Horarios
                                                          comprobarVentana(ventana);
                                                          
                                                          
                                                            break;
                    case "Profesores"                   : ventana = ProfesorIGU.getVentana(); 
                                                          comprobarVentana(ventana);
                                                            break;
                    case "Horario"			: ventanaCon = ConHorarioIGU.getConVentana(semestre);	//obtiene ventana de Horario
                                                          comprobarVentana(ventanaCon);
                                                            break;
                    case "Profesor"                     : ventana = ConProfesorIGU.getConVentana();	//obtiene ventana de Horario
                                                          comprobarVentana(ventana);
                                                          break;
                    case "Acerca de"			: JOptionPane.showInternalMessageDialog(escritorio,"        Sistema de control de horarios para la\nDivisión de Ingeniería en Ciencias de la Tierra\n\nAutores:\n      Josué Hernández\n      Javier Genico\n      Angel Romero"+
                                                          "\n\n Version: 1.0", "Acerca De", JOptionPane.PLAIN_MESSAGE);	//ventana de Acerca de
                                                            break; 
                    case "Salir"                        : System.exit(0);
                                                            break;
                    

            }
            
            
            
            
	}
        
        public void comprobarVentana(JInternalFrame ventana){
            if (ventana != null) {//si la ventana se pudo crear
                ventana.setVisible(true);
                if (ventana.getParent() == null || !ventana.isShowing()) {              //si no se ha agregado al escritorio
                   if(!ventana.isIcon()){
                        escritorio.add(ventana);//la hace visible
                        ventana.show(); //y la muestra
                        ventana.moveToFront();
                
                    } else {
                       try {
                           ventana.setIcon(false);
                       } catch (PropertyVetoException ex) {
                           Logger.getLogger(MenuPrincipalControl.class.getName()).log(Level.SEVERE, null, ex);
                       }
                        ventana.moveToFront();
                    }   
                }
           }
        }  
}
