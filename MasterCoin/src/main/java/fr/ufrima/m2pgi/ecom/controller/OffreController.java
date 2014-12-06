package fr.ufrima.m2pgi.ecom.controller;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import fr.ufrima.m2pgi.ecom.model.EchangeOffre;
import fr.ufrima.m2pgi.ecom.service.EchangeOffreService;
import fr.ufrima.m2pgi.ecom.util.Util;

@ViewScoped
@ManagedBean
public class OffreController {

	@ManagedProperty(value = "#{login}")
	private Login login;

	@Inject
	private EchangeOffreService echangeService;

	@Inject
	private FacesContext facesContext;

	private EchangeOffre newEchangeOffre;

	
	@PostConstruct
	private void init() {
		newEchangeOffre = new EchangeOffre();
	}

	public void register() throws Exception {
		login.forwardToLoginIfNotLoggedIn();
		try {
			echangeService.addOffre(login.getCurrentUser(), newEchangeOffre);
			Util.DisplaySucces(facesContext);
			init();
		} catch (Exception e) {
			Util.DisplayError(e, facesContext);
		}
	}

	public EchangeOffre getNewEchangeOffre() {
		return newEchangeOffre;
	}

	public void setNewEchangeOffre(EchangeOffre newEchangeOffre) {
		this.newEchangeOffre = newEchangeOffre;
	}

	public void setLogin(Login login) {
		this.login = login;
	}
}
