package fr.ufrima.m2pgi.ecom.facade;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import fr.ufrima.m2pgi.ecom.model.Transaction;

@Stateless
public class TransactionFacade
{
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
}
