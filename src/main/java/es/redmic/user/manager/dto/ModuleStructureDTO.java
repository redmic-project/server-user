package es.redmic.user.manager.dto;

/*-
 * #%L
 * User
 * %%
 * Copyright (C) 2019 REDMIC Project / Server
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

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
