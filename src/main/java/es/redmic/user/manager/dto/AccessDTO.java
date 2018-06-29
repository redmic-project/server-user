package es.redmic.user.manager.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import es.redmic.models.es.common.deserializer.CustomRelationDeserializer;

public class AccessDTO {
	
	private Integer perms;

	@JsonSerialize(as = ModuleBaseDTO.class)
	@JsonDeserialize(using = CustomRelationDeserializer.class)
	@JsonProperty("module")
	private ModuleDTO moduleBean;
	
	public AccessDTO(){
	}

	public Integer getPerms() {
		return perms;
	}

	public void setPerms(Integer perms) {
		this.perms = perms;
	}

	public ModuleDTO getModule() {
		return moduleBean;
	}

	public void setModule(ModuleDTO module) {
		this.moduleBean = module;
	}
}
