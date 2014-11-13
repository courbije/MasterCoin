package fr.ufrima.m2pgi.ecom.service;

import java.util.List;

import fr.ufrima.m2pgi.ecom.model.EchangeOffre;
import fr.ufrima.m2pgi.ecom.model.Monnaie;
import fr.ufrima.m2pgi.ecom.model.Transaction;

public class EchangeTxFacade {

	public void trouverOffres(Transaction newTransaction) {

		Monnaie monnaieAchat = newTransaction.getMonnaieAchat();
		Monnaie monnaieVendre = newTransaction.getMonnaieVendre();
		int montantAchat = newTransaction.getMontantAchat();
		
		EchangeOffreFacade eof = new EchangeOffreFacade();
		List<EchangeOffre> echangeOffre=eof.findAllWhere(monnaieAchat,monnaieVendre);
		
		
	}

}
