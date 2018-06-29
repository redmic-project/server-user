package es.redmic.user.manager.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class UserResettingRequestDTO {

	@NotNull
	@Email
	private String email;

	public UserResettingRequestDTO() {
		super();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
