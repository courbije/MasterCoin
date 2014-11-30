package fr.ufrima.m2pgi.ecom.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import fr.ufrima.m2pgi.ecom.facade.EchangeOffreFacade;
import fr.ufrima.m2pgi.ecom.model.EchangeOffre;
import fr.ufrima.m2pgi.ecom.service.EchangeOffreService;
import fr.ufrima.m2pgi.ecom.util.Util;

@RequestScoped
@ManagedBean
public class EchangeRapideController {
	
    @ManagedProperty(value="#{login}")
    private Login login;
    
	@ManagedProperty(value = "#{achatController}")
	private AchatController achatController;

	@ManagedProperty(value = "#{offreController}")
	private OffreController offreController;
	
	@Inject
	private EchangeOffreFacade echangeFacade;

	@Inject
	private EchangeOffreService echangeService;
	
	@Inject
	private FacesContext facesContext;
	
	private List<EchangeOffre> echangeOffreUser;

	
	@PostConstruct
	private void init() {
		echangeOffreUser = echangeFacade.findByCompte(login.getCurrentUser());
	}

	public void registerOffre() throws Exception {
		offreController.register();
		init();
	}
	

	public void registerAchat() throws Exception {
		achatController.register();
		init();
	}
	
	public void removeOffre(String id) throws Exception {
		try {
			echangeService.removeOffre(Long.parseLong(id), login.getCurrentUser());
			Util.DisplaySucces(facesContext);
			init();
		} catch (Exception e) {
			Util.DisplayError(e,facesContext);
		}
	}

	public void setLogin(Login login) {
		this.login = login;
	}

	public List<EchangeOffre> getOffres() {
		return echangeOffreUser;
	}

	public void setAchatController(AchatController achatController) {
		this.achatController = achatController;
	}

	public void setOffreController(OffreController offreController) {
		this.offreController = offreController;
	}
	
}
