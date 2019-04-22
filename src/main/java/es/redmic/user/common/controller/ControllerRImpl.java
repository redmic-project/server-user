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

import java.lang.reflect.ParameterizedType;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import es.redmic.databaselib.common.model.LongModel;
import es.redmic.models.es.common.dto.BodyItemDTO;
import es.redmic.models.es.common.dto.DTO;
import es.redmic.models.es.common.dto.SuperDTO;
import es.redmic.user.common.dto.BodyListDTO;
import es.redmic.user.common.query.QueryModel;
import es.redmic.user.common.query.Range;
import es.redmic.user.common.service.IServiceR;
import es.redmic.user.config.OrikaScanBean;

public abstract class ControllerRImpl<TDTO extends DTO, TModel extends LongModel>
		implements IControllerR<TDTO, TModel> {

	@Autowired
	protected OrikaScanBean factory;

	private final IServiceR<TModel, TDTO> service;
	protected Class<TDTO> typeOfTDTO;
	protected Class<TDTO> typeOfTModel;

	@SuppressWarnings("unchecked")
	public ControllerRImpl(IServiceR<TModel, TDTO> service) {
		this.service = service;
		this.typeOfTDTO = (Class<TDTO>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
		this.typeOfTModel = (Class<TDTO>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[1];
	}

	@Override
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public SuperDTO findById(@PathVariable("id") Long id, HttpServletResponse response) {
		TModel model = null;
		model = service.findById(id);
		return new BodyItemDTO<TDTO>(service.convertModelToDto(model));
	}

	@Override
	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	public SuperDTO findAll(@ModelAttribute("querymodel") QueryModel querymodel,
			@RequestHeader(value = "Range", required = false) String rangeHeader, HttpServletResponse response) {

		Range range = querymodel.getRange();

		if (rangeHeader != null) {
			range.set(rangeHeader);
			// range.setTotal(service.count(querymodel));
			response.setHeader("Content-Range", range.toString());
		}
		Long total = service.count(querymodel);
		range.setTotal(total);
		querymodel.setRange(range);

		Iterable<TModel> models = service.findAll(querymodel);
		BodyListDTO<TDTO> DTO = new BodyListDTO<TDTO>(factory.mapAsList(models, typeOfTDTO));
		DTO.setTotal(total);
		return DTO;
	}
}
