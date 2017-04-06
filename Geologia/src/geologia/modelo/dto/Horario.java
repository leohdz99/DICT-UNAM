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
    private float horaEntrada;
    private float horaSalida;
    private int idSalon;
    private int idProfesor;
    private int dias;
    
    

    public Horario() {
    }
    
    public Horario(int idHorario, String grupo, float horaEntrada, float horaSalida, int idSalon,
            int idProfesor, int dias ) {
        
        this.idHorario = idHorario;
        this.grupo = grupo;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
        this.idSalon = idSalon;
        this.idProfesor = idProfesor;
        this.dias = dias;
      
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

    public float getHoraSalida(){
        
        return horaSalida;
    }

    public float getHoraEnrtada() {
       
       return horaEntrada;
       
    }

    public void setHoraSalida(float horaSalida) {
        this.horaSalida = horaSalida;
    }

    public void setHoraEntrada(float horaEntrada) {
        this.horaEntrada = horaEntrada;
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


     public int getDias() {
        return dias;
    }   

    public void setDias(int dias) {
        this.dias = dias;
    }

     public String toString(){
        return "idHorario: " + idHorario +
                ", grupo: '" + grupo + '\'' +
                ", horaEntrada: '" + horaEntrada + '\'' +
                ", horaSalida: '" + horaSalida + '\'' + 
                ", idSalon: " + idSalon + '\''+
                ", idProfesor: '" + idProfesor + '\'' +
                ", dias3: '" + dias;
                           
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
                    horaEntrada == horario.horaEntrada && horaSalida == horario.horaSalida &&
                    idSalon == horario.idSalon && 
                    horario.idProfesor == idProfesor && dias == horario.dias){
                    return  true;
                }else{
                    return false;
                }
            }
        }
    }
}


