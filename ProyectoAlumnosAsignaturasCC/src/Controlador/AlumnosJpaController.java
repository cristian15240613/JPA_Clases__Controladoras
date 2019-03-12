/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Controlador.exceptions.IllegalOrphanException;
import Controlador.exceptions.NonexistentEntityException;
import Controlador.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import JPA.Direcciones;
import JPA.Alumnoasignatura;
import JPA.Alumnos;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author crist
 */
public class AlumnosJpaController implements Serializable {

    public AlumnosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Alumnos alumnos) throws PreexistingEntityException, Exception {
        if (alumnos.getAlumnoasignaturaCollection() == null) {
            alumnos.setAlumnoasignaturaCollection(new ArrayList<Alumnoasignatura>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Direcciones idDir = alumnos.getIdDir();
            if (idDir != null) {
                idDir = em.getReference(idDir.getClass(), idDir.getIdDir());
                alumnos.setIdDir(idDir);
            }
            Collection<Alumnoasignatura> attachedAlumnoasignaturaCollection = new ArrayList<Alumnoasignatura>();
            for (Alumnoasignatura alumnoasignaturaCollectionAlumnoasignaturaToAttach : alumnos.getAlumnoasignaturaCollection()) {
                alumnoasignaturaCollectionAlumnoasignaturaToAttach = em.getReference(alumnoasignaturaCollectionAlumnoasignaturaToAttach.getClass(), alumnoasignaturaCollectionAlumnoasignaturaToAttach.getAlumnoasignaturaPK());
                attachedAlumnoasignaturaCollection.add(alumnoasignaturaCollectionAlumnoasignaturaToAttach);
            }
            alumnos.setAlumnoasignaturaCollection(attachedAlumnoasignaturaCollection);
            em.persist(alumnos);
            if (idDir != null) {
                idDir.getAlumnosCollection().add(alumnos);
                idDir = em.merge(idDir);
            }
            for (Alumnoasignatura alumnoasignaturaCollectionAlumnoasignatura : alumnos.getAlumnoasignaturaCollection()) {
                Alumnos oldAlumnosOfAlumnoasignaturaCollectionAlumnoasignatura = alumnoasignaturaCollectionAlumnoasignatura.getAlumnos();
                alumnoasignaturaCollectionAlumnoasignatura.setAlumnos(alumnos);
                alumnoasignaturaCollectionAlumnoasignatura = em.merge(alumnoasignaturaCollectionAlumnoasignatura);
                if (oldAlumnosOfAlumnoasignaturaCollectionAlumnoasignatura != null) {
                    oldAlumnosOfAlumnoasignaturaCollectionAlumnoasignatura.getAlumnoasignaturaCollection().remove(alumnoasignaturaCollectionAlumnoasignatura);
                    oldAlumnosOfAlumnoasignaturaCollectionAlumnoasignatura = em.merge(oldAlumnosOfAlumnoasignaturaCollectionAlumnoasignatura);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAlumnos(alumnos.getIdAlumno()) != null) {
                throw new PreexistingEntityException("Alumnos " + alumnos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Alumnos alumnos) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Alumnos persistentAlumnos = em.find(Alumnos.class, alumnos.getIdAlumno());
            Direcciones idDirOld = persistentAlumnos.getIdDir();
            Direcciones idDirNew = alumnos.getIdDir();
            Collection<Alumnoasignatura> alumnoasignaturaCollectionOld = persistentAlumnos.getAlumnoasignaturaCollection();
            Collection<Alumnoasignatura> alumnoasignaturaCollectionNew = alumnos.getAlumnoasignaturaCollection();
            List<String> illegalOrphanMessages = null;
            for (Alumnoasignatura alumnoasignaturaCollectionOldAlumnoasignatura : alumnoasignaturaCollectionOld) {
                if (!alumnoasignaturaCollectionNew.contains(alumnoasignaturaCollectionOldAlumnoasignatura)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Alumnoasignatura " + alumnoasignaturaCollectionOldAlumnoasignatura + " since its alumnos field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idDirNew != null) {
                idDirNew = em.getReference(idDirNew.getClass(), idDirNew.getIdDir());
                alumnos.setIdDir(idDirNew);
            }
            Collection<Alumnoasignatura> attachedAlumnoasignaturaCollectionNew = new ArrayList<Alumnoasignatura>();
            for (Alumnoasignatura alumnoasignaturaCollectionNewAlumnoasignaturaToAttach : alumnoasignaturaCollectionNew) {
                alumnoasignaturaCollectionNewAlumnoasignaturaToAttach = em.getReference(alumnoasignaturaCollectionNewAlumnoasignaturaToAttach.getClass(), alumnoasignaturaCollectionNewAlumnoasignaturaToAttach.getAlumnoasignaturaPK());
                attachedAlumnoasignaturaCollectionNew.add(alumnoasignaturaCollectionNewAlumnoasignaturaToAttach);
            }
            alumnoasignaturaCollectionNew = attachedAlumnoasignaturaCollectionNew;
            alumnos.setAlumnoasignaturaCollection(alumnoasignaturaCollectionNew);
            alumnos = em.merge(alumnos);
            if (idDirOld != null && !idDirOld.equals(idDirNew)) {
                idDirOld.getAlumnosCollection().remove(alumnos);
                idDirOld = em.merge(idDirOld);
            }
            if (idDirNew != null && !idDirNew.equals(idDirOld)) {
                idDirNew.getAlumnosCollection().add(alumnos);
                idDirNew = em.merge(idDirNew);
            }
            for (Alumnoasignatura alumnoasignaturaCollectionNewAlumnoasignatura : alumnoasignaturaCollectionNew) {
                if (!alumnoasignaturaCollectionOld.contains(alumnoasignaturaCollectionNewAlumnoasignatura)) {
                    Alumnos oldAlumnosOfAlumnoasignaturaCollectionNewAlumnoasignatura = alumnoasignaturaCollectionNewAlumnoasignatura.getAlumnos();
                    alumnoasignaturaCollectionNewAlumnoasignatura.setAlumnos(alumnos);
                    alumnoasignaturaCollectionNewAlumnoasignatura = em.merge(alumnoasignaturaCollectionNewAlumnoasignatura);
                    if (oldAlumnosOfAlumnoasignaturaCollectionNewAlumnoasignatura != null && !oldAlumnosOfAlumnoasignaturaCollectionNewAlumnoasignatura.equals(alumnos)) {
                        oldAlumnosOfAlumnoasignaturaCollectionNewAlumnoasignatura.getAlumnoasignaturaCollection().remove(alumnoasignaturaCollectionNewAlumnoasignatura);
                        oldAlumnosOfAlumnoasignaturaCollectionNewAlumnoasignatura = em.merge(oldAlumnosOfAlumnoasignaturaCollectionNewAlumnoasignatura);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = alumnos.getIdAlumno();
                if (findAlumnos(id) == null) {
                    throw new NonexistentEntityException("The alumnos with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Alumnos alumnos;
            try {
                alumnos = em.getReference(Alumnos.class, id);
                alumnos.getIdAlumno();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The alumnos with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Alumnoasignatura> alumnoasignaturaCollectionOrphanCheck = alumnos.getAlumnoasignaturaCollection();
            for (Alumnoasignatura alumnoasignaturaCollectionOrphanCheckAlumnoasignatura : alumnoasignaturaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Alumnos (" + alumnos + ") cannot be destroyed since the Alumnoasignatura " + alumnoasignaturaCollectionOrphanCheckAlumnoasignatura + " in its alumnoasignaturaCollection field has a non-nullable alumnos field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Direcciones idDir = alumnos.getIdDir();
            if (idDir != null) {
                idDir.getAlumnosCollection().remove(alumnos);
                idDir = em.merge(idDir);
            }
            em.remove(alumnos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Alumnos> findAlumnosEntities() {
        return findAlumnosEntities(true, -1, -1);
    }

    public List<Alumnos> findAlumnosEntities(int maxResults, int firstResult) {
        return findAlumnosEntities(false, maxResults, firstResult);
    }

    private List<Alumnos> findAlumnosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Alumnos.class));
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

    public Alumnos findAlumnos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Alumnos.class, id);
        } finally {
            em.close();
        }
    }

    public int getAlumnosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Alumnos> rt = cq.from(Alumnos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
