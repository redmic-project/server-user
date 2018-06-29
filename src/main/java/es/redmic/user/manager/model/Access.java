package es.redmic.user.manager.model;

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