package fr.ufrima.m2pgi.ecom.service;

import java.util.List;

import javax.inject.Inject;

import fr.ufrima.m2pgi.ecom.facade.EchangeOffreFacade;
import fr.ufrima.m2pgi.ecom.model.EchangeOffre;
import fr.ufrima.m2pgi.ecom.model.Monnaie;
import fr.ufrima.m2pgi.ecom.model.Transaction;

public class EchangeTxService {

	@Inject
	private EchangeOffreFacade echangeOffreFacade;
	
	
	public void trouverOffres(Transaction newTransaction) {

		Monnaie monnaieAchat = newTransaction.getMonnaieAchat();
		Monnaie monnaieVendre = newTransaction.getMonnaieVendre();
		int montantAchat = newTransaction.getMontantAchat();
		List<EchangeOffre> echangeOffre=echangeOffreFacade.findAllWhere(monnaieAchat,monnaieVendre);
		
		
	}

}
