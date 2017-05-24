/*
 * Copyright (C) 2017 JoshPosh
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
package geologia.control;

/**
 *
 * @author JoshPosh
 */

import geologia.modelo.dao.HorarioDAO;

import geologia.modelo.dto.Asignatura;
import geologia.modelo.dto.Horario;
import geologia.modelo.dto.Profesor;
import geologia.modelo.dto.Salon;

import geologia.vista.PruebaHIGU;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JComboBox;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class HorarioControl extends KeyAdapter implements ActionListener, 
        DocumentListener,MouseListener, ListSelectionListener, ItemListener{

    private PruebaHIGU ventana;
    private Profesor prf ;
    private Asignatura asgn;
    private Salon sln;
    private Horario hr, hrM;

    public HorarioControl(PruebaHIGU ventana) {
        this.ventana = ventana;
    }
    
    ValidadorH validador = new ValidadorH() {
        
      
        
        @Override
        public boolean validarHorario() {
            
            boolean ok = true;
            
            //********************Horas***************************
            
            //Validando que los campos no esten vacios 
            if(ventana.getCbHrsE().getSelectedIndex() == 0 
                    || ventana.getCbHrsS().getSelectedIndex() == 0 
                    || ventana.getCbMinE().getSelectedIndex() == 0 
                    || ventana.getCbMinS().getSelectedIndex() == 0){
                ok = false;
            }
            
            
            //*******************Dias*****************************
            
            //Validamos que esté seleccionado un dia minimo 
            if (!ventana.getCkLunes().isSelected() 
                    && ventana.getCkMartes().isSelected() 
                    && ventana.getCkMiercoles().isSelected() 
                    && ventana.getCkJueves().isSelected() 
                    && ventana.getCkViernes().isSelected() 
                    && ventana.getCkSabado().isSelected()){
             
             JOptionPane.showMessageDialog(null, 
                    "Seleccione los dias que tendra el horario", "Advertencia", 
                    JOptionPane.ERROR_MESSAGE);
             ok = false;
            }
            
            //**********************Grupo**************************
            if(ventana.getTxGrupo().getText().isEmpty()){
                JOptionPane.showMessageDialog(null, 
                    "Ingrese el Grupo", "Advertencia", 
                    JOptionPane.ERROR_MESSAGE);
                    ok =false;
            }
                
            
            return ok;
            
        }
        
        @Override
        public boolean validarProfesor() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public boolean validarAsignatura() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public boolean vaildarSalon() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    };
    
    @Override
    public void itemStateChanged(ItemEvent e) {
        switch(e.getSource().toString()){
            
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case "Guardar": guardar();
                break;
            case "Modificar":
                break;
            case "Cancelar": ventana.limpiar();
                break;
        }
    }    
        /*
        if (e.getSource() == ventana.getJcSalon()){
            buscarSalon();
        }
        */
        
        
        /*
        
        
        modificar
        
        1 cargar los datos del nodo a modificar en un objeto horario
        2 eliminar el nodo horario y sus relaciones
        3 hacemos las modificaciones y las asignamos al objeto horario
        4 hacemos la insercion del nodo modificado: guardar()
        */
    public double horaEntrada(){
       
       
        double x, y;

        x = Double.parseDouble(ventana.getCbHrsE().toString());
        y = Double.parseDouble(ventana.getCbMinE().toString());
        double z = y / 60.0;
        
        return x + z;
       
       
   }
   
     public double horaSalida(){
       
       
        double hrS, minS;

        hrS = Double.parseDouble(ventana.getCbHrsS().toString());
        minS = Double.parseDouble(ventana.getCbMinS().toString());
        double horarioS = minS / 60.0;
        
        return hrS + minS;
       
       
   }
    
    
    public void guardar(){
        
    HorarioDAO.insertar(hr, sln, asgn, prf);
        ventana.limpiar();
        
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

    @Override
    public void keyTyped(KeyEvent e) {
        
    }
    
   public void buscarSalon(){
        /*
        String salon =  ventana.getJcSalon().getSelectedItem().toString();
        JTable tabla = ventana.getTblLista();
        
                TableRowSorter<TableModel>srtSalones = new TableRowSorter<>(tabla.getModel());	
				//crea sorter de tabla para el modelo de datos de tblLista
		RowFilter<TableModel, Integer> modeloFiltro = null;	//declra filtro

        try {
        	modeloFiltro = RowFilter.regexFilter(salon,3);	
        			//especifica el origen de los datos para el filtro (campo de texto de la barra de htas) 
        } catch (java.util.regex.PatternSyntaxException e) {	//captura excepción de datos incorrectos
        	return;												//y no hace nada solo regresa
        }
        
		tabla.setRowSorter(srtSalones);				//especifica el objeto de ordenamiento de datos de la tabla
                srtSalones.setRowFilter(modeloFiltro);			//especifica el filtro para el sorter
    

      */  
    }


    @Override
    public void valueChanged(ListSelectionEvent e) {
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        /*
        JTable tabla = ventana.getTblLista();										//obtiene la tabla
        int renglon = tabla.getSelectedRow();									//obtiene renglón seleccinado
        ventana.getTxtHoraE().setText("");
        ventana.getTxtHoraS().setText("");//toma id del renglón de la tabla
        ventana.getTxtGrupo().setText((String) (tabla.getValueAt(renglon, 1)));
        //ventana.getJcTipo().setSelectedItem((String) (tabla.getValueAt(renglon, 2)));
        ventana.getJcSalon().setSelectedItem((String) (tabla.getValueAt(renglon, 3)));
        ventana.getJcAsignatura().setSelectedItem((String)(tabla.getValueAt(renglon, 10)));
        ventana.getJcNombreProf().setSelectedItem((String)(tabla.getValueAt(renglon, 12)));
        */
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }


}
