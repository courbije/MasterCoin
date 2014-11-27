package fr.ufrima.m2pgi.ecom.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.transaction.UserTransaction;

import fr.ufrima.m2pgi.ecom.facade.CompteFacade;
import fr.ufrima.m2pgi.ecom.facade.MonnaieFacade;
import fr.ufrima.m2pgi.ecom.model.Compte;
import fr.ufrima.m2pgi.ecom.model.EchangeOffre;
import fr.ufrima.m2pgi.ecom.model.Monnaie;
import fr.ufrima.m2pgi.ecom.service.EchangeOffreService;
import fr.ufrima.m2pgi.ecom.service.PorteMonnaieService;

@Singleton
@Startup
@TransactionManagement(TransactionManagementType.BEAN)
public class AutoRun {
	
	@Resource
    private UserTransaction transaction ;

	@Inject
	private CompteFacade compteFacade;

	@Inject
	private MonnaieFacade monnaieFacade;

	@Inject
	private PorteMonnaieService porteMonnaieService;

	@Inject
	private EchangeOffreService echangeOffreService;

	private Random rand = new Random();

	private ArrayList<Monnaie> monnaies = new ArrayList<Monnaie>();
	private ArrayList<Compte> comptes = new ArrayList<Compte>();

	@PostConstruct
	public void initialiser() throws Exception {
    	if (compteFacade.find("dontedit", "dontedit") != null) {
    		return;
    	}
    		generateMonnaies();
    		generateComptes();
    		generatePorteMonnaies();
    		generateEchange();	
	}

	private void generateEchange()  throws Exception {
			for (int i = 0; i < 200; i++) {
				try {
				createEchange();
				} catch (Exception e) {
					
				}
			}			
	}

	private void createEchange()  throws Exception {
		transaction.begin();
		EchangeOffre eo = new EchangeOffre();
		Compte compte = comptes.get(rng(0, comptes.size()));
		eo.setCompte(compte);
		eo.setDateCreation(new Date(rng(2000, 2014), rng(0, 12), rng(0, 30)));
		eo.setMonnaieAchat(monnaies.get(rng(0, monnaies.size())));
		eo.setMonnaieVendre(monnaies.get(rng(0, monnaies.size())));
		eo.setMontantAchat(new Double(rng(1, 100)));
		eo.setMontantVendre(new Double(rng(1, 100)));
		try {
			echangeOffreService.addOffre(compte, eo);
		} catch (Exception e) {
			transaction.rollback();
		}
		transaction.commit();
	}

	private void generatePorteMonnaies() throws Exception {
		transaction.begin();
		for (int i = 0; i < 30; i++) {
			porteMonnaieService.addToPorteMonnaie(comptes.get(rng(0, comptes.size())), monnaies.get(rng(0, monnaies.size())), Drng(1, 2000));
		}
		transaction.commit();
	}

	private void generateComptes() throws Exception {
		transaction.begin();
		Compte c = new Compte();
		c.setDateNaissance(new Date(rng(1900, 2000), rng(0, 12), rng(0, 20)));
		c.setLogin("dontedit");
		c.setPassword("dontedit");
		c.setMail("dontedit@fake.fake");
		c.setNom("dontedit");
		c.setPrenom("dontedit");
		compteFacade.create(c);
		c = createCompte("myfake1");
		comptes.add(c);
		c = createCompte("myfake2");
		comptes.add(c);
		c = createCompte("myfake3");
		comptes.add(c);
		c = createCompte("myfake4");
		comptes.add(c);
		c = createCompte("myfake5");
		comptes.add(c);
		transaction.commit();
	}

	private void generateMonnaies() throws Exception {
		transaction.begin();
		Monnaie m = new Monnaie();
		m.setAcroyme("BTC");
		m.setNom("BitCoin");
		monnaies.add(m);
		monnaieFacade.create(m);
		m = new Monnaie();
		m.setAcroyme("XRP");
		m.setNom("Ripple");
		monnaies.add(m);
		monnaieFacade.create(m);
		m = new Monnaie();
		m.setAcroyme("LTC");
		m.setNom("Litecoin");
		monnaies.add(m);
		monnaieFacade.create(m);
		m = new Monnaie();
		m.setAcroyme("DOGE");
		m.setNom("Dogecoin");
		monnaies.add(m);
		monnaieFacade.create(m);
		m = new Monnaie();
		m.setAcroyme("NXT");
		m.setNom("Nxt");
		monnaies.add(m);
		monnaieFacade.create(m);
		m = new Monnaie();
		m.setAcroyme("BTSX");
		m.setNom("BitShares");
		monnaies.add(m);
		monnaieFacade.create(m);
		m = new Monnaie();
		m.setAcroyme("PPC");
		m.setNom("Peercoin");
		monnaies.add(m);
		monnaieFacade.create(m);
		transaction.commit();
	}

	private Compte createCompte(String login) {
		Compte c = new Compte();
		c.setDateNaissance(new Date(rng(1900, 2000), rng(0, 12), rng(0, 30)));
		c.setLogin(login);
		c.setPassword("azerty");
		c.setMail(login + "@fake.fake");
		c.setNom("fake");
		c.setPrenom("fake");
		compteFacade.create(c);
		return c;
	}

	private int rng(int min, int max) {
		return rand.nextInt(max - min) + min;
	}
	
	private double Drng(int min, int max) {
		return rand.nextDouble()*(max-min)+min;
	}

}
