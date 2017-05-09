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
public class Asignatura {
    
    private int clvAsig;
    private String nomAsig;
    private double hrsSem;
    
    public Asignatura(){
        clvAsig = 0;
        nomAsig = null;
        hrsSem = 0.0;
    }

    public Asignatura(int clvAsig, String nomAsig, double hrsSem) {
        this.clvAsig = clvAsig;
        this.nomAsig = nomAsig;
        this.hrsSem = hrsSem;
    }

    public int getClvAsig() {
        return clvAsig;
    }

    public void setClvAsig(int clvAsig) {
        this.clvAsig = clvAsig;
    }

    public String getNomAsig() {
        return nomAsig;
    }

    public void setNomAsig(String nomAsig) {
        this.nomAsig = nomAsig;
    }

    public double getHrsSem() {
        return hrsSem;
    }

    public void setHrsSem(double hrsSem) {
        this.hrsSem = hrsSem;
    }
    
    
}
