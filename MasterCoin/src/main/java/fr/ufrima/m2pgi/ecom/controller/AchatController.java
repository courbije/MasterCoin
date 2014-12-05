package fr.ufrima.m2pgi.ecom.controller;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import fr.ufrima.m2pgi.ecom.model.Transaction;
import fr.ufrima.m2pgi.ecom.service.EchangeTxService;
import fr.ufrima.m2pgi.ecom.service.SameMoneyException;
import fr.ufrima.m2pgi.ecom.util.Util;

@ViewScoped
@ManagedBean
public class AchatController {
	@ManagedProperty(value = "#{login}")
	private Login login;

	@ManagedProperty(value = "#{panier}")
	private Panier panier;

	@Inject
	private EchangeTxService echangeTxFacade;

	@Inject
	private FacesContext facesContext;

	private Transaction newTransaction;

	@PostConstruct
	private void initNewMember() {
		newTransaction = new Transaction();
		newTransaction.setMontantVendre(0.0);
	}

	public String register() throws Exception {
		login.forwardToLoginIfNotLoggedIn();
		try {
			newTransaction.setCompteAcheteur(this.login.getCurrentUser());
			newTransaction.setDateValidation(new Date());
			echangeTxFacade.trouverOffres(newTransaction);
			Util.DisplaySucces(facesContext);
			initNewMember();
		} catch (Exception e) {
			Util.DisplayError(e, facesContext);
		}
		return "";
	}

	public void pannier() {
		try {
			panier.addArticles(newTransaction);
			Util.DisplaySucces("Article ajouté !",facesContext);
		} catch (SameMoneyException e) {
			Util.DisplayError(e, facesContext);
		}
	}
	
	public Transaction getNewTransaction() {
		return newTransaction;
	}

	public void setNewTransaction(Transaction newTransaction) {
		this.newTransaction = newTransaction;
	}

	public void setLogin(Login login) {
		this.login = login;
	}

	public void setPanier(Panier panier) {
		this.panier = panier;
	}
	
	public void calculerQuantitePropose() {
		newTransaction.setMontantVendre(echangeTxFacade.calculerMontantVendre(newTransaction, login.getCurrentUser()));
	}
}
