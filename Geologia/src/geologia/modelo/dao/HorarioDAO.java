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
			
                       
                        
                       
				
			//INT = NUMERIC??
			//CHAR y VATCHAR = STIRNG??
                        
                        query = "INSERT INTO Horario(semestre,tipo,grupo,horaEntrada,horaSalida,idAsignatura,idSalon,idProfesor,Dia1,Dia2,Dia3) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
                        sentencia = conexion.prepareStatement(query);

                        
			sentencia.setString(1,horario.getSemestre());
			sentencia.setString(2,horario.getTipo());
			sentencia.setString(3,horario.getGrupo());
			sentencia.setTime(4,horario.getHoraEnrtada());
			sentencia.setTime(5,horario.getHoraSalida());
			sentencia.setInt(6,horario.getIdAsignatura());
			sentencia.setInt(7,horario.getIdSalon());
			sentencia.setInt(8,horario.getIdProfesor());
			sentencia.setInt(9,7);
			sentencia.setInt(10,7);
			sentencia.setInt(11,7);
			

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

		}catch(SQLException e){ //por qué "e"??
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
				"SET semestre = ?, tipo = ?, grupo = ?, "
                                        + "horaEntrada = ?, horaSalida = ?, idAsignatura = ?, idSalon = ?, idProfesor = ?, "
                                        + "Dia1 = ?, Dia2 = ?, Dia3 = ?" + 
				"WHERE idHorario = ?");

			sentencia.setInt(1,horario.getIdHorario());
			sentencia.setString(2,horario.getSemestre());
			sentencia.setString(3,horario.getTipo());
			sentencia.setString(4,horario.getGrupo());
			sentencia.setTime(5,horario.getHoraEnrtada());
			sentencia.setTime(6,horario.getHoraSalida());
			sentencia.setInt(7,horario.getIdAsignatura());
			sentencia.setInt(8,horario.getIdSalon());
			sentencia.setInt(9,horario.getIdProfesor());
			sentencia.setInt(10,7);
			sentencia.setInt(11,7);
			sentencia.setInt(12,7);

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
		return access; //return ok; 
	}
 //*************CONSULTAR****************
	public static Object[][] obtenerHorarios(String nombreProf, String materia, String salon, String semestre){
		
                Object horarios[][] = null;
		int totalTuplas = 0;
		String query;

		try{
			conexion = ConexionBD.getConexion();
			orden = conexion.createStatement();
                        
                        
			query = "SELECT Count(*)\n" +
                                "FROM Horario\n" +
                                "Inner JOIN Asignatura ON Horario.idAsignatura = Asignatura.idAsignatura\n" +
                                "INNER JOIN Profesor ON Horario.idProfesor = Profesor.idProfesor\n" +
                                "INNER JOIN Dias D1 ON Horario.Dia1 = D1.idDias\n" +
                                "INNER JOIN Dias D2 ON Horario.Dia2 = D2.idDias\n" +
                                "INNER JOIN Dias D3 ON Horario.Dia3 = D3.idDias\n" +
                                 "INNER JOIN salon ON horario.idSalon = salon.idSalon\n"+
                                "where concat(tituloProfesor,' ',nombreProfesor,' ',apellidoPaternoProfesor,' ',apellidoMaternoProfesor) like '%"+nombreProf+"%'\n"+
                                "AND salon like '%"+salon+"%'\n"+
                                "AND semestre like '%"+semestre+"%'\n"+
                                "AND nombreAsignatura like '%"+materia+"%';";
			vista = orden.executeQuery(query);

			vista.next();
			totalTuplas = vista.getInt(1);

			horarios = new Object[totalTuplas][13];
                        
                        query = "SELECT concat(Horario.horaEntrada, ' - ', Horario.horaSalida) as Horario, Horario.grupo as Grupo, Horario.tipo as Tipo, salon.salon as Salon, salon.cupoSalon as Cupo, salon.vacanteSalon as Vacante,\n"+ 
                                "D1.dias AS 'Dia 1', D2.dias AS 'Dia 2' , D3.dias AS 'Dia 3', Asignatura.claveAsignatura as Clave, Asignatura.nombreAsignatura as Asignatura, Profesor.folioProfesor as 'Folio Profesor',\n"+
                                "concat(Profesor.tituloProfesor,' ',Profesor.nombreProfesor,' ',Profesor.apellidoPaternoProfesor,' ', Profesor.apellidoMaternoProfesor) as Profesor\n"+
                                "FROM Horario\n"+
                                "Inner JOIN Asignatura ON Horario.idAsignatura = Asignatura.idAsignatura \n"+
                                "INNER JOIN Profesor ON Horario.idProfesor = Profesor.idProfesor\n"+
                                "INNER JOIN Dias D1 ON Horario.Dia1 = D1.idDias\n"+
                                "INNER JOIN Dias D2 ON Horario.Dia2 = D2.idDias\n"+
                                "INNER JOIN Dias D3 ON Horario.Dia3 = D3.idDias\n"+
                                "INNER JOIN salon ON horario.idSalon = salon.idSalon\n"+
                                "WHERE concat(tituloProfesor,' ',nombreProfesor,' ',apellidoPaternoProfesor,' ',apellidoMaternoProfesor) like '%"+nombreProf+"%'\n"+
                                "AND salon like '%"+salon+"%'\n"+
                                "AND nombreAsignatura like '%"+materia+"%'\n"+
                                "AND semestre like '%"+semestre+"%'\n"+
                                "ORDER BY Dia1, asignatura, horaEntrada, grupo, salon;";
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
				horarios[pos][6] = vista.getString("Dia 1");
				horarios[pos][7] = vista.getString("Dia 2");
				horarios[pos][8] = vista.getString("Dia 3"); 
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

                    query = "SELECT dias FROM Dias\n"+
                            "ORDER BY idDias;";		//query para contar profesores
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
                sentencia.setString(2, hr.getTipo());
                sentencia.setInt(3, hr.getIdAsignatura());
                
                
                
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