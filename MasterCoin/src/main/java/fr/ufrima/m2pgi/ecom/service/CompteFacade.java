package fr.ufrima.m2pgi.ecom.service;

import java.util.List;

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
    
    public Compte find(Long id) {
        return em.find(Compte.class, id);
    }

    @SuppressWarnings("unchecked")
	public List<Compte> findAll() {
        return em.createQuery("select object(o) from Compte as o").getResultList();
    }
    
}