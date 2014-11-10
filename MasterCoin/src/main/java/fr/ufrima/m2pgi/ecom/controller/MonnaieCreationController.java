package fr.ufrima.m2pgi.ecom.controller;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import fr.ufrima.m2pgi.ecom.model.Monnaie;
import fr.ufrima.m2pgi.ecom.service.MonnaieFacade;

@ViewScoped
@ManagedBean
public class MonnaieCreationController {
	
	@Inject
	private MonnaieFacade monnaieFacade;

	@Inject
	private FacesContext facesContext;

	private Monnaie newMonnaie;

	public Monnaie getNewMonnaie() {
		return newMonnaie;
	}

	public void setNewMonnaie(Monnaie newMonnaie) {
		this.newMonnaie = newMonnaie;
	}

	@PostConstruct
	public void initNewMember() {
		newMonnaie = new Monnaie();
	}

	public void register() throws Exception {
		try {
			monnaieFacade.create(newMonnaie);
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
