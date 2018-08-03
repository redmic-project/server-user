package es.redmic.user.manager.dto;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserRegisterDTO extends UserDTO {

	@NotNull
	@Size(min = 6, max = 255)
	private String password;

	@AssertTrue
	private Boolean accept;

	@NotNull
	private String reCaptcha;

	public UserRegisterDTO() {
		super();
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getAccept() {
		return accept;
	}

	public void setAccept(Boolean accept) {
		this.accept = accept;
	}

	public String getReCaptcha() {
		return reCaptcha;
	}

	public void setReCaptcha(String reCaptcha) {
		this.reCaptcha = reCaptcha;
	}

}
