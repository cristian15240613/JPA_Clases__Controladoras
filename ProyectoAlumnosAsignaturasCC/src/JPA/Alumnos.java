/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JPA;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author crist
 */
@Entity
@Table(name = "alumnos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Alumnos.findAll", query = "SELECT a FROM Alumnos a")
    , @NamedQuery(name = "Alumnos.findByIdAlumno", query = "SELECT a FROM Alumnos a WHERE a.idAlumno = :idAlumno")
    , @NamedQuery(name = "Alumnos.findByApellidos", query = "SELECT a FROM Alumnos a WHERE a.apellidos = :apellidos")
    , @NamedQuery(name = "Alumnos.findByCurso", query = "SELECT a FROM Alumnos a WHERE a.curso = :curso")
    , @NamedQuery(name = "Alumnos.findByNombre", query = "SELECT a FROM Alumnos a WHERE a.nombre = :nombre")
    , @NamedQuery(name = "Alumnos.findByTitulacion", query = "SELECT a FROM Alumnos a WHERE a.titulacion = :titulacion")})
public class Alumnos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_alumno")
    private Integer idAlumno;
    @Column(name = "apellidos")
    private String apellidos;
    @Column(name = "curso")
    private Integer curso;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "titulacion")
    private Integer titulacion;
    @JoinColumn(name = "id_dir", referencedColumnName = "id_dir")
    @ManyToOne
    private Direcciones idDir;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "alumnos")
    private Collection<Alumnoasignatura> alumnoasignaturaCollection;

    public Alumnos() {
    }

    public Alumnos(Integer idAlumno) {
        this.idAlumno = idAlumno;
    }

    public Integer getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(Integer idAlumno) {
        this.idAlumno = idAlumno;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Integer getCurso() {
        return curso;
    }

    public void setCurso(Integer curso) {
        this.curso = curso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getTitulacion() {
        return titulacion;
    }

    public void setTitulacion(Integer titulacion) {
        this.titulacion = titulacion;
    }

    public Direcciones getIdDir() {
        return idDir;
    }

    public void setIdDir(Direcciones idDir) {
        this.idDir = idDir;
    }

    @XmlTransient
    public Collection<Alumnoasignatura> getAlumnoasignaturaCollection() {
        return alumnoasignaturaCollection;
    }

    public void setAlumnoasignaturaCollection(Collection<Alumnoasignatura> alumnoasignaturaCollection) {
        this.alumnoasignaturaCollection = alumnoasignaturaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAlumno != null ? idAlumno.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Alumnos)) {
            return false;
        }
        Alumnos other = (Alumnos) object;
        if ((this.idAlumno == null && other.idAlumno != null) || (this.idAlumno != null && !this.idAlumno.equals(other.idAlumno))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "controlador.Alumnos[ idAlumno=" + idAlumno + " ]";
    }
    
}
