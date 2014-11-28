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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.Date;
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

import fr.ufrima.m2pgi.ecom.facade.CompteFacade;
import fr.ufrima.m2pgi.ecom.facade.EchangeOffreFacade;
import fr.ufrima.m2pgi.ecom.facade.MonnaieFacade;
import fr.ufrima.m2pgi.ecom.model.Compte;
import fr.ufrima.m2pgi.ecom.model.EchangeOffre;
import fr.ufrima.m2pgi.ecom.model.Monnaie;
import fr.ufrima.m2pgi.ecom.util.Resources;

@RunWith(Arquillian.class)
public class EchangeTxTest {
    @Deployment
    public static Archive<?> createTestArchive() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addClasses(MonnaieFacade.class, Monnaie.class,Compte.class, CompteFacade.class,EchangeOffre.class, EchangeOffreFacade.class, Resources.class)
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
    Logger log;
    
    
    Compte newCompte;
    Monnaie monnaie1;
    Monnaie monnaie2;
    
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
    }

    @After
    public void end() {
         compteFacade.remove(newCompte);
         monnaieFacade.remove(monnaie1);
         monnaieFacade.remove(monnaie2);
    }

    
    
    @Test
    public void testCreation() throws Exception {
    	int debut = echangeOffreFacade.findAll().size();
        EchangeOffre newEchangeOffre = new EchangeOffre();
        newEchangeOffre.setDateCreation(new Date());
        newEchangeOffre.setCompte(newCompte);
        newEchangeOffre.setMonnaieAchat(monnaie1);
        newEchangeOffre.setMonnaieVendre(monnaie2);
        newEchangeOffre.setMontantAchat(100.0);
        newEchangeOffre.setMontantVendre(300.0);
        
        echangeOffreFacade.create(newEchangeOffre);
        assertNotNull(newEchangeOffre.getId());
        
        Double modif = 500.0;
        newEchangeOffre.setMontantVendre(modif);
        echangeOffreFacade.edit(newEchangeOffre);
        EchangeOffre editEchangeOffre = echangeOffreFacade.find(newEchangeOffre.getId());
        assertEquals(modif, editEchangeOffre.getMontantVendre());
        
        echangeOffreFacade.remove(editEchangeOffre);
        assertEquals(debut, echangeOffreFacade.findAll().size());

    }

    @Test
    public void testCreationMissingAtribue() throws Exception {
        EchangeOffre newEchangeOffre = new EchangeOffre();
        newEchangeOffre.setDateCreation(new Date());
        newEchangeOffre.setMonnaieAchat(monnaie1);
        newEchangeOffre.setMonnaieVendre(monnaie2);
        newEchangeOffre.setMontantAchat(100.0);
        newEchangeOffre.setMontantVendre(300.0);
        try {
        	echangeOffreFacade.create(newEchangeOffre);
        	fail();
        } catch (Exception e) {	
        }
    }
}
