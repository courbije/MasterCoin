package fr.ufrima.m2pgi.ecom.data;

import java.util.List;

import javax.annotation.PostConstruct;
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
	private List<EchangeOffre> echangesOffres;

    @Produces
    @Named
    public List<EchangeOffre> getOffres() {
    	return echangesOffres;
    }

    @PostConstruct
    public void init() {
    	echangesOffres = echangeOffreFacade.findAll();
    }
}
