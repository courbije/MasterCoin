package fr.ufrima.m2pgi.ecom.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import fr.ufrima.m2pgi.ecom.facade.PorteMonnaieFacade;
import fr.ufrima.m2pgi.ecom.model.Monnaie;
import fr.ufrima.m2pgi.ecom.model.PorteMonnaie;
import fr.ufrima.m2pgi.ecom.service.PorteMonnaieService;
import fr.ufrima.m2pgi.ecom.util.Util;

@RequestScoped
@ManagedBean
public class PorteMonnaieController {
	
	

	@Inject
	private PorteMonnaieService porteMonnaieService;

	@Inject
	private PorteMonnaieFacade porteMonnaieFacade;
	
	@Inject
	private FacesContext facesContext;

	@ManagedProperty(value = "#{login}")
	private Login login;

	private Monnaie monnaie;

	@Min(0)
	@NotNull
	private Integer amount;

	public void setLogin(Login login) {
		this.login = login;
	}

	public void registerAdd() throws Exception {
		try {
			porteMonnaieService.addToPorteMonnaie(login.getCurrentUser(), monnaie, amount);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registered!", "Registration successful");
			facesContext.addMessage(null, m);
		} catch (Exception e) {
			Util.DisplayError(e,facesContext);
		}
		init();
	}
	

	private void init() {
		amount = null;
		monnaie = null;
	}

	public void registerRemove() throws Exception {
		try {
			porteMonnaieService.removeToPorteMonnaie(login.getCurrentUser(), monnaie, amount);
			Util.DisplaySucces(facesContext);
		} catch (Exception e) {
			Util.DisplayError(e,facesContext);
		}
		init();
	}

	public List<PorteMonnaie> getPorteMonnaies() {
		return porteMonnaieFacade.findByCompte(login.getCurrentUser());
	}
	
	public List<Monnaie> getNotEmptyMonnaies() {
		List<PorteMonnaie> pm = porteMonnaieFacade.findByCompte(login.getCurrentUser());
		List<Monnaie> res = new ArrayList<Monnaie>();
		for (PorteMonnaie p : pm) {
			if (p.getMontant() > 0) {
				res.add(p.getMonnaie());				
			}
		}
		return res;
	}

	public Monnaie getMonnaie() {
		return monnaie;
	}

	public void setMonnaie(Monnaie monnaie) {
		this.monnaie = monnaie;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}
}
