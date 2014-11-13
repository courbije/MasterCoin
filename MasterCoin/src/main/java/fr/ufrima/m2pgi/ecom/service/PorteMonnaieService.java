package fr.ufrima.m2pgi.ecom.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

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
	//   porteMonnaieFacade.find(id);
	   
   }
    
}