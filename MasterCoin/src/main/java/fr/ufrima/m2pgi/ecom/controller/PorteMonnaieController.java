package fr.ufrima.m2pgi.ecom.controller;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import fr.ufrima.m2pgi.ecom.model.Monnaie;
import fr.ufrima.m2pgi.ecom.service.PorteMonnaieService;

@ViewScoped
@ManagedBean
public class PorteMonnaieController {
	
	@Inject
	private PorteMonnaieService porteMonnaieService;

	@Inject
	private FacesContext facesContext;
	
	
	@ManagedProperty(value = "#{login}")
	private Login login;


	private Monnaie monnaie;

	private Integer amount;

	public void setLogin(Login login) {
		this.login = login;
	}
	
	
	public void register() throws Exception {
		try {
			porteMonnaieService.addToPorteMonnaie(login.getCurrentUser(), monnaie, amount);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registered!", "Registration successful");
			facesContext.addMessage(null, m);
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


	public Monnaie getMonnaie() {
		return monnaie;
	}


	public void setMonnaie(Monnaie monnaie) {
		this.monnaie = monnaie;
	}


	public Integer getAmount() {
		return amount;
	}


	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	
	
}
