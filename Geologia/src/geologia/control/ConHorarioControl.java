package geologia.control;

/*
 * Copyright (C) 2017 58599749
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
import java.awt.event.*;

import javax.swing.*;
import geologia.vista.ConHorarioIGU;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import geologia.vista.ConHorarioIGU;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class ConHorarioControl implements ActionListener, DocumentListener {
    
    private ConHorarioIGU ventana;

    public ConHorarioControl(ConHorarioIGU ventana) {
        this.ventana = ventana;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
            
        switch(e.getActionCommand()){
            
            case "Buscar": buscarSalon();
                break;
            case "Generar Reporte":     JOptionPane.showMessageDialog(null, "No Ere hake", "Ejemplo", JOptionPane.INFORMATION_MESSAGE);
                break;
        }
        
        switch(e.getSource().toString()){
            
        }
        
        
    }
    
    public void buscarSalon(){
        
        String salon =  ventana.getJcbMostrar().getSelectedItem().toString();
        JTable tabla = ventana.getTblLista();
        
                TableRowSorter<TableModel>srtSalones = new TableRowSorter<>(tabla.getModel());	
				//crea sorter de tabla para el modelo de datos de tblLista
		RowFilter<TableModel, Object> modeloFiltro = null;	//declra filtro

        try {
        	modeloFiltro = RowFilter.regexFilter(salon,3);	
        			//especifica el origen de los datos para el filtro (campo de texto de la barra de htas) 
        } catch (java.util.regex.PatternSyntaxException e) {	//captura excepción de datos incorrectos
        	return;												//y no hace nada solo regresa
        }
        
		tabla.setRowSorter(srtSalones);				//especifica el objeto de ordenamiento de datos de la tabla
                srtSalones.setRowFilter(modeloFiltro);			//especifica el filtro para el sorter
    

        
    }
    

    @Override
    public void insertUpdate(DocumentEvent e) {
        buscarSalon();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        buscarSalon();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
       buscarSalon();
    }
    
    
}
