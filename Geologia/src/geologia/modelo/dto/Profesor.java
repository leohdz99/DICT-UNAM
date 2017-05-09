/*
 * Copyright (C) 2017 Josué Hernández
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
 * @author 58599749
 */
public class Profesor {
    
    private String folProf;
    private String prof;
    private String rfc;
    private String activo;
    
    public Profesor(){
        folProf = null;
        prof = null;
        rfc  = null;
        activo = null;               
    }

    public Profesor(String folProf, String prof, String rfc, String activo) {
        this.folProf = folProf;
        this.prof = prof;
        this.rfc = rfc;
        this.activo = activo;
    }

    public String getFolProf() {
        return folProf;
    }

    public void setFolProf(String folProf) {
        this.folProf = folProf;
    }

    public String getProf() {
        return prof;
    }

    public void setProf(String prof) {
        this.prof = prof;
    }

    public String getRfc() {
        return rfc;
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
    
    
}
