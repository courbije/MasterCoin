package fr.ufrima.m2pgi.ecom.data;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import javax.inject.Named;

import fr.ufrima.m2pgi.ecom.facade.CompteFacade;
import fr.ufrima.m2pgi.ecom.model.Compte;

@RequestScoped
@ManagedBean
public class CompteData {

    @Inject
    private CompteFacade compteFacade;
	private List<Compte> comptes;

    @Produces
    @Named
    public List<Compte> getComptes() {
    	return comptes;
    }

    @PostConstruct
    public void init() {
    	comptes = compteFacade.findAll();
    }
    
}
