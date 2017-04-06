/*
 * Copyright (C) 2017 javigen
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
import geologia.modelo.dto.HorarioComp;
import geologia.modelo.dao.ProfesorDAO;
import geologia.modelo.dao.AsignaturaDAO;
import geologia.modelo.dao.SalonDAO;
import java.util.ArrayList;

/**
 *
 * @author javigen
 */
public class HorarioCompDAO {
    
    private static PreparedStatement sentencia;
    private static Statement orden;
    private static ResultSet vista;
    private static Connection conexion;
    
    //*************INSERTAR****************

	public static boolean insertar(HorarioComp horario){
		int constraint;
                String query;
		boolean access = true;

		try{
			
                    
                        conexion = ConexionBD.getConexion();
			
              
			query = "INSERT INTO HorarioComp(idHorario,horaEntrada,horaSalida,dias) VALUES (?, ?, ?, ?);";
                        sentencia = conexion.prepareStatement(query);
                        
                        sentencia.setInt(1,horario.getIdHorarioC());
			sentencia.setFloat(2,horario.getHoraEnrtadaC());
        		sentencia.setFloat(3,horario.getHoraSalidaC());
			sentencia.setInt(4,horario.getDiasC());
			
			

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
		return access; 
	}

 //*************ACTUALIZAR****************

	public static boolean actualizar(HorarioComp horario){
		int constraint;
		boolean access = true;

		try{
			conexion = ConexionBD.getConexion();
			sentencia = conexion.prepareStatement(
				"UPDATE HorarioReal " + 
				"SET horaEntrada = ?, horaSalida = ?, dias = ?, " +
                                "WHERE idHorario = ?");

			sentencia.setInt(1,horario.getIdHorarioC());
			sentencia.setFloat(2,horario.getHoraEnrtadaC());
        		sentencia.setFloat(3,horario.getHoraSalidaC());
			sentencia.setInt(4,horario.getDiasC());
			

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
        
        

    
}
