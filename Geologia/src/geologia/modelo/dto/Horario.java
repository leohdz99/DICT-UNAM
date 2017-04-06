/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geologia.modelo.dto;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Formatter;
import javax.swing.JOptionPane;
import java.math.BigDecimal;

/**
 *
 * @author javierGenico
 */
public class Horario {
        
    private int idHorario;
    private String grupo;
    private int idSalon;
    private int idProfesor;

    
    

    public Horario() {
    }
    
    public Horario(int idHorario, String grupo, int idSalon, int idProfesor ) {
        
        this.idHorario = idHorario;
        this.grupo = grupo;
        this.idSalon = idSalon;
        this.idProfesor = idProfesor;
      
    }

    public int getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(int idHorario) {
        this.idHorario = idHorario;
    }
    
    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }
    public int getIdSalon() {
        return idSalon;
    }

    public void setIdSalon(int idSalon) {
        this.idSalon = idSalon;
    }

    public int getIdProfesor() {
        return idProfesor;
    }

    public void setIdProfesor(int idProfesor) {
        this.idProfesor = idProfesor;
    } 

     public String toString(){
        return "idHorario: " + idHorario +
                ", grupo: '" + grupo + '\'' +
                ", idSalon: " + idSalon + '\''+
                ", idProfesor: '" + idProfesor;
                
                           
    }
    
     @Override
    public boolean equals(Object elemento) {
        if(elemento == null){
            return  false;
        }else{
            if (!(elemento instanceof Horario)){
                return  false;
            }else{
                Horario horario = (Horario) elemento;
                if (idHorario == horario.idHorario && grupo.equals(horario.grupo) && 
                    idSalon == horario.idSalon && horario.idProfesor == idProfesor){
                    return  true;
                }else{
                    return false;
                }
            }
        }
    }
}


