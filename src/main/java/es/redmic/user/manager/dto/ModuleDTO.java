package es.redmic.user.manager.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import es.redmic.models.es.common.deserializer.CustomRelationDeserializer;

public class ModuleDTO extends ModuleBaseDTO {

	private Boolean hidden;

	private String icon;

	@JsonProperty("internPath")
	private String internpath;

	@JsonSerialize(as = ModuleBaseDTO.class)
	@JsonDeserialize(using = CustomRelationDeserializer.class)
	private ModuleDTO parent;

	public ModuleDTO() {
		super();
	}

	public Boolean getHidden() {
		return hidden;
	}

	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getInternPath() {
		return internpath;
	}

	public void setInternPath(String internPath) {
		this.internpath = internPath;
	}


	public ModuleDTO getParent() {
		return parent;
	}

	public void setParent(ModuleDTO parent) {
		this.parent = parent;
	}

}
