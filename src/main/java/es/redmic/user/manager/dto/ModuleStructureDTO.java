package es.redmic.user.manager.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

public class ModuleStructureDTO extends ModulePermsDTO {

	@JsonProperty("modules")
	private List<ModulePermsDTO> moduleBean;

	public List<ModulePermsDTO> getModule() {
		return moduleBean;
	}

	@JsonIgnore
	public void setModule(List<ModulePermsDTO> modules) {
		this.moduleBean = modules;
	}

	@JsonSetter("modules")
	public void setModule(ModulePermsDTO module) {
		if (this.moduleBean == null)
			this.moduleBean = new ArrayList<ModulePermsDTO>();
		this.moduleBean.add(module);

		Collections.sort(moduleBean, new Comparator<ModulePermsDTO>() {
			@Override
			public int compare(ModulePermsDTO module1, ModulePermsDTO module2)
			{
				return module1.getOrder().compareTo(module2.getOrder());
			}
		});
	}

	@Override
	public boolean equals(Object o) {
		return (o instanceof ModuleStructureDTO && this.getId() == ((ModuleStructureDTO) o).getId());

	}
}
