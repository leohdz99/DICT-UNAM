package geologia.modelo;

import java.sql.*;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;


public class ConexionBD {
    
    
    private ResourceBundle rb = ResourceBundle.getBundle("geologia.control.configBD"); 
    private final String DRIVER = "com.mysql.jdbc.Driver";
    private String ipDir = rb.getString("dir_ip");
    private String URL_JDBC = "jdbc:mysql://"+ ipDir +"/dict";
    
    private static Connection conexion = null;
    //private String usuarioBD;
    //private String PassBD;
    
    //Constructor de la conexión a la BD
    private ConexionBD() {
        try {
            Class.forName(DRIVER);
            conexion = DriverManager.getConnection(URL_JDBC, rb.getString("usrBD")
                    , rb.getString("passBD"));
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
            new ConexionBD();
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
