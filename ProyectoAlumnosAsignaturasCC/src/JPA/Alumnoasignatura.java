/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JPA;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "alumnoasignatura")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Alumnoasignatura.findAll", query = "SELECT a FROM Alumnoasignatura a")
    , @NamedQuery(name = "Alumnoasignatura.findByCursada", query = "SELECT a FROM Alumnoasignatura a WHERE a.cursada = :cursada")
    , @NamedQuery(name = "Alumnoasignatura.findByIdAlumno", query = "SELECT a FROM Alumnoasignatura a WHERE a.alumnoasignaturaPK.idAlumno = :idAlumno")
    , @NamedQuery(name = "Alumnoasignatura.findByIdAsignatura", query = "SELECT a FROM Alumnoasignatura a WHERE a.alumnoasignaturaPK.idAsignatura = :idAsignatura")})
public class Alumnoasignatura implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AlumnoasignaturaPK alumnoasignaturaPK;
    @Column(name = "cursada")
    private Character cursada;
    @JoinColumn(name = "id_alumno", referencedColumnName = "id_alumno", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Alumnos alumnos;
    @JoinColumn(name = "id_asignatura", referencedColumnName = "id_asignatura", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Asignaturas asignaturas;

    public Alumnoasignatura() {
    }

    public Alumnoasignatura(AlumnoasignaturaPK alumnoasignaturaPK) {
        this.alumnoasignaturaPK = alumnoasignaturaPK;
    }

    public Alumnoasignatura(int idAlumno, int idAsignatura) {
        this.alumnoasignaturaPK = new AlumnoasignaturaPK(idAlumno, idAsignatura);
    }

    public AlumnoasignaturaPK getAlumnoasignaturaPK() {
        return alumnoasignaturaPK;
    }

    public void setAlumnoasignaturaPK(AlumnoasignaturaPK alumnoasignaturaPK) {
        this.alumnoasignaturaPK = alumnoasignaturaPK;
    }

    public Character getCursada() {
        return cursada;
    }

    public void setCursada(Character cursada) {
        this.cursada = cursada;
    }

    public Alumnos getAlumnos() {
        return alumnos;
    }

    public void setAlumnos(Alumnos alumnos) {
        this.alumnos = alumnos;
    }

    public Asignaturas getAsignaturas() {
        return asignaturas;
    }

    public void setAsignaturas(Asignaturas asignaturas) {
        this.asignaturas = asignaturas;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (alumnoasignaturaPK != null ? alumnoasignaturaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Alumnoasignatura)) {
            return false;
        }
        Alumnoasignatura other = (Alumnoasignatura) object;
        if ((this.alumnoasignaturaPK == null && other.alumnoasignaturaPK != null) || (this.alumnoasignaturaPK != null && !this.alumnoasignaturaPK.equals(other.alumnoasignaturaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "controlador.Alumnoasignatura[ alumnoasignaturaPK=" + alumnoasignaturaPK + " ]";
    }
    
}
