package fr.ufrima.m2pgi.ecom.service;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import fr.ufrima.m2pgi.ecom.model.PorteMonnaie;


@Stateless
public class PorteMonnaieFacade
{
    @Inject
    private EntityManager em;
    
    public void create(PorteMonnaie porteMonnaie) {
        em.persist(porteMonnaie);
    }

    public void edit(PorteMonnaie porteMonnaie) {
        em.merge(porteMonnaie);
    }

    public void remove(PorteMonnaie porteMonnaie) {
        em.remove(em.merge(porteMonnaie));
    }
}