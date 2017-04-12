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
import geologia.modelo.dao.HorarioDAO;
import geologia.modelo.dto.Horario;
import java.awt.event.*;

import javax.swing.*;
import geologia.vista.ConHorarioIGU;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import com.sun.org.apache.xerces.internal.impl.io.MalformedByteSequenceException;
import geologia.modelo.ConexionBD;
import java.io.InputStream;
import java.net.URL;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import geologia.vista.GeneradorPDF;
import java.io.FileOutputStream;

public class ConHorarioControl implements ActionListener, DocumentListener {
    
    private ConHorarioIGU ventana;
    private Connection ConexionBD;

    public ConHorarioControl(ConHorarioIGU ventana) {
        this.ventana = ventana;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
            
        switch(e.getActionCommand()){
            
             case "Buscar por Salon": buscarSalon();
                           // buscarAsignatura();
                break;
            case "Buscar por Asignatura": buscarAsignatura();
                    break;
            case "Guardar Reporte": GeneradorPDF g = new GeneradorPDF();
                                    g.setVisible(true);
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
     public void buscarAsignatura(){
        
        String asignatura =  ventana.getJcbMostrar2().getSelectedItem().toString();
        JTable tabla = ventana.getTblLista();
        
                TableRowSorter<TableModel>srtAsignatura = new TableRowSorter<TableModel>(tabla.getModel());	
				//crea sorter de tabla para el modelo de datos de tblLista
		RowFilter<TableModel, Object> modeloFiltro = null;	//declra filtro

        try {
        	modeloFiltro = RowFilter.regexFilter(asignatura,10);	
        			//especifica el origen de los datos para el filtro (campo de texto de la barra de htas) 
        } catch (java.util.regex.PatternSyntaxException e) {	//captura excepción de datos incorrectos
        	return;												//y no hace nada solo regresa
        }
        
		tabla.setRowSorter(srtAsignatura);				//especifica el objeto de ordenamiento de datos de la tabla
                srtAsignatura.setRowFilter(modeloFiltro);			//especifica el filtro para el sorter
    

        
    }
    

    @Override
    public void insertUpdate(DocumentEvent e) {
        buscarSalon();
        buscarAsignatura();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        buscarSalon();
        buscarAsignatura();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
       buscarSalon();
       buscarAsignatura();
    }
    
    
}
