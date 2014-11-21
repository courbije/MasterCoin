package fr.ufrima.m2pgi.ecom.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;

import fr.ufrima.m2pgi.ecom.facade.PorteMonnaieHistoriqueFacade;
import fr.ufrima.m2pgi.ecom.model.PorteMonnaieHistorique;

@RequestScoped
@ManagedBean
public class HistoriqueController {


	@Inject
	private PorteMonnaieHistoriqueFacade porteMonnaieHistoriqueFacade;

	@ManagedProperty(value = "#{login}")
	private Login login;

	public void setLogin(Login login) {
		this.login = login;
	}

	public List<PorteMonnaieHistorique> getPorteMonnaiesHistorique() {
		return porteMonnaieHistoriqueFacade.findByCompte(login.getCurrentUser());
	}

}
