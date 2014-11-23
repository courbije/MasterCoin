package fr.ufrima.m2pgi.ecom.controller;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import fr.ufrima.m2pgi.ecom.facade.CompteFacade;
import fr.ufrima.m2pgi.ecom.model.Compte;
import fr.ufrima.m2pgi.ecom.util.Util;

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
			login.setCurrentUser(newCompte);
			Util.DisplaySucces(facesContext);
			initNewMember();
			return "accueil?faces-redirect=true";
		} catch (Exception e) {
			Util.DisplayError(e,facesContext);
			return null;
		}
	}
}
