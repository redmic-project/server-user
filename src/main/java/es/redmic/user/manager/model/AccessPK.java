package es.redmic.user.manager.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the access database table.
 * 
 */
@Embeddable
public class AccessPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(insertable=false, updatable=false)
	private Long userid;

	@Column(insertable=false, updatable=false)
	private Long module;

	public AccessPK() {
	}
	public Long getUserid() {
		return this.userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	public Long getModule() {
		return this.module;
	}
	public void setModule(Long module) {
		this.module = module;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AccessPK)) {
			return false;
		}
		AccessPK castOther = (AccessPK)other;
		return 
			this.userid.equals(castOther.userid)
			&& this.module.equals(castOther.module);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.userid.hashCode();
		hash = hash * prime + this.module.hashCode();
		
		return hash;
	}
}