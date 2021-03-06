package es.redmic.user.manager.service;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.redmic.user.common.service.ServiceRWImpl;
import es.redmic.user.manager.dto.ModuleDTO;
import es.redmic.user.manager.model.Module;
import es.redmic.user.manager.repository.ModuleRepository;

@Service
public class ModuleService extends ServiceRWImpl<Module, ModuleDTO> {

	ModuleRepository repository;
	
	@Autowired
	public ModuleService(ModuleRepository repository) {
		super(repository);
		this.repository = repository;
	}
	
	public List<Object> getOpenModules() {
		
		return repository.findOpenModules();
	}
}
