/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geologia.modelo.dto;

/**
 *
 * @author 58599749
 */
public class Salon {
    
    private int idSalon;
    private String salon;
    private int cupoAsignatura; // Nota en la base de datos se llama cupoAsignatura
    private int vacanteAsignatura;// nota las variables cupoAsignatura y vacanteAsignatura estan en duda de si se ponen en asignatura o en salon
    
    public Salon(){
        
    }
    
    public Salon(int idSalon, String salon, int cupoAsignatura, int vacanteAsignatura){
        
        this.idSalon = idSalon;
        this.salon = salon;
        this.cupoAsignatura = cupoAsignatura;
        this.vacanteAsignatura = vacanteAsignatura;
    }
    
    
    public int getIdSalon() {
        return idSalon;
    }
    public void setIdSalon(int idSalon) {
        this.idSalon = idSalon;
    }
    
    public String getSalon() {
        return salon;
    }

    public void setSalon(String salon) {
        this.salon = salon;
    }
    
    public int getCupoAsignatura() {
        return cupoAsignatura;
    }

    public void setCupoAsignatura(int cupoAsignatura) {
        this.cupoAsignatura = cupoAsignatura;
    }
    
     public int getVacanteAsignatura() {
        return vacanteAsignatura;
    }

    public void setVacanteAsignatura(int vacanteAsignatura) { // este get y set ya existe en Asignatura
        this.vacanteAsignatura = vacanteAsignatura;
    }
    
    @Override
    public String toString() {
        return  "idSalon: " + idSalon +
                ", salon: '" + salon + '\'' +
                ", cupoSalon: '" + cupoAsignatura + '\'' +
                ", vacanteAsignatura: " + vacanteAsignatura;
    }
    
    @Override
    public boolean equals(Object elemento) {
        if(elemento == null){
            return  false;
        }else{
            if (!(elemento instanceof Salon)){
                return  false;
            }else{
                Salon salon = (Salon) elemento;
                if (idSalon == salon.idSalon && salon.equals(salon.salon)&& cupoAsignatura == salon.cupoAsignatura && 
                        vacanteAsignatura == salon.vacanteAsignatura){
                    return  true;
                }else{
                    return false;
                }
            }
        }
    }
    
}
