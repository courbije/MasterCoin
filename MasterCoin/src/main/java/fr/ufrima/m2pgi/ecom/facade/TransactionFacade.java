package fr.ufrima.m2pgi.ecom.facade;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import fr.ufrima.m2pgi.ecom.model.Compte;
import fr.ufrima.m2pgi.ecom.model.Transaction;

@Stateless
public class TransactionFacade {
	@Inject
	private EntityManager em;

	public void create(Transaction transaction) {
		em.persist(transaction);
	}

	public void edit(Transaction transaction) {
		em.merge(transaction);
	}

	public void remove(Transaction transaction) {
		em.remove(em.merge(transaction));
	}

	public Transaction find(Long id) {
		return em.find(Transaction.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Transaction> findAll() {
		return em.createQuery("select object(t) from Transaction as t").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Date> findAlldistinct() {
		return em.createQuery("select DISTINCT(t.dateValidation) from Transaction as t order by t.dateValidation").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Transaction> findByCompte(Compte compte) {
		return em.createQuery("select object(t) from Transaction as t where t.compteVendeur = :compte or t.compteAcheteur = :compte")
				.setParameter("compte", compte)
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Transaction> findByCompteVendeur(Compte compte) {
		return em.createQuery("select object(t) from Transaction as t where t.compteVendeur = :compte")
				.setParameter("compte", compte)
				.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Transaction> findByCompteAcheteur(Compte compte) {
		return em.createQuery("select object(t) from Transaction as t where t.compteAcheteur = :compte")
				.setParameter("compte", compte)
				.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Transaction> findAllWhereDateIsBetween(Date date1,Date date2) {
		return em.createQuery("select object(t) from Transaction as t where t.dateValidation < :date2 and t.dateValidation>=:date1 order by t.monnaieAchat,t.monnaieVendre")
				.setParameter("date1", date1)
				.setParameter("date2", date2)
				.getResultList();
	}
}
