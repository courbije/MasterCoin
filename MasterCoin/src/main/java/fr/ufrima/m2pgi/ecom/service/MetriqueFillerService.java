package fr.ufrima.m2pgi.ecom.service;

import javax.ejb.LocalBean;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
@LocalBean
public class MetriqueFillerService {
		
		@Inject
		private MetriqueTraitementService metriqueTraitementService;
		
		@Schedule(dayOfWeek="*")		
		public void update(){
			 metriqueTraitementService.traitement(null);	
		}
		
}



	

