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

import static org.junit.Assert.*;

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
import fr.ufrima.m2pgi.ecom.exception.NotEnoughMoneyInBaseException;
import fr.ufrima.m2pgi.ecom.exception.SameMoneyException;
import fr.ufrima.m2pgi.ecom.facade.CompteFacade;
import fr.ufrima.m2pgi.ecom.facade.EchangeOffreFacade;
import fr.ufrima.m2pgi.ecom.facade.MonnaieFacade;
import fr.ufrima.m2pgi.ecom.facade.PorteMonnaieFacade;
import fr.ufrima.m2pgi.ecom.facade.PorteMonnaieHistoriqueFacade;
import fr.ufrima.m2pgi.ecom.facade.TransactionFacade;
import fr.ufrima.m2pgi.ecom.model.Compte;
import fr.ufrima.m2pgi.ecom.model.EchangeOffre;
import fr.ufrima.m2pgi.ecom.model.Monnaie;
import fr.ufrima.m2pgi.ecom.model.PorteMonnaie;
import fr.ufrima.m2pgi.ecom.model.PorteMonnaieHistorique;
import fr.ufrima.m2pgi.ecom.model.Transaction;
import fr.ufrima.m2pgi.ecom.service.EchangeTxService;
import fr.ufrima.m2pgi.ecom.service.PorteMonnaieService;
import fr.ufrima.m2pgi.ecom.util.Resources;
import fr.ufrima.m2pgi.ecom.util.Util;

@RunWith(Arquillian.class)
public class EchangeTxServiceTest {
    @Deployment
    public static Archive<?> createTestArchive() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addClasses(MonnaieFacade.class, Monnaie.class, Compte.class, CompteFacade.class,EchangeOffre.class, 
                		EchangeOffreFacade.class, EchangeTxService.class,  Transaction.class, TransactionFacade.class,
                		PorteMonnaie.class, PorteMonnaieFacade.class, NotEnoughMoneyException.class, NotEnoughMoneyInBaseException.class, SameMoneyException.class, PorteMonnaieHistorique.class,
                		PorteMonnaieService.class, Resources.class, PorteMonnaieHistoriqueFacade.class, PorteMonnaieHistoriqueFacade.class, PorteMonnaieHistorique.class, Util.class)
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                // Deploy our test datasource
                .addAsWebInfResource("test-ds.xml");
    }

    
    
    @Inject
    EchangeOffreFacade echangeOffreFacade;

    @Inject
    MonnaieFacade monnaieFacade;
    
    @Inject
    CompteFacade compteFacade;
    
    @Inject
    EchangeTxService echangeTxService;
    
    @Inject
    TransactionFacade transactionFacade;
    
    @Inject
    PorteMonnaieFacade porteMonnaieFacade;
    
    @Inject
    PorteMonnaieService porteMonnaieService;
    
    @Inject
    PorteMonnaieHistoriqueFacade porteMonnaieHistoriqueFacade;
    
    @Inject
    Logger log;
    
    
    Compte compte1;
    Compte compte2;
    Monnaie monnaie1;
    Monnaie monnaie2;
    EchangeOffre offre1;
    EchangeOffre offre2;
    List<Transaction> transactions;
    
    
    @Before 
    public void initialize() {
    	 compte1 = new Compte();
         compte1.setLogin("azerty");
         compte1.setPassword("azerty");
         compte1.setMail("bidule@machin.com");
         compte1.setNom("df");
         compte1.setPrenom("df");
         compte1.setDateNaissance(new Date());
         compteFacade.create(compte1);
         
    	 compte2 = new Compte();
         compte2.setLogin("azertyio");
         compte2.setPassword("azerty");
         compte2.setMail("bidule@machin.coom");
         compte2.setNom("df");
         compte2.setPrenom("df");
         compte2.setDateNaissance(new Date());
         compteFacade.create(compte2);
         
         monnaie1 = new Monnaie();    
         monnaie1.setAcroyme("BitCoin");
         monnaie1.setNom("BitCoin");
         monnaieFacade.create(monnaie1);
         
         monnaie2 = new Monnaie();
         monnaie2.setAcroyme("DogeCoin");
         monnaie2.setNom("DogeCoin");
         monnaieFacade.create(monnaie2);
         
         offre1 = new EchangeOffre();
         offre1.setDateCreation(new Date());
         offre1.setCompte(compte1);
         offre1.setMonnaieAchat(monnaie1);
         offre1.setMonnaieVendre(monnaie2);
         offre1.setMontantAchat(100.0);
         offre1.setMontantVendre(300.0);
         echangeOffreFacade.create(offre1);
         
         offre2 = new EchangeOffre();
         offre2.setDateCreation(new Date());
         offre2.setCompte(compte1);
         offre2.setMonnaieAchat(monnaie1);
         offre2.setMonnaieVendre(monnaie2);
         offre2.setMontantAchat(50.0);
         offre2.setMontantVendre(3000.0);
         
         echangeOffreFacade.create(offre2);
    }

    @After
    public void end() {
    	for (EchangeOffre e: echangeOffreFacade.findAll()){
    		echangeOffreFacade.remove(e);
    	}
    	for (Transaction tr : transactions){
    		transactionFacade.remove(tr);
    	}
    	PorteMonnaie res = porteMonnaieFacade.find(compte1,monnaie1);
    	porteMonnaieFacade.remove(res);
    	res = porteMonnaieFacade.find(compte2,monnaie1);
     	porteMonnaieFacade.remove(res);
     	res = porteMonnaieFacade.find(compte2,monnaie2);
     	porteMonnaieFacade.remove(res);
     	
     	List<PorteMonnaieHistorique> poH = porteMonnaieHistoriqueFacade.findByCompte(compte1);
     	for (PorteMonnaieHistorique p : poH){
     		porteMonnaieHistoriqueFacade.remove(p);
     	}
     	
     	poH = porteMonnaieHistoriqueFacade.findByCompte(compte2);
     	for (PorteMonnaieHistorique p : poH){
     		porteMonnaieHistoriqueFacade.remove(p);
     	}
     	
        monnaieFacade.remove(monnaie1);
        monnaieFacade.remove(monnaie2);
        compteFacade.remove(compte1);
        compteFacade.remove(compte2);

    	
    }

    
    
    @Test
    public void testValider() throws Exception {
    	int debut = transactionFacade.findAll().size();
    	porteMonnaieService.addToPorteMonnaie(compte1,monnaie1,3000.);
    	porteMonnaieService.addToPorteMonnaie(compte2,monnaie1,3000.);
    	porteMonnaieService.addToPorteMonnaie(compte2,monnaie2,3000.);
    	Transaction newTransaction = new Transaction();
    	newTransaction.setCompteAcheteur(compte2);
		newTransaction.setDateValidation(new Date());
		newTransaction.setMonnaieAchat(monnaie2);
		newTransaction.setMonnaieVendre(monnaie1);
		newTransaction.setMontantAchat(3150.0);
		
		Double pcA1=0., pcA2=0., pcV2=0.;
		if (porteMonnaieFacade.find(compte1,monnaie1)!=null){
			pcA1 = porteMonnaieFacade.find(compte1,monnaie1).getMontant();
		}
		if (porteMonnaieFacade.find(compte2,monnaie1)!=null){
			pcA2 = porteMonnaieFacade.find(compte2,monnaie1).getMontant();
		}
		if (porteMonnaieFacade.find(compte2,monnaie2)!=null){
			pcV2 = porteMonnaieFacade.find(compte2,monnaie2).getMontant();
		}
		transactions = echangeTxService.trouverOffres(newTransaction);
		
    	List<Transaction> trans = transactionFacade.findAll();
    	
    	for (Transaction t : trans){
    		if (t.getMontantAchat()==3000.0){
    			assertEquals(t.getMontantVendre(),50.0,0.);    			
    		}
    		else if (t.getMontantAchat()==150.0){
    			assertEquals(t.getMontantVendre(),50.0,0.); 
    		}
    		else {
    			fail();
    		}
    	}
		

    	
		assertEquals(debut+2, transactionFacade.findAll().size());
		assertEquals(pcA1+100.,porteMonnaieFacade.find(compte1,monnaie1).getMontant(),0.);
		assertEquals(pcA2-100.,porteMonnaieFacade.find(compte2,monnaie1).getMontant(),0.);
		assertEquals(pcV2+3150.,porteMonnaieFacade.find(compte2,monnaie2).getMontant(),0.);
    }

    
}
