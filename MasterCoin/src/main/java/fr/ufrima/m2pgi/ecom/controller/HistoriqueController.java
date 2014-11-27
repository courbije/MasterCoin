package fr.ufrima.m2pgi.ecom.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;

import fr.ufrima.m2pgi.ecom.facade.PorteMonnaieHistoriqueFacade;
import fr.ufrima.m2pgi.ecom.model.EchangeOffre;
import fr.ufrima.m2pgi.ecom.model.PorteMonnaieHistorique;

@RequestScoped
@ManagedBean
public class HistoriqueController {


	@Inject
	private PorteMonnaieHistoriqueFacade porteMonnaieHistoriqueFacade;

	@ManagedProperty(value = "#{login}")
	private Login login;

	private List<PorteMonnaieHistorique> pmhistoUser;

	public void setLogin(Login login) {
		this.login = login;
	}

	@PostConstruct
	public void init() {
		pmhistoUser = porteMonnaieHistoriqueFacade.findByCompte(login.getCurrentUser());
	}
	
	public List<PorteMonnaieHistorique> getPorteMonnaiesHistorique() {
		return pmhistoUser;
	}

}
