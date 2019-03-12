/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Controlador.exceptions.NonexistentEntityException;
import Controlador.exceptions.PreexistingEntityException;
import JPA.Alumnoasignatura;
import JPA.AlumnoasignaturaPK;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import JPA.Alumnos;
import JPA.Asignaturas;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author crist
 */
public class AlumnoasignaturaJpaController implements Serializable {

    public AlumnoasignaturaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Alumnoasignatura alumnoasignatura) throws PreexistingEntityException, Exception {
        if (alumnoasignatura.getAlumnoasignaturaPK() == null) {
            alumnoasignatura.setAlumnoasignaturaPK(new AlumnoasignaturaPK());
        }
        alumnoasignatura.getAlumnoasignaturaPK().setIdAsignatura(alumnoasignatura.getAsignaturas().getIdAsignatura());
        alumnoasignatura.getAlumnoasignaturaPK().setIdAlumno(alumnoasignatura.getAlumnos().getIdAlumno());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Alumnos alumnos = alumnoasignatura.getAlumnos();
            if (alumnos != null) {
                alumnos = em.getReference(alumnos.getClass(), alumnos.getIdAlumno());
                alumnoasignatura.setAlumnos(alumnos);
            }
            Asignaturas asignaturas = alumnoasignatura.getAsignaturas();
            if (asignaturas != null) {
                asignaturas = em.getReference(asignaturas.getClass(), asignaturas.getIdAsignatura());
                alumnoasignatura.setAsignaturas(asignaturas);
            }
            em.persist(alumnoasignatura);
            if (alumnos != null) {
                alumnos.getAlumnoasignaturaCollection().add(alumnoasignatura);
                alumnos = em.merge(alumnos);
            }
            if (asignaturas != null) {
                asignaturas.getAlumnoasignaturaCollection().add(alumnoasignatura);
                asignaturas = em.merge(asignaturas);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAlumnoasignatura(alumnoasignatura.getAlumnoasignaturaPK()) != null) {
                throw new PreexistingEntityException("Alumnoasignatura " + alumnoasignatura + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Alumnoasignatura alumnoasignatura) throws NonexistentEntityException, Exception {
        alumnoasignatura.getAlumnoasignaturaPK().setIdAsignatura(alumnoasignatura.getAsignaturas().getIdAsignatura());
        alumnoasignatura.getAlumnoasignaturaPK().setIdAlumno(alumnoasignatura.getAlumnos().getIdAlumno());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Alumnoasignatura persistentAlumnoasignatura = em.find(Alumnoasignatura.class, alumnoasignatura.getAlumnoasignaturaPK());
            Alumnos alumnosOld = persistentAlumnoasignatura.getAlumnos();
            Alumnos alumnosNew = alumnoasignatura.getAlumnos();
            Asignaturas asignaturasOld = persistentAlumnoasignatura.getAsignaturas();
            Asignaturas asignaturasNew = alumnoasignatura.getAsignaturas();
            if (alumnosNew != null) {
                alumnosNew = em.getReference(alumnosNew.getClass(), alumnosNew.getIdAlumno());
                alumnoasignatura.setAlumnos(alumnosNew);
            }
            if (asignaturasNew != null) {
                asignaturasNew = em.getReference(asignaturasNew.getClass(), asignaturasNew.getIdAsignatura());
                alumnoasignatura.setAsignaturas(asignaturasNew);
            }
            alumnoasignatura = em.merge(alumnoasignatura);
            if (alumnosOld != null && !alumnosOld.equals(alumnosNew)) {
                alumnosOld.getAlumnoasignaturaCollection().remove(alumnoasignatura);
                alumnosOld = em.merge(alumnosOld);
            }
            if (alumnosNew != null && !alumnosNew.equals(alumnosOld)) {
                alumnosNew.getAlumnoasignaturaCollection().add(alumnoasignatura);
                alumnosNew = em.merge(alumnosNew);
            }
            if (asignaturasOld != null && !asignaturasOld.equals(asignaturasNew)) {
                asignaturasOld.getAlumnoasignaturaCollection().remove(alumnoasignatura);
                asignaturasOld = em.merge(asignaturasOld);
            }
            if (asignaturasNew != null && !asignaturasNew.equals(asignaturasOld)) {
                asignaturasNew.getAlumnoasignaturaCollection().add(alumnoasignatura);
                asignaturasNew = em.merge(asignaturasNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                AlumnoasignaturaPK id = alumnoasignatura.getAlumnoasignaturaPK();
                if (findAlumnoasignatura(id) == null) {
                    throw new NonexistentEntityException("The alumnoasignatura with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(AlumnoasignaturaPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Alumnoasignatura alumnoasignatura;
            try {
                alumnoasignatura = em.getReference(Alumnoasignatura.class, id);
                alumnoasignatura.getAlumnoasignaturaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The alumnoasignatura with id " + id + " no longer exists.", enfe);
            }
            Alumnos alumnos = alumnoasignatura.getAlumnos();
            if (alumnos != null) {
                alumnos.getAlumnoasignaturaCollection().remove(alumnoasignatura);
                alumnos = em.merge(alumnos);
            }
            Asignaturas asignaturas = alumnoasignatura.getAsignaturas();
            if (asignaturas != null) {
                asignaturas.getAlumnoasignaturaCollection().remove(alumnoasignatura);
                asignaturas = em.merge(asignaturas);
            }
            em.remove(alumnoasignatura);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Alumnoasignatura> findAlumnoasignaturaEntities() {
        return findAlumnoasignaturaEntities(true, -1, -1);
    }

    public List<Alumnoasignatura> findAlumnoasignaturaEntities(int maxResults, int firstResult) {
        return findAlumnoasignaturaEntities(false, maxResults, firstResult);
    }

    private List<Alumnoasignatura> findAlumnoasignaturaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Alumnoasignatura.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Alumnoasignatura findAlumnoasignatura(AlumnoasignaturaPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Alumnoasignatura.class, id);
        } finally {
            em.close();
        }
    }

    public int getAlumnoasignaturaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Alumnoasignatura> rt = cq.from(Alumnoasignatura.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
