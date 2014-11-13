package fr.ufrima.m2pgi.ecom.data;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import fr.ufrima.m2pgi.ecom.facade.CompteFacade;
import fr.ufrima.m2pgi.ecom.model.Compte;

@RequestScoped
public class CompteData {

    @Inject
    private CompteFacade compteFacade;

    public List<Compte> getComptes() {
    	return compteFacade.findAll();
    }

}
