package es.redmic.user.unit.common.service;

/*-
 * #%L
 * User
 * %%
 * Copyright (C) 2019 REDMIC Project / Server
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import es.redmic.user.common.query.Range;

public class PredicateTest {
	/*
	 * private static final String SEARCH_TERM_TEXT = "firstname1"; private
	 * static final String EXPECTED_PREDICATE_TEXT_STRING =
	 * "contains(user.email,firstname1) || contains(user.firstname,firstname1) || contains(user.image,firstname1) || contains(user.lastname,firstname1)"
	 * ;
	 * 
	 * private static final String SEARCH_TERM_WHERE =
	 * "surname LIKE 'surname1' or firstname LIKE '%firstname2%' or updated IS_NULL"
	 * ; private static final String QContact =
	 * "es.redmic.user.manager.model.QUser";
	 */

	/*
	 * @Test public void textQuery() throws ClassNotFoundException,
	 * SecurityException, DBNotFoundException { QueryModel qm = new
	 * QueryModel(); qm.setText(SEARCH_TERM_TEXT); qm.setqTable(QContact);
	 * Predicate pred = null; pred = qm.getText(); String predicateAsString =
	 * pred.toString(); assertEquals(EXPECTED_PREDICATE_TEXT_STRING,
	 * predicateAsString); }
	 * 
	 * @Test public void whereQuery() throws ClassNotFoundException,
	 * DBNotFoundException, NoSuchFieldException, SecurityException { QueryModel
	 * qm = new QueryModel(); qm.setWhere(SEARCH_TERM_WHERE);
	 * qm.setqTable(QContact); Predicate pred = qm.getWhere(); String
	 * predicateAsString = pred.toString();
	 * //assertEquals(EXPECTED_PREDICATE_TEXT_WHERE, predicateAsString); }
	 */

	@Test
	public void requestRangeFirstPage() {
		Range range = new Range();
		range.set("items=0-4");
		range.setTotal(20L);
		assertEquals("items 0-4/20", range.toString());
		assertEquals(0, range.getStart());
		assertEquals(4, range.getEnd());
		assertEquals(0, range.getPage());
		assertEquals(5, range.getSize());
	}

	@Test
	public void requestRangeIntermediatePage() {
		Range range = new Range();
		range.set("items=5-9");
		range.setTotal(20L);
		assertEquals("items 5-9/20", range.toString());
		assertEquals(5, range.getStart());
		assertEquals(9, range.getEnd());
		assertEquals(1, range.getPage());
		assertEquals(5, range.getSize());
	}

	@Test
	public void requestRangeLastPage() {
		Range range = new Range();
		range.set("items=10-14");
		range.setTotal(12L);
		assertEquals("items 10-12/12", range.toString());
		assertEquals(10, range.getStart());
		assertEquals(14, range.getEnd());
		assertEquals(2, range.getPage());
		assertEquals(5, range.getSize());
	}
}
