package es.redmic.user.manager.controller;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.user.common.controller.ControllerRWImpl;
import es.redmic.user.manager.dto.ModuleDTO;
import es.redmic.user.manager.model.Module;
import es.redmic.user.manager.service.ModuleService;

@RestController
@RequestMapping(value = "${controller.mapping.MODULE}")
public class ModuleController extends ControllerRWImpl<ModuleDTO, Module> {

	ModuleService service;
	
	@Autowired
	public ModuleController(ModuleService service) {
		super(service);
		
		this.service = service;
	}
	
	/*
	 * Obtener solo los modulos a los que puede acceder un usuario invitado
	 */

	@RequestMapping(value = "/openmodules/", method = RequestMethod.GET)
	public Object findModules() {
		
		return service.getOpenModules();
	}
}
