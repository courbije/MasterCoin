package fr.ufrima.m2pgi.ecom.facade;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;

import fr.ufrima.m2pgi.ecom.model.Compte;
import fr.ufrima.m2pgi.ecom.model.Monnaie;
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

	@SuppressWarnings("unchecked")
	public PorteMonnaie find(Compte compte, Monnaie monnaie) {
		List<PorteMonnaie> res = em.createQuery("select object(c) from PorteMonnaie as c where c.compte = :compte and c.monnaie = :monnaie")
				.setParameter("compte", compte)
				.setParameter("monnaie", monnaie)
				.setLockMode(LockModeType.PESSIMISTIC_WRITE)
				.getResultList();
		if (res.size() == 0) return null;
		else return res.get(0);
	}

	@SuppressWarnings("unchecked")
	public List<PorteMonnaie> findByCompte(Compte compte) {
		return em.createQuery("select object(c) from PorteMonnaie as c where c.compte = :compte")
				.setParameter("compte", compte)
				.getResultList();
	}
    
}