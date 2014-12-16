package fr.ufrima.m2pgi.ecom.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.faces.bean.ManagedProperty;
import javax.inject.Inject;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import fr.ufrima.m2pgi.ecom.facade.CompteFacade;
import fr.ufrima.m2pgi.ecom.facade.EchangeOffreFacade;
import fr.ufrima.m2pgi.ecom.facade.MetriqueFacade;
import fr.ufrima.m2pgi.ecom.facade.MonnaieFacade;
import fr.ufrima.m2pgi.ecom.facade.TransactionFacade;
import fr.ufrima.m2pgi.ecom.model.Compte;
import fr.ufrima.m2pgi.ecom.model.EchangeOffre;
import fr.ufrima.m2pgi.ecom.model.Metrique;
import fr.ufrima.m2pgi.ecom.model.Monnaie;
import fr.ufrima.m2pgi.ecom.model.Transaction;
import fr.ufrima.m2pgi.ecom.service.EchangeOffreService;
import fr.ufrima.m2pgi.ecom.service.MetriqueFillerService;
import fr.ufrima.m2pgi.ecom.service.MetriqueTraitementService;
import fr.ufrima.m2pgi.ecom.service.PorteMonnaieService;

@Singleton
@Startup
@TransactionManagement(TransactionManagementType.BEAN)
public class AutoRun {

	private final int nbEchange = 400;
	private final int nbTransaction = 1000;
	private final int nbPm = 100;
	private final int arondi = 1000;
	
	@Resource
	private UserTransaction transaction;

	@Inject
	private CompteFacade compteFacade;

	@Inject
	private MonnaieFacade monnaieFacade;

	@Inject
	private PorteMonnaieService porteMonnaieService;

	@Inject
	private TransactionFacade transactionFacade;
	
	@Inject
	private MetriqueTraitementService metriqueTraitementService;

	private Random rand = new Random();

	private ArrayList<Monnaie> monnaies = new ArrayList<Monnaie>();
	private ArrayList<Compte> comptes = new ArrayList<Compte>();

	@Inject
	private EchangeOffreFacade echangeOffreFacade;

	@PostConstruct
	public void initialiser() throws Exception {
		if (compteFacade.find("dontedit", "dontedit") != null) {
			return;
		}
		generateMonnaies();
		generateComptes();
		generatePorteMonnaies();
		generateEchange();
		generateTransaction();
		generateMetrique();
	}

	private void generateMetrique() throws Exception {
		
		List<Date> dates = transactionFacade.findAlldistinct();
		for (Date d : dates){
			transaction.begin();
			Calendar cal = Calendar.getInstance();
		    cal.setTime(d);
		    cal.add(Calendar.DATE,1);
			metriqueTraitementService.traitement(cal.getTime());	
			transaction.commit();
		}
	}

	private void generateEchange() throws Exception {
		for (int i = 0; i < nbEchange; i++) {
			createEchange();
		}
	}
	
	private void generateTransaction() throws Exception {
		for (int i = 0; i < nbTransaction; i++) {
			createTransaction();
		}
	}

	private void createEchange() throws Exception {
		transaction.begin();
		EchangeOffre eo = new EchangeOffre();
		Compte compte = comptes.get(rng(0, comptes.size()));
		eo.setCompte(compte);
		eo.setDateCreation(new Date(114, rng(0, 11), rng(0, 30)));
		eo.setMonnaieAchat(monnaies.get(rng(0, monnaies.size())));
		eo.setMonnaieVendre(monnaies.get(rng(0, monnaies.size())));
		eo.setMontantAchat(Drng(1, 100));
		eo.setMontantVendre(Drng(1, 100));
		if (!eo.getMonnaieAchat().equals(eo.getMonnaieVendre())) {
			echangeOffreFacade.create(eo);
		}
		transaction.commit();
	}
	
	private void createTransaction() throws Exception {
		transaction.begin();
		Transaction t = new Transaction();
		t.setCompteVendeur(comptes.get(rng(0, comptes.size())));
		t.setCompteAcheteur(comptes.get(rng(0, comptes.size())));
		t.setDateCreation(new Date(114, rng(0, 11), rng(0, 30)));
		t.setDateValidation(new Date(114, rng(0, 11), rng(0, 30)));
		t.setMonnaieAchat(monnaies.get(rng(0, monnaies.size())));
		t.setMonnaieVendre(monnaies.get(rng(0, monnaies.size())));
		t.setMontantAchat(Drng(1, 100));
		t.setMontantVendre(Drng(1, 100));
		if (!t.getMonnaieAchat().equals(t.getMonnaieVendre()) && !t.getCompteAcheteur().equals(t.getCompteVendeur())) {
			transactionFacade.create(t);
		}
		transaction.commit();
	}

	private void generatePorteMonnaies() throws Exception {
		transaction.begin();
		for (int i = 0; i < nbPm; i++) {
			porteMonnaieService.addToPorteMonnaieWithHisto(comptes.get(rng(0, comptes.size())), monnaies.get(rng(0, monnaies.size())), Drng(1, 2000));
		}
		transaction.commit();
		for (int i = 0; i < nbPm; i++) {
			try {		
				transaction.begin();
				porteMonnaieService.removeFromPorteMonnaieWithHisto(comptes.get(rng(0, comptes.size())), monnaies.get(rng(0, monnaies.size())), Drng(1, 200));
				transaction.commit();
			} catch (Exception e) {
				transaction.rollback();
			}
		}
	}

	private void generateComptes() throws Exception {
		transaction.begin();
		Compte c = new Compte();
		c.setDateNaissance(new Date(rng(0, 114), rng(0, 12), rng(0, 20)));
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
		m = new Monnaie();
		m.setAcroyme("eUSD");
		m.setNom("e-Dollar");
		monnaies.add(m);
		monnaieFacade.create(m);
		m = new Monnaie();
		m.setAcroyme("eEUR");
		m.setNom("e-Euro");
		monnaies.add(m);
		monnaieFacade.create(m);
		transaction.commit();
	}

	private Compte createCompte(String login) {
		Compte c = new Compte();
		c.setDateNaissance(new Date(rng(0, 114), rng(0, 12), rng(0, 30)));
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
		// Truncation in case the decimals is too long
		return Math.floor((rand.nextDouble() * (max - min) + min) * arondi) / arondi;
	}

}
