package es.redmic.user.manager.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import es.redmic.models.es.common.dto.DTOImplement;

public class UserNameDTO extends DTOImplement {

	@NotNull
	@Size(max = 90)
	@JsonProperty("lastName")
	private String lastname;

	@NotNull
	@Size(max = 30)
	@JsonProperty("firstName")
	private String firstname;

	public UserNameDTO() {
		super();
	}

	public String getLastName() {
		return lastname;
	}

	public void setLastName(String lastname) {
		this.lastname = lastname;
	}

	public String getFirstName() {
		return firstname;
	}

	public void setFirstName(String firstname) {
		this.firstname = firstname;
	}
}
