/*
 * Copyright (C) 2017 josue
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
package geologia.modelo.dao;

import java.sql.*;
import geologia.modelo.ConexionBD;
import javax.swing.JOptionPane;
/**
 *
 * @author josue
 */
public class LoginDAO {
    
    private static CallableStatement procAlmacenado;
    private static ResultSet tuplas;
    private static Connection conexion;
    
    
    public static boolean validarUsuario(String usr, String pass){
        
        
        boolean ok = false;
        
        
        try {
            conexion = ConexionBD.obtenerConexion();
            procAlmacenado = conexion.prepareCall("{call validarUsuario(?, ?)}");
            
            procAlmacenado.setString(1, usr);
            procAlmacenado.setString(2, pass);
            
            tuplas = procAlmacenado.executeQuery();
            
            if (tuplas.next()) {
                ok = tuplas.getBoolean("match_found");
            }
            
            
            
        } catch (SQLException se) {
            JOptionPane.showMessageDialog(null, "Error: "+se.getMessage(), "Error de la base de datos", JOptionPane.ERROR_MESSAGE);           
        }finally{
            ConexionBD.cerrarConexion();
        }
        
        return ok;
    }
}
