package fr.ufrima.m2pgi.ecom.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;

import fr.ufrima.m2pgi.ecom.facade.PorteMonnaieHistoriqueFacade;
import fr.ufrima.m2pgi.ecom.facade.TransactionFacade;
import fr.ufrima.m2pgi.ecom.model.PorteMonnaieHistorique;
import fr.ufrima.m2pgi.ecom.model.Transaction;

@RequestScoped
@ManagedBean
public class HistoriqueController {


	@Inject
	private PorteMonnaieHistoriqueFacade porteMonnaieHistoriqueFacade;

	@Inject
	private TransactionFacade transactionFacade;
	
	@ManagedProperty(value = "#{login}")
	private Login login;

	private List<PorteMonnaieHistorique> pmhistoUser;

	private List<Transaction> transactionHisto;

	
	public List<Transaction> getTransactionHisto() {
		return transactionHisto;
	}



	public void setLogin(Login login) {
		this.login = login;
	}
	
	

	@PostConstruct
	public void init() {
		pmhistoUser = porteMonnaieHistoriqueFacade.findByCompte(login.getCurrentUser());
		transactionHisto = transactionFacade.findByCompte(login.getCurrentUser());
		
	}
	
	public List<PorteMonnaieHistorique> getPorteMonnaiesHistorique() {
		return pmhistoUser;
	}

}
