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

import geologia.control.LoginControl;


public class LoginIGU extends JFrame{
	
    // Componentes
    private final JLabel lbUsuario = new JLabel("Usuario:");
    private final JLabel lbPass = new JLabel("Contraseña:");
    private final JTextField txtUsuario = new JTextField(10);
    private final JPasswordField txtPass = new JPasswordField(10);
    private final JButton btnAceptar = new JButton("Ingresar");
    
    //Control de la ventana
    private final LoginControl controlador = new LoginControl(this);

    //Constructor
    public LoginIGU(){
        super();
        agregarComp();
        agregarCtrlGraf();
        agregarCtrlGraf();
    }
    
    private void agregarMenu(){
        String [] opciones = {"Configuracion"};
        String [] imagenes = {"imagenes/Uno.png",};
        String [] ayuda = {"Administrar " + opciones[0]};
        String [][] subOpciones = {												//subopciones
				{"Editar"}														//del menú de Ayuda	
		};
        JMenuBar bmnPrincipal = new JMenuBar();		//barra de menú	
        JMenu mnOpcion;
        JMenuItem smnOpcion;
        
        for (int opcion = 0; opcion < opciones.length; opcion++){	//toma cada elemento del arreglo menú
			
            mnOpcion = new JMenu(opciones[opcion]);										//crea menú
            //mnOpcion.setFont(new Font("Arial", Font.PLAIN, 14));						//cambia Fuente de letra
            mnOpcion.setIcon(new ImageIcon(getClass().getResource(imagenes[opcion])));	//define imagen
            mnOpcion.setToolTipText(ayuda[opcion]);	//pone mensaje de ayuda			
            mnOpcion.setMnemonic(opciones[opcion].charAt(0));
            for (int subOpcion = 0; subOpcion < subOpciones[opcion].length; subOpcion++){	//toma cada subopcion de cada menu
				smnOpcion = new JMenuItem(subOpciones[opcion][subOpcion]);				//crea subopción
				smnOpcion.setMnemonic((subOpciones[opcion][subOpcion]).charAt(0));		//pone acceso por teclado
				smnOpcion.addActionListener(controlador);								//especifica controlador de eventos
				mnOpcion.add(smnOpcion); 												//agrega subopción al menu
			}
            bmnPrincipal.add(mnOpcion);													//agrega opción a la barra

        }
        
        setJMenuBar(bmnPrincipal);
    }
    
    
    
    private void agregarComp(){
        
       
        
        setLayout(null);
        
        add(lbUsuario);
        lbUsuario.setBounds(50,20, 120, 40);
        add(txtUsuario);
        txtUsuario.setBounds(200, 20, 120, 30);
        add(lbPass);
        lbPass.setBounds(50,60, 120, 40);
        add(txtPass);
        txtPass.setBounds(200, 60, 120, 30);
        add(btnAceptar);
        btnAceptar.setBounds(150, 100, 100, 30);
        
        setSize(400, 200);
        setResizable(false);

        Dimension dim = this.getToolkit().getScreenSize();	//obtiene tamaño pantalla
        Rectangle rec = this.getBounds();					//crea rectángulo a partir de límites la ventana
        this.setLocation((dim.width - rec.width) / 2, ((dim.height - rec.height) / 2) - 20);	//define posición

        setTitle("División de Ingeniería en Ciencias de la Tierra");
        Image icono = 
                new ImageIcon(getClass().getResource("imagenes/icon.png")).getImage();
        setIconImage(icono);
        System.out.println("Directorio ejecucion = " + 
                System.getProperty("user.dir"));
        agregarMenu();
        
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    private void agregarCtrlGraf(){
        btnAceptar.addActionListener(controlador);
    }

    public JTextField getUsuario(){
        return txtUsuario;
    }

    public JPasswordField getPass(){
        return txtPass;
    }

    public JButton getBtnAceptar() {
        return btnAceptar;
    } 
    
    public void limpiar(){	
	txtUsuario.setText("");
        txtPass.setText("");//borra contenido
        txtUsuario.requestFocus();	//asigna cursor
    }
    
}