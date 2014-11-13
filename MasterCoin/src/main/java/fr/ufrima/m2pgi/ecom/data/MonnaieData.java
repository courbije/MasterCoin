package fr.ufrima.m2pgi.ecom.data;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import fr.ufrima.m2pgi.ecom.facade.MonnaieFacade;
import fr.ufrima.m2pgi.ecom.model.Monnaie;

@RequestScoped
public class MonnaieData {

    @Inject
    private MonnaieFacade monnaieFacade;

    public List<Monnaie> getMonnaies() {
    	return monnaieFacade.findAll();
    }

}
