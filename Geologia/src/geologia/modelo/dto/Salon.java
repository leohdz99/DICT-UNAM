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
    
    private String salon;
    private int capacidad;
    
    public Salon(){
        salon = null;
        capacidad = 0;
    }

    public Salon(String salon, int capacidad) {
        this.salon = salon;
        this.capacidad = capacidad;
    }

    public String getSalon() {
        return salon;
    }

    public void setSalon(String salon) {
        this.salon = salon;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }
    
    
    
}
