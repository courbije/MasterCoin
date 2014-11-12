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

	public void setCurrentUser(Compte compte) {

		this.compte = compte;

	}

	public boolean isLoggedIn() {
		return compte != null;
	}

	public Compte getCurrentUser() {
		return compte;
	}

	public void forwardToLoginIfNotLoggedIn(ComponentSystemEvent cse) {
		if (!isLoggedIn()) {
			FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "/login?faces-redirect=true");
		}
	}

}
