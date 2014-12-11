package fr.ufrima.m2pgi.ecom.facade;

import java.io.IOException;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import fr.ufrima.m2pgi.ecom.model.Monnaie;
import fr.ufrima.m2pgi.ecom.model.MonnaieDTO;

@Stateless
public class MonnaieFacade
{
    @Inject
    private EntityManager em;
    
    public void create(MonnaieDTO monnaieDTO) {
        em.persist(MonnaieDTO2Monnaie(monnaieDTO));
    }
    
    public void create(Monnaie monnaie) {
    	em.persist(monnaie);
    }
    
	public void edit(Monnaie monnaie) {
        em.merge(monnaie);
    }

    public void remove(Monnaie monnaie) {
        em.remove(em.merge(monnaie));
    }
    
    public Monnaie find(Long id) {
        return em.find(Monnaie.class, id);
    }

    @SuppressWarnings("unchecked")
	public List<Monnaie> findAll() {
        return em.createQuery("select object(o) from Monnaie as o").getResultList();
    }
    
    private Monnaie MonnaieDTO2Monnaie(MonnaieDTO monnaieDTO) {
		Monnaie monnaie = new Monnaie();
		monnaie.setNom(monnaieDTO.getNom());
		monnaie.setAcroyme(monnaieDTO.getAcroyme());
		monnaie.setType(monnaieDTO.getImage().getContentType());
		try {
			monnaie.setImage(monnaieDTO.getImage().getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return monnaie;
	}    
}