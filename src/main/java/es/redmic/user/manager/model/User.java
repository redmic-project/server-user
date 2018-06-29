package es.redmic.user.manager.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import es.redmic.databaselib.common.model.LongModel;
import es.redmic.databaselib.user.model.Role;

/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@Table(name = "user", schema="app")
@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
public class User extends LongModel {

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime", parameters = {
			@Parameter(name = "databaseZone", value = "jvm"), @Parameter(name = "javaZone", value = "jvm") })
	private DateTime datelastaccess;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime", parameters = {
			@Parameter(name = "databaseZone", value = "jvm"), @Parameter(name = "javaZone", value = "jvm") })
	private DateTime dateregistration;

	private String email;
	
	private String image;

	private Boolean enable;

	private String lastname;

	@Column(name = "name")
	private String firstname;

	private String password;

	@Column(name="tokenconfirmation", unique=true)
	private String tokenConfirmation;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime", parameters = {
			@Parameter(name = "databaseZone", value = "jvm"), @Parameter(name = "javaZone", value = "jvm") })
	private DateTime datetokenconfirmation;

	// bi-directional many-to-one association to Access
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	private List<Access> accesses;

	// bi-directional many-to-one association to Role
	@ManyToOne
	@JoinColumn(name = "role", nullable = false)
	private Role roleBean;
	
	// bi-directional many-to-one association to Role
	@ManyToOne
	@JoinColumn(name = "usersectorid")
	private UserSector sector;

	public User() {
	}

	public DateTime getDateLastAccess() {
		return this.datelastaccess;
	}

	public void setDateLastAccess(DateTime datelastaccess) {
		this.datelastaccess = datelastaccess;
	}

	public DateTime getDateRegistration() {
		return this.dateregistration;
	}

	public void setDateRegistration(DateTime dateregistration) {
		this.dateregistration = dateregistration;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Boolean getEnabled() {
		return this.enable;
	}

	public void setEnabled(Boolean enable) {
		this.enable = enable;
	}

	public String getLastName() {
		return this.lastname;
	}

	public void setLastName(String lastname) {
		this.lastname = lastname;
	}

	public String getFirstName() {
		return this.firstname;
	}

	public void setFirstName(String firstname) {
		this.firstname = firstname;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTokenConfirmation() {
		return this.tokenConfirmation;
	}

	public void setTokenConfirmation(String tokenconfirmation) {
		this.tokenConfirmation = tokenconfirmation;
	}

	public DateTime getDatetokenconfirmation() {
		return datetokenconfirmation;
	}

	public void setDatetokenconfirmation(DateTime datetokenconfirmation) {
		this.datetokenconfirmation = datetokenconfirmation;
	}

	public List<Access> getAccesses() {
		return this.accesses;
	}

	public void setAccesses(List<Access> accesses) {
		this.accesses = accesses;
	}

	public Access addAccess(Access access) {
		getAccesses().add(access);
		access.setUser(this);

		return access;
	}

	public Access removeAccess(Access access) {
		getAccesses().remove(access);
		access.setUser(null);

		return access;
	}

	public Role getRole() {
		return this.roleBean;
	}

	public void setRole(Role roleBean) {
		this.roleBean = roleBean;
	}
	
	public UserSector getSector() {
		return this.sector;
	}

	public void setSector(UserSector sector) {
		this.sector = sector;
	}
}