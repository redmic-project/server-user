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

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.exception.custom.ResourceNotFoundException;
import es.redmic.exception.databinding.DTONotValidException;
import es.redmic.exception.security.NotAllowedException;
import es.redmic.models.es.common.dto.BodyItemDTO;
import es.redmic.models.es.common.dto.SuperDTO;
import es.redmic.user.common.controller.ControllerRWImpl;
import es.redmic.user.common.dto.TokenDTO;
import es.redmic.user.manager.dto.UserBaseDTO;
import es.redmic.user.manager.dto.UserDTO;
import es.redmic.user.manager.dto.UserModulePermsDTO;
import es.redmic.user.manager.dto.UserRegisterDTO;
import es.redmic.user.manager.model.User;
import es.redmic.user.manager.service.UserService;

@RestController
@RequestMapping(value = "${controller.mapping.USER}")
public class UserController extends ControllerRWImpl<UserDTO, User> {

	private UserService service;

	@Autowired
	public UserController(UserService service) {
		super(service);
		this.service = service;
	}

	@Override
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public SuperDTO add(@Valid @RequestBody UserDTO dto, BindingResult bindingResult) {
		throw new ResourceNotFoundException();
	}

	/* Registrar un nuevo usuario */

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public SuperDTO add(@Valid @RequestBody UserRegisterDTO dto, BindingResult bindingResult) {

		if (bindingResult.hasErrors())
			throw new DTONotValidException(bindingResult);
		service.save(dto);
		return new SuperDTO(true);
	}

	@RequestMapping(value = "/activateAccount", method = RequestMethod.POST)
	public SuperDTO activateAccount(@Valid @RequestBody TokenDTO dto, BindingResult bindingResult) {

		if (bindingResult.hasErrors())
			throw new DTONotValidException(bindingResult);
		service.activateAccount(dto);
		return new SuperDTO(true);
	}

	/* Obtener datos de la cuenta de usuario (S�lo el propio usuario) */

	@RequestMapping(value = "/accountData", method = RequestMethod.GET)
	public SuperDTO accountData(HttpServletResponse response) {
		String username = service.getUsername();
		UserBaseDTO profile = factory.map(service.findProfileByUsername(username), UserModulePermsDTO.class);
		return new BodyItemDTO<UserBaseDTO>(profile);
	}

	@RequestMapping(value = "/accountData/{id}", method = RequestMethod.PUT)
	public SuperDTO accountDataEdit(@Valid @RequestBody UserBaseDTO dto, BindingResult bindingResult,
			@PathVariable("id") Long id, HttpServletResponse response) {

		if (bindingResult.hasErrors())
			throw new DTONotValidException(bindingResult);

		if (id != service.findProfileByUsername(service.getUsername()).getId()) {
			throw new NotAllowedException();
		}
		dto.setId(id);
		UserBaseDTO profile = new UserBaseDTO();
		factory.map(service.updateProfile(dto), profile);
		return new BodyItemDTO<UserBaseDTO>(profile);
	}
}
