package fr.ufrima.m2pgi.ecom.data;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import fr.ufrima.m2pgi.ecom.facade.EchangeOffreFacade;
import fr.ufrima.m2pgi.ecom.model.EchangeOffre;

@RequestScoped
public class EchangeOffreData {

    @Inject
    private EchangeOffreFacade echangeOffreFacade;

    @Produces
    @Named
    public List<EchangeOffre> getOffres() {
    	return echangeOffreFacade.findAll();
    }

}
