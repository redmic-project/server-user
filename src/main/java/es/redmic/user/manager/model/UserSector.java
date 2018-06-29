package es.redmic.user.manager.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import es.redmic.databaselib.common.model.LongModel;

/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@Table(name = "usersector", schema="app")
@NamedQuery(name = "Usersector.findAll", query = "SELECT u FROM UserSector u")
public class UserSector extends LongModel {

	@Column(nullable = false, length = 150)
	private String name;

	@Column(nullable = false, length = 150)
	private String name_en;

	public UserSector() {
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName_en() {
		return name_en;
	}

	public void setName_en(String name_en) {
		this.name_en = name_en;
	}
}