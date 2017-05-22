/*
 * Copyright (C) 2017 Javier Génico
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

import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import org.neo4j.driver.v1.*;
import org.neo4j.driver.v1.exceptions.AuthenticationException;
import org.neo4j.driver.v1.exceptions.Neo4jException;


public class ConexionGBD {
    
    private static Driver driver = null; 
    private final String DRIVER = "bolt://localhost:7687";
    private final String USER = "neo4j";
    private final String PASS = "123";
    
    private ConexionGBD(){
        
        try {
            
            driver = GraphDatabase.driver(DRIVER, AuthTokens.basic(USER, PASS));
            
        } catch (AuthenticationException ae) {
            ae.getMessage();
        } catch (Neo4jException nfje) {
            nfje.getMessage();
        } catch (NullPointerException npe){
            System.out.println("No se puede conectar a la base de datos, "
                    + "Asegurese de su conexión.");
        }
    }
    
    public static Driver obtenerConexion() {
        if (driver == null) {
           new ConexionGBD();
        }
        return driver;
    }
    
    public static void cerrarConexion(){
        try {
            if (driver != null) {
                driver.close();
                driver = null;
            }
        } catch (Neo4jException nfje) {
            JOptionPane.showMessageDialog(null, nfje.getMessage(), 
                    "No se pudo cerrar la base de datos", 
                    JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }
}
