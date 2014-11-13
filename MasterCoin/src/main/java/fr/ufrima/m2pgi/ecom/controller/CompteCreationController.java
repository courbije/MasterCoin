package fr.ufrima.m2pgi.ecom.controller;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import fr.ufrima.m2pgi.ecom.facade.CompteFacade;
import fr.ufrima.m2pgi.ecom.model.Compte;

@ViewScoped
@ManagedBean
public class CompteCreationController {

	@ManagedProperty(value = "#{login}")
	private Login login;

	public void setLogin(Login login) {
		this.login = login;
	}

	@Inject
	private CompteFacade compteFacade;

	@Inject
	private FacesContext facesContext;

	private Compte newCompte;

	public Compte getNewCompte() {
		return newCompte;
	}

	public void setNewCompte(Compte newCompte) {
		this.newCompte = newCompte;
	}

	@PostConstruct
	public void initNewMember() {
		newCompte = new Compte();
	}

	public String register() throws Exception {
		try {
			newCompte = compteFacade.create(newCompte);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registered!", "Registration successful");
			facesContext.addMessage(null, m);
			login.setCurrentUser(newCompte);
			initNewMember();
			return "accueil?faces-redirect=true";
		} catch (Exception e) {
			String errorMessage = getRootErrorMessage(e);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Registration unsuccessful");
			facesContext.addMessage(null, m);
			return null;
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
