/*
 * Copyright (C) 2017 JoshPosh
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package geologia.vista;


import java.awt.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import geologia.control.ConfiguracionControl;
import java.util.ResourceBundle;

/**
 *
 * @author JoshPosh
 */
public class ConfiguracionIGU extends JFrame {
    
        private ResourceBundle rb = ResourceBundle.getBundle("geologia.control.configBD"); 
        private JTextField txtUsuarioBD = new JTextField(10);	
	private JPasswordField txtPassBD = new JPasswordField(10);
        private JTextField txtServidor = new JTextField(10);
        private JTextField txtAdmin = new JTextField(10);
        private JPasswordField txtPassAdmin = new JPasswordField(10);
	//botones
	private JButton btnGuardar = new JButton("Guardar");
	private JButton btnCancelar = new JButton("Cancelar");
        
        private Box boxVertical = Box.createVerticalBox();
        private ConfiguracionControl controlador = new ConfiguracionControl(this);

        public ConfiguracionIGU(){
            super("Configuracion");
            agregarElementos();
            setSize(500, 300);
            setResizable(false);
            //a partir del tamaño de la pantalla especifica su posición centrada
            Dimension dim = this.getToolkit().getScreenSize();	//obtiene tamaño pantalla
            Rectangle rec = this.getBounds();					//crea rectángulo a partir de límites la ventana
            this.setLocation((dim.width - rec.width) / 2, ((dim.height - rec.height) / 2) - 20);	//define posición
            show();
        }
        
        private void agregarElementos(){
           agregarPanel();
           add(boxVertical);
        }
        
        private void agregarPanel(){
            JPanel pnConfig = new JPanel();
		pnConfig.setBorder(new TitledBorder(null, "Configuracion del Sistema", TitledBorder.CENTER, TitledBorder.TOP));
				//tipo de borde por omision, "Título", justificación borde, posición borde (arriba)
		pnConfig.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
		
		//Crea panel de datos 
		JPanel pnDatos = new JPanel();
		pnDatos.setLayout(new GridLayout(5,2, 10, 10));
		
		//agrega componentes al panel de datos
		JLabel lbUuarioBD = new JLabel("Usuario BD:");
		pnDatos.add(lbUuarioBD);
                txtUsuarioBD.setText(rb.getString("usrBD"));
		pnDatos.add(txtUsuarioBD);

		JLabel lbPsswd = new JLabel("Password BD:");
		pnDatos.add(lbPsswd);
                txtPassBD.setText("passBD");
		pnDatos.add(txtPassBD);

                JLabel lbServidor = new JLabel("Servidor BD:");
                pnDatos.add(lbServidor);
                txtServidor.setText(rb.getString("dir_ip"));
                pnDatos.add(txtServidor);
                
                JLabel lbAdministrador = new JLabel("Usuario Administrador:");
                pnDatos.add(lbAdministrador);
                txtAdmin.setText(rb.getString("admin"));
                pnDatos.add(txtAdmin);
                
                JLabel lbPsswdAdmin = new JLabel("Password Adminsitrador:");
                pnDatos.add(lbPsswdAdmin);
                txtPassAdmin.setText(rb.getString("passAdmin"));
                pnDatos.add(txtPassAdmin);

		//agrega los paneles de datos y de botones al panel de Edición		
		pnConfig.add(pnDatos);
                pnConfig.add(agregarBotones());
		
		boxVertical.add(pnConfig);
        }
        
        private JPanel agregarBotones(){
            JPanel pnBotones = new JPanel();
            pnBotones.setLayout(new BoxLayout(pnBotones, BoxLayout.Y_AXIS));
            
            btnGuardar.setMnemonic('G');
            
            pnBotones.add(btnGuardar);
            pnBotones.add(Box.createVerticalStrut(10));
            pnBotones.add(btnCancelar);
            
            return pnBotones;
        }

        public JTextField getTxtUsuarioBD() {
            return txtUsuarioBD;
        }

        public void setTxtUsuarioBD(JTextField txtUsuarioBD) {
            this.txtUsuarioBD = txtUsuarioBD;
        }

        public JPasswordField getTxtPassBD() {
            return txtPassBD;
        }

        public void setTxtPassBD(JPasswordField txtPassBD) {
            this.txtPassBD = txtPassBD;
        }

        public JTextField getTxtServidor() {
            return txtServidor;
        }

        public void setTxtServidor(JTextField txtServidor) {
            this.txtServidor = txtServidor;
        }

        public JTextField getTxtAdmin() {
            return txtAdmin;
        }

        public void setTxtAdmin(JTextField txtAdmin) {
            this.txtAdmin = txtAdmin;
        }

        public JPasswordField getTxtPassAdmin() {
            return txtPassAdmin;
        }

        public void setTxtPassAdmin(JPasswordField txtPassAdmin) {
            this.txtPassAdmin = txtPassAdmin;
        }
        
        
}
