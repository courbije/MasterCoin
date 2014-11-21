package fr.ufrima.m2pgi.ecom.service;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

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
	
	public void trouverOffres(Transaction newTransaction) {

		Monnaie monnaieAchat = newTransaction.getMonnaieAchat();
		Monnaie monnaieVendre = newTransaction.getMonnaieVendre();
		Double montantVoulu = newTransaction.getMontantAchat();
		
		List<EchangeOffre> echangeOffre=echangeOffreFacade.findAllWhere(monnaieAchat,monnaieVendre);
		
		Collections.sort(echangeOffre);
		
		double montantCourant=0; int i=0;
		while (i<echangeOffre.size()&&montantCourant<montantVoulu){
			montantCourant=+echangeOffre.get(i).getMontantAchat();
			if (montantCourant>montantVoulu){
				double ancienMontantAchat = echangeOffre.get(i).getMontantAchat();
				double ancienMontantVendre = echangeOffre.get(i).getMontantVendre();
				
				echangeOffre.get(i).setMontantAchat(montantCourant-montantVoulu);
				echangeOffre.get(i).setMontantVendre(echangeOffre.get(i).getMontantAchat()*ancienMontantVendre/ancienMontantAchat);
				
				Transaction t = mettreAJourTransaction(ancienMontantAchat-(montantCourant-montantVoulu),ancienMontantVendre-echangeOffre.get(i).getMontantAchat()*ancienMontantVendre/ancienMontantAchat, echangeOffre.get(i).getCompte(), newTransaction);
				
				mettreAJourCompte(t.getCompteAcheteur(),t.getCompteVendeur(),monnaieAchat,monnaieVendre,ancienMontantAchat-echangeOffre.get(i).getMontantAchat(),ancienMontantVendre-echangeOffre.get(i).getMontantVendre());
				
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
	
	
	private void mettreAJourCompte(Compte compteVendeur, Compte compteAcheteur, Monnaie monnaieAchat, Monnaie monnaieVendre, Double double1, Double double2) {
		
		
	}
	
	private Transaction mettreAJourTransaction(Double montantAchat, Double montantVendre, Compte compteVendeur, Transaction newTransaction){
		Transaction t = newTransaction;
		t.setMontantAchat(montantAchat);
		t.setMontantVendre(montantVendre);
		t.setCompteVendeur(compteVendeur);
		return t;
	}

}
