package es.redmic.user.manager.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import es.redmic.models.es.common.deserializer.CustomRelationDeserializer;
import es.redmic.models.es.common.dto.DTOImplement;
import es.redmic.models.es.common.dto.DomainImplDTO;

public class UserChangeSectorDTO extends DTOImplement  {
	
	@JsonSerialize(as = DomainImplDTO.class)
	@JsonDeserialize(using = CustomRelationDeserializer.class)
	private UserSectorDTO sector;

	public UserChangeSectorDTO() {
		super();
	}
	
	public UserSectorDTO getSector() {
		return sector;
	}

	public void setSector(UserSectorDTO sector) {
		this.sector = sector;
	}
}
