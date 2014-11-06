package fr.ufrima.m2pgi.ecom.controller;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import fr.ufrima.m2pgi.ecom.model.Compte;

@SessionScoped
@ManagedBean
public class Login {
	
	private Compte compte;
	
	public void setCurrentUser(Compte compte) {

		this.compte= compte;

	}

	public boolean isLoggedIn() {
		return compte != null;
	}
	
	public Compte getCurrentUser() {

		return compte;

	}

}
