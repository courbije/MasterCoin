package fr.ufrima.m2pgi.ecom.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import fr.ufrima.m2pgi.ecom.model.EchangeOffre;
import fr.ufrima.m2pgi.ecom.model.PorteMonnaie;


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
}