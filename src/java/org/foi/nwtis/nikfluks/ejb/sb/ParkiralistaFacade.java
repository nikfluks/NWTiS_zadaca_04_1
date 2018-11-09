/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.nikfluks.ejb.sb;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import org.foi.nwtis.nikfluks.ejb.eb.Parkiralista;

/**
 * Klasa nasljeđuje AbstractFacade određuje da je generički tip T = Parkiralista
 *
 * @author Nikola
 * @version 1
 */
@Stateless
public class ParkiralistaFacade extends AbstractFacade<Parkiralista> {

    @PersistenceContext(unitName = "zadaca_4_1PU")
    private EntityManager em;

    /**
     * Getter za EntityManger em
     *
     * @return em
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * Konstruktor klase, prosljeđuje tip nadklasi.
     */
    public ParkiralistaFacade() {
        super(Parkiralista.class);
    }

    /**
     * Nadjačana metoda iz nadklase koja dohvaća sve zapise iz tablice, ali ih i sortirana uzlazno (case insensitive).
     *
     * @return svi zapisi iz tablice sortirani uzlazno
     */
    @Override
    public List<Parkiralista> findAll() {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(Parkiralista.class);
        Root<Parkiralista> parkiralista = cq.from(Parkiralista.class);
        Expression<String> Naziv = parkiralista.get("naziv");
        Expression<String> naziv = cb.lower(Naziv);
        cq.orderBy(cb.asc(naziv));
        return getEntityManager().createQuery(cq).getResultList();
    }

    /**
     * Pronalazi određeni zapis iz tablice temeljem id-a.
     *
     * @param id id temeljem kojeg se pronalazi zapis
     * @return zapis iz tablice s zadanim id-om
     */
    public Parkiralista findById(int id) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(Parkiralista.class);
        Root<Parkiralista> parkiralista = cq.from(Parkiralista.class);
        cq.where(cb.equal(parkiralista.get("id"), id));
        return (Parkiralista) getEntityManager().createQuery(cq).getSingleResult();
    }

}
