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
import fr.ufrima.m2pgi.ecom.facade.TransactionFacade;
import fr.ufrima.m2pgi.ecom.model.EchangeOffre;
import fr.ufrima.m2pgi.ecom.model.Monnaie;
import fr.ufrima.m2pgi.ecom.model.Transaction;

@ViewScoped
@ManagedBean
public class AdminController {
	
	@Inject
	private EchangeOffreFacade echangeOffreFacade;

	private List<EchangeOffre> echangeOffres;
	
	@Inject
	private TransactionFacade transactionFacade;
	
	private List<Transaction> transactions;

	@PostConstruct
	private void init() {
		echangeOffres=echangeOffreFacade.findAll();
		transactions=transactionFacade.findAll();
	}

	public List<EchangeOffre> getEchangeOffres() {
		return echangeOffres;
	}
	

	public List<Transaction> getTransactions() {
		return transactions;
	}
	
	
}
