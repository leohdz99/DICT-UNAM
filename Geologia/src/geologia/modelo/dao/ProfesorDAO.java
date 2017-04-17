/*  DAO Profesor = 
	Insertar / Actualizar / Consultar / Buscar / Código de busqueda
*/

package geologia.modelo.dao;

import java.sql.*;
import javax.swing.JOptionPane;
import geologia.modelo.ConexionBD;
import geologia.modelo.dto.Profesor; //import modelo.dto.Horario?? //por lo que entendi, aqui se pone el dto con el que se relaciona, pero no se D: 
import java.util.ArrayList;

public class ProfesorDAO{

	private static PreparedStatement insertSQL;
	private static Statement sentencia;
	private static ResultSet tuplas;
	private static Connection conexion;

//*************INSERTAR****************
	public static boolean insertar(Profesor profesor){
		int constraint;
		boolean access = true;

		try{
			conexion = ConexionBD.getConexion();
			insertSQL = conexion.prepareStatement( //no se si es preparedStatement o PrepaderStatement, pero mi logica es la que empieza con P mayuscula 
				"INSERT INTO Profesor (folio, titulo, apPatern," 
				+ "apMatern, nombre, rfc, activo)"  
				+ "VALUES (?, ?, ?, ?, ?, ?, ?)");
			//INT = NUMERIC??
			//CHAR y VATCHAR = STIRNG??
			
			insertSQL.setInt(1,profesor.getFolioProfesor());
			insertSQL.setString(2,profesor.getTituloProfesor());
			insertSQL.setString(3,profesor.getApPaterno());
			insertSQL.setString(4,profesor.getApMaterno());
			insertSQL.setString(5,profesor.getNombreProf());
			insertSQL.setString(6,profesor.getRfc());
			insertSQL.setString(7,profesor.getActivo());

			constraint = insertSQL.executeUpdate();

			if(constraint == 0){
				conexion.rollback();
				access = false;
			}else{
				conexion.commit();
				insertSQL.close();
				ConexionBD.cerrarConexion();
			}

		}catch(SQLException e){ //por qué "e"??
			access = false;
		}finally {
			ConexionBD.cerrarConexion();
		}
		return access; //return ok;
	}
//*************ACTUALIZAR****************

	public static boolean actualizar(Profesor profesor){
		int constraint;
		boolean access = true;

		try{
			conexion = ConexionBD.getConexion();
			insertSQL = conexion.prepareStatement(
				"UPDATE Horario" + 
				"SET folio = ?, titulo = ?, apPatern = ?, apMatern = ?,"
				+ "nombre = ?, rfc = ?, activo = ?" + 
				"WHERE folio = ?");

			insertSQL.setInt(1,profesor.getFolioProfesor());
			insertSQL.setString(2,profesor.getTituloProfesor());
			insertSQL.setString(3,profesor.getApPaterno());
			insertSQL.setString(4,profesor.getApMaterno());
			insertSQL.setString(5,profesor.getNombreProf());
			insertSQL.setString(6,profesor.getRfc());
			insertSQL.setString(7,profesor.getActivo());
                        insertSQL.setInt(8,profesor.getFolioProfesor());

			constraint = insertSQL.executeUpdate();

			if (constraint ==0){
				conexion.rollback();
				access = false;
			}else {
				conexion.commit();
				insertSQL.close();
				ConexionBD.cerrarConexion();
			}
		}catch (SQLException se){
			access = false;
		}finally {
			ConexionBD.cerrarConexion();
		}
		return access; //return ok; 
	}
//*************CONSULTAR****************	
	public static boolean consultarProfesor(Profesor profesor){
		
		boolean access = false;

		try{
			conexion = ConexionBD.getConexion();
			insertSQL = conexion.prepareStatement(
				"SELECT COUNT(*) as encontrado FROM profesor "+
                                        "WHERE nombre = ? "+
                                        "AND apPatern = ? "+
                                        "AND apMatern = ?;");

			tuplas = insertSQL.executeQuery();
                        
                        if(tuplas.next()){
                            access = tuplas.getBoolean("Encontrado");
                        }
			
		}catch (SQLException se){
			JOptionPane.showMessageDialog(null, "Error: "+se.getMessage(), 
                                "Error de la base de datos", JOptionPane.ERROR_MESSAGE);  
		}finally {
			ConexionBD.cerrarConexion();
		}
		return access;		
            }
//*************BUSQUEDA / BUSCAR****************
	public static Object[][] obtenerProfesor(String nombre){
            
            Object profesores[][] = null;
		int totalTuplas = 0;
		String query;

		try{
			conexion = ConexionBD.getConexion();
			sentencia = conexion.createStatement();
                        
                        
			query = "SELECT Count(*)\n" +
                                "FROM Profesor\n" +
                                "WHERE concat(titulo,' ',nombre,' ',apPatern,' ',apMatern) like '%"+nombre+"%';";
                                
			tuplas = sentencia.executeQuery(query);

			tuplas.next();
			totalTuplas = tuplas.getInt(1);

			profesores = new Object[totalTuplas][4];
                        
                        query = "SELECT rfc as RFC,folio as 'Folio Profesor',concat(titulo,' ',nombre,' ',apPatern,' ',apMatern) as Profesor, activo as Activo FROM Profesor\n"+
                                "where concat(titulo,' ',nombre,' ',apPatern,' ',apMatern) like '%"+nombre+"%'\n"+
                                "ORDER BY idProfesor;";
                        tuplas = sentencia.executeQuery(query);
			int pos = 0;
                        
                            
			//segun yo, aqui se debe de acomodar a como quieren que salga en iReport D: pero no sale
			while (tuplas.next()){	
                                profesores[pos][0] = tuplas.getString("RFC");
                                profesores[pos][1] = tuplas.getString("Profesor");
                                profesores[pos][2] = tuplas.getString("Folio Profesor");
                                profesores[pos][3] = tuplas.getString("Activo");
                                pos++;
			}
		}catch (Exception e){
			JOptionPane.showMessageDialog(null, "Error, no se pueden mostrar a los profesores" + e.getMessage(),
				"ERROR", JOptionPane.ERROR_MESSAGE);
		}finally{
			ConexionBD.cerrarConexion();
		}
		return profesores;
	}
        
        public static ArrayList<String> obtenerNombreProfesor(String nombre){
            
                ArrayList<String> paternos = new ArrayList<String>();    //define el arreglo
		String query;				//cadena que espeficica la query
				
		try {
		        	
                    conexion = ConexionBD.getConexion();	//obtiene conexión (nunca la crea aquí)

                    sentencia = conexion.createStatement();	//crea objeto instrucción

                    query = "SELECT concat(titulo,' ',apPatern,' ',apMatern,' ',nombre) FROM Profesor";		//query para contar profesores
                    tuplas = sentencia.executeQuery(query);							//ejecuta query
		    
                    int post = 0;
                    
                    while (tuplas.next()) {			//recorre todos los renglones

                        paternos.add(tuplas.getString(1));
                        
		    }
		    //captura excepciones
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al leer los Datos " + e.getMessage(), 
						"Error", JOptionPane.ERROR_MESSAGE);
		} finally {
				
			ConexionBD.cerrarConexion();
		}
		
		return paternos;	//regresa el arreglo
            
        }
        
        public static int getIdPorfesor(String nombreProfesor){

		int idAsignatura = 0;	//define matriz
		String query;				//cadena que espeficica la query
				
		try {
		        	
                    conexion = ConexionBD.getConexion();	//obtiene conexión (nunca la crea aquí)
                    sentencia = conexion.createStatement();	//crea objeto instrucción

	        

                    query = "SELECT idProfesor FROM Profesor\n"+
                            "WHERE concat(tituloProfesor,' ',apellidoPaternoProfesor,' ',apellidoMaternoProfesor,' ',nombreProfesor) like '%"+nombreProfesor+"%'\n"+
                            "ORDER BY apellidoPaternoProfesor;";				//query para obtener Profesores que corresponden con el apellido
		    tuplas = sentencia.executeQuery(query);		//ejecuta query					
		    
		    if (tuplas.next()) {			//recorre todos los renglones
		       	idAsignatura = tuplas.getInt("idProfesor");			//pone el nombre en la matriz 
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
        
}