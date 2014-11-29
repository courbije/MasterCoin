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
	
	private String idV;
	
	private String idA;
	
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
	
	public List<EchangeOffre> getListeEchangeOffreAV() {
		if (listeEchangeOffreAV == null) {
			listeEchangeOffreAV = echangeOffreFacade.findAllWhere(newEchangeOffre.getMonnaieAchat(), newEchangeOffre.getMonnaieVendre());
		}
		return listeEchangeOffreAV;
	}

	public String getIdV() {
		return idV;
	}

	public void setIdV(String idV) {
		if(idV!=null && !idV.equals("")) {
			this.newEchangeOffre.setMonnaieVendre(this.monnaieFacade.find(Long.parseLong(idV)));
		}
		listeEchangeOffreAV =null;
		this.idV = idV;
	}

	public String getIdA() {
		return idA;
	}
	

	public void setIdA(String idA) {
		if(idA!=null && !idA.equals("")) {
			this.newEchangeOffre.setMonnaieAchat(this.monnaieFacade.find(Long.parseLong(idA)));
		}
		listeEchangeOffreAV =null;
		this.idA = idA;
	}
}
