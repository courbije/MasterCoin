package fr.ufrima.m2pgi.ecom.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;

import fr.ufrima.m2pgi.ecom.facade.MetriqueFacade;
import fr.ufrima.m2pgi.ecom.facade.TransactionFacade;
import fr.ufrima.m2pgi.ecom.model.Metrique;
import fr.ufrima.m2pgi.ecom.model.Monnaie;
import fr.ufrima.m2pgi.ecom.model.Transaction;

@Stateless
@LocalBean
public class MetriqueFillerService {
		
		@Inject
		private MetriqueFacade metriqueFacade;
		
		@Inject
		private TransactionFacade transactionFacade;
		
		@Schedule(hour="*/1")		
		public void updateEveryHour(){
			Date d = new Date();
			Calendar cal = Calendar.getInstance();
		    cal.setTime(d);
			Metrique m = new Metrique();
		    Date dateCourante = cal.getTime();
		    cal.add(Calendar.HOUR_OF_DAY, -1);
		    m.setDate(cal.getTime());
			List<Transaction> tr = transactionFacade.findAllWhereDateIsBetween(cal.getTime(),dateCourante);
			int i = 0;
			while (i < tr.size()){
				Monnaie monnaieCourante1 = tr.get(i).getMonnaieAchat();
				Monnaie monnaieCourante2 = tr.get(i).getMonnaieVendre();
				double sommeCourante1 = 0;
				double sommeCourante2 = 0;
				while (i<tr.size() && tr.get(i).getMonnaieAchat().equals(monnaieCourante1)&&tr.get(i).getMonnaieVendre().equals(monnaieCourante2)){
					sommeCourante1+= tr.get(i).getMontantAchat();
					sommeCourante2+= tr.get(i).getMontantVendre();
					i++;
				}
				m.setMonnaie1(monnaieCourante1);
				m.setMonnaie2(monnaieCourante2);
				m.setMontantMonnaie1(sommeCourante1);
				m.setMontantMonnaie2(sommeCourante2);
				metriqueFacade.create(m);
				i++;
			}
		}

	
}
