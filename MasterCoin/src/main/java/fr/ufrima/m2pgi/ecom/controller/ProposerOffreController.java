package fr.ufrima.m2pgi.ecom.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import fr.ufrima.m2pgi.ecom.facade.EchangeOffreFacade;
import fr.ufrima.m2pgi.ecom.facade.MonnaieFacade;
import fr.ufrima.m2pgi.ecom.model.EchangeOffre;
import fr.ufrima.m2pgi.ecom.service.EchangeOffreService;
import fr.ufrima.m2pgi.ecom.util.Util;

@ViewScoped
@ManagedBean
public class ProposerOffreController {
	
    @ManagedProperty(value="#{login}")
    private Login login;
    
	public void setLogin(Login login) {
		this.login = login;
	}
	
	@Inject
	private EchangeOffreService echangeOffreService;

	@Inject
	private EchangeOffreFacade echangeOffreFacade;
	
	@Inject 
	private MonnaieFacade monnaieFacade;
	
	@Inject
	private FacesContext facesContext;

	private EchangeOffre newEchangeOffre;

	private List<EchangeOffre> listeEchangeOffreAV;

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
			this.echangeOffreService.addOffre(this.login.getCurrentUser(), this.newEchangeOffre);
			Util.DisplaySucces(facesContext);
			initNewMember();
		} catch (Exception e) {
			Util.DisplayError(e,facesContext);
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
	
	public List<EchangeOffre> getListeEchangeOffreAV() {
		return listeEchangeOffreAV;
	}

	public String monnaieVente(String id) {
		if(id!=null) {
			if(!this.newEchangeOffre.getMonnaieAchat().equals(this.monnaieFacade.find(Long.parseLong(id,10)))){
				this.newEchangeOffre.setMonnaieVendre(this.monnaieFacade.find(Long.parseLong(id,10)));
			}
		}
		listeEchangeOffreAV = echangeOffreFacade.findAllWhere(newEchangeOffre.getMonnaieAchat(), newEchangeOffre.getMonnaieVendre());
		return null;
	}

	
}
