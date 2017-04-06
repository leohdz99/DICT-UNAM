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

import geologia.control.HorarioControl;
import geologia.modelo.dao.AsignaturaDAO;
import geologia.modelo.dao.HorarioDAO;
import geologia.modelo.dao.ProfesorDAO;
import geologia.modelo.dao.SalonDAO;
import java.awt.*;
import java.sql.Time;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

public class HorarioIGU extends JInternalFrame {
    private static HorarioIGU ventana;		//define un objeto de esta clase para que sólo exista un objeto de ella
						//(patrón singleton) para usar este objeto se crea el método getVentana()
	private String semestre;
	
	//componentes
	//campos de texto
	
    
        private JComboBox jcTipo = new JComboBox();
        private JComboBox jcSalon = new JComboBox();
        private JComboBox jcAsignatura = new JComboBox();
        private JTextField txtGrupo = new JTextField();
        private JTextField txtHoraE = new JTextField(10);	
	private JTextField txtHoraS = new JTextField(10);
        private JComboBox jcNombreProf = new JComboBox();
        private Checkbox chkLunes = new Checkbox("Lunes");
        private Checkbox chkMartes = new Checkbox("Martes");
        private Checkbox chkMiercoles = new Checkbox("Miercoles");
        private Checkbox chkJueves = new Checkbox("Jueves");
        private Checkbox chkViernes = new Checkbox("Viernes");
        private Checkbox chkSabado = new Checkbox("Sabado");
        
       
        JLabel lbHoraE = new JLabel("Hora Entrada:");
        JLabel lbHoraS = new JLabel("Hora Salida:");
        JLabel lbTipo = new JLabel("Tipo:");
        JLabel lbSalon = new JLabel("Salon:");
        JLabel lbAsignatura = new JLabel("Asignatura:");
        JLabel lbGrupo = new JLabel("Grupo:");
        JLabel lbProfesor = new JLabel("Profesor:");
        //botones
	private JButton btnGuardar = new JButton("Guardar");
	private JButton btnCancelar = new JButton("Cancelar");
        private JButton btnModificar = new JButton("Modificar");
	//tabla 
	private JTable tblLista = new JTable();
	//barra de herramientas
	//private BarraHtasIGU barraHtas;
	//panel principal de la ventana
	private Box boxVerticalPrincipal = Box.createVerticalBox();
        private HorarioControl controlador = new HorarioControl(this);
        
	//constructor
	private HorarioIGU (String sem){
		
                
                super("*** Horarios ***", false, true, false, true);
		this.semestre = sem;
		addBxVerticalPrincipal();//crea y agrega el panel principal con todos los componentes
		
		setEventos();	//define la clase controladora para los componentes
		setSize(1000, 700);
                
                
	}
	
	private void addBxVerticalPrincipal(){
		
		addPanelEdicion();	//agrega panel de edición de datos
		addPanelTabla();	//agrega panel de tabla
		//addBarraHtas();		//agrega barra de herramientas
		add(boxVerticalPrincipal);	//agrega el panel principal al Frame
	}
	
	private void addPanelEdicion() {
		
		//crea y define características del panel de Edición
		JPanel pnHorario = new JPanel();
		pnHorario.setBorder(new TitledBorder(null, "Edici\u00F3n de Horario del semestre: "+semestre, TitledBorder.CENTER, TitledBorder.TOP));
				//tipo de borde por omision, "Título", justificación borde, posición borde (arriba)
		pnHorario.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
		
		//Crea panel de datos 
		JPanel pnDatos = new JPanel();
		pnDatos.setLayout(new GridLayout(8, 2, 25, 5));
		
                pnDatos.add(lbAsignatura);
                jcAsignatura.setModel(new DefaultComboBoxModel(new String[] {}));
                llenarJcAsignatura();
                pnDatos.add(jcAsignatura);
                
                pnDatos.add(lbTipo);
                String tipoAsig[] = {"", "T", "P", "C"};
                for(int i = 0; i < tipoAsig.length ; i++){
                    jcTipo.addItem(tipoAsig[i]);
                }
                pnDatos.add(jcTipo);
             
                pnDatos.add(lbGrupo);             
                pnDatos.add(txtGrupo);
                
                pnDatos.add(lbProfesor);
                jcNombreProf.setModel(new DefaultComboBoxModel(new String[] {}));
                //llenarJcNombreProf();
                pnDatos.add(jcNombreProf);
		
                pnDatos.add(lbSalon);
                jcSalon.setModel(new DefaultComboBoxModel(new String[] {}));
                //llenarJcSalon();
                pnDatos.add(jcSalon);
                
		pnDatos.add(lbHoraE);						
		pnDatos.add(txtHoraE);

		pnDatos.add(lbHoraS);	
                pnDatos.add(txtHoraS);
                
                JPanel pnDias = new JPanel();
                pnDias.setLayout(new GridLayout(1,0));
                pnDias.setBorder(new TitledBorder(null, "Seleccione los Dias", TitledBorder.CENTER, TitledBorder.TOP));
                pnDias.add(chkLunes);
                pnDias.add(chkMartes);
                pnDias.add(chkMiercoles);
                pnDias.add(chkJueves);
                pnDias.add(chkViernes);
                pnDias.add(chkSabado);
                
                pnDatos.add(pnDias);
		pnHorario.add(pnDatos);
                pnHorario.add(getPanelBotones());	//crea panel de botones y lo agrega al panel de edición
		
                
		boxVerticalPrincipal.add(pnHorario);	//agrega el panel de Edición al panel principal de la ventana 
	}
	
	private JPanel getPanelBotones(){
		
		//crea y define características del panel de botones
		JPanel pnBotones = new JPanel();
		pnBotones.setLayout(new BoxLayout(pnBotones, BoxLayout.Y_AXIS));
		
		//define características de botones de acciones		
		btnGuardar.setMnemonic('G');
						
		//agrega botones con 5 pixeles de espacio entre ellos 
		pnBotones.add(btnGuardar);	
		pnBotones.add(Box.createVerticalStrut(5));	//agrega 5 pixeles de expacio
		pnBotones.add(btnCancelar);
                pnBotones.add(Box.createVerticalStrut(5));
                btnModificar.setEnabled(false);
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
		pnLista.setBorder(new TitledBorder(null, "Lista de Horarios", TitledBorder.CENTER, TitledBorder.TOP));	
								//tipo de borde (omision), "titulo", justificación del título, posición del título
				
		JScrollPane scrTabla = new JScrollPane();				//scroll para ver barras de desplazamiento
		scrTabla.setPreferredSize(new Dimension(900, 300));		//redimensiona para que quepa en panel
		scrTabla.setViewportView(tblLista);	//agrega la tabla al scroll con viewport para poder ver los encabezados se columnas 
		pnLista.add(scrTabla);									//agrega scroll con la tabla al panel
			
		setTabla();	//define el modelo de la tabla (datos)
		boxVerticalPrincipal.add(pnLista);	//agrega el panel de la tabla al panel principal
	}
	
	public void setTabla() {
		
		String titulos[] = {"Horario", "Grupo","Tipo","Salon","Cupo","Vacante","Dia 1","Dia 2","Dia 3","Clave", "Asignatura", "Folio Profesor","Profesor"  };				//títulos de la tabla
		Object datos[][] = null;//HorarioDAO.obtenerHorarios("","", "", semestre);					//obtiene todos los datos de la base de datos

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
                tblLista.getColumnModel().getColumn(8).setPreferredWidth(40);
                tblLista.getColumnModel().getColumn(9).setPreferredWidth(60);
                tblLista.getColumnModel().getColumn(10).setPreferredWidth(250);
                tblLista.getColumnModel().getColumn(11).setPreferredWidth(60);
                tblLista.getColumnModel().getColumn(12).setPreferredWidth(300);
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
	
        public void setEventos(){
            btnCancelar.addActionListener(controlador);
            btnGuardar.addActionListener(controlador);
            btnModificar.addActionListener(controlador);
            jcAsignatura.addActionListener(controlador);
            jcNombreProf.addActionListener(controlador);
            jcSalon.addActionListener(controlador);
            tblLista.addMouseListener(controlador);
            tblLista.getSelectionModel().addListSelectionListener(controlador);
        }
	
	public static HorarioIGU getVentana(String sem){
					
		if(ventana == null){				//si no se ha creado por única vez
			ventana = new HorarioIGU(sem);		//se crea
		}
		
		return ventana;					
	}

    public JTable getTblLista() {
        return tblLista;
    }
    

    public void llenarJcNombreProf() {
        jcNombreProf.removeAllItems(); //Vaciamos el JComboBox
        ArrayList<String> resultat;
        resultat = ProfesorDAO.obtenerNombreProfesor("");//La consulta tiene que retornar un ArrayList
        jcNombreProf.addItem("");
        for(int i=0; i < resultat.size();i++){
            jcNombreProf.addItem(resultat.get(i));
        }
    }

    public void llenarJcAsignatura() {
        jcAsignatura.removeAllItems(); //Vaciamos el JComboBox
        ArrayList<String> resultat;
        resultat = AsignaturaDAO.obtenerNombreAsignatura("");//La consulta tiene que retornar un ArrayList
        jcAsignatura.addItem("");
        for(int i=0; i < resultat.size();i++){
            jcAsignatura.addItem(resultat.get(i));
        }
    }
    
    public void llenarJcSalon() {
        jcSalon.removeAllItems(); //Vaciamos el JComboBox
        ArrayList<String> resultat;
        resultat = SalonDAO.obtenerNombreSalon();//La consulta tiene que retornar un ArrayList
        jcSalon.addItem("");
        for(int i=0; i < resultat.size();i++){
            jcSalon.addItem(resultat.get(i));
        }
    }
    
    public void llenarJcDias(JComboBox jcb){
        jcb.removeAllItems();
        ArrayList<String> resultados;
        resultados = HorarioDAO.obtenerDias();
        for (int i = 0; i < resultados.size(); i++) {
            jcb.addItem(resultados.get(i));
        }
        jcb.setSelectedIndex(6);
    }

    public JComboBox getJcTipo() {
        return jcTipo;
    }

    public JComboBox getJcSalon() {
        return jcSalon;
    }

    public JComboBox getJcAsignatura() {
        return jcAsignatura;
    }

    public JTextField getTxtGrupo() {
        return txtGrupo;
    }

    public JTextField getTxtHoraE() {
        return txtHoraE;
    }

    public JTextField getTxtHoraS() {
        return txtHoraS;
    }

    public JComboBox getJcNombreProf() {
        return jcNombreProf;
    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    public JButton getBtnCancelar() {
        return btnCancelar;
    }

    public JButton getBtnModificar() {
        return btnModificar;
    }

    public String getSemestre() {
        return semestre;
    }

    public HorarioIGU() {
    }
    public void limpiar(){
        jcAsignatura.setSelectedIndex(0);
        jcSalon.setSelectedIndex(0);
        jcNombreProf.setSelectedIndex(0);
        jcTipo.setSelectedIndex(0);
        txtGrupo.setText("");
        txtHoraE.setText("");
        txtHoraS.setText("");
    }
    
    

        
}
