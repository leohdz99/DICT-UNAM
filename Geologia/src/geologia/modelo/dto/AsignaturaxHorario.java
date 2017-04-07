/*
 * Copyright (C) 2017 javigen
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package geologia.modelo.dto;

/**
 *
 * @author javigen
 */
public class AsignaturaxHorario {
    
    private int idAsignatura;
    private int idHorario;
    private String semestre;
    


    public AsignaturaxHorario(){

    }

    public AsignaturaxHorario(int idAsignatura, int idHorario, String semestre){
        
        this.idAsignatura = idAsignatura;
        this.idHorario = idHorario;
        this.semestre = semestre;

    }
    
    public int getIdAsignatura(){
    
        return idAsignatura;
    }
    
    public void setIdAsignatura(int idAsignatura){
        
        this.idAsignatura = idAsignatura;
    }
    
    public int getIdHorario(){
        
        return idHorario;
    }
    
    public void setIdHorario(int idHorario){
        
        this.idHorario = idHorario;
    }
    
    public String getSemestre(){
        
        return semestre;
    }
    
    public void setSemestre(String semestre){
        
        this.semestre = semestre;
    }
    
     @Override
    public String toString() {
        return  "idAsignatura: " + idAsignatura +            
                ", idHorario: '" + idHorario + '\'' +
                ", semestre: " + semestre;
    }
    
    @Override
    public boolean equals(Object elemento) {
        if(elemento == null){
            return  false;
        }else{
            if (!(elemento instanceof AsignaturaxHorario)){
                return  false;
            }else{
                AsignaturaxHorario asignaturaxH = (AsignaturaxHorario) elemento;
                if (idAsignatura == asignaturaxH.idAsignatura &&
                        idHorario == asignaturaxH.idHorario && 
                                semestre.equals(asignaturaxH.semestre)){
                    return  true;
                }else{
                    return false;
                }
            }
        }
    }

    



            
}
