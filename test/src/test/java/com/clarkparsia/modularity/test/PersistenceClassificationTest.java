// Copyright (c) 2006 - 2008, Clark & Parsia, LLC. <http://www.clarkparsia.com>
// This source code is available under the terms of the Affero General Public License v3.
//
// Please see LICENSE.txt for full license terms, including the availability of proprietary exceptions.
// Questions, comments, or requests for clarification: licensing@clarkparsia.com

package com.clarkparsia.modularity.test;

import java.io.File;
import java.io.IOException;

import com.clarkparsia.modularity.IncrementalReasoner;
import com.clarkparsia.modularity.ModuleExtractor;
import com.clarkparsia.owlapiv3.OWL;
import com.clarkparsia.owlapiv3.OntologyUtils;
import com.clarkparsia.pellet.owlapiv3.PelletReasoner;
import com.google.common.base.Supplier;
import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;

import static com.clarkparsia.modularity.test.TestUtils.assertClassificationEquals;
import static org.junit.Assert.assertTrue;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2009
 * </p>
 * <p>
 * Company: Clark & Parsia, LLC. <http://www.clarkparsia.com>
 * </p>
 *
 * @author Blazej Bulka
 */
public class PersistenceClassificationTest extends AbstractModularityTest {
	private static final String TEST_FILE = "test-persistence-classification.zip";

	public PersistenceClassificationTest(final Supplier<ModuleExtractor> theModExtractorSupplier) {
		super(theModExtractorSupplier);
	}
	
	public void testFile(String fileName) throws IOException {
		String common = "file:"+ base + fileName;
		testClassification( common + ".owl");		
	}
	
	public void testClassification(String inputOnt) throws IOException {
		File testFile = new File( TEST_FILE );
		OWLOntology ontology = OntologyUtils.loadOntology( inputOnt );
		
		try {
			PelletReasoner unified = PelletReasoner.config().createReasoner(ontology);
			ModuleExtractor moduleExtractor = createModuleExtractor();

			IncrementalReasoner modular = IncrementalReasoner.config()
			                                                 .reasoner(unified)
			                                                 .extractor(moduleExtractor)
			                                                 .createIncrementalReasoner();

			modular.classify();

			modular.save(testFile);

			IncrementalReasoner modular2 = IncrementalReasoner.config().file(testFile).manager(OWLManager.createOWLOntologyManager()).createIncrementalReasoner();

			assertClassificationEquals( unified, modular2 );

			assertTrue( testFile.delete() );
			
			unified.dispose();
			modular.dispose();
			modular2.dispose();
		} 
		finally {
			OWL.manager.removeOntology( ontology );
		}
	}	

	@Test
	public void koalaPersistenceClassifyTest() throws IOException {
		testFile( "koala" );
	}
	
	@Test
	public void miniTambisPersistenceClassifyTest() throws IOException {
		testFile( "miniTambis" );
	}

	@Test
	public void sumoPersistenceClassifyTest() throws IOException {
		testFile( "SUMO" );
	}

	@Test
	public void sweetPersistenceClassifyTest() throws IOException {
		testFile( "SWEET" );
	}
	
	@Test
	public void galenPersistenceClassifyTest() throws IOException {
		testFile( "galen" );
	}

	@Test
	public void winePersistenceClassifyTest() throws IOException {
		testFile( "wine" );
	}
}
