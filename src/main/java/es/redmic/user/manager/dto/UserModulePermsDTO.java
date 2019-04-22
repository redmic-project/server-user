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

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import es.redmic.models.es.common.deserializer.CustomRelationDeserializer;
import es.redmic.models.es.common.dto.DomainImplDTO;
import es.redmic.user.common.deserializer.UserDateTimeDeserializer;
import es.redmic.user.common.serializer.UserDateTimeSerializer;

public class UserModulePermsDTO extends UserBaseDTO {

	@JsonSerialize(using = UserDateTimeSerializer.class)
	@JsonDeserialize(using = UserDateTimeDeserializer.class)
	@JsonProperty("dateLastAccess")
	private DateTime datelastaccess;

	@JsonSerialize(using = UserDateTimeSerializer.class)
	@JsonDeserialize(using = UserDateTimeDeserializer.class)
	@JsonProperty("dateRegistration")
	private DateTime dateregistration;

	@JsonProperty("enabled")
	private Boolean enable;

	@JsonSerialize(as = DomainImplDTO.class)
	@JsonDeserialize(using = CustomRelationDeserializer.class)
	private RoleDTO role;

	@JsonProperty("category")
	private List<ModuleStructureDTO> categoryBean;

	@JsonIgnore
	private List<AccessDTO> accesses;

	public UserModulePermsDTO() {
		super();
	}

	public DateTime getDateLastAccess() {
		return datelastaccess;
	}

	public void setDateLastAccess(DateTime datelastaccess) {
		this.datelastaccess = datelastaccess;
	}

	public DateTime getDateRegistration() {
		return dateregistration;
	}

	public void setDateRegistration(DateTime dateregistration) {
		this.dateregistration = dateregistration;
	}

	public Boolean getEnabled() {
		return enable;
	}

	public void setEnabled(Boolean enable) {
		this.enable = enable;
	}

	public RoleDTO getRole() {
		return role;
	}

	public void setRole(RoleDTO role) {
		this.role = role;
	}

	public List<AccessDTO> getAccesses() {
		return accesses;
	}

	public void setAccesses(List<AccessDTO> accesses) {
		this.accesses = accesses;
	}

	public List<ModuleStructureDTO> getCategoryBean() {
		return categoryBean;
	}

	public void setCategoryBean(List<ModuleStructureDTO> categoryBean) {
		this.categoryBean = categoryBean;

		Collections.sort(categoryBean, new Comparator<ModuleStructureDTO>() {
			@Override
			public int compare(ModuleStructureDTO cat1, ModuleStructureDTO cat2) {
				return cat1.getOrder().compareTo(cat2.getOrder());
			}
		});
	}

}
