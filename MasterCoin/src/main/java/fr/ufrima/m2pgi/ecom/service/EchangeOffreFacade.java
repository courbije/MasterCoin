package fr.ufrima.m2pgi.ecom.service;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import fr.ufrima.m2pgi.ecom.model.EchangeOffre;


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
}