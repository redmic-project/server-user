package es.redmic.user.manager.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserResettingPasswordDTO {

	@NotNull
	@Size(min = 6, max = 255)
	private String password;

	@NotNull
	private String token;

	public UserResettingPasswordDTO() {

	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
