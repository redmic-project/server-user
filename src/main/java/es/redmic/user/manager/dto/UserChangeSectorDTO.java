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
