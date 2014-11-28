package fr.ufrima.m2pgi.ecom.service;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import fr.ufrima.m2pgi.ecom.facade.CompteFacade;
import fr.ufrima.m2pgi.ecom.facade.EchangeOffreFacade;
import fr.ufrima.m2pgi.ecom.facade.TransactionFacade;
import fr.ufrima.m2pgi.ecom.model.Compte;
import fr.ufrima.m2pgi.ecom.model.EchangeOffre;
import fr.ufrima.m2pgi.ecom.model.Monnaie;
import fr.ufrima.m2pgi.ecom.model.Transaction;

public class EchangeTxService {

	@Inject
	private EchangeOffreFacade echangeOffreFacade;
	
	@Inject
	private TransactionFacade transactionFacade;
	
	private PorteMonnaieService porteMonnaieService;
	
	
	public void trouverOffres(Transaction newTransaction) throws NotEnoughtMoneyException {

		Monnaie monnaieAchat = newTransaction.getMonnaieAchat();
		Monnaie monnaieVendre = newTransaction.getMonnaieVendre();
		Double montantVoulu = newTransaction.getMontantAchat();
		
		List<EchangeOffre> echangeOffre=echangeOffreFacade.findAllWhere(monnaieAchat,monnaieVendre,newTransaction.getCompteAcheteur());
		
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
				
				Transaction t = mettreAJourTransaction(montantAchat,montantVendre, echangeOffre.get(i).getCompte(), newTransaction);
				
				mettreAJourCompte(t.getCompteAcheteur(),t.getCompteVendeur(),monnaieAchat,monnaieVendre,montantAchat,montantVendre);
				
				echangeOffreFacade.edit(echangeOffre.get(i));
				transactionFacade.create(t);
				montantCourant=montantVoulu;
			}
			else {
				Transaction t = mettreAJourTransaction(echangeOffre.get(i).getMontantAchat(),echangeOffre.get(i).getMontantVendre(),echangeOffre.get(i).getCompte(),newTransaction);
				mettreAJourCompte(t.getCompteAcheteur(),t.getCompteVendeur(),monnaieAchat,monnaieVendre,echangeOffre.get(i).getMontantAchat(),echangeOffre.get(i).getMontantVendre());
				transactionFacade.create(t);
				echangeOffreFacade.remove(echangeOffre.get(i));
			}
			i++;
		}
		if (montantCourant!=montantVoulu){
			// Pas assez d'argent dans la base
						
		}
		
	}
	
	private void mettreAJourCompte(Compte compteVendeur, Compte compteAcheteur, Monnaie monnaieAchat, Monnaie monnaieVendre, Double montantAchat, Double montantVendre) throws NotEnoughtMoneyException {
		porteMonnaieService.addToPorteMonnaie(compteVendeur,monnaieVendre,montantAchat);
		porteMonnaieService.removeFromPorteMonnaie(compteVendeur,monnaieAchat,montantVendre);
		porteMonnaieService.addToPorteMonnaie(compteAcheteur,monnaieAchat,montantAchat);
		
	}
	
	private Transaction mettreAJourTransaction(Double montantAchat, Double montantVendre, Compte compteVendeur, Transaction newTransaction){
		Transaction t = newTransaction;
		t.setMontantAchat(montantAchat);
		t.setMontantVendre(montantVendre);
		t.setCompteVendeur(compteVendeur);
		return t;
	}

}
