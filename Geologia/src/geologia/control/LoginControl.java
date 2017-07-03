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
			case "Ingresar":  ingresar();	
                                          break;
                        case "Editar":  configuracion();
                                          break;
		}
	}
        
	      
        public void configuracion(){
           new ConfiguracionIGU();
        }
        
	public void ingresar(){
            if(login.getUsuario().getText().length() > 0 
                    || login.getPass().getPassword().length > 0 ){
                if(LoginDAO.validarUsuario(login.getUsuario().getText(),
                        new String(login.getPass().getPassword()))){
  
                                login.dispose();
                                new MenuPrincipalIGU();
                   
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
