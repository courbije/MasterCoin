package fr.ufrima.m2pgi.ecom.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import fr.ufrima.m2pgi.ecom.data.EchangeRepository;
import fr.ufrima.m2pgi.ecom.model.Echange;
import fr.ufrima.m2pgi.ecom.service.EchangeRegistration;

@Model
public class EchangeController {

	@Inject
	private FacesContext facesContext;

	@Inject
	private EchangeRegistration echangeRegistration;

	@Inject
	private EchangeRepository echangeRepository;

	
	@Produces
	@Named
	private Echange newEchange;

	@PostConstruct
	public void initNewEchange() {
		newEchange = new Echange();
	}

	
	@Produces
    @Named
    public List<Echange> getEchanges() {
        return echangeRepository.findAll();
    }
	
	public void register() throws Exception {
		try {
			echangeRegistration.register(newEchange);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Registered!", "Registration successful");
			facesContext.addMessage(null, m);
			initNewEchange();
		} catch (Exception e) {
			String errorMessage = "fail";
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					errorMessage, "Registration unsuccessful");
			facesContext.addMessage(null, m);
		}
	}

}
