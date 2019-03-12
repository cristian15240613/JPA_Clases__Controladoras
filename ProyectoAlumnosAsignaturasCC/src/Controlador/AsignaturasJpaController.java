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
import JPA.Alumnoasignatura;
import JPA.Asignaturas;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author crist
 */
public class AsignaturasJpaController implements Serializable {

    public AsignaturasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Asignaturas asignaturas) throws PreexistingEntityException, Exception {
        if (asignaturas.getAlumnoasignaturaCollection() == null) {
            asignaturas.setAlumnoasignaturaCollection(new ArrayList<Alumnoasignatura>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Alumnoasignatura> attachedAlumnoasignaturaCollection = new ArrayList<Alumnoasignatura>();
            for (Alumnoasignatura alumnoasignaturaCollectionAlumnoasignaturaToAttach : asignaturas.getAlumnoasignaturaCollection()) {
                alumnoasignaturaCollectionAlumnoasignaturaToAttach = em.getReference(alumnoasignaturaCollectionAlumnoasignaturaToAttach.getClass(), alumnoasignaturaCollectionAlumnoasignaturaToAttach.getAlumnoasignaturaPK());
                attachedAlumnoasignaturaCollection.add(alumnoasignaturaCollectionAlumnoasignaturaToAttach);
            }
            asignaturas.setAlumnoasignaturaCollection(attachedAlumnoasignaturaCollection);
            em.persist(asignaturas);
            for (Alumnoasignatura alumnoasignaturaCollectionAlumnoasignatura : asignaturas.getAlumnoasignaturaCollection()) {
                Asignaturas oldAsignaturasOfAlumnoasignaturaCollectionAlumnoasignatura = alumnoasignaturaCollectionAlumnoasignatura.getAsignaturas();
                alumnoasignaturaCollectionAlumnoasignatura.setAsignaturas(asignaturas);
                alumnoasignaturaCollectionAlumnoasignatura = em.merge(alumnoasignaturaCollectionAlumnoasignatura);
                if (oldAsignaturasOfAlumnoasignaturaCollectionAlumnoasignatura != null) {
                    oldAsignaturasOfAlumnoasignaturaCollectionAlumnoasignatura.getAlumnoasignaturaCollection().remove(alumnoasignaturaCollectionAlumnoasignatura);
                    oldAsignaturasOfAlumnoasignaturaCollectionAlumnoasignatura = em.merge(oldAsignaturasOfAlumnoasignaturaCollectionAlumnoasignatura);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAsignaturas(asignaturas.getIdAsignatura()) != null) {
                throw new PreexistingEntityException("Asignaturas " + asignaturas + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Asignaturas asignaturas) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Asignaturas persistentAsignaturas = em.find(Asignaturas.class, asignaturas.getIdAsignatura());
            Collection<Alumnoasignatura> alumnoasignaturaCollectionOld = persistentAsignaturas.getAlumnoasignaturaCollection();
            Collection<Alumnoasignatura> alumnoasignaturaCollectionNew = asignaturas.getAlumnoasignaturaCollection();
            List<String> illegalOrphanMessages = null;
            for (Alumnoasignatura alumnoasignaturaCollectionOldAlumnoasignatura : alumnoasignaturaCollectionOld) {
                if (!alumnoasignaturaCollectionNew.contains(alumnoasignaturaCollectionOldAlumnoasignatura)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Alumnoasignatura " + alumnoasignaturaCollectionOldAlumnoasignatura + " since its asignaturas field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Alumnoasignatura> attachedAlumnoasignaturaCollectionNew = new ArrayList<Alumnoasignatura>();
            for (Alumnoasignatura alumnoasignaturaCollectionNewAlumnoasignaturaToAttach : alumnoasignaturaCollectionNew) {
                alumnoasignaturaCollectionNewAlumnoasignaturaToAttach = em.getReference(alumnoasignaturaCollectionNewAlumnoasignaturaToAttach.getClass(), alumnoasignaturaCollectionNewAlumnoasignaturaToAttach.getAlumnoasignaturaPK());
                attachedAlumnoasignaturaCollectionNew.add(alumnoasignaturaCollectionNewAlumnoasignaturaToAttach);
            }
            alumnoasignaturaCollectionNew = attachedAlumnoasignaturaCollectionNew;
            asignaturas.setAlumnoasignaturaCollection(alumnoasignaturaCollectionNew);
            asignaturas = em.merge(asignaturas);
            for (Alumnoasignatura alumnoasignaturaCollectionNewAlumnoasignatura : alumnoasignaturaCollectionNew) {
                if (!alumnoasignaturaCollectionOld.contains(alumnoasignaturaCollectionNewAlumnoasignatura)) {
                    Asignaturas oldAsignaturasOfAlumnoasignaturaCollectionNewAlumnoasignatura = alumnoasignaturaCollectionNewAlumnoasignatura.getAsignaturas();
                    alumnoasignaturaCollectionNewAlumnoasignatura.setAsignaturas(asignaturas);
                    alumnoasignaturaCollectionNewAlumnoasignatura = em.merge(alumnoasignaturaCollectionNewAlumnoasignatura);
                    if (oldAsignaturasOfAlumnoasignaturaCollectionNewAlumnoasignatura != null && !oldAsignaturasOfAlumnoasignaturaCollectionNewAlumnoasignatura.equals(asignaturas)) {
                        oldAsignaturasOfAlumnoasignaturaCollectionNewAlumnoasignatura.getAlumnoasignaturaCollection().remove(alumnoasignaturaCollectionNewAlumnoasignatura);
                        oldAsignaturasOfAlumnoasignaturaCollectionNewAlumnoasignatura = em.merge(oldAsignaturasOfAlumnoasignaturaCollectionNewAlumnoasignatura);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = asignaturas.getIdAsignatura();
                if (findAsignaturas(id) == null) {
                    throw new NonexistentEntityException("The asignaturas with id " + id + " no longer exists.");
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
            Asignaturas asignaturas;
            try {
                asignaturas = em.getReference(Asignaturas.class, id);
                asignaturas.getIdAsignatura();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The asignaturas with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Alumnoasignatura> alumnoasignaturaCollectionOrphanCheck = asignaturas.getAlumnoasignaturaCollection();
            for (Alumnoasignatura alumnoasignaturaCollectionOrphanCheckAlumnoasignatura : alumnoasignaturaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Asignaturas (" + asignaturas + ") cannot be destroyed since the Alumnoasignatura " + alumnoasignaturaCollectionOrphanCheckAlumnoasignatura + " in its alumnoasignaturaCollection field has a non-nullable asignaturas field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(asignaturas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Asignaturas> findAsignaturasEntities() {
        return findAsignaturasEntities(true, -1, -1);
    }

    public List<Asignaturas> findAsignaturasEntities(int maxResults, int firstResult) {
        return findAsignaturasEntities(false, maxResults, firstResult);
    }

    private List<Asignaturas> findAsignaturasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Asignaturas.class));
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

    public Asignaturas findAsignaturas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Asignaturas.class, id);
        } finally {
            em.close();
        }
    }

    public int getAsignaturasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Asignaturas> rt = cq.from(Asignaturas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
