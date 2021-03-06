package fr.ufrima.m2pgi.ecom.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;

import fr.ufrima.m2pgi.ecom.facade.EchangeOffreFacade;
import fr.ufrima.m2pgi.ecom.facade.MonnaieFacade;
import fr.ufrima.m2pgi.ecom.model.EchangeOffre;
import fr.ufrima.m2pgi.ecom.model.Monnaie;

@ViewScoped
@ManagedBean
public class EchangeController {
	
    @ManagedProperty(value="#{login}")
    private Login login;
    
	private String idV;
	
	private String idA;
	
	private Monnaie monnaieA;
	
	private Monnaie monnaieV;
	
	@Inject 
	private MonnaieFacade monnaieFacade;
	
	@Inject
	private EchangeOffreFacade echangeOffreFacade;

	private List<EchangeOffre> listeEchangeOffreAV;

	private List<EchangeOffre> listeEchangeOffreVA;

	@ManagedProperty(value = "#{achatController}")
	private AchatController achatController;

	@ManagedProperty(value = "#{offreController}")
	private OffreController offreController;
    
	@PostConstruct
	private void init() {
		if (monnaieA != null && monnaieV != null) {
			listeEchangeOffreAV = echangeOffreFacade.findAllWhere(monnaieA,monnaieV);	
			listeEchangeOffreVA = echangeOffreFacade.findAllWhere(monnaieV,monnaieA);
		}
	}
	
	public void forwardIfNoParameter(ComponentSystemEvent cse) {
		if (idV == null || idA == null || idV == "" || idA == "") {
			FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "/marcher/trouverEchange?faces-redirect=true&includeViewParams=true");	
		}
	}
	public void forwardIfNoParameter() {
		if (idV == null || idA == null || idV == "" || idA == "") {
			FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "/marcher/trouverEchange?faces-redirect=true&includeViewParams=true");	
		}
	}

	public void registerOffre() throws Exception {
		offreController.register();
		init();
	}
	
	public void registerAchat() throws Exception {
		achatController.register();
		init();
	}
	
	public void setAchatController(AchatController achatController) {
		this.achatController = achatController;
	}

	public void setOffreController(OffreController offreController) {
		this.offreController = offreController;
	}

	public Monnaie getMonnaieA() {
		return monnaieA;
	}

	public Monnaie getMonnaieV() {
		return monnaieV;
	}

	public List<EchangeOffre> getListeEchangeOffreAV() {
		return listeEchangeOffreAV;
	}
	

	public List<EchangeOffre> getListeEchangeOffreVA() {
		return listeEchangeOffreVA;
	}

	public String getIdV() {
		return idV;
	}

	public void setIdV(String idV) {
		if(idV!=null && !idV.equals("")) {
			this.monnaieV = this.monnaieFacade.find(Long.parseLong(idV));
			if (achatController!=null&&achatController.getNewTransaction()!=null)
				achatController.getNewTransaction().setMonnaieVendre(this.monnaieV);
		}
		init();
		this.idV = idV;
	}

	public void setLogin(Login login) {
		this.login = login;
	}

	public String getIdA() {
		return idA;
	}
	

	public void setIdA(String idA) {
		if(idA!=null && !idA.equals("")) {
			this.monnaieA = this.monnaieFacade.find(Long.parseLong(idA));
			if (achatController!=null&&achatController.getNewTransaction()!=null)				
				achatController.getNewTransaction().setMonnaieAchat(this.monnaieA);
		}
		init();
		this.idA = idA;
	}
}
