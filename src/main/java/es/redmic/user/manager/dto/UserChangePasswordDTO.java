package es.redmic.user.manager.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import es.redmic.models.es.common.dto.DTOImplement;

public class UserChangePasswordDTO extends DTOImplement {

	@NotNull
	@Size(min = 6, max = 255)
	private String password;

	@NotNull
	@Size(min = 6, max = 255)
	private String oldPassword;

	public UserChangePasswordDTO() {
		super();
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
}
