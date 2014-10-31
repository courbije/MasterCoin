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
package fr.ufrima.m2pgi.ecom.test;

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
import org.junit.Test;
import org.junit.runner.RunWith;

import fr.ufrima.m2pgi.ecom.model.Compte;
import fr.ufrima.m2pgi.ecom.service.CompteFacade;
import fr.ufrima.m2pgi.ecom.util.Resources;

@RunWith(Arquillian.class)
public class CompteTest {
    @Deployment
    public static Archive<?> createTestArchive() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addClasses(Compte.class, CompteFacade.class, Resources.class)
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                // Deploy our test datasource
                .addAsWebInfResource("test-ds.xml");
    }

    @Inject
    CompteFacade compteFacade;

    @Inject
    Logger log;

    @Test
    public void testCreation() throws Exception {
    	int debut = compteFacade.findAll().size();
        Compte newCompte = new Compte();
        newCompte.setLogin("toto");
        newCompte.setPassword("azerty");
        newCompte.setMail("bidule@machin.com");
        newCompte.setNom("df");
        newCompte.setPrenom("df");
        newCompte.setDateNaissance(new Date());
        compteFacade.create(newCompte);
        assertNotNull(newCompte.getId());
        
        newCompte.setNom("jack");
        compteFacade.edit(newCompte);
        Compte editCompte = compteFacade.find(newCompte.getId());
        assertEquals("jack", editCompte.getNom());
        
        compteFacade.remove(editCompte);
        assertEquals(debut, compteFacade.findAll().size());

    }

    @Test
    public void testCreationMissingAtribue() throws Exception {
        Compte newCompte = new Compte();
        newCompte.setLogin("toto");
        try {
        	compteFacade.create(newCompte);
        	fail();
        } catch (Exception e) {	
        }
    }
}
