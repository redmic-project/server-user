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
