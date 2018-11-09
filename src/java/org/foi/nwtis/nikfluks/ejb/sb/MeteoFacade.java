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
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import org.foi.nwtis.nikfluks.ejb.eb.Meteo;
import org.foi.nwtis.nikfluks.ejb.eb.Parkiralista;

/**
 * Klasa nasljeđuje AbstractFacade određuje da je generički tip T = Meteo
 *
 * @author Nikola
 * @version 1
 */
@Stateless
public class MeteoFacade extends AbstractFacade<Meteo> {

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
    public MeteoFacade() {
        super(Meteo.class);
    }

    /**
     * Pronalazi određeni zapis iz tablice temeljem id-a.
     *
     * @param id id temeljem kojeg se pronalazi zapis
     * @return zapis iz tablice s zadanim id-om
     */
    public List<Meteo> findByParkingId(int id) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root<Meteo> meteo = cq.from(Meteo.class);
        Path<Parkiralista> premaParkingu = meteo.get("id");
        Path<Integer> premaParkinguId = premaParkingu.get("id");
        cq.where(cb.equal(premaParkinguId, id));
        return getEntityManager().createQuery(cq).getResultList();
    }

}
