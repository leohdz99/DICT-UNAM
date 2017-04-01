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
package geologia.control;


import geologia.vista.ConfiguracionIGU;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;
import javax.swing.JOptionPane;

/**
 *
 * @author JoshPosh
 */
public class ConfiguracionControl implements ActionListener{
    private ConfiguracionIGU config;
    Properties propiedades = new Properties();
    OutputStream archivo = null;

    public ConfiguracionControl(ConfiguracionIGU config) {
            this.config = config;
    }

    

    @Override
    public void actionPerformed(ActionEvent e) {

            switch(e.getActionCommand()){
                    case "Aceptar":  aceptar();	
                                      break;
                    case "Cancelar":  cancelar();
                                        break;
            }
    }


    public void cancelar(){
       config.dispose();
    }

    public void aceptar(){
        try {
            archivo = new FileOutputStream("configBD.properties");
            propiedades.setProperty("usrBD", config.getTxtUsuarioBD().getText());
            propiedades.setProperty("passBD", new String(config.getTxtPassBD().getPassword()));
            propiedades.setProperty("dir_ip", config.getTxtServidor().getText());
            propiedades.setProperty("admin", config.getTxtAdmin().getText());
            propiedades.setProperty("passAdmin", new String(config.getTxtPassAdmin().getPassword()));
            
            propiedades.store(archivo, null);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            if (archivo != null) {
                try {
                    archivo.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }

    }
}

