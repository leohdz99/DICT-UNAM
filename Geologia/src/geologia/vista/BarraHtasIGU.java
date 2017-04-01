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
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

//import control.BarraHtasControl;	//Controladora de la barra de herramientas

public class BarraHtasIGU extends JPanel{		//es un PANEL

	private static final long serialVersionUID = 1L;	//clase serializable

	//componentes
	//botones
	private JButton btPrimero, btAnterior, btSiguiente, btUltimo, 
			btNuevo, btEditar, btBorrar, btBuscar;
	//campo de texto
	private JTextField txBuscarNombre;
	//controlador de la barra de herramientas
	//private BarraHtasControl controlador;

	//constructor
	public BarraHtasIGU(HorarioIGU ventana){		

		//controlador = new BarraHtasControl(this, ventana);				//crea objeto controlador de la barra
		setBorder(new LineBorder(new Color(192, 192, 192), 1, true));	//borde de linea simple color gris
		//setLayout();
				
		JToolBar barraHtas = new JToolBar();	//crea componente barra de herramientas
		add(barraHtas);							//agrega barra al panel
		barraHtas.setFloatable(false);			//evita que el usuario pueda moverla
		
		//Crea, especifica característica y agrega los botones
		//botón que mueve al primer renglón de la tabla
		btPrimero = new JButton("");			
		btPrimero.setActionCommand("Primero");
		btPrimero.setToolTipText("Ir a primer registro");
		btPrimero.setIcon(new ImageIcon(getClass().getResource("imagenes/primero.png")));		
		barraHtas.add(btPrimero);
		
		//botón que mueve al renglón anterior de la tabla
		btAnterior = new JButton("");
		btAnterior.setActionCommand("Anterior");
		btAnterior.setToolTipText("Ir al registro anterior");
		btAnterior.setIcon(new ImageIcon(getClass().getResource("imagenes/anterior.png")));
		barraHtas.add(btAnterior);
		
		//botón que mueve al siguiente renglón de la tabla
		btSiguiente = new JButton("");
		btSiguiente.setActionCommand("Siguiente");
		btSiguiente.setToolTipText("Ir al siguiente registro");
		btSiguiente.setIcon(new ImageIcon(getClass().getResource("imagenes/siguiente.png")));
		barraHtas.add(btSiguiente);
		
		//botón que mueve al último renglón de la tabla
		btUltimo = new JButton("");
		btUltimo.setActionCommand("Ultimo");
		btUltimo.setToolTipText("Ir al \u00DAltimo registro");
		barraHtas.add(btUltimo);
		btUltimo.setIcon(new ImageIcon(getClass().getResource("imagenes/ultimo.png")));
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		barraHtas.add(separator);
		
		//botón que permite agregar un nuevo registro a la BD
		btNuevo = new JButton("");
		btNuevo.setActionCommand("Nuevo");
		btNuevo.setToolTipText("Agregar un alumno");
		btNuevo.setIcon(new ImageIcon(getClass().getResource("imagenes/nuevo.png")));
		barraHtas.add(btNuevo);
		
		//botón que permite borrar un registro de la BD
		btBorrar = new JButton("");
		btBorrar.setActionCommand("Borrar");
		btBorrar.setToolTipText("Borrar un alumno");
		btBorrar.setIcon(new ImageIcon(getClass().getResource("imagenes/borrar.png")));
		barraHtas.add(btBorrar);
		
		//botón que permite modificar un registro de la BD
		btEditar = new JButton("");
		btEditar.setActionCommand("Editar");
		btEditar.setToolTipText("Modificar el nombre");
		btEditar.setIcon(new ImageIcon(getClass().getResource("imagenes/editar.png")));
		barraHtas.add(btEditar);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		barraHtas.add(separator_1);
		
		//botón que habilita el campo de texto para búscar un nombre
		btBuscar = new JButton("");
		btBuscar.setActionCommand("Buscar");
		btBuscar.setToolTipText("Buscar por nombre");
		btBuscar.setIcon(new ImageIcon(getClass().getResource("imagenes/buscar.png")));
		barraHtas.add(btBuscar);
		
		//etiqueta de nombre
		JLabel lbBuscarNombre = new JLabel("Materia:");
		lbBuscarNombre.setEnabled(false);
		barraHtas.add(lbBuscarNombre);
		
		//campo de texto que filtra los datos de la tabla
		txBuscarNombre = new JTextField(10);
		txBuscarNombre.setEditable(false);
		barraHtas.add(txBuscarNombre);
		
		setVisible(true);
		//setControl();
	}
	
	
	/*
	private void setControl() {
	
		//botones barra herramientas
		btPrimero.addActionListener(controlador);
		btAnterior.addActionListener(controlador);
		btSiguiente.addActionListener(controlador);
		btUltimo.addActionListener(controlador);
		btNuevo.addActionListener(controlador);
		btBorrar.addActionListener(controlador);
		btEditar.addActionListener(controlador);
		btBuscar.addActionListener(controlador);
	
		//campo de texto
		txBuscarNombre.getDocument().addDocumentListener(controlador);	//da localizaciónn del cambio para el filtro		
	}	
	*/
	//getters de los atributos (componentes)
	public JButton getBtPrimero() {
		return btPrimero;
	}
	public JButton getBtAnterior() {
		return btAnterior;
	}
	public JButton getBtSiguiente() {
		return btSiguiente;
	}
	public JButton getBtUltimo() {
		return btUltimo;
	}
	public JButton getBtNuevo() {
		return btNuevo;
	}
	public JButton getBtEditar() {
		return btEditar;
	}
	public JButton getBtBorrar() {
		return btBorrar;
	}
	public JButton getBtBuscar() {
		return btBuscar;
	}
	
	public JTextField getTxBuscarNombre() {
		return txBuscarNombre;
	}
}

