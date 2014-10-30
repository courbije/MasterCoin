package fr.ufrima.m2pgi.ecom.service;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import fr.ufrima.m2pgi.ecom.model.Compte;


@Stateless
public class CompteFacade
{
    @Inject
    private EntityManager em;
    
    public void create(Compte compte) {
        em.persist(compte);
    }

    public void edit(Compte compte) {
        em.merge(compte);
    }

    public void remove(Compte compte) {
        em.remove(em.merge(compte));
    }
}