/**
 *AsignaturaDAO: define todas las operaciones disponibles para acceder a la tabla de Asignatura en la BD
 * 				permite insertar, modificar, borrar, obtener todas las asignaturas que cumplen con una 
 * 				condición en Profesorrna matriz 
 * @author <ul><li>Javier Genico</li></ul>
 * @version 1.0
 * @since   2017-01-09
 */
package geologia.modelo.dao;

import java.sql.*;

import javax.swing.JOptionPane;

import geologia.modelo.ConexionBD;	//conexión de a la BD
import geologia.modelo.dto.Asignatura;
import java.util.ArrayList;

public class AsignaturaDAO{

	private static PreparedStatement sentenciaPreparada;
	private static Statement sentencia;
	private static ResultSet tuplas;
	private static Connection conexion;
        
       
        

	/**
	 * obtiene todos las Asignaturas que coincidan con el nombre de la asignatura pasado como parámetro
	 * @param nombreAsignatura- a buscar en la BD
	 * @return una matriz con los registros de la asigantura en los renglones y separando los atributos en las columnas
	 * 			se necesita para indicar los datos del modelo de datos de la JTable
	 */

	public static int getIdAsignatura(String nombreAsignatura){

		int idAsignatura = 0;	//define matriz
		String query;				//cadena que espeficica la query
				
		try {
		        	
                    conexion = ConexionBD.getConexion();	//obtiene conexión (nunca la crea aquí)
                    sentencia = conexion.createStatement();	//crea objeto instrucción

	        

                    query = "SELECT idAsignatura FROM Asignatura " +		
        			"WHERE nombreAsiglike '%" + nombreAsignatura + "%' " +
		        	"ORDER BY idAsignatura";				//query para obtener Profesores que corresponden con el apellido
		    tuplas = sentencia.executeQuery(query);		//ejecuta query					
		    
		    if (tuplas.next()) {			//recorre todos los renglones
		       	idAsignatura = tuplas.getInt("idAsignatura");			//pone el nombre en la matriz 
		    }

		    //captura excepciones
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error al leer las asignaturas " + e.getMessage(), 
						"Error", JOptionPane.ERROR_MESSAGE);
		} finally {	
			ConexionBD.cerrarConexion();
		}
		
		return idAsignatura;	//regresa la matriz		
	}
        
        public static ArrayList<String> obtenerNombreAsignatura(String nombre){
            
                ArrayList<String> asignaturas = new ArrayList<String>();    //define el arreglo
		String query;				//cadena que espeficica la query
				
		try {
		        	
                    conexion = ConexionBD.getConexion();	//obtiene conexión (nunca la crea aquí)

                    sentencia = conexion.createStatement();	//crea objeto instrucción

                    query = "SELECT nombreAsig FROM asignatura\n"+
                            "WHERE nombreAsig like '%"+nombre+"%'\n"+
                            "ORDER BY nombreAsig;";		//query para contar profesores
                    tuplas = sentencia.executeQuery(query);							//ejecuta query
		    
                    int pos = 0;
                    
                    while (tuplas.next()) {			//recorre todos los renglones

                        asignaturas.add(tuplas.getString(1));
                        
		    }
		    //captura excepciones
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al leer los Datos " + e.getMessage(), 
						"Error", JOptionPane.ERROR_MESSAGE);
		} finally {
				
			ConexionBD.cerrarConexion();
		}
		
		return asignaturas;	//regresa el arreglo
            
        }
}