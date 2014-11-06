package fr.ufrima.m2pgi.ecom.data;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import fr.ufrima.m2pgi.ecom.model.EchangeOffre;
import fr.ufrima.m2pgi.ecom.service.EchangeOffreFacade;

@RequestScoped
public class EchangeOffreData {

    @Inject
    private EchangeOffreFacade echangeOffreFacade;

    // @Named provides access the return value via the EL variable name "members" in the UI (e.g.
    // Facelets or JSP view)
    @Produces
    @Named
    public List<EchangeOffre> getOffres() {
    	return echangeOffreFacade.findAll();
    }

}
