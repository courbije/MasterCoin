package fr.ufrima.m2pgi.ecom.facade;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import fr.ufrima.m2pgi.ecom.model.EchangeOffre;
import fr.ufrima.m2pgi.ecom.model.Monnaie;

@Stateless
public class EchangeOffreFacade
{
    @Inject
    private EntityManager em;
    
    public void create(EchangeOffre echangeOffre) {
        em.persist(echangeOffre);
    }

    public void edit(EchangeOffre echangeOffre) {
        em.merge(echangeOffre);
    }

    public void remove(EchangeOffre echangeOffre) {
        em.remove(em.merge(echangeOffre));
    }
    
    public EchangeOffre find(Long id) {
        return em.find(EchangeOffre.class, id);
    }

    @SuppressWarnings("unchecked")
	public List<EchangeOffre> findAll() {
        return em.createQuery("select object(o) from EchangeOffre as o").getResultList();
    }
    
    @SuppressWarnings("unchecked")
	public List<EchangeOffre> findAllWhere(Monnaie monnaieAchat, Monnaie monnaieVente){
    	 List<EchangeOffre> res =em.createQuery("select object(o) from EchangeOffre as o where o.monnaieAchat=:monnaieAchat and o.monnaieVendre=:monnaieVendre")
    			.setParameter("monnaieAchat", monnaieAchat)
    			.setParameter("monnaieVendre",monnaieVente)
    			.getResultList();
    	 if (res.size()==0) return null;
    	 else return res;
    }
    	
}
