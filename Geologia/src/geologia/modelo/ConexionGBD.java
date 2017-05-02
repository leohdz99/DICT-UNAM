/*
 * Copyright (C) 2017 javigen
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
package geologia.modelo;

import java.sql.*;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import org.neo4j.driver.v1.*;
import static org.neo4j.driver.v1.Values.parameters;


public class ConexionGBD {
    
    
    private ResourceBundle rb = ResourceBundle.getBundle("geologia.control.configBD"); 
    private final String DRIVER = "org.neo4j.jdbc.Driver";
    private String ipDir = rb.getString("dir_ip");
    private String URL_JDBC = "jdbc:neo4j:bold://"+ ipDir +":7687";
    
    private static Connection conexion = null;
    //private String usuarioBD;
    //private String PassBD;
    
    //Constructor de la conexión a la BD
    private ConexionGBD() {
        try {
            Class.forName(DRIVER);
            conexion = DriverManager.getConnection(URL_JDBC, rb.getString("usrGDB")
                    , rb.getString("passGDB"));
            conexion.setAutoCommit(false);
            
        } catch(ClassNotFoundException cnfe){
            JOptionPane.showMessageDialog(null, cnfe.getMessage(),
                    "No se encontró el controlador", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        } catch(SQLException se){
            JOptionPane.showMessageDialog(null, se.getMessage(), 
                    "No se pudo conectar a la base de datos", 
                    JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        
        
    }

    public static Connection getConexion() {
        if (conexion == null) {
            new ConexionGBD();
        }
        return conexion;
    }
    
    public static void cerrarConexion(){
        try {
            if (conexion != null) {
                conexion.close();
                conexion = null;
            }
        } catch (SQLException se) {
            JOptionPane.showMessageDialog(null, se.getMessage(), 
                    "No se pudo cerrar la base de datos", 
                    JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }
    
    
}
