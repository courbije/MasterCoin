package fr.ufrima.m2pgi.ecom.controller;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import fr.ufrima.m2pgi.ecom.facade.MonnaieFacade;
import fr.ufrima.m2pgi.ecom.model.Monnaie;
import fr.ufrima.m2pgi.ecom.util.Util;

@ViewScoped
@ManagedBean
public class MonnaieCreationController {
	
	@Inject
	private MonnaieFacade monnaieFacade;

	@Inject
	private FacesContext facesContext;

	private Monnaie newMonnaie;

	public Monnaie getNewMonnaie() {
		return newMonnaie;
	}

	public void setNewMonnaie(Monnaie newMonnaie) {
		this.newMonnaie = newMonnaie;
	}

	@PostConstruct
	public void initNewMember() {
		newMonnaie = new Monnaie();
	}

	public void register() throws Exception {
		try {
			monnaieFacade.create(newMonnaie);
			Util.DisplaySucces(facesContext);
			initNewMember();
		} catch (Exception e) {			
			Util.DisplayError(e,facesContext);
		}
	}
}
