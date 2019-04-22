package es.redmic.user.config;

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

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.QueryTimeoutException;

import es.redmic.exception.database.DBQueryException;

public class EntityManagerWrapper {

	@Autowired
	private EntityManager em;

	private String queryString;

	private ArrayList<Object> parameters = new ArrayList<Object>();

	public EntityManagerWrapper() {
	}

	public String getQueryString() {
		return queryString;
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

	public ArrayList<Object> getParameters() {
		return parameters;
	}

	public void setParameters(ArrayList<Object> parameters) {
		this.parameters = parameters;
	}

	@SuppressWarnings("unchecked")
	public List<String> execute() {
		Query query = prepareQuery(em);
		List<String> result;
		try {
			result = query.getResultList();
		} catch (IllegalStateException | QueryTimeoutException | PersistenceException e) {
			throw new DBQueryException(e);
		} finally {
			em.close();
		}
		return result;
	}

	public Query prepareQuery(EntityManager em) {
		Query query = em.createNativeQuery(queryString);
		int i = 1;
		for (Object object : parameters) {
			query.setParameter(i, object);
			i++;
		}
		return query;
	}
}
