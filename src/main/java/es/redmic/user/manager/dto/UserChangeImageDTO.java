package es.redmic.user.manager.dto;

import javax.validation.constraints.Size;

import es.redmic.models.es.common.dto.DTO;

public class UserChangeImageDTO implements DTO {

	@Size(max = 150)
	private String image;

	private Long id;

	public UserChangeImageDTO() {
		super();
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
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
