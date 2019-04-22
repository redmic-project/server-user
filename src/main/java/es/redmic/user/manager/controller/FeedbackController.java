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

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.exception.databinding.DTONotValidException;
import es.redmic.models.es.common.dto.SuperDTO;
import es.redmic.user.manager.dto.FeedbackDTO;
import es.redmic.user.manager.service.FeedbackService;

@RestController
@RequestMapping("${controller.mapping.FEEDBACK}")
public class FeedbackController {

	@Value("${property.CONTACTREDMIC}")
	private String EMAIL_CONTACT;

	FeedbackService service;

	@Autowired
	public FeedbackController(FeedbackService service) {

		this.service = service;
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	@ResponseBody
	public SuperDTO add(@Valid @RequestBody FeedbackDTO dto, BindingResult errorDto) {

		if (errorDto.hasErrors())
			throw new DTONotValidException(errorDto);

		service.send(dto, EMAIL_CONTACT);

		return new SuperDTO(true);
	}
}
