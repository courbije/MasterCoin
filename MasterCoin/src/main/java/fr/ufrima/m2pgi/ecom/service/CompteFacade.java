package fr.ufrima.m2pgi.ecom.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import fr.ufrima.m2pgi.ecom.controller.Credentials;
import fr.ufrima.m2pgi.ecom.model.Compte;

@Stateless
public class CompteFacade {
	
	@Inject
	private EntityManager em;

	public Compte create(Compte compte) {
		em.persist(compte);
		return compte;
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

	@SuppressWarnings("unchecked")
	public Compte find(Credentials login) {
		List<Compte> res = em.createQuery("select object(c) from Compte as c where c.login = :username and c.password = :password")
		.setParameter("username", login.getUsername())
		.setParameter("password", login.getPassword())
		.getResultList();
		if (res.size() == 1) {
			return res.get(0);
		}
		return null;
	}
}