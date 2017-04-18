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
public class HorarioReal {
    
    private int idHorario;
    private float horaEntrada;
    private float horaSalida;
    // CAmbiarlo a String 
    private int dias;
    
     public HorarioReal() {
    }
    
    public HorarioReal(int idHorario, float horaEntrada, float horaSalida, int dias ) {
        
        this.idHorario = idHorario;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
        this.dias = dias;
      
    }
    
     public int getIdHorarioR() {
        return idHorario;
    }

    public void setIdHorarioR(int idHorario) {
        this.idHorario = idHorario;
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

    public int getDias() {
        return dias;
    }   

    public void setDias(int dias) {
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
                HorarioReal horario = (HorarioReal) elemento;
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
