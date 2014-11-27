package fr.ufrima.m2pgi.ecom.service;

import java.util.Date;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.inject.Inject;

import fr.ufrima.m2pgi.ecom.facade.EchangeOffreFacade;
import fr.ufrima.m2pgi.ecom.model.Compte;
import fr.ufrima.m2pgi.ecom.model.EchangeOffre;

@Stateless
public class EchangeOffreService {
		
		@Resource
		private EJBContext context;
		
		@Inject
		private EchangeOffreFacade echangeOffreFacade;

		@Inject
		private PorteMonnaieService porteMonnaieService;
		
		public void removeOffre(long id, Compte currentUser) {
			EchangeOffre eo = echangeOffreFacade.find(id);
			if (eo == null || !eo.getCompte().equals(currentUser)) {
				context.setRollbackOnly();
				throw new RuntimeException();
			}
			echangeOffreFacade.remove(eo);
			porteMonnaieService.addToPorteMonnaie(eo.getCompte(), eo.getMonnaieVendre(), eo.getMontantVendre());
		}

		public void addOffre(Compte compte, EchangeOffre eo) throws Exception {
			if(eo.getMonnaieAchat().equals(eo.getMonnaieVendre())) {
				context.setRollbackOnly();
				throw new SameMoneyException();
			}
			porteMonnaieService.removeFromPorteMonnaie(compte, eo.getMonnaieVendre(), eo.getMontantVendre());
			eo.setCompte(compte);
			eo.setDateCreation(new Date());
			echangeOffreFacade.create(eo);
		}

	
}
