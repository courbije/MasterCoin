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
import fr.ufrima.m2pgi.ecom.facade.CompteFacade;
import fr.ufrima.m2pgi.ecom.facade.MonnaieFacade;
import fr.ufrima.m2pgi.ecom.facade.PorteMonnaieFacade;
import fr.ufrima.m2pgi.ecom.facade.PorteMonnaieHistoriqueFacade;
import fr.ufrima.m2pgi.ecom.model.Compte;
import fr.ufrima.m2pgi.ecom.model.Monnaie;
import fr.ufrima.m2pgi.ecom.model.PorteMonnaie;
import fr.ufrima.m2pgi.ecom.model.PorteMonnaieHistorique;
import fr.ufrima.m2pgi.ecom.service.PorteMonnaieService;
import fr.ufrima.m2pgi.ecom.util.Resources;

@RunWith(Arquillian.class)
public class PorteMonnaieServiceTest {
	@Deployment
	public static Archive<?> createTestArchive() {
		return ShrinkWrap.create(WebArchive.class, "test.war").addClasses(NotEnoughMoneyException.class, PorteMonnaieFacade.class, 
				PorteMonnaieService.class, MonnaieFacade.class, Monnaie.class, 
				Compte.class, CompteFacade.class, PorteMonnaie.class, PorteMonnaieHistorique.class,
				PorteMonnaieHistoriqueFacade.class, PorteMonnaieFacade.class, Resources.class).addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml").addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
		// Deploy our test datasource
		.addAsWebInfResource("test-ds.xml");
	}

	@Inject
	PorteMonnaieService porteMonnaieService;

	@Inject
	PorteMonnaieFacade porteMonnaieFacade;

	@Inject
	PorteMonnaieHistoriqueFacade porteMonnaieHistoriqueFacade;

	@Inject
	MonnaieFacade monnaieFacade;

	@Inject
	CompteFacade compteFacade;

	@Inject
	Logger log;

	Compte newCompte;
	Monnaie monnaie1;

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
	}

	@After
	public void end() {
		compteFacade.remove(newCompte);
		monnaieFacade.remove(monnaie1);
	}

	@Test
	public void testAjout() throws Exception {
		Double d = 100.0;
		porteMonnaieService.addToPorteMonnaie(newCompte, monnaie1, d);
		PorteMonnaie p = porteMonnaieFacade.find(newCompte, monnaie1);
		assertEquals(d, p.getMontant());
		assertEquals(0, porteMonnaieHistoriqueFacade.findAll().size());
		porteMonnaieFacade.remove(p);
	}

	@Test
	public void testAjoutHisto() throws Exception {
		Double d = 100.0;
		porteMonnaieService.addToPorteMonnaieWithHisto(newCompte, monnaie1, d);
		PorteMonnaie p = porteMonnaieFacade.find(newCompte, monnaie1);
		assertEquals(d, p.getMontant());
		List<PorteMonnaieHistorique> res = porteMonnaieHistoriqueFacade.findAll();
		assertEquals(1, res.size());
		assertEquals(monnaie1, res.get(0).getMonnaie());
		assertEquals(d, res.get(0).getMontant());
		assertEquals(newCompte, res.get(0).getCompte());
		porteMonnaieHistoriqueFacade.remove(res.get(0));
		porteMonnaieFacade.remove(p);
	}

	@Test
	public void testRetrait() throws Exception {
		Double d = 100.0;
		Double d2 = 50.0;

		porteMonnaieService.addToPorteMonnaie(newCompte, monnaie1, d);
		porteMonnaieService.removeFromPorteMonnaie(newCompte, monnaie1, d2);
		
		PorteMonnaie p = porteMonnaieFacade.find(newCompte, monnaie1);
		Double dr = d - d2;
		assertEquals(dr, p.getMontant());
		assertEquals(0, porteMonnaieHistoriqueFacade.findAll().size());
		porteMonnaieFacade.remove(p);
	}
	
	@Test
	public void testRetraitFail() throws Exception {
		Double d = 100.0;
		Double d2 = 150.0;

		porteMonnaieService.addToPorteMonnaie(newCompte, monnaie1, d);
		try {
			porteMonnaieService.removeFromPorteMonnaie(newCompte, monnaie1, d2);
        	fail();
		} catch (NotEnoughMoneyException e) {
			
		}
		PorteMonnaie p = porteMonnaieFacade.find(newCompte, monnaie1);
		assertEquals(d, p.getMontant());
		assertEquals(0, porteMonnaieHistoriqueFacade.findAll().size());
		porteMonnaieFacade.remove(p);
		
	}
	

	@Test
	public void testRetraitHisto() throws Exception {
		Double d = 100.0;
		Double d2 = 50.0;

		porteMonnaieService.addToPorteMonnaie(newCompte, monnaie1, d);
		porteMonnaieService.removeFromPorteMonnaieWithHisto(newCompte, monnaie1, d2);
		
		PorteMonnaie p = porteMonnaieFacade.find(newCompte, monnaie1);
		Double dr = d - d2;
		assertEquals(dr, p.getMontant());
		List<PorteMonnaieHistorique> res = porteMonnaieHistoriqueFacade.findAll();
		assertEquals(1, res.size());
		assertEquals(monnaie1, res.get(0).getMonnaie());
		dr = -d2;
		assertEquals(dr, res.get(0).getMontant());
		assertEquals(newCompte, res.get(0).getCompte());
		porteMonnaieHistoriqueFacade.remove(res.get(0));
		porteMonnaieFacade.remove(p);
	}

}
