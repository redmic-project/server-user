package es.redmic.user.embedded.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;

/*-
 * #%L
 * User
 * %%
 * Copyright (C) 2025 REDMIC Project / Server
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

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.user.embedded.service.SupersetEmbeddedService;


@RestController
@RequestMapping(value = "${controller.mapping.SUPERSET_EMBEDDED}")
public class SupersetEmbeddedController {

	SupersetEmbeddedService service;

	public SupersetEmbeddedController(SupersetEmbeddedService service) {

		this.service = service;
	}

	@RequestMapping(value = "/get-token/{dashboardid}", method = RequestMethod.GET)
	public Object getToken(@PathVariable("dashboardid") String dashboardid) {

		return service.getToken(dashboardid);
	}
}
