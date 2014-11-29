package fr.ufrima.m2pgi.ecom.controller;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import fr.ufrima.m2pgi.ecom.facade.EchangeOffreFacade;
import fr.ufrima.m2pgi.ecom.model.EchangeOffre;
import fr.ufrima.m2pgi.ecom.model.Monnaie;
import fr.ufrima.m2pgi.ecom.model.Transaction;
import fr.ufrima.m2pgi.ecom.service.EchangeTxService;
import fr.ufrima.m2pgi.ecom.util.Util;


@ViewScoped
@ManagedBean
public class EchangeTxController {
	 @ManagedProperty(value="#{login}")
	    private Login login;
	    
		public void setLogin(Login login) {
			this.login = login;
		}
		
	@Inject
	private EchangeTxService echangeTxFacade;

	@Inject
	private FacesContext facesContext;

	@Inject
	private EchangeOffreFacade echangeOffreFacade; 
	
	private Transaction newTransaction;

	public Transaction getNewTransaction() {
		return newTransaction;
	}

	public void setNewTransaction(Transaction newTransaction) {
		this.newTransaction = newTransaction;
	}

	@PostConstruct
	public void initNewMember() {
		newTransaction = new Transaction();
	}

	public void register() throws Exception {
		try {
			newTransaction.setCompteAcheteur(this.login.getCurrentUser());
			newTransaction.setDateValidation(new Date());
			echangeTxFacade.trouverOffres(newTransaction);
			Util.DisplaySucces(facesContext);
			initNewMember();
		} catch (Exception e) {
			Util.DisplayError(e,facesContext);
		}
	}
	
	public Double getQuantitePropose() {

		Monnaie monnaieAchat = newTransaction.getMonnaieAchat();
		Monnaie monnaieVendre = newTransaction.getMonnaieVendre();
		Double montantVoulu = newTransaction.getMontantAchat();
		double montantObtenu= 0;
		List<EchangeOffre> echangeOffre=echangeOffreFacade.findAllWhere(monnaieVendre,monnaieAchat,this.login.getCurrentUser());
		if (echangeOffre==null){
			return 0.0;
		}
		Collections.sort(echangeOffre);
		
		double montantCourant=0; int i=0;
		while (i<echangeOffre.size()&&montantCourant<montantVoulu){
			montantCourant+=echangeOffre.get(i).getMontantVendre();
			if (montantCourant>montantVoulu){
				double ancienMontantAchat = echangeOffre.get(i).getMontantAchat();
				double ancienMontantVendre = echangeOffre.get(i).getMontantVendre();
				
				echangeOffre.get(i).setMontantVendre(montantCourant-montantVoulu);
				echangeOffre.get(i).setMontantAchat(echangeOffre.get(i).getMontantVendre()*ancienMontantAchat/ancienMontantVendre);
				montantObtenu += ancienMontantAchat - echangeOffre.get(i).getMontantAchat();
				montantCourant=montantVoulu;
			}
			else {
				montantObtenu += echangeOffre.get(i).getMontantAchat();
			}
			i++;
		}
		if (montantCourant!=montantVoulu){
			// Pas assez d'argent dans la base
			return 0.0;
		}
		return montantObtenu;

	}
}
