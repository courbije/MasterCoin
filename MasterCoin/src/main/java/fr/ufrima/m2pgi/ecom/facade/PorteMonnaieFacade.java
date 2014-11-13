package fr.ufrima.m2pgi.ecom.facade;

import java.util.List;

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
    
    public PorteMonnaie find(Long id) {
        return em.find(PorteMonnaie.class, id);
    }

    @SuppressWarnings("unchecked")
	public List<PorteMonnaie> findAll() {
        return em.createQuery("select object(o) from PorteMonnaie as o").getResultList();
    }
    
}