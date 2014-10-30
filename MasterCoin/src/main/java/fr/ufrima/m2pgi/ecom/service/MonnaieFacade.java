package fr.ufrima.m2pgi.ecom.service;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import fr.ufrima.m2pgi.ecom.model.Monnaie;


@Stateless
public class MonnaieFacade
{
    @Inject
    private EntityManager em;
    
    public void create(Monnaie monnaie) {
        em.persist(monnaie);
    }

    public void edit(Monnaie monnaie) {
        em.merge(monnaie);
    }

    public void remove(Monnaie monnaie) {
        em.remove(em.merge(monnaie));
    }
}