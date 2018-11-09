/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.nikfluks.ejb.sb;

import java.util.List;
import javax.persistence.EntityManager;

/**
 * Apstraktna klasa pomoću koje radimo CRUD operacije nad bazom.
 *
 * @author Nikola
 * @version 1
 * @param <T> Generički tip T
 */
public abstract class AbstractFacade<T> {

    private Class<T> entityClass;

    /**
     * Konstruktor klase, sprema stvarni tip klase.
     *
     * @param entityClass stvarni tip klase
     */
    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    /**
     * Apstrkatna metoda, nadjačavaju je podklase.
     *
     * @return
     */
    protected abstract EntityManager getEntityManager();

    /**
     * Kreirana novi zapis u tablici.
     *
     * @param entity objekt koji se zapisuje u tablicu
     */
    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    /**
     * Ažurira postojeći zapis u tablici.
     *
     * @param entity objekt koji se ažurira u tablici
     */
    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    /**
     * Briše postojeći zapis u tablici.
     *
     * @param entity objekt koji se briše iz tablice
     */
    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    /**
     * Pronalazi određeni zapis iz tablice temeljem id-a.
     *
     * @param id id temeljem kojeg se pronalazi zapis
     * @return zapis iz tablice s zadanim id-om
     */
    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    /**
     * Pronalazi sve zapise iz tablice.
     *
     * @return svi zapise iz određene tablice
     */
    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    /**
     * Pronalazi zapise iz tablice iz zadanog raspona.
     *
     * @param range raspon iz kojeg se traže zapisi iz tablice, prvi rezultat se nalazi na poziciji range[0], a zadnji na poziciji
     * range[1]
     * @return zapisi iz tablice iz određenog raspona
     */
    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    /**
     * Broji koliko zapisa ima određena tablica.
     *
     * @return broj zapisa određene tablice
     */
    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

}
