/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fr.ufrima.m2pgi.ecom.serviceTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import fr.ufrima.m2pgi.ecom.exception.NotEnoughMoneyException;
import fr.ufrima.m2pgi.ecom.exception.SameMoneyException;
import fr.ufrima.m2pgi.ecom.facade.CompteFacade;
import fr.ufrima.m2pgi.ecom.facade.EchangeOffreFacade;
import fr.ufrima.m2pgi.ecom.facade.MonnaieFacade;
import fr.ufrima.m2pgi.ecom.facade.PorteMonnaieFacade;
import fr.ufrima.m2pgi.ecom.facade.PorteMonnaieHistoriqueFacade;
import fr.ufrima.m2pgi.ecom.model.Compte;
import fr.ufrima.m2pgi.ecom.model.EchangeOffre;
import fr.ufrima.m2pgi.ecom.model.Monnaie;
import fr.ufrima.m2pgi.ecom.model.PorteMonnaie;
import fr.ufrima.m2pgi.ecom.model.PorteMonnaieHistorique;
import fr.ufrima.m2pgi.ecom.service.EchangeOffreService;
import fr.ufrima.m2pgi.ecom.service.PorteMonnaieService;
import fr.ufrima.m2pgi.ecom.util.Resources;

@RunWith(Arquillian.class)
public class EchangeOffreServiceTest {
	@Deployment
	public static Archive<?> createTestArchive() {
		return ShrinkWrap.create(WebArchive.class, "test.war").addClasses(NotEnoughMoneyException.class,SameMoneyException.class,CompteFacade.class,EchangeOffreFacade.class,
				MonnaieFacade.class,PorteMonnaieFacade.class, PorteMonnaieHistoriqueFacade.class,
				Compte.class,EchangeOffre.class,Monnaie.class, PorteMonnaie.class,PorteMonnaieHistorique.class,
				EchangeOffreService.class,PorteMonnaieService.class,Resources.class).addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml").addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
		// Deploy our test datasource
		.addAsWebInfResource("test-ds.xml");
	}

	@Inject
	EchangeOffreService echangeOffreService;

	@Inject
	PorteMonnaieService porteMonnaieService;

	@Inject
	PorteMonnaieFacade porteMonnaieFacade;

	@Inject
	PorteMonnaieHistoriqueFacade porteMonnaieHistoriqueFacade;

	@Inject
	EchangeOffreFacade echangeOffreFacade;

	@Inject
	MonnaieFacade monnaieFacade;

	@Inject
	CompteFacade compteFacade;

	@Inject
	Logger log;

	Compte newCompte;
	Monnaie monnaie1;
	Monnaie monnaie2;

	Double d;
	
	@Before
	public void initialize() {
		newCompte = new Compte();
		newCompte.setLogin("azerty");
		newCompte.setPassword("azerty");
		newCompte.setMail("bidule@machin.com");
		newCompte.setNom("df");
		newCompte.setPrenom("df");
		newCompte.setDateNaissance(new Date());
		compteFacade.create(newCompte);

		monnaie1 = new Monnaie();
		monnaie1.setAcroyme("BitCoin");
		monnaie1.setNom("BitCoin");
		monnaieFacade.create(monnaie1);

		monnaie2 = new Monnaie();
		monnaie2.setAcroyme("DogeCoin");
		monnaie2.setNom("DogeCoin");
		monnaieFacade.create(monnaie2);

		d = 1000.0;
		porteMonnaieService.addToPorteMonnaie(newCompte, monnaie1, d);
		porteMonnaieService.addToPorteMonnaie(newCompte, monnaie2, d);

	}

	@After
	public void end() {
		PorteMonnaie p = porteMonnaieFacade.find(newCompte, monnaie1);
		porteMonnaieFacade.remove(p);
		p = porteMonnaieFacade.find(newCompte, monnaie2);
		porteMonnaieFacade.remove(p);

		compteFacade.remove(newCompte);
		monnaieFacade.remove(monnaie1);
		monnaieFacade.remove(monnaie2);
	}

	@Test
    public void testAddRemoveOffre() throws Exception {
    	Double d1 = 100.0;
    	Double d2 = 300.0;
		EchangeOffre eo = new EchangeOffre();
		eo.setCompte(newCompte);
		eo.setMonnaieAchat(monnaie1);
		eo.setMonnaieVendre(monnaie2);
		eo.setMontantAchat(d1);
		eo.setMontantVendre(d2);
		echangeOffreService.addOffre(newCompte, eo);
		assertEquals(0, porteMonnaieHistoriqueFacade.findAll().size());
		PorteMonnaie p = porteMonnaieFacade.find(newCompte, monnaie2);
		Double dr = d-d2;
		assertEquals(dr, p.getMontant());
		p = porteMonnaieFacade.find(newCompte, monnaie1);
		assertEquals(d, p.getMontant());
		
		
		List<EchangeOffre> echanges = echangeOffreFacade.findByCompte(newCompte);
		assertEquals(1, echanges.size());
		echangeOffreService.removeOffre(echanges.get(0).getId(), newCompte);
		assertEquals(0, porteMonnaieHistoriqueFacade.findAll().size());
		p = porteMonnaieFacade.find(newCompte, monnaie2);
		assertEquals(d, p.getMontant());
		p = porteMonnaieFacade.find(newCompte, monnaie1);
		assertEquals(d, p.getMontant());
    }

	  public void testAddFail() throws Exception {
	    	Double d1 = 100.0;
	    	Double d2 = 300.0;
			EchangeOffre eo = new EchangeOffre();
			eo.setCompte(newCompte);
			eo.setMonnaieAchat(monnaie1);
			eo.setMonnaieVendre(monnaie1);
			eo.setMontantAchat(d1);
			eo.setMontantVendre(d2);
			try {
				echangeOffreService.addOffre(newCompte, eo);
	        	fail();
			} catch (SameMoneyException e) {
			}
			
			assertEquals(0, porteMonnaieHistoriqueFacade.findAll().size());
			PorteMonnaie p = porteMonnaieFacade.find(newCompte, monnaie2);
			Double dr = d-d2;
			assertEquals(dr, p.getMontant());
			p = porteMonnaieFacade.find(newCompte, monnaie1);
			assertEquals(d, p.getMontant());
			
			
			List<EchangeOffre> echanges = echangeOffreFacade.findByCompte(newCompte);
			assertEquals(1, echanges.size());
			echangeOffreService.removeOffre(echanges.get(0).getId(), newCompte);
			assertEquals(0, porteMonnaieHistoriqueFacade.findAll().size());
			p = porteMonnaieFacade.find(newCompte, monnaie2);
			assertEquals(d, p.getMontant());
			p = porteMonnaieFacade.find(newCompte, monnaie1);
			assertEquals(d, p.getMontant());
	    }

	
}
