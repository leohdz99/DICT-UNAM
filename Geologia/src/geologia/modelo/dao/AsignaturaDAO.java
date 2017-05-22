/**
 *AsignaturaDAO: define todas las operaciones disponibles para acceder a la tabla de Asignatura en la BD
 * 				permite insertar, modificar, borrar, obtener todas las asignaturas que cumplen con una 
 * 				condición en Profesorrna matriz 
 * @author <ul><li>Javier Genico</li></ul>
 * @version 1.0
 * @since   2017-01-09
 */
package geologia.modelo.dao;



import javax.swing.JOptionPane;

	//conexión de a la BD
import geologia.modelo.ConexionGBD;
import java.util.ArrayList;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.exceptions.Neo4jException;
import org.neo4j.driver.v1.exceptions.SessionExpiredException;

public class AsignaturaDAO{

	    
    // Varaibles
    private static org.neo4j.driver.v1.Driver conexion = null;
    private static Session sesion = null;
    private static StatementResult resultado = null;
    private static Record registro = null;

    
    //MÉTODO PARA LISTAR EN EL SISTEMA
    // <editor-fold defaultstate="collapsed" desc="Obtener Asignaturas">
    public static ArrayList<String> obtenerAsignatura(){
        
        
        conexion = ConexionGBD.obtenerConexion();
        
        ArrayList<String> asignaturas = null;
        try {
            sesion = conexion.session();
            
            try {
                
                // Aqui no ocupamos transacciones, puesto que estamos listando
                // Traemos todos los registros de la Consulta;
                resultado = sesion.run("MATCH(a:Asignatura)\n"
                        + "RETURN a.nomAsig as asign");
                asignaturas = new ArrayList<>();
                
                // Mientras existan registros
                while (resultado.hasNext()) {
                    
                    // Ttraemos el registro
                    registro = resultado.next();
                    
                    //Imprimimos en consola
                    asignaturas.add(registro.get("asign").asString());
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
        
        return asignaturas;
        
    }// </editor-fold>
            
}