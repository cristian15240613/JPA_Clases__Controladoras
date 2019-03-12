/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JPA;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author crist
 */
@Embeddable
public class AlumnoasignaturaPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "id_alumno")
    private int idAlumno;
    @Basic(optional = false)
    @Column(name = "id_asignatura")
    private int idAsignatura;

    public AlumnoasignaturaPK() {
    }

    public AlumnoasignaturaPK(int idAlumno, int idAsignatura) {
        this.idAlumno = idAlumno;
        this.idAsignatura = idAsignatura;
    }

    public int getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(int idAlumno) {
        this.idAlumno = idAlumno;
    }

    public int getIdAsignatura() {
        return idAsignatura;
    }

    public void setIdAsignatura(int idAsignatura) {
        this.idAsignatura = idAsignatura;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idAlumno;
        hash += (int) idAsignatura;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AlumnoasignaturaPK)) {
            return false;
        }
        AlumnoasignaturaPK other = (AlumnoasignaturaPK) object;
        if (this.idAlumno != other.idAlumno) {
            return false;
        }
        if (this.idAsignatura != other.idAsignatura) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "controlador.AlumnoasignaturaPK[ idAlumno=" + idAlumno + ", idAsignatura=" + idAsignatura + " ]";
    }
    
}
