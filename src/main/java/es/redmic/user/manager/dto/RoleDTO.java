package es.redmic.user.manager.dto;

import es.redmic.models.es.common.dto.DomainImplDTO;

public class RoleDTO extends DomainImplDTO{
	
	public RoleDTO() {
		super();
	}
	
	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
