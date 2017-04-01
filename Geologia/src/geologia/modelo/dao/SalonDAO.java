
/**
 * SalonDAO: define todas las operaciones disponibles para acceder a la tabla de Salon en la BD
 * 				permite insertar, modificar, borrar, obtener todos los alumnos que cumplen con una 
 * 				condición en una matriz 
 * @author <ul><li>Javier Genico</li></ul>
 * @version 1.0
 * @since   2016-12-09 
 */
package geologia.modelo.dao;

import java.sql.*;

import javax.swing.JOptionPane;

import geologia.modelo.ConexionBD;	//conexión de a la BD
import geologia.modelo.dto.Salon;
import java.util.ArrayList;

public class SalonDAO{

	private static PreparedStatement sentenciaPreparada;
	private static Statement sentencia;
	private static ResultSet tuplas;
	private static Connection conexion;

	
	/**
	 * obtiene todos los Salones que coincidan con el idSalon pasado como parámetro
	 * @param idSalon- a buscar en la BD
	 * @return una matriz con los registros de Salon en los renglones y separando los atributos en las columnas
	 * 			se necesita para indicar los datos del modelo de datos de la JTable
	 */

	public static int getIdSalon(String salon){

		int idSalon = 0;	//define matriz
		int totalTuplas = 0;		//total de tuplas que regresa la query
		String query;				//cadena que espeficica la query
				
		try {
		        	
                    conexion = ConexionBD.getConexion();	//obtiene conexión (nunca la crea aquí)

                    sentencia = conexion.createStatement();	//crea objeto instrucción

                    query = "SELECT idSalon FROM Salon " +
	        		"WHERE salon like '%" + salon + "%'";		//query para contar profesores
                    tuplas = sentencia.executeQuery(query);							//ejecuta query
		        
                    if(tuplas.next()){							//obtiene el primer renglon del resultado
                        idSalon = tuplas.getInt("idSalon");
                    }
		    //captura excepciones
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al leer los alumnos " + e.getMessage(), 
						"Error", JOptionPane.ERROR_MESSAGE);
		} finally {
				
			ConexionBD.cerrarConexion();
		}
		
		return idSalon;	//regresa la matriz		
	}
        
        public static ArrayList<String> obtenerNombreSalon(){
            
                ArrayList<String> salones = new ArrayList<String>();    //define el arreglo
		String query;				//cadena que espeficica la query
				
		try {
		        	
                    conexion = ConexionBD.getConexion();	//obtiene conexión (nunca la crea aquí)

                    sentencia = conexion.createStatement();	//crea objeto instrucción

                    query = "SELECT salon FROM Salon";		//query para contar profesores
                    tuplas = sentencia.executeQuery(query);							//ejecuta query
		    
                    int post = 0;
                    
                    while (tuplas.next()) {			//recorre todos los renglones

                        salones.add(tuplas.getString(1));
                        
		    }
		    //captura excepciones
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al leer los alumnos " + e.getMessage(), 
						"Error", JOptionPane.ERROR_MESSAGE);
		} finally {
				
			ConexionBD.cerrarConexion();
		}
		
		return salones;	//regresa el arreglo
            
        }
	
}
