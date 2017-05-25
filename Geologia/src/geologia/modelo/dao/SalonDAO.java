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

/**
 * SalonDAO: define todas las operaciones disponibles para acceder a 
 *           la tabla de Salon en la BD.
 *           Permite insertar, modificar, borrar, obtener todos los alumnos que 
 *           cumplen con una condición en una matriz. 
 * @author <ul><li>Javier Genico</li></ul>
 * @version 1.0
 * @since   2016-12-09 
 */
package geologia.modelo.dao;

import javax.swing.JOptionPane;

import geologia.modelo.ConexionGBD;

import java.util.ArrayList;

import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import static org.neo4j.driver.v1.Values.parameters;
import org.neo4j.driver.v1.exceptions.Neo4jException;
import org.neo4j.driver.v1.exceptions.SessionExpiredException;

public class SalonDAO{
    
    // Varaibles
    private static Driver conexion = null;
    private static Session sesion = null;
    private static StatementResult resultado = null;
    private static Record registro = null;

    
    //MÉTODO PARA LISTAR EN EL SISTEMA
    // <editor-fold defaultstate="collapsed" desc="Obtener Salones">
    public static ArrayList<String> obtenerSalones(){
        
        
        conexion = ConexionGBD.obtenerConexion();
       
        ArrayList<String> salones = null;
        try {
            sesion = conexion.session();
            
            try {
                
                // Aqui no ocupamos transacciones, puesto que estamos listando
                // Traemos todos los registros de la Consulta;
                resultado = sesion.run("MATCH(n:Salon)\n"
                        + "RETURN n.salon as salon");
                salones = new ArrayList<>();
                
                // Mientras existan registros
                while (resultado.hasNext()) {
                    
                    // Ttraemos el registro
                    registro = resultado.next();
                    
                    //Imprimimos en consola
                    salones.add(registro.get("salon").asString());
                }
            } catch (Neo4jException nfje) {
                System.out.println(nfje.getMessage());
            }
        } catch (SessionExpiredException see) {
            JOptionPane.showMessageDialog(null, "Error al leer los salones: " + 
                                            see.getMessage(), 
                                            "Error", JOptionPane.ERROR_MESSAGE);
        }catch (Neo4jException nfje){
            JOptionPane.showMessageDialog(null, "Error al leer los salones: " + 
                                            nfje.getMessage(), 
                                            "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            
            //Cierra la Sesion
            sesion.close();
            
            //Cierra la conexión
            ConexionGBD.cerrarConexion();
        }
        
        return salones;
        
    }// </editor-fold>
    
    //MÉTODO PARA verificar Salones
    // <editor-fold defaultstate="collapsed" desc="Verificar Salones">
    public static boolean verificarSalon(String salonVa){
        boolean ok = true;
        int cont = 0;
        conexion = ConexionGBD.obtenerConexion();
        
        try {
            sesion = conexion.session();
            
            try {
                
                //veriricacion del nodo a insertar
                resultado = sesion.run("MATCH (s:Salon) "
                            + "WHERE s.salon CONTAINS {salonVa}"
                            +" RETURN count(s.salon) as salon"
                        ,parameters("salonVa",salonVa)
                );
                
                registro = resultado.next();
                cont = registro.get("salon").asInt();
                
                if(cont > 0){
                    ok = false;
                }
                
            }catch(Neo4jException nfje){
                System.out.println(nfje.getMessage());
            }catch (NullPointerException npe){
                System.out.println(npe.getMessage());
                npe.printStackTrace();
            }
        } catch (SessionExpiredException see){
            System.out.println(see.getMessage());
        } catch (Neo4jException nfje){
            System.out.println(nfje.getMessage());
        } finally{
             
            //Cerrar la sesion
            sesion.close();
            
            //Cierre la conexion
            ConexionGBD.cerrarConexion();
        }
         
        return ok;
    }
    // </editor-fold>
}
