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
import geologia.modelo.dao.SalonDAO;
import geologia.modelo.dao.ProfesorDAO;

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
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

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
            String dias[] = obtenerDias().split("");
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
            if (obtenerDias().equals("")){
                
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
            
            //********validar horas entrada con las horas salida*****
            if(horaEntrada() > horaSalida()){
                ok = false;
            }
            
            for(int i=0;i<dias.length; i++) {
                if(!HorarioDAO.verificarHorario(
                    dias[i], 
                    (String)ventana.getCbAsignatura().getSelectedItem(), 
                    (String)ventana.getCbProfesor().getSelectedItem(), 
                    (String)ventana.getCbSalon().getSelectedItem(), 
                    (String)ventana.getTxGrupo().getText(), 
                    horaEntrada(), 
                    horaSalida())){
                
                    ok = false;
                }
            }
            
            
            return ok;
            
        }
        
       
        
        @Override
        public boolean validarProfesor() {
            
            String dias[] = obtenerDias().split("");
            boolean ok = true;
            for(int i=0;i<dias.length; i++) {
             if(!ProfesorDAO.verificarProfesor((String)ventana.getCbProfesor().getSelectedItem())){
                ok = false;
                }
            }
            return ok;
        }

        @Override
        public boolean vaildarSalon() {
            
            String dias[] = obtenerDias().split("");
            boolean ok = true;
            for(int i=0;i<dias.length; i++) {
                if(!SalonDAO.verificarSalon((String)ventana.getCbSalon().getSelectedItem())){
                ok = false;
                }
            }
            return ok;
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

        x = Double.parseDouble((String)ventana.getCbHrsE().getSelectedItem());
        y = Double.parseDouble((String)ventana.getCbMinE().getSelectedItem());
        double z = y / 60.0;
        
        return x + z;
       
       
   }
   
     public double horaSalida(){
       
       
        double x, y;

        x = Double.parseDouble((String)ventana.getCbHrsS().getSelectedItem());
        y = Double.parseDouble((String)ventana.getCbMinS().getSelectedItem());
        double z = y / 60.0;
        
        return x + z;
       
       
       
   }
    
    
    public void guardar(){
        
    if(validador.validarHorario()){
        
        hr = new Horario(                 
                (String)ventana.getTxGrupo().getText(), 
                horaEntrada(), 
                horaSalida(),
                obtenerDias()
        );
        sln = new Salon();
        asgn = new Asignatura();
        prf = new Profesor();
        
        sln.setSalon((String)ventana.getCbSalon().getSelectedItem());
        asgn.setNomAsig((String)ventana.getCbAsignatura().getSelectedItem());
        prf.setProf((String)ventana.getCbProfesor().getSelectedItem());
        
        if(HorarioDAO.insertar(hr, sln, asgn, prf)){
            JOptionPane.showMessageDialog(null, 
                    "insertado correctamente", "Advertencia", 
                    JOptionPane.INFORMATION_MESSAGE);
        }        
    } 
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
    
    public String obtenerDias(){
        
        String aux = "";
        if(ventana.getCkLunes().isSelected()){
            aux += "1";
        }
        if(ventana.getCkMartes().isSelected()){
            aux += "2";
        }
        if(ventana.getCkMiercoles().isSelected()){
            aux += "3";
        }
        if(ventana.getCkJueves().isSelected()){
            aux += "4";
        }
        if(ventana.getCkViernes().isSelected()){
            aux += "5";
        }
        if(ventana.getCkSabado().isSelected()){
            aux += "6";
        }
            
        return aux;
    }
    
   public void buscarSalon(){
        String salon = (String)ventana.getCbSalon().getSelectedItem();
        
        JTable tabla = ventana.getTbLista();
        
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
