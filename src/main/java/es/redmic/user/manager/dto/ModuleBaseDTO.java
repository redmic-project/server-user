package es.redmic.user.manager.dto;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import es.redmic.models.es.common.dto.CompactDTOImpl;

public class ModuleBaseDTO extends CompactDTOImpl {

	private Boolean enable;

	@JsonProperty("order")
	private Integer orderr;

	public ModuleBaseDTO() {
		super();
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	@Size(min = 1, max = 100)
	public String getName() {
		return name;
	}

	public Integer getOrder() {
		return orderr;
	}

	public void setOrder(Integer order) {
		this.orderr = order;
	}
}
