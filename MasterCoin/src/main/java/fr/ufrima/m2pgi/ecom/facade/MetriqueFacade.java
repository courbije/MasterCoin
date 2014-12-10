package fr.ufrima.m2pgi.ecom.facade;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import fr.ufrima.m2pgi.ecom.model.Compte;
import fr.ufrima.m2pgi.ecom.model.Metrique;
import fr.ufrima.m2pgi.ecom.model.Monnaie;

@Stateless
public class MetriqueFacade {
	
	@Inject
	private EntityManager em;

	public void create(Metrique metrique) {
		em.persist(metrique);
	}

	public void edit(Metrique metrique) {
		em.merge(metrique);
	}
	

	public void remove(Metrique metrique) {
		em.remove(em.merge(metrique));
	}

	public Metrique find(Long id) {
		return em.find(Metrique.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Metrique> findAll() {
		return em.createQuery("select object(o) from Metrique as o").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Metrique> findAllByMonnaie(Monnaie monnaie1, Monnaie monnaie2) {
		List<Metrique> res = em.createQuery("select object(o) from Metrique as o where o.monnaie1=:monnaie1 and o.monnaie2=:monnaie2")
				.setParameter("monnaie1", monnaie1)
				.setParameter("monnaie2", monnaie2)		
				
				.getResultList();
		if (res.size()==0) return null;
    	 else return res;
	}
}