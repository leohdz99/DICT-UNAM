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
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.*;
import javax.swing.*;
import java.net.URL;

import geologia.control.MenuPrincipalControl;

public class MenuPrincipalIGU extends JFrame{	//Frame principal


	//componentes
	private JDesktopPane escritorio;			//escritorio de la ventana principal (donde se ponen las otras ventanas)	
	private MenuPrincipalControl controlador;	//objeto controlador de eventos
        private String semestre;
        
	//constructor00
	public MenuPrincipalIGU(String sem){		
		
                semestre = sem;
		agregarEscritorio();									//crea y agrega el escritorio a la ventana	
		
		controlador = new MenuPrincipalControl(this);		//crea objeto controlador

		setTitle("División de Ingeniería en Ciencias de la Tierra (Beta 1)");	//pone título = super("Titulo")
		Image icono = new ImageIcon(getClass().getResource("imagenes/icon.png")).getImage();
		setIconImage(icono);
		agregarMenu();											//agrega el menu

		setSize(1024, 768);
		//a partir del tamaño de la pantalla especifica su posición centrada
		Dimension dim = this.getToolkit().getScreenSize();	//obtiene tamaño pantalla
                Rectangle rec = this.getBounds();					//crea rectángulo a partir de límites la ventana
                this.setLocation((dim.width - rec.width) / 2, ((dim.height - rec.height) / 2) - 20);	//define posición
					
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	/**
	 * menu de la ventana principal
	 */
	
	private void agregarMenu(){
		String [] opciones = {"Administración","Consultas", "Sistema"};	//menú principal		
		String [] imagenes = { "imagenes/Uno.png", "imagenes/Uno.png", "imagenes/Uno.png"};
		String [][] subOpciones = {												//subopciones
				{"Horarios", "Profesores"},
				{"Horario", "Profesor"},					
				{"Acerca de", "Salir"}													//del menú de Ayuda	
		};
		
		JMenuBar bmnPrincipal = new JMenuBar();		//barra de menú
		
		JMenu mnOpcion = null;		//objeto para crear cada menú				
		JMenuItem smnOpcion;		//objeto para crear cada submenú
		
		for (int opcion = 0; opcion < opciones.length; opcion++){	//toma cada elemento del arreglo menú
			
			mnOpcion = new JMenu(opciones[opcion]);										//crea menú
			mnOpcion.setFont(new Font("Arial", Font.PLAIN, 14));						//cambia Fuente de letra
			mnOpcion.setIcon(new ImageIcon(getClass().getResource(imagenes[opcion])));	//define imagen
			mnOpcion.setToolTipText("Administrar " + opciones[opcion]);					//pone mensaje de ayuda
			mnOpcion.setMnemonic(opciones[opcion].charAt(0));							//pone acceso por teclado
			bmnPrincipal.add(mnOpcion);													//agrega opción a la barra
			
			for (int subOpcion = 0; subOpcion < subOpciones[opcion].length; subOpcion++){	//toma cada subopcion de cada menu
				smnOpcion = new JMenuItem(subOpciones[opcion][subOpcion]);				//crea subopción
				smnOpcion.setMnemonic((subOpciones[opcion][subOpcion]).charAt(0));		//pone acceso por teclado
				smnOpcion.addActionListener(controlador);								//especifica controlador de eventos
				mnOpcion.add(smnOpcion); 												//agrega subopción al menu
			}
		}
		
		setJMenuBar(bmnPrincipal);		//especifica el menú de la ventana principal
	}
	
	/**
	 * crea y agrega escritorio a la ventana principal
	 */
	private void agregarEscritorio(){
			
		escritorio = new JDesktopPane(){

			private Image imagen; 
			{
			    try {
			    	URL imagePath = new URL(getClass().getResource("imagenes/division.png").toString());
			        imagen = ImageIO.read(imagePath);
			    } catch (IOException e) {
			        e.printStackTrace();
			    }
			}

    		
                    @Override
                    protected void paintComponent(Graphics g){
                            super.paintComponent(g);
                            g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
                    }

		};		//crea	
		escritorio.setBackground(Color.BLACK);	//color de fondo
		add(escritorio, BorderLayout.CENTER);	//agrega escritorio a la ventan principal
	}
	
	//permite obtener el escritorio
	public JDesktopPane getEscritorio(){
		return escritorio;
        }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }
        
       
}