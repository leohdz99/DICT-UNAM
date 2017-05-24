/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geologia.modelo.dao;

/**
 *
 * @author 58599749
 */
/*  DAO Horario = 
	Insertar / Actualizar / Consultar / Buscar / Código de busqueda
*/

import geologia.modelo.ConexionGBD;
import geologia.modelo.dto.Asignatura;
import geologia.modelo.dto.Horario;
import geologia.modelo.dto.Profesor;
import geologia.modelo.dto.Salon;

import java.util.ArrayList;

import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;
import org.neo4j.driver.v1.exceptions.Neo4jException;
import org.neo4j.driver.v1.exceptions.SessionExpiredException;
import static org.neo4j.driver.v1.Values.parameters;

public class HorarioDAO{
    
    private static Driver conexion = null;
    private static Session sesion = null;
    private static Transaction transact = null;
    private static StatementResult resultado = null;
    private static Record registro = null;

     // METODO PARA INSERTAR UN NODO PROFESOR
    // <editor-fold defaultstate="collapsed" desc="Insertar Horario">
    public static boolean insertar(Horario hr, Salon sln, 
            Asignatura asgn, Profesor prf){ 
        boolean esVerdadero = false;
        
        conexion = ConexionGBD.obtenerConexion();
        
        try {
            sesion = conexion.session();
            
            try {
                
                //Manejo de transacciones
                transact = sesion.beginTransaction();
                
                //Inserción del nodo
                transact.run("MATCH (s:Salon),(a:Asignatura),(p:Profesor)\n"
                        + "WHERE s.salon = {salon}\n"
                        + "AND\n"
                        + "a.nomAsig CONTAINS {asign}\n"
                        + "AND\n"
                        + "p.profe CONTAINS {prfe}\n"
                        + "CREATE (h:Horario {"
                            + "grupo: {grp},"
                            + "dias: {dias}, "
                            + "hrEnt: {hrEntda}, "
                            + "hrSal: {hrSalda} }),"
                        + "(h)-[:CUBRE]->(a), "
                        + "(s)-[:SE_IMPARTE]->(h), "
                        + "(p)-[:IMPARTE]->(h)"
                        ,parameters("salon", sln.getSalon(),
                                "asign", asgn.getNomAsig(),
                                "prfe", prf.getProf(),
                                "grp", hr.getGrupo(),
                                "dias", hr.getDias(),
                                "hrEntda", hr.getHrEnt(),
                                "hrSalda", hr.getHrSal()
                        )
                );
                
                //Si la transacción se realiza con éxito, realiza en commit
                transact.success();
                
                esVerdadero = true;
            } catch (Neo4jException nfje) {
                //Si falla al hacer la transacción, realiza un rollback
                transact.failure();
            }
        } catch (SessionExpiredException see) {
            
        } catch (Neo4jException nfje){
            
        } finally {
            
            //Las transacciones se cierran en automático, pero en caso de que no
            //se logre cerrar, la cerramos 
            if(transact.isOpen()){
                transact.close();
            }
            
            //Cierra la Sesion
            sesion.close();
            
            //Cierra la conexión
            ConexionGBD.cerrarConexion();
        }        
        
        return esVerdadero;
    }
    // </editor-fold> 
    
    // METODO PARA MODIFICAR UN NODO PROFESOR
    // <editor-fold defaultstate="collapsed" desc="Modificar Profesor">
    public static boolean modificar(Horario hr,Horario hrM, Salon sln, 
            Asignatura asgn, Profesor prf){
        boolean esVerdadero = false;
        
        conexion = ConexionGBD.obtenerConexion();
        
        try {
            sesion = conexion.session();
            
            try {
                
                //Manejo de transacciones
                transact = sesion.beginTransaction();
                
                //Modificación del nodo
                transact.run("MATCH (h:Horario)--(a:Asignatura), "
                            + "(s:Salon)--(h),"
                            + "(p:Profesor)--(h)"
                            + "WHERE a.nomAsig CONTAINS {a}\n"
                            + "AND s.salon CONTAINS ''\n"
                            + "AND p.profe CONTAINS ''\n"
                            + "AND h.grupo = 1\n"
                            + "AND h.hrEnt = 9.00\n"
                            + "AND h.hrSal = 11.00\n"
                            + "AND h.dias = 6\n"
                            + "SET h.hrEnt = 15.00,\n"
                            + "h.hrSal = 19.00,\n"
                            + "h.dias = 25"
                        ,parameters(
                                "asign", asgn.getNomAsig(),
                                "salon", sln.getSalon(),
                                "prfe", prf.getProf(),
                                "grp", hr.getGrupo(),
                                "dias", hr.getDias(),
                                "hrEntda", hr.getHrEnt(),
                                "hrSalda", hr.getHrSal(),
                                "grpM", hrM.getGrupo(),
                                "hrEntdaM", hrM.getHrEnt(),
                                "hrSaldaM", hrM.getHrSal(),
                                "dias", hrM.getDias()
                        )
                );
                
                //Si la transacción se realiza con éxito, realiza en commit
                transact.success();
                
                esVerdadero = true;
            } catch (Neo4jException nfje) {
                //Si falla al hacer la transacción, realiza un rollback
                transact.failure();
            }
        } catch (SessionExpiredException see) {
            
        } catch (Neo4jException nfje){
            
        } finally {
            
            //Las transacciones se cierran en automático, pero en caso de que no
            //se logre cerrar, la cerramos 
            if(transact.isOpen()){
                transact.close();
            }
            
            //Cierra la Sesion
            sesion.close();
            
            //Cierra la conexión
            ConexionGBD.cerrarConexion();
        }        
        
        
        return esVerdadero;
    }
    // </editor-fold> 
    
    // METODO PARA FILTRAR UN PROFESOR
    // <editor-fold defaultstate="collapsed" desc="Filtrar Profesor">
    public static Object[][] filtrarHorario(String nombreProf){
        
        int cont = 0;
        Object[][] horarios = null;        
        conexion = ConexionGBD.obtenerConexion();
        
        try {
            sesion = conexion.session();
            
            try{
                resultado = sesion.run("MATCH (h:Horario)--(a:Asignatura),"
                        + "(s:Salon)--(h),"
                        + "(p:Profesor)--(h)"
                        + "WHERE a.nomAsig CONTAINS ''\n"
                        + "AND s.salon CONTAINS ''\n"
                        + "AND p.profe CONTAINS ''\n"
                        + "RETURN count(h) as horarios");
                
                registro = resultado.next();
                cont = registro.get("horarios").asInt();
                
                horarios = new Object[cont][8];
                // Aqui no ocupamos transacciones, puesto que estamos listando
                // Traemos todos los registros de la Consulta;
                resultado = sesion.run("MATCH (h:Horario)--(a:Asignatura),"
                        + "(s:Salon)--(h),"
                        + "(p:Profesor)--(h)"
                        + "WHERE a.nomAsig CONTAINS ''\n"
                        + "AND s.salon CONTAINS ''\n"
                        + "AND p.profe CONTAINS ''\n"
                        + "RETURN a.claveAsig as Clave,"
                        + "a.nomAsig as Asignatura,"
                        + "h.grupo as Grupo,"
                        + "s.salon as Salon,"
                        + "p.profe as NomProf,"
                        + "p.folio as FolioProf,"
                        + "h.hrEnt as Hora_Entrada,"
                        + "h.hrSal as Hora_Salida,"
                        + "h.dias as Dias\n"
                        + "ORDER BY Grupo, Asignatura;");
                        
                
                
                cont = 0;
                // Mientras existan registros
                while (resultado.hasNext()) {
                    
                    // Traemos el registro
                    registro = resultado.next();
                    
                    //obtenemos los registros y los asignamos a la matriz
                    horarios[cont][0] = registro.get("Clave").asString();
                    horarios[cont][1] = registro.get("Asignatura").asString();
                    horarios[cont][2] = registro.get("Grupo").toString();
                    horarios[cont][3] = registro.get("Salon").asString();
                    horarios[cont][4] = registro.get("NomProf").asString();
                    horarios[cont][5] = registro.get("FolioProf").asString();
                    horarios[cont][6] = registro.get("Hora_Entrada").toString()
                            + " - " + registro.get("Hora_Salida").toString();
                    horarios[cont][7] = registro.get("Dias").toString();
                    cont ++;
                }
                
            } catch (Neo4jException nfje) {
                System.out.println(nfje.getMessage());
            } catch (NullPointerException npe){
                System.out.println(npe.getMessage());
                npe.printStackTrace();
            }
        } catch (SessionExpiredException see) {
            System.out.println(see.getMessage());
        }catch (Neo4jException nfje){
            System.out.println(nfje.getMessage());
        } finally {
            
            //Cierra la Sesion
            sesion.close();
            
            //Cierra la conexión
            ConexionGBD.cerrarConexion();
        }
        
        return horarios;
    }
    // </editor-fold>
    
    // METODO PARA OBTENER EL NOMBRE DE LOS PROFESORES 
    // <editor-fold defaultstate="collapsed" desc="Obtener Nombre Profesores">
    public static ArrayList<String> obtenerNomProf(){
        ArrayList<String> profesores = null;
        
        conexion = ConexionGBD.obtenerConexion();
        
        try {
            sesion = conexion.session();
            
            try {
                
                // Aqui no ocupamos transacciones, puesto que estamos listando
                // Traemos todos los registros de la Consulta;
                resultado = sesion.run("MATCH (p:Profesor)\n"
                        + "RETURN p.profe as nombre");
     
                profesores = new ArrayList<>();
                // Mientras existan registros
                while (resultado.hasNext()) {
                    
                    // Traemos el registro
                    registro = resultado.next();
                
                    //obtenemos los registros y los asignamos a ArrayList
                    profesores.add(registro.get("nombre").asString());
                }
            } catch (Neo4jException | NullPointerException nfje) {
                System.out.println(nfje.getMessage());
            }
        } catch (SessionExpiredException see) {
            System.out.println(see.getMessage());
        }catch (Neo4jException nfje){
            System.out.println(nfje.getMessage());
        } finally {
            
            //Cierra la Sesion
            sesion.close();
            
            //Cierra la conexión
            ConexionGBD.cerrarConexion();
        }
        
        
        return profesores;
    }
    // </editor-fold>       
        
    public static String obtenerDias(String dias){

        String arregloDias[] = {};
        int numDias[] = {};
        String diasTotales = "";
        arregloDias = dias.split("");

        String delimiter = "";


        for(int i = 0; i < arregloDias.length; i ++){

            switch(Integer.parseInt(arregloDias[i])){

                case 1 :	diasTotales += "Lunes" + delimiter;
                        break;
                case 2 :	diasTotales += delimiter + "Martes";
                        break;
                case 3 :	diasTotales+=delimiter + "Miercoles";
                        break;
                case 4 :	diasTotales += delimiter + "Jueves";	
                        break;
                case 5 :	diasTotales  += delimiter + "Viernes";
                        break;
                case 6 :	diasTotales += delimiter + "Sabado";
                        break;
                default : 	
                    break;
          }

          delimiter = " - ";
        }
        return diasTotales;
    }
    
    


 // METODO PARA ELIMINAR HORARIO 
    // <editor-fold defaultstate="collapsed" desc="Obtener Nombre Profesores">
    public static boolean eliminar(Horario hr,Horario hrM, Salon sln, 
            Asignatura asgn, Profesor prf){
        boolean esVerdadero = false;
        
        conexion = ConexionGBD.obtenerConexion();
        
        try {
            sesion = conexion.session();
            
            try {
                
                //Manejo de transacciones
                transact = sesion.beginTransaction();
                
                //Modificación del nodo
                transact.run("MATCH (h:Horario)--(a:Asignatura), "
                            + "(s:Salon)--(h),"
                            + "(p:Profesor)--(h)"
                            + "WHERE a.nomAsig CONTAINS {a}\n"
                            + "AND s.salon CONTAINS ''\n"
                            + "AND p.profe CONTAINS ''\n"
                            + "AND h.grupo = 1\n"
                            + "AND h.hrEnt = 9.00\n"
                            + "AND h.hrSal = 11.00\n"
                            + "AND h.dias = 6\n"
                            + "SET h.hrEnt = 15.00,\n"
                            + "h.hrSal = 19.00,\n"
                            + "h.dias = 25"
                        ,parameters(
                                "asign", asgn.getNomAsig(),
                                "salon", sln.getSalon(),
                                "prfe", prf.getProf(),
                                "grp", hr.getGrupo(),
                                "dias", hr.getDias(),
                                "hrEntda", hr.getHrEnt(),
                                "hrSalda", hr.getHrSal(),
                                "grpM", hrM.getGrupo(),
                                "hrEntdaM", hrM.getHrEnt(),
                                "hrSaldaM", hrM.getHrSal(),
                                "dias", hrM.getDias()
                        )
                );
                
                //Si la transacción se realiza con éxito, realiza en commit
                transact.success();
                
                esVerdadero = true;
            } catch (Neo4jException nfje) {
                //Si falla al hacer la transacción, realiza un rollback
                transact.failure();
            }
        } catch (SessionExpiredException see) {
            
        } catch (Neo4jException nfje){
            
        } finally {
            
            //Las transacciones se cierran en automático, pero en caso de que no
            //se logre cerrar, la cerramos 
            if(transact.isOpen()){
                transact.close();
            }
            
            //Cierra la Sesion
            sesion.close();
            
            //Cierra la conexión
            ConexionGBD.cerrarConexion();
        }        
        
        
        return esVerdadero;
    }
}
// </editor-fold>  
 