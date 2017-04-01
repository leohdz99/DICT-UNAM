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

/**
 *
 * @author javierGenico
 */
public class Horario {
        
    private int idHorario;
    private String semestre;
    private String tipo;
    private String grupo;
    private String horaEntrada;
    private String horaSalida;
    private int idAsignatura;
    private int idSalon;
    private int idProfesor;
    private int dia1;
    
    

    public Horario() {
    }
    
    public Horario( String semestre, String tipo, String grupo,
            String horaEntrada, String horaSalida, int idAsignatura, int idSalon,
            int idProfesor ) {
        
        
        this.semestre = semestre;
        this.tipo = tipo;
        this.grupo = grupo;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
        this.idAsignatura = idAsignatura;
        this.idSalon = idSalon;
        this.idProfesor = idProfesor;
      
    }

    public int getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(int idHorario) {
        this.idHorario = idHorario;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public Time getHoraSalida(){
        DateFormat formato = new SimpleDateFormat("HH:ss");
        Time hSalida = null; 
        try {
            hSalida = new Time(formato.parse(horaSalida).getTime());
        } catch (ParseException pe) {
                JOptionPane.showMessageDialog(null, "Introduzca Un fomato valido" 
                        + pe.getMessage(), "Hora Erronea", JOptionPane.ERROR_MESSAGE);
        }
        
        return hSalida;
    }

    public Time getHoraEnrtada() {
        DateFormat formato = new SimpleDateFormat("HH:ss");
        Time hEntrada = null; 
        try {
            hEntrada = new Time(formato.parse(horaEntrada).getTime());
        } catch (ParseException pe) {
                JOptionPane.showMessageDialog(null, "Introduzca Un fomato valido" 
                        + pe.getMessage(), "Hora Erronea", JOptionPane.ERROR_MESSAGE);
        }
        
        return hEntrada;
    }

    public void setHoraSalida(String horaSalida) {
        this.horaSalida = horaSalida;
    }

    public void setHoraEntrada(String horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public int getIdAsignatura() {
        return idAsignatura;
    }

    public void setIdAsignatura(int idAsignatura) {
        this.idAsignatura = idAsignatura;
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
                ", semestre: '" + semestre + '\''+
                ", tipo: '" + tipo + '\''+
                ", grupo: '" + grupo + '\'' +
                ", horaSalida: '" + horaSalida + '\'' +
                ", horaEntrada: '" + horaEntrada + '\'' +
                ", idAsignatura: '" + idAsignatura + '\''+
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
                Horario horario= (Horario) elemento;
                if (idHorario == horario.idHorario && semestre.equals(horario.semestre) && tipo.equals(horario.tipo) && 
                    grupo.equals(horario.grupo) &&  horaSalida.equals(horario.horaSalida) && horaEntrada.equals(horario.horaEntrada) &&
                    idAsignatura == horario.idAsignatura && idSalon == horario.idSalon && horario.idProfesor == idProfesor){
                    return  true;
                }else{
                    return false;
                }
            }
        }
    }
}


