package es.redmic.user.manager.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import es.redmic.databaselib.common.model.LongModel;

/**
 * The persistent class for the module database table.
 * 
 */
@Entity
@Table(name = "module", schema="app")
@NamedQuery(name = "Module.findAll", query = "SELECT m FROM Module m")
public class Module extends LongModel {

	private Boolean enable;

	private Boolean hidden;

	private String icon;

	private String internpath;

	private String name;

	private Integer orderr;

	// bi-directional many-to-one association to Category
	@ManyToOne
	@JoinColumn(name = "parentid")
	private Module parent;

	public Module() {
	}

	public Boolean getEnable() {
		return this.enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	public Boolean getHidden() {
		return this.hidden;
	}

	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}

	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getInternPath() {
		return this.internpath;
	}

	public void setInternPath(String internpath) {
		this.internpath = internpath;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getOrder() {
		return this.orderr;
	}

	public void setOrder(Integer order) {
		this.orderr = order;
	}

	public Module getParent() {
		return parent;
	}

	public void setParent(Module parent) {
		this.parent = parent;
	}
}