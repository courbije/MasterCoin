package fr.ufrima.m2pgi.ecom.controller;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.inject.Inject;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import fr.ufrima.m2pgi.ecom.model.MonnaieDTO;
import fr.ufrima.m2pgi.ecom.service.MonnaieService;
import fr.ufrima.m2pgi.ecom.util.Util;

@RequestScoped
@ManagedBean
public class MonnaieCreationController {

	@Inject
	private FacesContext facesContext;

	@Inject
	private MonnaieService monnaieService;
	
	private MonnaieDTO newMonnaie;

	public MonnaieDTO getNewMonnaie() {
		return newMonnaie;
	}

	public void setNewMonnaie(MonnaieDTO newMonnaie) {
		this.newMonnaie = newMonnaie;
	}

	@PostConstruct
	public void initNewMember() {
		newMonnaie = new MonnaieDTO();
	}

	public void register() throws Exception {
		try {
			monnaieService.registerMoney(newMonnaie);
			Util.DisplaySucces(facesContext);
			initNewMember();
		} catch (Exception e) {
			Util.DisplayError(e, facesContext);
		}
	}
	
	public void registerImage(String id) throws Exception {
		try {
			monnaieService.registerImage(newMonnaie,id);
			Util.DisplaySucces(facesContext);
			initNewMember();
		} catch (Exception e) {
			Util.DisplayError(e, facesContext);
		}
	}
	
	public boolean hasImage(String id) {
		return monnaieService.hasImage(id);
	}
	
	public StreamedContent getImage() throws Exception {
		if(facesContext.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            // So, we're rendering the HTML. Return a stub StreamedContent so that it will generate right URL.
            return new DefaultStreamedContent();
        }
		else {
	        return monnaieService.getImage(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id"));
	    }
    	
	}
}
