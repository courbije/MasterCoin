package fr.ufrima.m2pgi.ecom.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import fr.ufrima.m2pgi.ecom.facade.PorteMonnaieFacade;
import fr.ufrima.m2pgi.ecom.model.Compte;
import fr.ufrima.m2pgi.ecom.model.Monnaie;
import fr.ufrima.m2pgi.ecom.model.PorteMonnaie;


@Stateless
public class PorteMonnaieService
{
    @Inject
    private PorteMonnaieFacade porteMonnaieFacade;
    
   public void addToPorteMonnaie(Compte compte, Monnaie monnaie, Integer amount) {
	   PorteMonnaie res = porteMonnaieFacade.find(compte,monnaie);
	   if (res == null) {
		   PorteMonnaie newP = new PorteMonnaie();
		   newP.setCompte(compte);
		   newP.setMonnaie(monnaie);
		   newP.setMontant(amount);
		   porteMonnaieFacade.create(newP);
	   } else {
		   res.setMontant(res.getMontant()+amount);
		   porteMonnaieFacade.edit(res);
	   }
	   
   }
    
}