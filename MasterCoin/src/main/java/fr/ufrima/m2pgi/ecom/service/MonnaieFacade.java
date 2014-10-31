package fr.ufrima.m2pgi.ecom.service;

import java.util.List;

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
    
    public Monnaie find(Long id) {
        return em.find(Monnaie.class, id);
    }

    @SuppressWarnings("unchecked")
	public List<Monnaie> findAll() {
        return em.createQuery("select object(o) from Monnaie as o").getResultList();
    }
    
}