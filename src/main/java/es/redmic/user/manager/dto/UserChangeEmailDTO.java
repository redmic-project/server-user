package es.redmic.user.manager.dto;

import javax.validation.constraints.Size;

import es.redmic.models.es.common.dto.DTO;

public class UserChangeEmailDTO implements DTO {

	@Size(max = 255)
	private String email;

	private Long id;

	public UserChangeEmailDTO() {
		super();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}
}
