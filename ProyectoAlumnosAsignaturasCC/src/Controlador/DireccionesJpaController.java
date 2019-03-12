/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Controlador.exceptions.NonexistentEntityException;
import Controlador.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import JPA.Alumnos;
import JPA.Direcciones;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author crist
 */
public class DireccionesJpaController implements Serializable {

    public DireccionesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Direcciones direcciones) throws PreexistingEntityException, Exception {
        if (direcciones.getAlumnosCollection() == null) {
            direcciones.setAlumnosCollection(new ArrayList<Alumnos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Alumnos> attachedAlumnosCollection = new ArrayList<Alumnos>();
            for (Alumnos alumnosCollectionAlumnosToAttach : direcciones.getAlumnosCollection()) {
                alumnosCollectionAlumnosToAttach = em.getReference(alumnosCollectionAlumnosToAttach.getClass(), alumnosCollectionAlumnosToAttach.getIdAlumno());
                attachedAlumnosCollection.add(alumnosCollectionAlumnosToAttach);
            }
            direcciones.setAlumnosCollection(attachedAlumnosCollection);
            em.persist(direcciones);
            for (Alumnos alumnosCollectionAlumnos : direcciones.getAlumnosCollection()) {
                Direcciones oldIdDirOfAlumnosCollectionAlumnos = alumnosCollectionAlumnos.getIdDir();
                alumnosCollectionAlumnos.setIdDir(direcciones);
                alumnosCollectionAlumnos = em.merge(alumnosCollectionAlumnos);
                if (oldIdDirOfAlumnosCollectionAlumnos != null) {
                    oldIdDirOfAlumnosCollectionAlumnos.getAlumnosCollection().remove(alumnosCollectionAlumnos);
                    oldIdDirOfAlumnosCollectionAlumnos = em.merge(oldIdDirOfAlumnosCollectionAlumnos);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDirecciones(direcciones.getIdDir()) != null) {
                throw new PreexistingEntityException("Direcciones " + direcciones + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Direcciones direcciones) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Direcciones persistentDirecciones = em.find(Direcciones.class, direcciones.getIdDir());
            Collection<Alumnos> alumnosCollectionOld = persistentDirecciones.getAlumnosCollection();
            Collection<Alumnos> alumnosCollectionNew = direcciones.getAlumnosCollection();
            Collection<Alumnos> attachedAlumnosCollectionNew = new ArrayList<Alumnos>();
            for (Alumnos alumnosCollectionNewAlumnosToAttach : alumnosCollectionNew) {
                alumnosCollectionNewAlumnosToAttach = em.getReference(alumnosCollectionNewAlumnosToAttach.getClass(), alumnosCollectionNewAlumnosToAttach.getIdAlumno());
                attachedAlumnosCollectionNew.add(alumnosCollectionNewAlumnosToAttach);
            }
            alumnosCollectionNew = attachedAlumnosCollectionNew;
            direcciones.setAlumnosCollection(alumnosCollectionNew);
            direcciones = em.merge(direcciones);
            for (Alumnos alumnosCollectionOldAlumnos : alumnosCollectionOld) {
                if (!alumnosCollectionNew.contains(alumnosCollectionOldAlumnos)) {
                    alumnosCollectionOldAlumnos.setIdDir(null);
                    alumnosCollectionOldAlumnos = em.merge(alumnosCollectionOldAlumnos);
                }
            }
            for (Alumnos alumnosCollectionNewAlumnos : alumnosCollectionNew) {
                if (!alumnosCollectionOld.contains(alumnosCollectionNewAlumnos)) {
                    Direcciones oldIdDirOfAlumnosCollectionNewAlumnos = alumnosCollectionNewAlumnos.getIdDir();
                    alumnosCollectionNewAlumnos.setIdDir(direcciones);
                    alumnosCollectionNewAlumnos = em.merge(alumnosCollectionNewAlumnos);
                    if (oldIdDirOfAlumnosCollectionNewAlumnos != null && !oldIdDirOfAlumnosCollectionNewAlumnos.equals(direcciones)) {
                        oldIdDirOfAlumnosCollectionNewAlumnos.getAlumnosCollection().remove(alumnosCollectionNewAlumnos);
                        oldIdDirOfAlumnosCollectionNewAlumnos = em.merge(oldIdDirOfAlumnosCollectionNewAlumnos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = direcciones.getIdDir();
                if (findDirecciones(id) == null) {
                    throw new NonexistentEntityException("The direcciones with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Direcciones direcciones;
            try {
                direcciones = em.getReference(Direcciones.class, id);
                direcciones.getIdDir();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The direcciones with id " + id + " no longer exists.", enfe);
            }
            Collection<Alumnos> alumnosCollection = direcciones.getAlumnosCollection();
            for (Alumnos alumnosCollectionAlumnos : alumnosCollection) {
                alumnosCollectionAlumnos.setIdDir(null);
                alumnosCollectionAlumnos = em.merge(alumnosCollectionAlumnos);
            }
            em.remove(direcciones);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Direcciones> findDireccionesEntities() {
        return findDireccionesEntities(true, -1, -1);
    }

    public List<Direcciones> findDireccionesEntities(int maxResults, int firstResult) {
        return findDireccionesEntities(false, maxResults, firstResult);
    }

    private List<Direcciones> findDireccionesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Direcciones.class));
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

    public Direcciones findDirecciones(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Direcciones.class, id);
        } finally {
            em.close();
        }
    }

    public int getDireccionesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Direcciones> rt = cq.from(Direcciones.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
