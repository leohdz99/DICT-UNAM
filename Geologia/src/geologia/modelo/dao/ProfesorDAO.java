/*
 * Copyright (C) 2017 Josué Hernández
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

import geologia.modelo.ConexionGBD;
import geologia.modelo.dto.Profesor; 

import java.util.ArrayList;

import static org.neo4j.driver.v1.Values.parameters;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;
import org.neo4j.driver.v1.exceptions.Neo4jException;
import org.neo4j.driver.v1.exceptions.SessionExpiredException;

public class ProfesorDAO{
    
    private static Driver conexion = null;
    private static Session sesion = null;
    private static Transaction transact = null;
    private static StatementResult resultado = null;
    private static Record registro = null;
    
    
    // METODO PARA INSERTAR UN NODO PROFESOR
    // <editor-fold defaultstate="collapsed" desc="Insertar Profesor">
    public static boolean insertar(Profesor profesor){ 
        boolean esVerdadero = false;
        
        conexion = ConexionGBD.obtenerConexion();
        
        try {
            sesion = conexion.session();
            
            try {
                
                //Manejo de transacciones
                transact = sesion.beginTransaction();
                
                //Inserción del nodo
                transact.run("MERGE (prof:Profesor {"
                        + "profe: {prof},"
                        + "folio: {folio},"
                        + "rfc: {rfc},"
                        + "activo: {activo}"
                        + "})",parameters("prof",profesor.getProf(),
                                "folio", profesor.getFolProf(),
                                "rfc", profesor.getRfc(),
                                "activo", profesor.getActivo()));
                
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
    public static boolean modificar(Profesor profActual, 
            Profesor profModificado){
        boolean esVerdadero = false;
        
        conexion = ConexionGBD.obtenerConexion();
        
        try {
            sesion = conexion.session();
            
            try {
                
                //Manejo de transacciones
                transact = sesion.beginTransaction();
                
                //Modificación del nodo
                transact.run("MATCH (p:Profesor {"
                        + "profe: {prof},"
                        + "folio: {folio},"
                        + "rfc: {rfc},"
                        + "activo: {activo}"
                        + "})\n"
                        + "SET p.profe = {profM},"
                        + "p.folio = {folioM},"
                        + "p.rfc = {rfcM},"
                        + "p.activo = {activoM}"
                        ,parameters("prof",profActual.getProf(),
                                "folio", profActual.getFolProf(),
                                "rfc", profActual.getRfc(),
                                "activo", profActual.getActivo(),
                                "profM", profModificado.getProf(),
                                "folioM", profModificado.getFolProf(),
                                "rfcM", profModificado.getRfc(),
                                "rfcM", profModificado.getActivo()));
                
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
    public static Object[][] filtrarProfesor(String nombreProf){
        
        int cont = 0;
        Object profesores[][] = null;        
        conexion = ConexionGBD.obtenerConexion();
        
        try {
            sesion = conexion.session();
            
            try {
                
                resultado = sesion.run("MATCH (p:Profesor)\n"
                        + "WHERE p.profe CONTAINS {nomProf}\n"
                        + "RETURN count(p.profe) as numP", 
                        parameters("nomProf", nombreProf)
                );
                
                registro = resultado.next();
                cont = registro.get("numP").asInt();
                
                profesores = new Object[cont][4];
                // Aqui no ocupamos transacciones, puesto que estamos listando
                // Traemos todos los registros de la Consulta;
                resultado = sesion.run("MATCH (p:Profesor)\n"
                        + "WHERE p.profe CONTAINS {nomProf}\n"
                        + "RETURN p.rfc as rfcP,"
                        + "p.profe as nombreP,"
                        + "p.folio as folioP,"
                        + "p.activo as activoP",
                        parameters("nomProf", nombreProf)
                );
                        
                Profesor pf = new Profesor();
                
                cont = 0;
                // Mientras existan registros
                while (resultado.hasNext()) {
                    
                    // Traemos el registro
                    registro = resultado.next();
                    
                    pf.setRfc(registro.get("rfcP").asString());
                    pf.setProf(registro.get("nombreP").asString());
                    pf.setFolProf(registro.get("folioP").asString());
                    pf.setActivo(registro.get("activoP").asString());
                    
                    //obtenemos los registros y los asignamos a la matriz
                    profesores[cont][0] = pf.getRfc();
                    profesores[cont][1] = pf.getProf();
                    profesores[cont][2] = pf.getFolProf();
                    profesores[cont][3] = pf.getActivo();
                    
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
        
        return profesores;
    }
    // </editor-fold>
    
    //MÉTODO PARA verificar Profesores
    // <editor-fold defaultstate="collapsed" desc="Verificar Profesores">
    public static boolean verificarProfesor(String nomProf, String grupoVa, 
                            double hrEV, double hrSV, String diaV){
        boolean ok = true;
        int cont = 0;
        conexion = ConexionGBD.obtenerConexion();
        
        try {
            sesion = conexion.session();
            
            try {
                
                //veriricacion del nodo a insertar
                resultado = sesion.run("MATCH (p:Profesor)--(h.Horario) "
                            + "WHERE p.profe CONTAINS {nombreProfVa}"
                            + "AND h.grupo = {grupoVa}\n"
                            + "AND h.hrEnt = {hrEVa}\n"
                            + "AND h.hrSal = {hrSVa}\n"
                            + "AND h.dias CONTAINS {diasVa}"
                            +" RETURN count(p.profe) as profesores"
                        ,parameters("nombreProfVa",nomProf, "grupoVa", grupoVa,
                                    "hrEVA",hrEV, "hrSVa", hrSV, "diasVa", diaV)
                );
                
                registro = resultado.next();
                cont = registro.get("profesores").asInt();
                
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
            } catch (Neo4jException nfje) {
                System.out.println(nfje.getMessage());
            } catch (NullPointerException npe){
                System.out.println(npe.getMessage());
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
}
    