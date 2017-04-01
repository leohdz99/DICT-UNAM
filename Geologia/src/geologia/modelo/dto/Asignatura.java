/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geologia.modelo.dto;

/**
 *
 * @author Javier
 */

import java.sql.Time;

public class Asignatura {
    
    private int idAsignatura;
    private int claveAsignatura;
    private String nombreAsignatura;


    public Asignatura(){
        
    }
    public Asignatura(int idAsignatura, int claveAsignatura, 
                     String nombreAsignatura){
        
        this.idAsignatura = idAsignatura;
        this.claveAsignatura = claveAsignatura;
        this.nombreAsignatura = nombreAsignatura;
        
        
    }
    
    public int getIdAsignatura() {
        return idAsignatura;
    }

    public void setIdAsignatura(int idAsignatura) {
        this.idAsignatura = idAsignatura;
    }
    
  
    
    public int getClaveAsignatura() {
        return claveAsignatura;
    }

    public void setClaveAsignatura(int claveAsignatura) {
        this.claveAsignatura = claveAsignatura;
    }
    
    public String getNombreAsignatura() {
        return nombreAsignatura;
    }

    public void setNombreAsignatura(String nombreAsignatura) {
        this.nombreAsignatura = nombreAsignatura;
    }
        
    
    @Override
    public String toString() {
        return  "idAsignatura: " + idAsignatura +            
                ", claveAsignatura: '" + claveAsignatura + '\'' +
                ", nombreAsignatura: " + nombreAsignatura;
    }
    
    @Override
    public boolean equals(Object elemento) {
        if(elemento == null){
            return  false;
        }else{
            if (!(elemento instanceof Asignatura)){
                return  false;
            }else{
                Asignatura asignatura = (Asignatura) elemento;
                if (idAsignatura == asignatura.idAsignatura &&
                        claveAsignatura == asignatura.claveAsignatura && 
                        nombreAsignatura.equals(asignatura.nombreAsignatura)){
                    return  true;
                }else{
                    return false;
                }
            }
        }
    }


}
