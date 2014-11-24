package fr.ufrima.m2pgi.ecom.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import fr.ufrima.m2pgi.ecom.facade.PorteMonnaieFacade;
import fr.ufrima.m2pgi.ecom.model.Monnaie;
import fr.ufrima.m2pgi.ecom.model.PorteMonnaie;
import fr.ufrima.m2pgi.ecom.service.NotEnoughtMoneyException;
import fr.ufrima.m2pgi.ecom.service.PorteMonnaieService;

@RequestScoped
@ManagedBean
public class PorteMonnaieController {
	
	

	@Inject
	private PorteMonnaieService porteMonnaieService;

	@Inject
	private PorteMonnaieFacade porteMonnaieFacade;
	
	@Inject
	private FacesContext facesContext;

	@ManagedProperty(value = "#{login}")
	private Login login;

	private Monnaie monnaie;

	@Min(0)
	@NotNull
	private Double amount;

	public void setLogin(Login login) {
		this.login = login;
	}

	public void registerAdd() throws Exception {
		try {
			porteMonnaieService.addToPorteMonnaie(login.getCurrentUser(), monnaie, amount);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registered!", "Registration successful");
			facesContext.addMessage(null, m);
		} catch (Exception e) {
			String errorMessage = getRootErrorMessage(e);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Registration unsuccessful");
			facesContext.addMessage(null, m);
		}
		init();
	}
	

	private void init() {
		amount = null;
		monnaie = null;
	}

	public void registerRemove() throws Exception {
		try {
			porteMonnaieService.removeToPorteMonnaie(login.getCurrentUser(), monnaie, amount);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registered!", "Registration successful");
			facesContext.addMessage(null, m);
		} catch (NotEnoughtMoneyException e) {
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Pas assez d'argent", "Registration unsuccessful");
			facesContext.addMessage(null, m);
		}catch (Exception e) {
			String errorMessage = getRootErrorMessage(e);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Registration unsuccessful");
			facesContext.addMessage(null, m);
		}
		init();
	}

	public List<PorteMonnaie> getPorteMonnaies() {
		return porteMonnaieFacade.findByCompte(login.getCurrentUser());
	}
	
	public List<Monnaie> getNotEmptyMonnaies() {
		List<PorteMonnaie> pm = porteMonnaieFacade.findByCompte(login.getCurrentUser());
		List<Monnaie> res = new ArrayList<Monnaie>();
		for (PorteMonnaie p : pm) {
			if (p.getMontant() > 0) {
				res.add(p.getMonnaie());				
			}
		}
		return res;
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

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
}
