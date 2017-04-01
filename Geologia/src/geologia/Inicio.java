/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geologia;

import geologia.vista.LoginIGU;
import java.awt.EventQueue;

/**
 *
 * @author josue
 */
public class Inicio {
	public static void main(String[] args) {
            EventQueue.invokeLater(() -> {
                new geologia.vista.LoginIGU(); //To change body of generated methods, choose Tools | Templates.
            });           
	}
}
