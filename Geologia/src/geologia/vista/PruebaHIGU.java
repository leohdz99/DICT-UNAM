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
import geologia.modelo.dao.HorarioRealDAO;
import geologia.modelo.dao.HorarioCompDAO;
import geologia.modelo.dao.HorarioDAO;
import geologia.modelo.dao.ProfesorDAO;
import geologia.modelo.dao.SalonDAO;
import java.awt.*;
import java.sql.Time;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

public class PruebaHIGU extends JInternalFrame {
    
    //define un objeto de esta clase para que sólo exista un objeto de ella
    //(patrón singleton) para usar este objeto se crea el método getVentana()
    private static PruebaHIGU ventana;	
    
    //Declaración del controlador de los eventos para la ventana
    private HorarioControl control;
    
    //Declaración de los componetes de la ventana
    private JButton btnCancelar;
    private JButton btnGuardar;
    private JButton btnModificar;
    private JComboBox cbAsignatura;
    private JComboBox cbHrsE;
    private JComboBox cbHrsS;
    private JComboBox cbMinE;
    private JComboBox cbMinS;
    private JComboBox cbProfesor;
    private JComboBox cbSalon;
    private JCheckBox ckJueves;
    private JCheckBox ckLunes;
    private JCheckBox ckMartes;
    private JCheckBox ckMiercoles;
    private JCheckBox ckSabado;
    private JCheckBox ckViernes;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JScrollPane scTabla;
    private JLabel lbAsignatura;
    private JLabel lbDias;
    private JLabel lbEdicion;
    private JLabel lbGrupo;
    private JLabel lbHora;
    private JLabel lbHoraE;
    private JLabel lbHoraS;
    private JLabel lbLista;
    private JLabel lbProfesor;
    private JLabel lbSalon;
    private JTable tbLista;
    private JTextField txGrupo;
    
    
    public PruebaHIGU(){
        super("***Horarios***", true, true, true, true);
        inicializarComp();
        agregarEtiquetas();
        agregarCombo();
        agregarCheck();
        agregarBotones();
        agregarTabla();
        establecerLayout();
        
    }
    
    // Metodo para creación del objeto (Patrón Singleton).
    public static PruebaHIGU getVentana(){
					
		if(ventana == null){				//si no se ha creado por única vez
			ventana = new PruebaHIGU();		//se crea
		}
		
		return ventana;					
    }
    
    // Metodo para inicializar los componentes de la ventana
    private void inicializarComp(){
        
        lbEdicion = new JLabel();
        lbAsignatura = new JLabel();
        lbGrupo = new JLabel();
        lbProfesor = new JLabel();
        lbSalon = new JLabel();
        txGrupo = new JTextField();
        cbAsignatura = new JComboBox();
        cbSalon = new JComboBox();
        cbProfesor = new JComboBox();
        lbHora = new JLabel();
        cbHrsE = new JComboBox();
        cbMinE = new JComboBox();
        jLabel2 = new JLabel();
        lbDias = new JLabel();
        ckLunes = new JCheckBox();
        ckMartes = new JCheckBox();
        ckMiercoles = new JCheckBox();
        ckJueves = new JCheckBox();
        ckViernes = new JCheckBox();
        ckSabado = new JCheckBox();
        btnGuardar = new JButton();
        btnModificar = new JButton();
        btnCancelar = new JButton();
        lbLista = new JLabel();
        scTabla = new JScrollPane();
        tbLista = new JTable();
        cbHrsS = new JComboBox();
        jLabel1 = new JLabel();
        cbMinS = new JComboBox();
        lbHoraE = new JLabel();
        lbHoraS = new JLabel();
        
    }
    
    
    //Metodo para agregar los componentes de etiquetas a la ventana
    public void agregarEtiquetas(){
        lbEdicion.setText("Edición del Horario");
        lbAsignatura.setText("Asignatura:");
        lbGrupo.setText("Grupo:");
        lbProfesor.setText("Profesor:");
        lbSalon.setText("Salon:");
        txGrupo.setText("prueba");
        lbHora.setText("Horario");
        lbLista.setText("Lista de Horarios");
        jLabel1.setText(":");
        jLabel2.setText(":");
        lbDias.setText("Seleccione los Días");
    }
    
    // Metodo para agregar la tabla a la ventana
    public void agregarTabla(){
        
        tbLista.setModel(new DefaultTableModel(
               new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Clave", "Asignatura", "Grupo", "Salon", "Profesor", "Folio", "Horario", "Días"
            }
        ){
            Class[] types = new Class [] {
                java.lang.String.class, 
                java.lang.String.class, 
                java.lang.String.class, 
                java.lang.String.class, 
                java.lang.String.class, 
                java.lang.String.class, 
                java.lang.String.class, 
                java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        
        tbLista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);	//indica que sólo puede seleccionarse un reglón
        tbLista.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tbLista.getTableHeader().setReorderingAllowed(false);
        scTabla.setViewportView(tbLista);
        
        if (tbLista.getColumnModel().getColumnCount() > 0) {
            tbLista.getColumnModel().getColumn(0).setResizable(false);
            tbLista.getColumnModel().getColumn(1).setResizable(false);
            tbLista.getColumnModel().getColumn(2).setResizable(false);
            tbLista.getColumnModel().getColumn(3).setResizable(false);
            tbLista.getColumnModel().getColumn(4).setResizable(false);
            tbLista.getColumnModel().getColumn(5).setResizable(false);
            tbLista.getColumnModel().getColumn(6).setResizable(false);
            tbLista.getColumnModel().getColumn(7).setResizable(false);
        }
        
    }
    
    // Metodo para establecer el layout de la ventana y cargarla.
    // Para la ventana se utiliza el GroupLayout
    public void establecerLayout(){
        javax.swing.GroupLayout HorarioIGULayout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(HorarioIGULayout);
        HorarioIGULayout.setHorizontalGroup(
            HorarioIGULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HorarioIGULayout.createSequentialGroup()
                .addGroup(HorarioIGULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(HorarioIGULayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(HorarioIGULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(HorarioIGULayout.createSequentialGroup()
                                .addGroup(HorarioIGULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbGrupo)
                                    .addComponent(lbProfesor)
                                    .addComponent(lbSalon))
                                .addGroup(HorarioIGULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(HorarioIGULayout.createSequentialGroup()
                                        .addGap(72, 72, 72)
                                        .addGroup(HorarioIGULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txGrupo, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cbProfesor, 0, 192, Short.MAX_VALUE)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, HorarioIGULayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(cbSalon, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(HorarioIGULayout.createSequentialGroup()
                                .addComponent(lbAsignatura, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(45, 45, 45)
                                .addComponent(cbAsignatura, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnGuardar, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGroup(HorarioIGULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(HorarioIGULayout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addComponent(btnModificar)
                                .addGap(26, 26, 26)
                                .addComponent(btnCancelar))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, HorarioIGULayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbDias)
                                .addGap(146, 146, 146))
                            .addGroup(HorarioIGULayout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addGroup(HorarioIGULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lbEdicion)
                                    .addComponent(ckLunes)
                                    .addComponent(lbHoraE)
                                    .addComponent(lbHoraS)
                                    .addComponent(lbLista, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(HorarioIGULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(HorarioIGULayout.createSequentialGroup()
                                        .addComponent(ckMartes)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(ckMiercoles)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(ckJueves)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(ckViernes)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(ckSabado))
                                    .addGroup(HorarioIGULayout.createSequentialGroup()
                                        .addGroup(HorarioIGULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, HorarioIGULayout.createSequentialGroup()
                                                .addComponent(cbHrsS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addGroup(HorarioIGULayout.createSequentialGroup()
                                                .addComponent(cbHrsE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel2)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(HorarioIGULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cbMinE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cbMinS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                    .addGroup(HorarioIGULayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(scTabla, javax.swing.GroupLayout.PREFERRED_SIZE, 704, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(23, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, HorarioIGULayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lbHora)
                .addGap(262, 262, 262))
        );
        HorarioIGULayout.setVerticalGroup(
            HorarioIGULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HorarioIGULayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbEdicion)
                .addGap(2, 2, 2)
                .addComponent(lbHora)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(HorarioIGULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbAsignatura)
                    .addComponent(cbAsignatura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(cbMinE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbHrsE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbHoraE))
                .addGap(17, 17, 17)
                .addGroup(HorarioIGULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbGrupo)
                    .addComponent(txGrupo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbHrsS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(cbMinS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbHoraS))
                .addGap(18, 18, 18)
                .addGroup(HorarioIGULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbDias, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(HorarioIGULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lbProfesor)
                        .addComponent(cbProfesor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(HorarioIGULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbSalon)
                    .addComponent(cbSalon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ckLunes)
                    .addComponent(ckMartes)
                    .addComponent(ckMiercoles)
                    .addComponent(ckJueves)
                    .addComponent(ckViernes)
                    .addComponent(ckSabado))
                .addGap(18, 18, 18)
                .addGroup(HorarioIGULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar)
                    .addComponent(btnModificar)
                    .addComponent(btnCancelar))
                .addGap(30, 30, 30)
                .addComponent(lbLista)
                .addGap(18, 18, 18)
                .addComponent(scTabla, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 278, Short.MAX_VALUE))
        );
        
        pack();
    }
    
    
    public void agregarBotones(){        
        btnGuardar.setText("Guardar");
        btnModificar.setText("Modificar");
        btnCancelar.setText("Cancelar");
    }
    
    public void agregarCombo(){
        cbAsignatura.setModel(new DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbSalon.setModel(new DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbProfesor.setModel(new DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        
        cbHrsE.setModel(new DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbMinE.setModel(new DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        
        cbHrsS.setModel(new DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbMinS.setModel(new DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
    }
    private void agregarCheck(){     
        ckLunes.setText("Lunes");
        ckMartes.setText("Martes");
        ckMiercoles.setText("Miércoles");
        ckJueves.setText("Jueves");
        ckViernes.setText("Viernes");
        ckSabado.setText("Sábado");
    }
    
    public void limpiar(){
        cbAsignatura.setSelectedIndex(0);
        cbSalon.setSelectedIndex(0);
        cbProfesor.setSelectedIndex(0);
        txGrupo.setText("");
    }
}

//CODIGO ANTERIOR
    
 /*
    
	//private String semestre;
	//componentes
	//campos de texto
	
    
        private JComboBox jcSalon = new JComboBox();
        private JComboBox jcAsignatura = new JComboBox();
        private JTextField txtGrupo = new JTextField();
        private JTextField txtHoraE = new JTextField();
        private JTextField txtHoraS = new JTextField();
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
	private PruebaHIGU (){
		
                
                super("*** Horarios ***", false, true, false, true);
		
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
		pnHorario.setBorder(new TitledBorder(null, "Edici\u00F3n de Horario del semestre ", TitledBorder.CENTER, TitledBorder.TOP));
				//tipo de borde por omision, "Título", justificación borde, posición borde (arriba)
		pnHorario.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
		
		//Crea panel de datos 
		JPanel pnDatos = new JPanel();
		pnDatos.setLayout(new GridLayout(8, 2, 25, 5));
		
                pnDatos.add(lbAsignatura);
                jcAsignatura.setModel(new DefaultComboBoxModel(new String[] {}));
                pnDatos.add(jcAsignatura);
                llenarJcAsignatura();

                pnDatos.add(lbGrupo);             
                pnDatos.add(txtGrupo);
                
                pnDatos.add(lbProfesor);
                jcNombreProf.setModel(new DefaultComboBoxModel(new String[] {}));
                pnDatos.add(jcNombreProf);
		llenarJcNombreProf();
                pnDatos.add(lbSalon);
                jcSalon.setModel(new DefaultComboBoxModel(new String[] {}));
                pnDatos.add(jcSalon);             
                llenarJcSalon();
                
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
		pnBotones.setLayout(new BoxLayout(pnBotones, BoxLayout.X_AXIS));
		
		//define características de botones de acciones		
		btnGuardar.setMnemonic('G');
						
		//agrega botones con 5 pixeles de espacio entre ellos 
		pnBotones.add(btnGuardar);
                pnBotones.add(Box.createVerticalStrut(15));
                pnBotones.add(Box.createHorizontalStrut(15));//agrega 5 pixeles de expacio
		pnBotones.add(btnCancelar);
                pnBotones.add(Box.createVerticalStrut(5));
                pnBotones.add(Box.createHorizontalStrut(15));
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
		scrTabla.setPreferredSize(new Dimension(800, 100));		//redimensiona para que quepa en panel
		scrTabla.setViewportView(tblLista);	//agrega la tabla al scroll con viewport para poder ver los encabezados se columnas 
		pnLista.add(scrTabla);									//agrega scroll con la tabla al panel
			
		setTabla();	//define el modelo de la tabla (datos)
		boxVerticalPrincipal.add(pnLista);	//agrega el panel de la tabla al panel principal
	}
	
	public void setTabla() {
		
		String titulos[] = {"Horario", "Grupo","Salon","Vacante","Dias","Clave", "Asignatura", "Folio Profesor","Profesor"  };				//títulos de la tabla
		Object datos[][] = HorarioDAO.obtenerHorarios("","");					//obtiene todos los datos de la base de datos

		final DefaultTableModel modeloTabla = new DefaultTableModel(){	//crea modelo de tabla
			
			private static final long serialVersionUID = 1L;			//objeto serializable

			public boolean isCellEditable(int row, int column) {  		//tabla no editable
				return false;
			}
		};
		
		modeloTabla.setDataVector(datos, titulos);		//define los datos y títulos del modelo
		tblLista.setModel(modeloTabla);					//especifica el modelo de datos de la tabla
		tblLista.changeSelection(0, 0, false, false);	//pone en renglon 0, columna 0, limpia selec. anterior  
						//evita que de tamalo de columnas automáticamente
                
                tblLista.getColumnModel().getColumn(0).setPreferredWidth(120);	//indica tamaño de columna 0
		tblLista.getColumnModel().getColumn(1).setPreferredWidth(40);
                tblLista.getColumnModel().getColumn(2).setPreferredWidth(40);
                tblLista.getColumnModel().getColumn(3).setPreferredWidth(40);
                tblLista.getColumnModel().getColumn(4).setPreferredWidth(40);
                tblLista.getColumnModel().getColumn(5).setPreferredWidth(120);
                tblLista.getColumnModel().getColumn(6).setPreferredWidth(40);
                tblLista.getColumnModel().getColumn(7).setPreferredWidth(200);
                
        }

	
        
	public void setCampos() {
		
		int renglon = tblLista.getSelectedRow();					//obtiene renglón seleccionado
		
		if (renglon >= 0) {											//si hay renglón seleccionado
			txId.setText((String) tblLista.getValueAt(renglon, 0));	//toma id y lo asigna al campo de texto txId
			txId.requestFocus();									//le asigna el cursor
			txNombre.setText((String) (tblLista.getValueAt(tblLista.getSelectedRow(), 1)));	//toma y asigna nombre
		}		
	}
	
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
    
    public JComboBox getJcSalon() {
        return jcSalon;
    }

    public JComboBox getJcAsignatura() {
        return jcAsignatura;
    }

    public JTextField getTxtGrupo() {
        return txtGrupo;
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

   // public String getSemestre() {
      //  return semestre;
    //}
  
    
*/
       