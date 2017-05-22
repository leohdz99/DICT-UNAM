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
    
    
}


 /* INSERTAR****************

	public static boolean insertar(Horario horario){
		int constraint;
                String query;
		boolean access = true;

		try{
			
                        conexion = ConexionBD.getConexion();
			query = "INSERT INTO Horario(idHorario,grupo,idSalon,idProfesor) "
                                + "VALUES (?, ?, ?, ?);";
                        sentencia = conexion.prepareStatement(query);
                        
                        sentencia.setInt(1,horario.getIdHorario());
			sentencia.setString(2,horario.getGrupo());
        		sentencia.setInt(3,horario.getIdSalon());
			sentencia.setInt(4,horario.getIdProfesor());
			
			

			constraint = sentencia.executeUpdate();

			if(constraint == 0){
				conexion.rollback();
                                JOptionPane.showMessageDialog(null, 
                                "Fallo al insertar horario", 
                                "Insercion fallida", JOptionPane.ERROR_MESSAGE);
				access = false;
			}else{
				conexion.commit();
                                JOptionPane.showMessageDialog(null, 
                                "Horario Insertado exitosamente", 
                                "Insercion exitosa", JOptionPane.INFORMATION_MESSAGE);
				sentencia.close();
				ConexionBD.cerrarConexion();
			}

		}catch(SQLException e){
			JOptionPane.showMessageDialog(null, "Error, no se puede Insertar los Horarios: " + e.getMessage(),
				"ERROR", JOptionPane.ERROR_MESSAGE);
                        e.printStackTrace();
		}finally {
			ConexionBD.cerrarConexion();
		}
		return access; //return ok;
	}

 //*************ACTUALIZAR****************

	public static boolean actualizar(Horario horario){
		int constraint;
		boolean access = true;

		try{
			conexion = ConexionBD.getConexion();
			sentencia = conexion.prepareStatement(
				"UPDATE Horario " + 
				"SET grupo = ?, idSalon = ?, idProfesor = ?, " +
                                "WHERE idHorario = ?");

			sentencia.setInt(1,horario.getIdHorario());
			sentencia.setString(2,horario.getGrupo());
			sentencia.setInt(3,horario.getIdSalon());
			sentencia.setInt(4,horario.getIdProfesor());
			

			constraint = sentencia.executeUpdate();

			if (constraint == 0){
				conexion.rollback();
				access = false;
			}else {
				conexion.commit();
				sentencia.close();
				ConexionBD.cerrarConexion();
			}
		}catch (SQLException se){
			access = false;
		}finally {
			ConexionBD.cerrarConexion();
		}
		return access; 
	}
 //*************CONSULTAR****************
	public static Object[][] obtenerHorarios(String nombreProf, String salon){
		
                Object horarios[][] = null;
		int totalTuplas = 0;
		String query;

		try{
			conexion = ConexionBD.getConexion();
			orden = conexion.createStatement();
                        
                        
			query = "SELECT Count(*)\n " 
                                 + "FROM horario\n" 
                                  + "left outer join horarioReal on horario.idHorario = horarioReal.idHorario\n"
                                  + "left outer join horarioReal on horario.idHorario = horarioReal.idHorario\n"
                                  + "union\n"
                                  + "select * from horario\n "
                                  + "left outer join horarioComp on horario.idHorario = horarioComp.idHorario\n"
                                  + "order by grupo;";
                              
                        
			vista = orden.executeQuery(query);

			vista.next();
			totalTuplas = vista.getInt(1);

			horarios = new Object[totalTuplas][13];
                        
                        query =     "select  claveAsig as clave, nombreAsig as asignatura, grupo, salon,  Profesor.folio as folio,"
                                    +"concat(Profesor.titulo,' ',Profesor.nombre,' ',Profesor.apPatern,' ',Profesor.apMatern) as profesor,"
                                    +"concat(horaEntrada,' - ',horaSalida) as horario, dias"
                                    +" from asignaturaxhorario\n"
                                    +"left outer join"
                                    +"\nasignatura on asignaturaxhorario.idAsignatura = asignatura.idAsignatura"
                                    +"\nleft outer join "
                                    +"horario on asignaturaxhorario.idHorario = horario.idHorario"
                                    +"\nleft outer join "
                                    +"profesor on profesor.idProfesor = horario.idProfesor"
                                    +"\nleft outer join "
                                    +"salon on salon.idSalon = horario.idSalon"
                                    +"\nleft outer join "
                                    +"horarioReal on horario.idHorario as h1 = horarioReal.idHorario"
                                    +"\nwhere dias like '%%'\n" 
                                    +"union\n"
                                    +"select claveAsig as clave, nombreAsig as asignatura, grupo, salon,  Profesor.folio as folio,"
                                    +"concat(Profesor.titulo,' ',Profesor.nombre,' ',Profesor.apPatern,' ',Profesor.apMatern) as profesor,"
                                    +"concat(horaEntrada,' - ',horaSalida) as horario, dias\n"
                                    +"from asignaturaxhorario\n"
                                    +"left outer join \n"
                                    +"asignatura on asignaturaxhorario.idAsignatura = asignatura.idAsignatura"
                                    +"\nleft outer join "
                                    +"horario on asignaturaxhorario.idHorario = horario.idHorario\n"
                                    +"left outer join "
                                    +"profesor on profesor.idProfesor = horario.idProfesor"
                                    +"left outer join "
                                    +"salon on salon.idSalon = horario.idSalon\n"
                                    +"right outer join "
                                    +"horariocomp on horario.idHorario as h2 = horariocomp.idHorario;";                              ;
                                  /* "SELECT horario.idHorario, claveAsig as clave, nombreAsig as asignatura, grupo, salon,  Profesor.folio as folio,\n"
                                  + "concat(Profesor.titulo,' ',Profesor.nombre,' ',Profesor.apPatern,' ',Profesor.apMatern) as profesor,\n"
                                  + "concat(horaEntrada,' - ',horaSalida) as horario, dias\n"
                                  + "FROM asignaturaxhorario\n"
                                  + "left outer join asignatura on asignaturaxhorario.idAsignatura = asignatura.idAsignatura\n"
                                  + "left outer join horario on asignaturaxhorario.idHorario = horario.idHorario\n"
                                  + "left outer join profesor on profesor.idProfesor = horario.idHorario\n"
                                  + "left outer join salon on salon.idSalon = horario.idSalon\n"
                                  + "left outer join horarioreal on horario.idHorario = horarioreal.idHorario\n"
                                  + "WHERE dias LIKE '%%'\n" 
                                  + "UNION\n"
                                  + "SELECT horario.idHorario, claveAsig as clave, nombreAsig as asignatura, grupo, salon,  Profesor.folio as folio,\n"
                                  + "concat(Profesor.titulo,' ',Profesor.nombre,' ',Profesor.apPatern,' ',Profesor.apMatern) as profesor,\n"
                                  + "concat(horaEntrada,' - ',horaSalida) as horario, dias\n"
                                  + "FROM asignaturaxhorario\n"
                                  + "left outer join asignatura on asignaturaxhorario.idAsignatura = asignatura.idAsignatura\n"
                                  + "left outer join horario on asignaturaxhorario.idHorario = horario.idHorario\n"
                                  + "left outer join profesor on profesor.idProfesor = horario.idHorario\n"
                                  + "left outer join salon on salon.idSalon = horario.idSalon\n"
                                  + "right outer join horariocomp on horario.idHorario = horariocomp.idHorario\n"
                                  + "WHERE dias LIKE '%%'\n" //para Buscar Dias repetidos, hay que verificar la parte de los traslapes
                                  + "order by asignatura;\n";
                        vista = orden.executeQuery(query);
			int pos = 0;
			//segun yo, aqui se debe de acomodar a como quieren que salga en iReport D: pero no sale
			while (vista.next()){
				horarios[pos][0] = vista.getString("Horario");
				horarios[pos][1] = vista.getString("Grupo");
				horarios[pos][2] = vista.getString("Salon");
				//horarios[pos][4] = vista.getString("Cupo");
				horarios[pos][3] = vista.getString("Vacante");
				horarios[pos][4] = obtenerDias(vista.getString("Dias"));
				horarios[pos][5] = vista.getString("Clave");
				horarios[pos][6] = vista.getString("Asignatura");
				horarios[pos][7] = vista.getString("Folio Profesor");
                                horarios[pos][8] = vista.getString("Profesor");
                                pos++;
			}
		}catch (SQLException e){
			JOptionPane.showMessageDialog(null, "Error, no se pueden mostrar los Horarios" + e.getMessage(),
				"ERROR", JOptionPane.ERROR_MESSAGE);
		}finally{
			ConexionBD.cerrarConexion();
		}
		return horarios;
	}
*/
        
                
        /*
        public static int validarHorario(Horario hr){
            int traslape = 0;
            
            try{
                conexion = ConexionBD.getConexion();	//obtiene conexión (nunca la crea aquí)

                orden = conexion.createStatement();	//crea objeto instrucción

                String query =            
                "select count(*) as traslapes from horario\n"+
                "where\n"+ 
                "idSalon = ?\n"+
                "and ((Dia1 = ? or Dia1 = ? or Dia1 = ?)\n"+ 
                "or (Dia2 = ? or Dia2 = ? or Dia2 = ?)\n"+ 
                "or (Dia3 = ? or Dia3 = ? or Dia3 = ?))\n"+
                "and ((? < horaEntrada\n"+
                "and ? > horaSalida) or\n"+
                "(? < horaSalida\n"+
                "and ? > horaEntrada));";
                
                sentencia = conexion.prepareStatement(query);
                
                if(hr.getDia2() == 7)
                    hr.setDia2(8);
                if(hr.getDia3() == 7)
                    hr.setDia3(8);
                sentencia.setInt(1, hr.getIdSalon());
                sentencia.setInt(2, hr.getDia1());
                sentencia.setInt(3, hr.getDia2());
                sentencia.setInt(4, hr.getDia3());
                sentencia.setInt(5, hr.getDia1());
                sentencia.setInt(6, hr.getDia2());
                sentencia.setInt(7, hr.getDia3());
                sentencia.setInt(8, hr.getDia1());
                sentencia.setInt(9, hr.getDia2());
                sentencia.setInt(10, hr.getDia3());
                sentencia.setTime(11, hr.getHoraEnrtada());
                sentencia.setTime(12, hr.getHoraSalida());
                sentencia.setTime(13, hr.getHoraEnrtada());
                sentencia.setTime(14, hr.getHoraSalida());
                
                
                vista = sentencia.executeQuery();

                if (vista.next()) {
                    traslape = vista.getInt("traslapes");
                }
            } catch(SQLException se){
                JOptionPane.showMessageDialog(null, "Error al leer los Datos: " + se.getMessage(), 
						"Error", JOptionPane.ERROR_MESSAGE);
            } finally {		
		ConexionBD.cerrarConexion();
            }
            return traslape;
        }
        */
/*
        public static int verificarGrupos(Horario hr){
            int gruposRep = 0;
            
            try{
                conexion = ConexionBD.getConexion();	//obtiene conexión (nunca la crea aquí)

                orden = conexion.createStatement();	//crea objeto instrucción

                String query =            
                "select count(*) as gruposRep from horario\n"+
                "where\n"+ 
                "idSalon = ?\n"+
                "and tipo = ?\n"+
                "and idAsignatura = ?;"; 
                
                
                sentencia = conexion.prepareStatement(query);
                
                
                sentencia.setInt(1, hr.getIdSalon());
                //sentencia.setString(2, hr.getTipo());
                //sentencia.setInt(3, hr.getIdAsignatura());
                
                
                
                vista = sentencia.executeQuery();

                if (vista.next()) {
                    gruposRep = vista.getInt("gruposRep");
                }
            } catch(SQLException se){
                JOptionPane.showMessageDialog(null, "Error al leer los Datos: " + se.getMessage(), 
						"Error", JOptionPane.ERROR_MESSAGE);
            } finally {		
		ConexionBD.cerrarConexion();
            }
            return gruposRep;
        }
        
}
*/
 //*************BUSQUEDA / BUSCAR****************
        


	//public static boolean buscar(Horario horario){}	





/*
package chuidiang.ejemplos.jtable;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 * Ejemplo de uso de TableRowSorter y RowFilter.
 * 
 * @author Chuidiang
 * 
 
public class PruebaJTable {

	private TableRowSorter<TableModel> modeloOrdenado;

	/**
	 * main del ejemplo.
	 * 
	 * @param args
	 
	public static void main(String[] args) {
		new PruebaJTable();
	}

	/**
	 * Instancia un JFrame con un JTable dentro y diez filas de datos. Lleva un
	 * trozo de código comentado para poder reemplazar.
	 
	public PruebaJTable() {
		JFrame v = new JFrame("Prueba JTable");

		// Modelo de datos, segunda columna Integer y primera String. Los
		// índices empiezan en cero.
		DefaultTableModel modelo = new DefaultTableModel() {
			@Override
			public Class getColumnClass(int columna) {
				if (columna == 1)
					return Integer.class;
				return String.class;
			}
		};

		// Añadimos unos datos.
		modelo.addColumn("columna 1");
		modelo.addColumn("columna 2");
		for (int i = 0; i < 10; i++) {
			modelo.addRow(new Object[] { "" + i, 100 - i });
		}

		// Metemos el modelo ordenable en la tabla.
		modeloOrdenado = new TableRowSorter<TableModel>(modelo);
		tabla.setRowSorter(modeloOrdenado);
		modeloOrdenado.setRowFilter(RowFilter.regexFilter("2", 1));

		// Lo pintamos todo en la ventana y la mostramos.
		JTable tabla = new JTable(modelo);
		JScrollPane scroll = new JScrollPane(tabla);
		v.getContentPane().add(scroll);
		v.pack();
		v.setVisible(true);
		v.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

	}*/