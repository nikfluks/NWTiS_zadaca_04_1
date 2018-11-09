/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.nikfluks.ejb.sb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.foi.nwtis.nikfluks.ejb.eb.Dnevnik;

/**
 * Klasa nasljeđuje AbstractFacade određuje da je generički tip T = Dnevnik
 *
 * @author Nikola
 * @version 1
 */
@Stateless
public class DnevnikFacade extends AbstractFacade<Dnevnik> {

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
    public DnevnikFacade() {
        super(Dnevnik.class);
    }

    /**
     * Dohvaća zapise iz tablice koji zadovoljavaju sve ulazne filtere
     *
     * @param ipAdresa filter IP adrese; ako stupac ipadresa ima točno takvu vrijednost onda se vraća taj zapis
     * @param odDatum filter od datum; ako stupac vrijeme nije manji od te vrijednosti onda se vraća taj zapis
     * @param doDatum filter do datum; ako stupac vrijeme nije veći od te vrijednosti onda se vraća taj zapis
     * @param urlAdresa filter URL adrese; ako stupac url ima točno takvu vrijednost onda se vraća taj zapis
     * @param trajanje filter trajanje; ako stupac trajanje ima točno takvu vrijednost onda se vraća taj zapis
     * @return lista svih zapisa koji zadovoljaju sve filtere
     */
    public List<Dnevnik> filtriraj(String ipAdresa, Date odDatum, Date doDatum, String urlAdresa, int trajanje) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(Dnevnik.class);
        Root<Dnevnik> dnevnik = cq.from(Dnevnik.class);
        List<Predicate> filteri = new ArrayList<>();

        filteri.add(cb.between(dnevnik.<Date>get("vrijeme"), odDatum, doDatum));

        if (ipAdresa != null && !ipAdresa.equals("")) {
            filteri.add(cb.equal(dnevnik.get("ipadresa"), ipAdresa));
        }
        if (urlAdresa != null && !urlAdresa.equals("")) {
            filteri.add(cb.equal(dnevnik.get("url"), urlAdresa));
        }
        if (trajanje != -1) {
            filteri.add(cb.equal(dnevnik.get("trajanje"), trajanje));
        }

        cq.select(dnevnik).where(filteri.toArray(new Predicate[]{}));
        return getEntityManager().createQuery(cq).getResultList();
    }
}
