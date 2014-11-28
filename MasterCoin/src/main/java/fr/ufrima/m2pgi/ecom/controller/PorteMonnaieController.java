package fr.ufrima.m2pgi.ecom.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
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
import fr.ufrima.m2pgi.ecom.service.NotEnoughtMoneyException;
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
	private Double amount;

	private List<PorteMonnaie> porteMonnaieUser;

	private ArrayList<Monnaie> notEmptyMonnaie;

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
	
	@PostConstruct
	private void init() {
		amount = null;
		monnaie = null;
		porteMonnaieUser = porteMonnaieFacade.findByCompte(login.getCurrentUser());
		notEmptyMonnaie = new ArrayList<Monnaie>();
		for (PorteMonnaie p : porteMonnaieUser) {
			if (p.getMontant() > 0) {
				notEmptyMonnaie.add(p.getMonnaie());				
			}
		}
	}

	public void registerRemove() throws Exception {
		try {
			porteMonnaieService.removeFromPorteMonnaie(login.getCurrentUser(), monnaie, amount);
			Util.DisplaySucces(facesContext);
			init();
		} catch (NotEnoughtMoneyException e) {
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Pas assez d'argent", "Registration unsuccessful");
			facesContext.addMessage(null, m);
		}catch (Exception e) {
			Util.DisplayError(e,facesContext);
		}
	}

	public List<PorteMonnaie> getPorteMonnaies() {
		return porteMonnaieUser;
	}
	
	public List<Monnaie> getNotEmptyMonnaies() {
		return notEmptyMonnaie;
	}

	public Monnaie getMonnaie() {
		return monnaie;
	}

	public void setMonnaie(Monnaie monnaie) {
		this.monnaie = monnaie;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
}
