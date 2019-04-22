package es.redmic.user.common.controller;

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

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import es.redmic.databaselib.common.model.SuperModel;
import es.redmic.models.es.common.dto.DTO;
import es.redmic.models.es.common.dto.SuperDTO;
import es.redmic.user.common.query.QueryModel;

public interface IControllerR<TDTO extends DTO, TMod extends SuperModel> {

	public SuperDTO findById(@PathVariable("id") Long id, HttpServletResponse response);

	public SuperDTO findAll(@ModelAttribute("querymodel") QueryModel querymodel,
			@RequestHeader(value = "Range", required = false) String rangeHeader, HttpServletResponse response);
}
