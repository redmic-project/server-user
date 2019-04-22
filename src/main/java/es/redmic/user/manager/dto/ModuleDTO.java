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
