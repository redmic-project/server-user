package es.redmic.user.common.dto;

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

import java.util.List;

import es.redmic.models.es.common.dto.SuperDTO;

public class BodyListDTO<TDto> extends SuperDTO {

	private List<TDto> body;

	private Long total;

	public BodyListDTO(List<TDto> list) {
		super(true);
		this.body = list;
	}

	public List<TDto> getBody() {
		return body;
	}

	public void setBody(List<TDto> list) {
		this.body = list;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}
}
