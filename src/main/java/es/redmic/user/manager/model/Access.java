package es.redmic.user.manager.model;

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

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import es.redmic.databaselib.common.model.SuperModel;

/**
 * The persistent class for the access database table.
 * 
 */
@Entity
@Table(name = "access", schema="app")
@NamedQuery(name = "Access.findAll", query = "SELECT a FROM Access a")
public class Access extends SuperModel {

	@EmbeddedId
	private AccessPK id;

	private Integer perms;

	// bi-directional many-to-one association to Module
	@ManyToOne
	@JoinColumn(name = "module", insertable = false, updatable = false)
	private Module moduleBean;

	// bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name = "userid", insertable = false, updatable = false)
	private User user;

	public Access() {
	}

	public AccessPK getId() {
		return this.id;
	}

	public void setId(AccessPK id) {
		this.id = id;
	}

	public Integer getPerms() {
		return this.perms;
	}

	public void setPerms(Integer perms) {
		this.perms = perms;
	}

	public Module getModule() {
		return this.moduleBean;
	}

	public void setModule(Module moduleBean) {
		this.moduleBean = moduleBean;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
