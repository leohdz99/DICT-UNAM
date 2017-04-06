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
public class HorarioComp {
    
    private int idHorario;
    private float horaEntrada;
    private float horaSalida;
    private int dias;
    
     public HorarioComp() {
    }
    
    public HorarioComp(int idHorario, float horaEntrada, float horaSalida, int dias ) {
        
        this.idHorario = idHorario;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
        this.dias = dias;
      
    }
    
     public int getIdHorarioC() {
        return idHorario;
    }

    public void setIdHorarioC(int idHorario) {
        this.idHorario = idHorario;
    }
    
     public float getHoraSalidaC(){
        
        return horaSalida;
    }

    public float getHoraEnrtadaC() {
       
       return horaEntrada;
       
    }
    
    public void setHoraSalidaC(float horaSalida) {
        this.horaSalida = horaSalida;
    }

    public void setHoraEntradaC(float horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public int getDiasC() {
        return dias;
    }   

    public void setDiasC(int dias) {
        this.dias = dias;
    }
    
    public String toString(){
        return "idHorario: " + idHorario +
                ", horaEntrada: '" + horaEntrada + '\'' +
                ", horaSalida: '" + horaSalida + '\'' + 
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
                HorarioComp horario = (HorarioComp) elemento;
                if (idHorario == horario.idHorario && horaEntrada == horario.horaEntrada && 
                    horaSalida == horario.horaSalida && dias == horario.dias){
                    return  true;
                }else{
                    return false;
                }
            }
        }
    }
    
}
