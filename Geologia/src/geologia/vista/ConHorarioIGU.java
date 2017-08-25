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
package geologia.vista;
import geologia.modelo.dao.HorarioDAO;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.TitledBorder;
import geologia.modelo.dao.SalonDAO; 
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import geologia.control.ConHorarioControl;
import geologia.modelo.dao.AsignaturaDAO;

/**
 *
 * @author 58599749
 */
public class ConHorarioIGU extends JInternalFrame{
    
    private static ConHorarioIGU ventanaCon;//define un objeto de esta clase para que sólo exista un objeto de ella
						//(patrón singleton) para usar este objeto se crea el método getVentana()
    private JButton btnBuscar = new JButton("Buscar por Salon");
    private JButton btnBuscar2 = new JButton("Buscar por Asignatura");
    private JButton btnReporte = new JButton("Guardar Reporte");
    private JTable tblLista = new JTable();
    private Box boxVerticalPrincipal = Box.createVerticalBox();
    private JComboBox jcbMostrar = new JComboBox();
    private JComboBox jcbMostrarAsig = new JComboBox();
    private ConHorarioControl control = new ConHorarioControl(this);
    
    private ConHorarioIGU(){
        
         super("*** CONSULTA DE HORARIOS ***", false, true, false, false); 
         
         addBxVerticalPrincipal();//crea y agrega el panel principal con todos los componentes
		
		//setEventos();	//define la clase controladora para los componentes
        setSize(800, 600);
                show();
    }
    
    private void addBxVerticalPrincipal(){
		
		addPanelEdicion();	//agrega panel de edición de datos
		addPanelTabla();	//agrega panel de tabla
                		//agrega barra de herramientas
		add(boxVerticalPrincipal);	//agrega el panel principal al Frame
    }
    
    private void addPanelEdicion() {
		
		//crea y define características del panel de Edición
		JPanel pnConsulta = new JPanel();
		pnConsulta.setBorder(new TitledBorder(null, "Consultar Horario", TitledBorder.CENTER, TitledBorder.TOP));
				//tipo de borde por omision, "Título", justificación borde, posición borde (arriba)
		pnConsulta.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
		
		//Crea panel de datos 
		JPanel pnDatos = new JPanel();
		pnDatos.setLayout(new GridLayout(5,2, 5, 5));
                
		
		//agrega componentes al panel de datos
		JLabel lbMostrar = new JLabel("Buscar Horario por Salon: ");
                JLabel lbMostrarAsig = new JLabel("Buscar Horario por Asignatura: ");
		pnDatos.add(lbMostrar);	
                jcbMostrar.setModel(new DefaultComboBoxModel(new String[] {}));
                pnDatos.add(jcbMostrar);
                pnDatos.add(lbMostrarAsig);
                jcbMostrarAsig.setModel(new DefaultComboBoxModel(new String[] {}));
		pnDatos.add(jcbMostrarAsig);
                llenarCB();
                llenarCB2();
                
                
                
		
		//txNombre.setActionCommand("NombreAlumno");	//para poder reconocerlo en el controlador con getActionCommand
		//agrega los paneles de datos y de botones al panel de Edición
		pnConsulta.add(pnDatos);
                pnDatos.add(getPanelBotones());
		
		boxVerticalPrincipal.add(pnConsulta);	//agrega el panel de Edición al panel principal de la ventana 
	}
    
         private JPanel getPanelBotones(){
		
		//crea y define características del panel de botones
		JPanel pnBotones = new JPanel();
		pnBotones.setLayout(new BoxLayout(pnBotones, BoxLayout.X_AXIS));
		
		//define características de botones de acciones		
		btnBuscar.setMnemonic('B');
                btnReporte.setMnemonic('G');
                btnBuscar2.setMnemonic('p');
                
                btnBuscar.addActionListener(control);
                btnBuscar2.addActionListener(control);
                btnReporte.addActionListener(control);
						
		//agrega botones con 5 pixeles de espacio entre ellos 
		pnBotones.add(btnBuscar);
                pnBotones.add(Box.createVerticalStrut(15));
                pnBotones.add(Box.createHorizontalStrut(15));//agrega 5 pixeles de expacio
                pnBotones.add(btnBuscar2);
		pnBotones.add(Box.createVerticalStrut(15));
                pnBotones.add(Box.createHorizontalStrut(15));//agrega 5 pixeles de expacio
                pnBotones.add(btnReporte);
  
		
		return pnBotones;	//regresa el panel
	}
    
        public void addPanelTabla() {

		//crea y define características del panel de la tabla
		JPanel pnLista = new JPanel();
		pnLista.setBorder(new TitledBorder(null, "Lista de Horarios", TitledBorder.CENTER, TitledBorder.TOP));
								//tipo de borde (omision), "titulo", justificación del título, posición del título
				
		JScrollPane scrTabla = new JScrollPane();				//scroll para ver barras de desplazamiento
		scrTabla.setPreferredSize(new Dimension(900, 200));		//redimensiona para que quepa en panel
		scrTabla.setViewportView(tblLista);	//agrega la tabla al scroll con viewport para poder ver los encabezados se columnas 
		pnLista.add(scrTabla);									//agrega scroll con la tabla al panel
                
		setTabla();	//define el modelo de la tabla (datos)
		boxVerticalPrincipal.add(pnLista);	//agrega el panel de la tabla al panel principal
	}
        public void setTabla() {
		
		String titulos[] = {"Horario", "Grupo","Salon","Dias","Clave", "Asignatura", "Folio Profesor","Profesor" };				//títulos de la tabla
		Object datos[][] = null;//HorarioDAO.obtenerHorarios("","");					//obtiene todos los datos de la base de datos

		final DefaultTableModel modeloTabla = new DefaultTableModel(){	//crea modelo de tabla
			
			private static final long serialVersionUID = 1L;			//objeto serializable

			public boolean isCellEditable(int row, int column) {  		//tabla no editable
				return false;
			}
		};
		
		modeloTabla.setDataVector(datos, titulos);		//define los datos y títulos del modelo
		tblLista.setModel(modeloTabla);					//especifica el modelo de datos de la tabla
		tblLista.changeSelection(0, 0, false, false);	//pone en renglon 0, columna 0, limpia selec. anterior  
		tblLista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);	//indica que sólo puede seleccionarse un reglón
                
                
		tblLista.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);				//evita que de tamalo de columnas automáticamente
                
                tblLista.getColumnModel().getColumn(0).setPreferredWidth(120);	//indica tamaño de columna 0
		tblLista.getColumnModel().getColumn(1).setPreferredWidth(40);
                tblLista.getColumnModel().getColumn(2).setPreferredWidth(40);
                tblLista.getColumnModel().getColumn(3).setPreferredWidth(40);
                tblLista.getColumnModel().getColumn(4).setPreferredWidth(40);
                tblLista.getColumnModel().getColumn(5).setPreferredWidth(40);
                tblLista.getColumnModel().getColumn(6).setPreferredWidth(40);
                tblLista.getColumnModel().getColumn(7).setPreferredWidth(40);
                
                
        }
    
        public static ConHorarioIGU getConVentana(){
					
		if(ventanaCon == null){				//si no se ha creado por única vez
			ventanaCon = new ConHorarioIGU();		//se crea
		}
		
		return ventanaCon;					
        }
    
    public void llenarCB() {
        jcbMostrar.removeAllItems(); //Vaciamos el JComboBox
        ArrayList<String> resultat;
        resultat = null; //SalonDAO.obtenerNombreSalon();//La consulta tiene que retornar un ArrayList
        jcbMostrar.addItem("");
        for(int i=0; i < resultat.size();i++){
            jcbMostrar.addItem(resultat.get(i));
        }
    }
    
    public void llenarCB2() {
        jcbMostrarAsig.removeAllItems(); //Vaciamos el JComboBox
        ArrayList<String> resultat;
        resultat = AsignaturaDAO.obtenerAsignatura();//La consulta tiene que retornar un ArrayList
        jcbMostrarAsig.addItem("");
        for(int i=0; i < resultat.size();i++){
            jcbMostrarAsig.addItem(resultat.get(i));
        }
    }
         

    public JComboBox getJcbMostrar() {
        return jcbMostrar;
    }
    public JComboBox getJcbMostrar2() {
        return jcbMostrarAsig;
    }
    public JTable getTblLista() {
        return tblLista;
    }
    
}
    

