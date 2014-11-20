package fr.ufrima.m2pgi.ecom.facade;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import fr.ufrima.m2pgi.ecom.model.Compte;
import fr.ufrima.m2pgi.ecom.model.PorteMonnaieHistorique;


@Stateless
public class PorteMonnaieHistoriqueFacade
{
    @Inject
    private EntityManager em;
    
    public void create(PorteMonnaieHistorique porteMonnaieHistorique) {
        em.persist(porteMonnaieHistorique);
    }

    public void edit(PorteMonnaieHistorique porteMonnaieHistorique) {
        em.merge(porteMonnaieHistorique);
    }

    public void remove(PorteMonnaieHistorique porteMonnaieHistorique) {
        em.remove(em.merge(porteMonnaieHistorique));
    }
    
    public PorteMonnaieHistorique find(Long id) {
        return em.find(PorteMonnaieHistorique.class, id);
    }

    @SuppressWarnings("unchecked")
	public List<PorteMonnaieHistorique> findAll() {
        return em.createQuery("select object(o) from PorteMonnaieHistorique as o").getResultList();
    }

	@SuppressWarnings("unchecked")
	public List<PorteMonnaieHistorique> findByCompte(Compte compte) {
		return em.createQuery("select object(c) from PorteMonnaieHistorique as c where c.compte = :compte")
				.setParameter("compte", compte)
				.getResultList();
	}
    
}