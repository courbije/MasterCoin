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
public class CompteModificationController {

	@ManagedProperty(value = "#{login}")
	private Login login;

	public void setLogin(Login login) {
		this.login = login;
	}

	@Inject
	private CompteFacade compteFacade;

	@Inject
	private FacesContext facesContext;

	private Compte myCompte;

	public Compte getMyCompte() {
		return myCompte;
	}

	public void setMyCompte(Compte myCompte) {
		this.myCompte = myCompte;
	}

	@PostConstruct
	public void initNewMember() {
		myCompte = login.getCurrentUser();
	}

	public void edit() throws Exception {
		try {
			compteFacade.edit(myCompte);
			Util.DisplaySucces(facesContext);
			initNewMember();
		} catch (Exception e) {
			Util.DisplayError(e,facesContext);
		}
	}
}
