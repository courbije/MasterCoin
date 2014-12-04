package fr.ufrima.m2pgi.ecom.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.inject.Inject;

import fr.ufrima.m2pgi.ecom.facade.EchangeOffreFacade;
import fr.ufrima.m2pgi.ecom.facade.TransactionFacade;
import fr.ufrima.m2pgi.ecom.model.Compte;
import fr.ufrima.m2pgi.ecom.model.EchangeOffre;
import fr.ufrima.m2pgi.ecom.model.Monnaie;
import fr.ufrima.m2pgi.ecom.model.Transaction;
import fr.ufrima.m2pgi.ecom.util.Util;

@Stateless
public class EchangeTxService {

	@Resource
	private EJBContext context;
	
	@Inject
	private EchangeOffreFacade echangeOffreFacade;
	
	@Inject
	private TransactionFacade transactionFacade;
	
	@Inject
	private PorteMonnaieService porteMonnaieService;
	
	
	public List<Transaction> trouverOffres(Transaction newTransaction) throws NotEnoughMoneyException, NotEnoughMoneyInBaseException, SameMoneyException {

		List<Transaction> trans = new ArrayList<Transaction>();
		Monnaie monnaieAchat = newTransaction.getMonnaieAchat();
		Monnaie monnaieVendre = newTransaction.getMonnaieVendre();
		Double montantVoulu = newTransaction.getMontantAchat();
		if (monnaieAchat.equals(monnaieVendre)){
			context.setRollbackOnly();
			throw new SameMoneyException();
		}
		List<EchangeOffre> echangeOffre=echangeOffreFacade.findAllWhere(monnaieVendre, monnaieAchat,newTransaction.getCompteAcheteur());
		if (echangeOffre==null){
			context.setRollbackOnly();
			throw new NotEnoughMoneyInBaseException();
		}
		Collections.sort(echangeOffre);
		
		double montantCourant=0; int i=0;
		while (i<echangeOffre.size()&&montantCourant<montantVoulu){
			montantCourant+=echangeOffre.get(i).getMontantVendre();
			if (montantCourant>montantVoulu){
				double ancienMontantAchat = echangeOffre.get(i).getMontantAchat();
				double ancienMontantVendre = echangeOffre.get(i).getMontantVendre();
				double montantVendre = ancienMontantVendre-(montantCourant-montantVoulu);
				double montantAchat = montantVendre*ancienMontantAchat/ancienMontantVendre;
				echangeOffre.get(i).setMontantVendre(montantCourant-montantVoulu);
				echangeOffre.get(i).setMontantAchat(echangeOffre.get(i).getMontantVendre()*ancienMontantAchat/ancienMontantVendre);
				
				Transaction t = mettreAJourTransaction(montantAchat,montantVendre, echangeOffre.get(i).getCompte(),echangeOffre.get(i).getDateCreation(), newTransaction);
				
				mettreAJourCompte(t.getCompteVendeur(),t.getCompteAcheteur(),monnaieVendre,monnaieAchat,Util.round(montantAchat),Util.round(montantVendre));
				
				echangeOffreFacade.edit(echangeOffre.get(i));
				transactionFacade.create(t);
				trans.add(t);
				montantCourant=montantVoulu;
			}
			else {
				Transaction t = mettreAJourTransaction(echangeOffre.get(i).getMontantAchat(),echangeOffre.get(i).getMontantVendre(),echangeOffre.get(i).getCompte(),echangeOffre.get(i).getDateCreation(),newTransaction);
				mettreAJourCompte(t.getCompteVendeur(),t.getCompteAcheteur(),monnaieVendre,monnaieAchat,echangeOffre.get(i).getMontantAchat(),echangeOffre.get(i).getMontantVendre());
				transactionFacade.create(t);
				trans.add(t);
				echangeOffreFacade.remove(echangeOffre.get(i));
			}
			i++;
		}
		if (montantCourant!=montantVoulu){
			context.setRollbackOnly();
			throw new NotEnoughMoneyInBaseException();			
		}
		return trans;
		
	}
	
	private void mettreAJourCompte(Compte compteVendeur, Compte compteAcheteur, Monnaie monnaieAchat, Monnaie monnaieVendre, Double montantAchat, Double montantVendre) throws NotEnoughMoneyException {
		porteMonnaieService.addToPorteMonnaie(compteAcheteur,monnaieVendre,montantVendre);
		porteMonnaieService.removeFromPorteMonnaie(compteAcheteur,monnaieAchat,montantAchat);
		porteMonnaieService.addToPorteMonnaie(compteVendeur,monnaieAchat,montantAchat);
		
	}
	
	private Transaction mettreAJourTransaction(Double montantAchat, Double montantVendre, Compte compteVendeur, Date date, Transaction newTransaction){
		Transaction t = newTransaction.clone();
		t.setDateCreation(date);
		t.setMontantAchat(montantAchat);
		t.setMontantVendre(montantVendre);
		t.setCompteVendeur(compteVendeur);
		return t;
	}

	public void validerPanniers(ArrayList<Transaction> articles, Compte compte) throws NotEnoughMoneyException, NotEnoughMoneyInBaseException, SameMoneyException {
		for (Transaction t : articles) {
			t.setCompteAcheteur(compte);
			t.setDateValidation(new Date());
			trouverOffres(t);
		}
	}

	public double calculerMontantVendre(Transaction newTransaction, Compte compte) {
		Monnaie monnaieAchat = newTransaction.getMonnaieAchat();
		Monnaie monnaieVendre = newTransaction.getMonnaieVendre();
		Double montantVoulu = newTransaction.getMontantAchat();
		double montantObtenu = 0;
		
		List<EchangeOffre> echangeOffre;
		if (compte != null) {
			echangeOffre = echangeOffreFacade.findAllWhere(monnaieVendre, monnaieAchat, compte);			
		} else {
			echangeOffre = echangeOffreFacade.findAllWhere(monnaieVendre, monnaieAchat);			
		}
		if (echangeOffre == null) {
			return 0.0;
		}
		Collections.sort(echangeOffre);

		double montantCourant = 0;
		int i = 0;
		while (i < echangeOffre.size() && montantCourant < montantVoulu) {
			montantCourant += echangeOffre.get(i).getMontantVendre();
			if (montantCourant > montantVoulu) {
				double ancienMontantAchat = echangeOffre.get(i).getMontantAchat();
				double ancienMontantVendre = echangeOffre.get(i).getMontantVendre();
				echangeOffre.get(i).setMontantVendre(montantCourant - montantVoulu);
				echangeOffre.get(i).setMontantAchat(echangeOffre.get(i).getMontantVendre() * ancienMontantAchat / ancienMontantVendre);
				montantObtenu += ancienMontantAchat - echangeOffre.get(i).getMontantAchat();
				montantCourant = montantVoulu;
			} else {
				montantObtenu += echangeOffre.get(i).getMontantAchat();
			}
			i++;
		}
		context.setRollbackOnly();
		if (montantCourant != montantVoulu) {
			// Pas assez d'argent dans la base
			return 0.0;
		}
		return montantObtenu;
	}

}
