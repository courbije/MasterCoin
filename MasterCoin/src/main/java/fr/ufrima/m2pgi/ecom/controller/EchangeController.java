package fr.ufrima.m2pgi.ecom.controller;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import fr.ufrima.m2pgi.ecom.model.Echange;
import fr.ufrima.m2pgi.ecom.service.EchangeRegistration;

public class EchangeController {
	

       @Inject
    private EchangeRegistration echangeRegistration;
	
	@Produces
    @Named
    private Echange newEchange;

    @PostConstruct
    public void initNewEchange() {
        newEchange = new Echange();
    }
    
    public void register() throws Exception {
        try {
        	echangeRegistration.register(newEchange);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registered!", "Registration successful");
            initNewEchange();
        } catch (Exception e) {
            String errorMessage = "fail";
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Registration unsuccessful");
        }
    }

}
