package fr.ufrima.m2pgi.ecom.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import fr.ufrima.m2pgi.ecom.facade.MetriqueFacade;
import fr.ufrima.m2pgi.ecom.facade.TransactionFacade;
import fr.ufrima.m2pgi.ecom.model.Metrique;
import fr.ufrima.m2pgi.ecom.model.Monnaie;
import fr.ufrima.m2pgi.ecom.model.Transaction;


@Stateless
public class MetriqueTraitementService {
	
	@Inject
	private MetriqueFacade metriqueFacade;
	
	@Inject
	private TransactionFacade transactionFacade;
	


	public void traitement(Date maDate) {
		Date d;
		if (maDate==null){
			d = new Date();
		}
		else {
			d=maDate;
		}
		Calendar cal = Calendar.getInstance();
	    cal.setTime(d);
		Metrique base = new Metrique();
	    Date dateCourante = cal.getTime();
	    cal.add(Calendar.DATE, -1);
	    base.setDate(cal.getTime());
		List<Transaction> tr = transactionFacade.findAllWhereDateIsBetween(cal.getTime(),dateCourante);
		int i = 0;
		while (i < tr.size()){
			Metrique m= base.clone();
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
	}
}
}