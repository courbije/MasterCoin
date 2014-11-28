package fr.ufrima.m2pgi.ecom.controller;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import fr.ufrima.m2pgi.ecom.model.Compte;

@SessionScoped
@ManagedBean
public class Login {

	private Compte compte;
	private String from_url = "";

	public void setCurrentUser(Compte compte) {

		this.compte = compte;

	}

	public boolean isLoggedIn() {
		return compte != null;
	}

	public Compte getCurrentUser() {
		return compte;
	}
	
	public String getFromUrl() {
		return from_url;
	}
	
	public void setFromUrl(String url) {
	
		this.from_url = url;
		
	}
	
	public void forwardToLoginIfNotLoggedIn(ComponentSystemEvent cse) {
		if (!isLoggedIn()) {
			setFromUrl(FacesContext.getCurrentInstance().getViewRoot().getViewId());
			FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "/login?faces-redirect=true&includeViewParams=true");
		}
	}

}
