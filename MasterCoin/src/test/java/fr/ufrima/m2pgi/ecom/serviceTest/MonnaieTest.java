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

import java.util.logging.Logger;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import fr.ufrima.m2pgi.ecom.model.Monnaie;
import fr.ufrima.m2pgi.ecom.service.MonnaieFacade;
import fr.ufrima.m2pgi.ecom.util.Resources;

@RunWith(Arquillian.class)
public class MonnaieTest {
    @Deployment
    public static Archive<?> createTestArchive() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addClasses(Monnaie.class, MonnaieFacade.class, Resources.class)
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                // Deploy our test datasource
                .addAsWebInfResource("test-ds.xml");
    }

    @Inject
    MonnaieFacade monnaieFacade;

    @Inject
    Logger log;

    @Test
    public void testCreation() throws Exception {
        Monnaie newMonnaie = new Monnaie();
        newMonnaie.setAcroyme("BitCoin");
        newMonnaie.setNom("BitCoin");
        monnaieFacade.create(newMonnaie);
        assertNotNull(newMonnaie.getId());
    }

    @Test
    public void testCreationMissingAtribue() throws Exception {
        Monnaie newMonnaie = new Monnaie();
        newMonnaie.setAcroyme("BitCoin");
        try {
        	monnaieFacade.create(newMonnaie);
        	fail();
        } catch (Exception e) {	
        }
    }
    
    @Test
    public void testRemove() throws Exception {
    	int debut = monnaieFacade.findAll().size();
        Monnaie newMonnaie = new Monnaie();
        newMonnaie.setAcroyme("BitCoin");
        newMonnaie.setNom("BitCoin");
        monnaieFacade.create(newMonnaie);
        assertNotNull(newMonnaie.getId());
        monnaieFacade.remove(newMonnaie);
        assertEquals(debut, monnaieFacade.findAll().size());
    }
    
    @Test
    public void testEdit() throws Exception {
        Monnaie newMonnaie = new Monnaie();
        newMonnaie.setAcroyme("BitCoin");
        newMonnaie.setNom("BitCoin");
        monnaieFacade.create(newMonnaie);
        newMonnaie.setNom("DogeCoin");
        monnaieFacade.edit(newMonnaie);
        Monnaie editMonnaie = monnaieFacade.find(newMonnaie.getId());
        assertEquals("DogeCoin", editMonnaie.getNom());
    }
    
    @Test
    public void testFind() throws Exception {
    	int debut = monnaieFacade.findAll().size();
        Monnaie newMonnaie = new Monnaie();
        newMonnaie.setAcroyme("BitCoin");
        newMonnaie.setNom("BitCoin");
        Monnaie newMonnaie2 = new Monnaie();
        newMonnaie2.setAcroyme("DogeCoin");
        newMonnaie2.setNom("DogeCoin");
        monnaieFacade.create(newMonnaie);
        monnaieFacade.create(newMonnaie2);
        assertNotNull(newMonnaie.getId());
        assertNotNull(newMonnaie2.getId());
        assertEquals(newMonnaie, monnaieFacade.find(newMonnaie.getId()));
        assertEquals(debut + 2, monnaieFacade.findAll().size());
    }
    
}
