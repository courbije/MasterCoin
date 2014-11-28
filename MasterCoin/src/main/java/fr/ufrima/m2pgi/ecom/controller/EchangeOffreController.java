package fr.ufrima.m2pgi.ecom.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import fr.ufrima.m2pgi.ecom.facade.EchangeOffreFacade;
import fr.ufrima.m2pgi.ecom.model.EchangeOffre;
import fr.ufrima.m2pgi.ecom.service.EchangeOffreService;
import fr.ufrima.m2pgi.ecom.util.Util;

@ViewScoped
@ManagedBean
public class EchangeOffreController {
	
    @ManagedProperty(value="#{login}")
    private Login login;
    
	public void setLogin(Login login) {
		this.login = login;
	}
	
	@Inject
	private EchangeOffreFacade echangeFacade;

	@Inject
	private EchangeOffreService echangeService;
	
	@Inject
	private FacesContext facesContext;


	private EchangeOffre newEchangeOffre;

	private List<EchangeOffre> echangeOffreUser;

	public EchangeOffre getNewEchangeOffre() {
		return newEchangeOffre;
	}

	public void setNewEchangeOffre(EchangeOffre newEchangeOffre) {
		this.newEchangeOffre = newEchangeOffre;
	}

	@PostConstruct
	public void init() {
		newEchangeOffre = new EchangeOffre();
		echangeOffreUser = echangeFacade.findByCompte(login.getCurrentUser());
	}

	public void register() throws Exception {
		try {
			echangeService.addOffre(login.getCurrentUser(),newEchangeOffre);
			Util.DisplaySucces(facesContext);
			init();
		} catch (Exception e) {
			Util.DisplayError(e,facesContext);
		}
	}
	
	public void removeOffre(String id) throws Exception {
		try {
			echangeService.removeOffre(Long.parseLong(id), login.getCurrentUser());
			Util.DisplaySucces(facesContext);
		} catch (Exception e) {
			Util.DisplayError(e,facesContext);
		}
	}

	public List<EchangeOffre> getOffres() {
		return echangeOffreUser;
	}
}
