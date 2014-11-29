package fr.ufrima.m2pgi.ecom.service;

import java.util.Date;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.inject.Inject;

import fr.ufrima.m2pgi.ecom.facade.PorteMonnaieFacade;
import fr.ufrima.m2pgi.ecom.facade.PorteMonnaieHistoriqueFacade;
import fr.ufrima.m2pgi.ecom.model.Compte;
import fr.ufrima.m2pgi.ecom.model.Monnaie;
import fr.ufrima.m2pgi.ecom.model.PorteMonnaie;
import fr.ufrima.m2pgi.ecom.model.PorteMonnaieHistorique;

@Stateless
public class PorteMonnaieService {
	
	@Resource
	private EJBContext context;
	
	@Inject
	private PorteMonnaieFacade porteMonnaieFacade;

	@Inject
	private PorteMonnaieHistoriqueFacade porteMonnaieHistoriqueFacade;

	
	public void addToPorteMonnaie(Compte compte, Monnaie monnaie, Double amount) {
		PorteMonnaie res = porteMonnaieFacade.find(compte, monnaie);
		if (res == null) {
			PorteMonnaie newP = new PorteMonnaie();
			newP.setCompte(compte);
			newP.setMonnaie(monnaie);
			newP.setMontant(amount);
			porteMonnaieFacade.create(newP);
		} else {
			res.setMontant(res.getMontant() + amount);
			porteMonnaieFacade.edit(res);
		}
		createNewHistorique(compte, monnaie, amount);
	}

	private void createNewHistorique(Compte compte, Monnaie monnaie, Double amount) {
		PorteMonnaieHistorique porteMonnaieHistorique = new PorteMonnaieHistorique();
		porteMonnaieHistorique.setCompte(compte);
		porteMonnaieHistorique.setMonnaie(monnaie);
		porteMonnaieHistorique.setMontant(amount);
		porteMonnaieHistorique.setDate(new Date());
		porteMonnaieHistoriqueFacade.create(porteMonnaieHistorique);
	}

	public void removeFromPorteMonnaie(Compte compte, Monnaie monnaie, Double amount) throws NotEnoughMoneyException {
		PorteMonnaie res = porteMonnaieFacade.find(compte, monnaie);
		if (res == null) {
			context.setRollbackOnly();
			throw new NotEnoughMoneyException();
		}
		Double i = res.getMontant() - amount;
		if (i < 0) {
			context.setRollbackOnly();
			throw new NotEnoughMoneyException();
		}
		res.setMontant(i);
		porteMonnaieFacade.edit(res);
		createNewHistorique(compte, monnaie, -amount);
	}
}