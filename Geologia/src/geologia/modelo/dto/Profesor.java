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
public class Profesor {
    
    private int idProfesor;
    private int folioProfesor;
    private String tituloProfesor;
    private String apPaterno;
    private String apMaterno;
    private String nombreProf;
    private String rfc;
    private String activo;

   
    
    public Profesor(){
        
    }
    public Profesor(int idProfesor, int folioProfesor, String tituloProfesor, String apPaterno, String apMaterno, 
            String nombreProf, String rfc, String activo){
        
        this.idProfesor = idProfesor;
        this.folioProfesor = folioProfesor;
        this.tituloProfesor = tituloProfesor;
        this.apPaterno = apPaterno;
        this.apMaterno = apMaterno;
        this.nombreProf = nombreProf;
        this.rfc = rfc;
        this.activo = activo;
        
    }


    public int getIdProfesor() {
        return idProfesor;
    }

    public int getFolioProfesor() {
        return folioProfesor;
    }

    public String getTituloProfesor() {
        return tituloProfesor;
    }

    public String getApPaterno() {
        return apPaterno;
    }

    public String getApMaterno() {
        return apMaterno;
    }

    public String getNombreProf() {
        return nombreProf;
    }

    public String getRfc() {
        return rfc;
    }
    
     public void setIdProfesor(int idProfesor) {
        this.idProfesor = idProfesor;
    }

    public void setFolioProfesor(int folioProfesor) {
        this.folioProfesor = folioProfesor;
    }

    public void setTituloProfesor(String tituloProfesor) {
        this.tituloProfesor = tituloProfesor;
    }

    public void setApPaterno(String apPaterno) {
        this.apPaterno = apPaterno;
    }

    public void setApMaterno(String apMaterno) {
        this.apMaterno = apMaterno;
    }

    public void setNombreProf(String nombreProf) {
        this.nombreProf = nombreProf;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

      public String getActivo() {
        return activo;
    }
    
     public void setActivo(String activo) {
        this.activo = activo;
    }
    
    public String toString(){
        return "idProfesor: " + idProfesor +
                ", folioProfesor: '" + folioProfesor + '\''+
                ", tituloProfesor: '" + tituloProfesor + '\'' +
                ", apPaterno: '" + apPaterno + '\'' +
                ", apMaterno: '" + apMaterno + '\'' +
                ", nombreProf: '" + nombreProf + '\'' +
                ", rfc: '" + rfc + '\'' +
                ", activo " + activo;
    }
    
     @Override
    public boolean equals(Object elemento) {
        if(elemento == null){
            return  false;
        }else{
            if (!(elemento instanceof Profesor)){
                return  false;
            }else{
                Profesor profesor= (Profesor) elemento;
                if (idProfesor == profesor.idProfesor && folioProfesor == profesor.folioProfesor && profesor.equals(profesor.tituloProfesor)&& 
                        profesor.equals(profesor.apPaterno) && profesor.equals(profesor.apMaterno) && profesor.equals(profesor.nombreProf) &&
                        profesor.equals(profesor.rfc) && profesor.equals(profesor.activo)){
                    return  true;
                }else{
                    return false;
                }
            }
        }
    }
    
    
}
