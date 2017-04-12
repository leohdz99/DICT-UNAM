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

import java.sql.*;
import javax.swing.JOptionPane;
import geologia.modelo.ConexionBD;
import geologia.modelo.dto.Horario;
import geologia.modelo.dao.ProfesorDAO;
import geologia.modelo.dao.AsignaturaDAO;
import geologia.modelo.dao.SalonDAO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HorarioDAO{

	private static PreparedStatement sentencia;
	private static Statement orden;
	private static ResultSet vista;
	private static Connection conexion;

 //*************INSERTAR****************

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
                        
                        query = 
                                   "SELECT horario.idHorario, claveAsig as clave, nombreAsig as asignatura, grupo, salon,  Profesor.folio as folio,\n"
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
				horarios[pos][2] = vista.getString("Tipo");
				horarios[pos][3] = vista.getString("Salon");
				horarios[pos][4] = vista.getString("Cupo");
				horarios[pos][5] = vista.getString("Vacante");
				horarios[pos][6] = vista.getString("Dias");
				horarios[pos][9] = vista.getString("Clave");
				horarios[pos][10] = vista.getString("Asignatura");
				horarios[pos][11] = vista.getString("Folio Profesor");
                                horarios[pos][12] = vista.getString("Profesor");
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
        
        public static ArrayList<String> obtenerDias(){
            ArrayList<String> dias = new ArrayList<>();    //define el arreglo
		String query;				//cadena que espeficica la query
				
		try {
		        	
                    conexion = ConexionBD.getConexion();	//obtiene conexión (nunca la crea aquí)

                    orden = conexion.createStatement();	//crea objeto instrucción

                    query = "SELECT dias FROM horarioReal\n"+
                            "ORDER BY idHorario;";		//query para contar profesores
                    vista = orden.executeQuery(query);							//ejecuta query
		    
                    int pos = 0;
                    
                    while (vista.next()) {			//recorre todos los renglones

                        dias.add(vista.getString(1));
                        
		    }
		    //captura excepciones
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al leer los Datos: " + e.getMessage(), 
						"Error", JOptionPane.ERROR_MESSAGE);
		} finally {
				
			ConexionBD.cerrarConexion();
		}
		
		return dias;	
        }
        
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

	}
}
*/