/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geologia.modelo.dto;

/**
 *
 * @author javierGenico
 */
public class Horario {
        
    private String grupo;
    private double hrEnt;
    private double hrSal;
    private String dias;
    
    
    public Horario(){
        grupo = null;
        hrEnt = 0.0;
        hrSal = 0.0;
        dias = null;
    }

    public Horario(String grupo, double hrEnt, double hrSal, String dias) {
        this.grupo = grupo;
        this.hrEnt = hrEnt;
        this.hrSal = hrSal;
        this.dias = dias;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public double getHrEnt() {
        return hrEnt;
    }

    public void setHrEnt(double hrEnt) {
        this.hrEnt = hrEnt;
    }

    public double getHrSal() {
        return hrSal;
    }

    public void setHrSal(double hrSal) {
        this.hrSal = hrSal;
    }

    public String getDias() {
        return dias;
    }

    public void setDias(String dias) {
        this.dias = dias;
    }
    
    
}


