package fr.ufrima.m2pgi.ecom.controller;

import java.util.Date;





import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import fr.ufrima.m2pgi.ecom.facade.EchangeOffreFacade;
import fr.ufrima.m2pgi.ecom.facade.MonnaieFacade;
import fr.ufrima.m2pgi.ecom.model.EchangeOffre;
import fr.ufrima.m2pgi.ecom.service.NotEnoughtMoneyException;
import fr.ufrima.m2pgi.ecom.service.PorteMonnaieService;
import fr.ufrima.m2pgi.ecom.service.SameMoneyException;

@ViewScoped
@ManagedBean
public class ProposerOffreController {
    @ManagedProperty(value="#{login}")
    private Login login;
    
	public void setLogin(Login login) {
		this.login = login;
	}
	
	@Inject
	private PorteMonnaieService porteMonnaieService;
	
	@Inject
	private EchangeOffreFacade echangeFacade;

	@Inject 
	private MonnaieFacade monnaieFacade;
	
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
			newEchangeOffre.setCompte(this.login.getCurrentUser());
			newEchangeOffre.setDateCreation(new Date());
			if(this.newEchangeOffre.getMonnaieAchat().equals(this.newEchangeOffre.getMonnaieVendre())) {
				throw new SameMoneyException();
			}
			porteMonnaieService.removeToPorteMonnaie(newEchangeOffre.getCompte(), newEchangeOffre.getMonnaieVendre(), newEchangeOffre.getMontantVendre());
			echangeFacade.create(newEchangeOffre);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registered!", "Registration successful");
			facesContext.addMessage(null, m);
			initNewMember();
		} catch (SameMoneyException e) {
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Impossible d'avoir deux fois la même monnaie", "Registration unsuccessful");
			facesContext.addMessage(null, m);
		} catch (NotEnoughtMoneyException e) {
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Pas assez d'argent", "Registration unsuccessful");
			facesContext.addMessage(null, m);
		} catch (Exception e) {
			String errorMessage = getRootErrorMessage(e);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Registration unsuccessful");
			facesContext.addMessage(null, m);
		}
	}
	
	public boolean isNullMonnaieAchat() {
		return this.newEchangeOffre.getMonnaieAchat()==null;
	}
	
	public String monnaieAchat(String id) {
		if(id!=null) {
			this.newEchangeOffre.setMonnaieAchat(this.monnaieFacade.find(Long.parseLong(id,10)));
		}
		return null;
	}
	
	public boolean isNullMonnaieVente() {
		return this.newEchangeOffre.getMonnaieVendre()==null;
	}
	
	public String monnaieVente(String id) {
		if(id!=null) {
			if(!this.newEchangeOffre.getMonnaieAchat().equals(this.monnaieFacade.find(Long.parseLong(id,10)))){
				this.newEchangeOffre.setMonnaieVendre(this.monnaieFacade.find(Long.parseLong(id,10)));
			}
		}
		return null;
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
