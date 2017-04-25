/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geologia.vista;

/**
 *
 * @author josue
 */


import geologia.modelo.dao.ProfesorDAO;
import java.awt.*;
import java.sql.Time;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

public class PruebaProfIGU extends JInternalFrame {
    private static PruebaProfIGU ventana;		//define un objeto de esta clase para que sólo exista un objeto de ella
						//(patrón singleton) para usar este objeto se crea el método getVentana()
	
	
	//componentes
	//campos de texto
        private JTextField txtFolioProf = new JTextField(10);
	private JTextField txtTituloProf = new JTextField(10);
	private JTextField txtNombreProf = new JTextField(10);
        private JTextField txtApPatProf = new JTextField(10);
        private JTextField txtApMatProf = new JTextField(10);
        private JTextField txtRfcProf = new JTextField(10);
        private JComboBox jcActivo = new JComboBox();
        //botones
	private JButton btnGuardar = new JButton("Guardar");
        private JButton btnModificar = new JButton("Modificar");
	private JButton btnCancelar = new JButton("Cancelar");
	//tabla 
	private JTable tblLista = new JTable();
	//barra de herramientas
	//private BarraHtasIGU barraHtas;
	//panel principal de la ventana
	private Box boxVerticalPrincipal = Box.createVerticalBox();
        
        private String semestre;
        
	//constructor
	private PruebaProfIGU (){
		
                super("*** Profesores ***", false, true, false, true);
                		                             
		addBxVerticalPrincipal();//crea y agrega el panel principal con todos los componentes
		
		//setEventos();	//define la clase controladora para los componentes
		setSize(500, 700);
                
                
	}
	
	private void addBxVerticalPrincipal(){
		
		addPanelEdicion();	//agrega panel de edición de datos
		addPanelTabla();	//agrega panel de tabla
		//addBarraHtas();		//agrega barra de herramientas
		
                add(boxVerticalPrincipal);
                
	}
	
	private void addPanelEdicion() {
		
		//crea y define características del panel de Edición
		JPanel pnProfesor = new JPanel();
		pnProfesor.setBorder(new TitledBorder(null, "Edici\u00F3n de Profesores", TitledBorder.CENTER, TitledBorder.TOP));
				//tipo de borde por omision, "Título", justificación borde, posición borde (arriba)
		pnProfesor.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
		
		//Crea panel de datos 
		JPanel pnDatos = new JPanel();
		pnDatos.setLayout(new GridLayout(7, 2, 45, 5));
		
                JLabel lbFolio = new JLabel("Folio:");
                pnDatos.add(lbFolio);
                pnDatos.add(txtFolioProf);
                
		//agrega componentes al panel de datos
		JLabel lbTituloP = new JLabel("Titulo Profesor:");
		pnDatos.add(lbTituloP);						
		pnDatos.add(txtTituloProf);

		JLabel lbNombreP = new JLabel("Hora Salida:");
		pnDatos.add(lbNombreP);
                pnDatos.add(txtNombreProf);
                
                JLabel lbApPatP = new JLabel("Apellido Paterno:");
                pnDatos.add(lbApPatP);
                pnDatos.add(txtApPatProf);
                
                JLabel lbApMatP = new JLabel("Apellido Materno:");
                pnDatos.add(lbApMatP);
                pnDatos.add(txtApMatProf);
                
                JLabel lbRfcP = new JLabel("RFC:");
                pnDatos.add(lbRfcP);
                pnDatos.add(txtRfcProf);
                
                JLabel lbActivo = new JLabel("Activo");
                pnDatos.add(lbActivo);                
                String camposJC[] = {"", "S", "N"};
                for(int i = 0; i < camposJC.length; i++){
                    jcActivo.addItem(camposJC[i]);
                }                
                pnDatos.add(jcActivo);
		
                //agrega los paneles de datos y de botones al panel de Edición		
		pnProfesor.add(pnDatos);
		pnProfesor.add(getPanelBotones());	//crea panel de botones y lo agrega al panel de edición
		
		boxVerticalPrincipal.add(pnProfesor);	//agrega el panel de Edición al panel principal de la ventana 
	}
	
	private JPanel getPanelBotones(){
		
		//crea y define características del panel de botones
		JPanel pnBotones = new JPanel();
		pnBotones.setLayout(new BoxLayout(pnBotones, BoxLayout.Y_AXIS));
		
		//define características de botones de acciones		
		btnGuardar.setMnemonic('G');
                btnCancelar.setMnemonic('C');
                btnModificar.setMnemonic('M');
						
		//agrega botones con 5 pixeles de espacio entre ellos 
		pnBotones.add(btnGuardar);	
		pnBotones.add(Box.createVerticalStrut(5));	//agrega 5 pixeles de expacio
		pnBotones.add(btnCancelar);
                pnBotones.add(Box.createVerticalStrut(5));
                pnBotones.add(btnModificar);
                
		
		return pnBotones;	//regresa el panel
	}

	public void addBarraHtas() {
		
		//barraHtas = new BarraHtasIGU(this);	//crea barra de herramientas
		//boxVerticalPrincipal.add(barraHtas); //agrega barra a ventana principal
	}
	
	public void addPanelTabla() {

		//crea y define características del panel de la tabla
		JPanel pnLista = new JPanel();
		pnLista.setBorder(new TitledBorder(null, "Lista de Profesores", TitledBorder.CENTER, TitledBorder.TOP));	
								//tipo de borde (omision), "titulo", justificación del título, posición del título
				
		JScrollPane scrTabla = new JScrollPane();				//scroll para ver barras de desplazamiento
		scrTabla.setPreferredSize(new Dimension(420, 250));		//redimensiona para que quepa en panel
		scrTabla.setViewportView(tblLista);	//agrega la tabla al scroll con viewport para poder ver los encabezados se columnas 
		pnLista.add(scrTabla);									//agrega scroll con la tabla al panel

		setTabla();	//define el modelo de la tabla (datos)
		boxVerticalPrincipal.add(pnLista);
             //agrega el panel de la tabla al panel principal
	}
	
	public void setTabla() {
		
		String titulos[] = {"RFC", "Folio","Profesor","Activo"};				//títulos de la tabla
		Object datos[][] = ProfesorDAO.obtenerProfesor("");					//obtiene todos los datos de la base de datos

		final DefaultTableModel modeloTabla = new DefaultTableModel(){	//crea modelo de tabla
			
			@Override
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
		tblLista.getColumnModel().getColumn(1).setPreferredWidth(120);
                tblLista.getColumnModel().getColumn(2).setPreferredWidth(120);
                tblLista.getColumnModel().getColumn(3).setPreferredWidth(40);
        }
        

	/*
        
	public void setCampos() {
		
		int renglon = tblLista.getSelectedRow();					//obtiene renglón seleccionado
		
		if (renglon >= 0) {											//si hay renglón seleccionado
			txId.setText((String) tblLista.getValueAt(renglon, 0));	//toma id y lo asigna al campo de texto txId
			txId.requestFocus();									//le asigna el cursor
			txNombre.setText((String) (tblLista.getValueAt(tblLista.getSelectedRow(), 1)));	//toma y asigna nombre
		}		
	}*/
	

	
	public static PruebaProfIGU getVentana(){
					
		if(ventana == null){				//si no se ha creado por única vez
			ventana = new PruebaProfIGU();		//se crea
		}
		
		return ventana;					
	}

    public JTable getTblLista() {
        return tblLista;
    }
   
        
}
