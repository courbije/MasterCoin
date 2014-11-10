package fr.ufrima.m2pgi.ecom.controller;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import fr.ufrima.m2pgi.ecom.model.EchangeOffre;
import fr.ufrima.m2pgi.ecom.service.EchangeOffreFacade;

@ViewScoped
@ManagedBean
public class EchangeOffreController {
	
	@Inject
	private EchangeOffreFacade echangeFacade;

	@Inject
	private FacesContext facesContext;

	private EchangeOffre newEchangeOffre;

	public EchangeOffre getNewEchangeOffre() {
		return newEchangeOffre;
	}

	public void setNewEchangeOffre(EchangeOffre newEchangeOffre) {
		this.newEchangeOffre = newEchangeOffre;
	}

	@PostConstruct
	public void initNewMember() {
		newEchangeOffre = new EchangeOffre();
	}

	public void register() throws Exception {
		try {
			newEchangeOffre.setDateCreation(new Date());
			echangeFacade.create(newEchangeOffre);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registered!", "Registration successful");
			facesContext.addMessage(null, m);
			initNewMember();
		} catch (Exception e) {
			String errorMessage = getRootErrorMessage(e);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Registration unsuccessful");
			facesContext.addMessage(null, m);
		}
	}

	private String getRootErrorMessage(Exception e) {
		// Default to general error message that registration failed.
		String errorMessage = "Registration failed. See server log for more information";
		if (e == null) {
			// This shouldn't happen, but return the default messages
			return errorMessage;
		}

		// Start with the exception and recurse to find the root cause
		Throwable t = e;
		while (t != null) {
			// Get the message from the Throwable class instance
			errorMessage = t.getLocalizedMessage();
			t = t.getCause();
		}
		// This is the root cause message
		return errorMessage;
	}

}
