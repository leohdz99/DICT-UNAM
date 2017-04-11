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
import geologia.modelo.dao.AsignaturaDAO;
import geologia.modelo.dao.HorarioDAO;
import geologia.modelo.dao.ProfesorDAO;
import geologia.modelo.dao.SalonDAO;
import geologia.modelo.dao.HorarioRealDAO;
import geologia.modelo.dao.HorarioCompDAO;
import geologia.modelo.dto.Horario;
import geologia.modelo.dto.HorarioReal;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import geologia.vista.HorarioIGU;
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
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class HorarioControl extends KeyAdapter implements ActionListener, 
        DocumentListener,MouseListener, ListSelectionListener, ItemListener{

    private HorarioIGU ventana;

    public HorarioControl(HorarioIGU ventana) {
        this.ventana = ventana;
    }
    
    ValidadorH validador = new ValidadorH() {
        
        private Pattern patron;
        private Matcher igual;
        private final String FORMATO_HORA = "[0-2][0-9][.][0-5][0-9]";
        
        @Override
        public boolean validarHora(String hora) {
            
            boolean val = true;
            
            patron = Pattern.compile(FORMATO_HORA);
            igual = patron.matcher(hora);
                        
            if(!igual.matches()){
                JOptionPane.showMessageDialog(null, 
                        "El formato de hora es incorrecto", "Advertencia", 
                        JOptionPane.ERROR_MESSAGE);
                val = false;
            }
            
            return val;
        }

        @Override
        public boolean validarDias() {
            boolean val = false;
            
            
            return val;
        }

        @Override
        public boolean validarGrupo(String grupo) {
            boolean val = true;
            if(grupo.isEmpty()){
                JOptionPane.showMessageDialog(null, 
                    "Ingrese el Grupo", "Advertencia", 
                    JOptionPane.ERROR_MESSAGE);
                    val =false;
            }else{ 
                if (!isNumeric(grupo)) {
                    JOptionPane.showMessageDialog(null, 
                    "Solo numeros en el campo del grupo", "Advertencia", 
                    JOptionPane.ERROR_MESSAGE);
                    val =false;
                } 
            }
            return val;
        }

        @Override
        public boolean vaildarComboBox(JComboBox cb) {
            boolean val = true;
                       
            if(cb.getSelectedIndex() == 0)
                       val = false;
                       cb.requestFocus();
            return val;
        }
        
        private boolean isNumeric(String cadena){
            try {
                    Integer.parseInt(cadena);
                    return true;
            } catch (NumberFormatException nfe){
                    return false;
            }
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
        
        if (e.getSource() == ventana.getJcSalon()){
            buscarSalon();
        }
    }
    
    
    public void guardar(){
        
        if (!validador.vaildarComboBox(ventana.getJcAsignatura())){
            JOptionPane.showMessageDialog(null, "Falta la Asignatura", 
                    "advertencia", JOptionPane.ERROR_MESSAGE);
            ventana.getJcAsignatura().requestFocus();
        }else if(!validador.vaildarComboBox(ventana.getJcNombreProf())){
            JOptionPane.showMessageDialog(null, "Falta profesor", 
                    "advertencia", JOptionPane.ERROR_MESSAGE);
            ventana.getJcNombreProf().requestFocus();
        }else if(!validador.vaildarComboBox(ventana.getJcSalon())){
            JOptionPane.showMessageDialog(null, "Falta salon", 
                    "advertencia", JOptionPane.ERROR_MESSAGE);
            ventana.getJcSalon().requestFocus();
        } else if (!validador.validarGrupo(ventana.getTxtGrupo().getText())){
             JOptionPane.showMessageDialog(null, "Error en alguno de los campos", 
                    "advertencia", JOptionPane.ERROR_MESSAGE);
             ventana.getTxtGrupo().requestFocus();
        } else if (!validador.validarHora(ventana.getTxtHoraE().getText())){
            JOptionPane.showMessageDialog(null, "Error en alguno de los campos", 
                    "advertencia", JOptionPane.ERROR_MESSAGE);
            ventana.getTxtHoraE().requestFocus();
        } else if(!validador.validarHora(ventana.getTxtHoraS().getText())){
            JOptionPane.showMessageDialog(null, "Error en alguno de los campos", 
                    "advertencia", JOptionPane.ERROR_MESSAGE);
            ventana.getTxtHoraS().requestFocus();
        } else  {
            JOptionPane.showMessageDialog(null, "Campos correctamente validados", 
                    "Advertencia", JOptionPane.INFORMATION_MESSAGE);
            
            Horario horario =  new Horario(ventana.getSemestre(), 
                
                ventana.getTxtGrupo().getText(),
                ventana.getTxtHoraE().getText(),
                ventana.getTxtHoraS().getText(), 
                AsignaturaDAO.getIdAsignatura(ventana.getJcAsignatura()
                        .getSelectedItem().toString()), 
                SalonDAO.getIdSalon(ventana.getJcSalon()
                        .getSelectedItem().toString()), 
                ProfesorDAO.getIdPorfesor(ventana.getJcNombreProf().
                        getSelectedItem().toString()));
            
            System.out.println(horario.toString());
            //System.out.println(HorarioDAO.validarHorario(horario));
            System.out.println(HorarioDAO.verificarGrupos(horario));
            
            int traslapes =  0;//HorarioDAO.validarHorario(horario);
            int grupoRepetido = HorarioDAO.verificarGrupos(horario);
            
            
           
            if(traslapes == 0){
                if(grupoRepetido == 0){
                    HorarioDAO.insertar(horario);
                    ventana.limpiar();
                    ventana.setTabla();
                    
                }else{
                    JOptionPane.showMessageDialog(null, 
                    "El Grupo que tratas de ingresar ya existe", 
                    "Advertencia", JOptionPane.INFORMATION_MESSAGE);
                }
                
            }else{
                JOptionPane.showMessageDialog(null, 
                    "El horario que tratas de ingresar se Traslapa con "+traslapes+" horarios", 
                    "Advertencia", JOptionPane.INFORMATION_MESSAGE);
            }
            //ventana.limpiar();
        }
        
        
        
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
    

        
    }


    @Override
    public void valueChanged(ListSelectionEvent e) {
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        JTable tabla = ventana.getTblLista();										//obtiene la tabla
        int renglon = tabla.getSelectedRow();									//obtiene renglón seleccinado
        ventana.getTxtHoraE().setText("");
        ventana.getTxtHoraS().setText("");//toma id del renglón de la tabla
        ventana.getTxtGrupo().setText((String) (tabla.getValueAt(renglon, 1)));
        //ventana.getJcTipo().setSelectedItem((String) (tabla.getValueAt(renglon, 2)));
        ventana.getJcSalon().setSelectedItem((String) (tabla.getValueAt(renglon, 3)));
        ventana.getJcAsignatura().setSelectedItem((String)(tabla.getValueAt(renglon, 10)));
        ventana.getJcNombreProf().setSelectedItem((String)(tabla.getValueAt(renglon, 12)));
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
