package fr.ufrima.m2pgi.ecom.data;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import javax.inject.Named;

import fr.ufrima.m2pgi.ecom.facade.MonnaieFacade;
import fr.ufrima.m2pgi.ecom.model.Monnaie;

@RequestScoped
@ManagedBean
public class MonnaieData {

    @Inject
    private MonnaieFacade monnaieFacade;
	private List<Monnaie> monnaies;
	
    
    @Produces
    @Named
    public List<Monnaie> getMonnaies() {
    	return monnaies;
    }
    
    @PostConstruct
    public void init() {
    	monnaies = monnaieFacade.findAll();
    }

}
