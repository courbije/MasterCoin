package fr.ufrima.m2pgi.ecom.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.faces.bean.ManagedProperty;
import javax.inject.Inject;

import fr.ufrima.m2pgi.ecom.controller.MetriqueController;
import fr.ufrima.m2pgi.ecom.facade.MetriqueFacade;
import fr.ufrima.m2pgi.ecom.facade.TransactionFacade;
import fr.ufrima.m2pgi.ecom.model.Metrique;
import fr.ufrima.m2pgi.ecom.model.Monnaie;
import fr.ufrima.m2pgi.ecom.model.Transaction;

@Stateless
public class MetriqueFillerService {
		
		@Inject
		private MetriqueTraitementService metriqueTraitementService;
		
		@Schedule(dayOfMonth="*/1")		
		public void update(){
			 metriqueTraitementService.traitement(null);	
		}
		
}



	

