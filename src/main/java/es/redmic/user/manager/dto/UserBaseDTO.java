package es.redmic.user.manager.dto;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import es.redmic.models.es.common.deserializer.CustomRelationDeserializer;
import es.redmic.models.es.common.dto.DomainImplDTO;

public class UserBaseDTO extends UserNameDTO {

	@NotNull
	@Size(min=4, max = 255)
	@Email
	private String email;

	@Column(length = 150)
	private String image;

	@JsonSerialize(as = DomainImplDTO.class)
	@JsonDeserialize(using = CustomRelationDeserializer.class)
	private UserSectorDTO sector;

	public UserBaseDTO() {
		super();
	}

	public UserSectorDTO getSector() {
		return sector;
	}

	public void setSector(UserSectorDTO sector) {
		this.sector = sector;
	}

	public String getEmail() {
		return email;
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
}
