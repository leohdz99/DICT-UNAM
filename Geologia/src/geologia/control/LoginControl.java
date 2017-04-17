package geologia.control;

/**
 *
 * @author josue
 */
import java.awt.event.*;

import javax.swing.*;
import geologia.vista.LoginIGU;
import geologia.vista.MenuPrincipalIGU;
import geologia.modelo.dao.LoginDAO;
import geologia.vista.ConfiguracionIGU;


public class LoginControl extends KeyAdapter implements ActionListener{

	private LoginIGU login;

	public LoginControl(LoginIGU login) {
		this.login = login;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		switch(e.getActionCommand()){
			case "Aceptar":  aceptar();	
                                          break;
                        case "Editar":  configuracion();
                                            break;
		}
	}
        
	      
        public void configuracion(){
           new ConfiguracionIGU();
        }
        
	public void aceptar(){
            if(login.getUsuario().getText().length() > 0 
                    && login.getPass().getPassword().length > 0 ){
                if(LoginDAO.validarUsuario(login.getUsuario().getText(),
                        new String(login.getPass().getPassword()))){
                    //boolean val = true;
                    //while (val){
                       // try {
                           /* String sem = JOptionPane.showInputDialog(null, 
                            "Semestre a Ingresar(Ejemplo 2000-1): ", 
                            "Ingrese un semestre", 
                            JOptionPane.INFORMATION_MESSAGE);

                            if(!sem.matches("[0-9]{4}-[1-2]{1}")){
                                int opt = JOptionPane.showConfirmDialog(login, "Desea Intentar de Nuevo",
                                        "Semestre Incorrecto", JOptionPane.YES_NO_OPTION, 
                                        JOptionPane.ERROR_MESSAGE);
                                if (opt == JOptionPane.NO_OPTION) {
                                    System.exit(0);
                                }
                            *///}else{
                                login.dispose();
                                new MenuPrincipalIGU();
                                //val = false;
                            
                        //} catch(NullPointerException npe) {
                          //  System.exit(0);
                        //}
                        
                    //}
                   
                } else {
                    JOptionPane.showMessageDialog( null, 
                            "Usuario y/o Contraseña incorrectos", 
                            "Mensaje de error", JOptionPane.ERROR_MESSAGE);
                    login.limpiar();
                }                            
            } else{
                JOptionPane.showMessageDialog( null, 
                        "Ingrese Usuario y/o Contraseña", 
                        "Advertencia", JOptionPane.WARNING_MESSAGE);
                login.limpiar();
            }

	}
}
