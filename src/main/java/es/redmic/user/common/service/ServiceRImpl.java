package es.redmic.user.common.service;

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
import java.lang.reflect.Type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.BooleanBuilder;

import es.redmic.databaselib.common.model.LongModel;
import es.redmic.databaselib.common.repository.BaseRepository;
import es.redmic.exception.database.DBNotFoundException;
import es.redmic.exception.database.DBQueryException;
import es.redmic.models.es.common.dto.DTO;
import es.redmic.user.common.query.QueryModel;
import es.redmic.user.config.OrikaScanBean;

public abstract class ServiceRImpl<TModel extends LongModel, TDTO extends DTO> implements IServiceR<TModel, TDTO> {

	@Autowired
	protected OrikaScanBean factory;

	protected Class<TDTO> typeOfTDTO;
	protected Class<TModel> typeOfTModel;

	protected BaseRepository<TModel, Long> repository;

	@SuppressWarnings("unchecked")
	public ServiceRImpl(BaseRepository<TModel, Long> repository) {

		this.repository = repository;

		this.typeOfTModel = (Class<TModel>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
		this.typeOfTDTO = (Class<TDTO>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[1];
	}

	@Transactional(readOnly = true)
	public Iterable<TModel> findAll(QueryModel queryModel) {
		Iterable<TModel> models = null;

		String exitsText = queryModel.exitsText();
		String existWhere = queryModel.exitsWhere();

		if (exitsText != null && existWhere != null) {
			BooleanBuilder text = (BooleanBuilder) queryModel.getText();
			BooleanBuilder where = (BooleanBuilder) queryModel.getWhere();
			models = repository.findAll(text.and(where), queryModel.getPageRequest());
		} else if (exitsText != null) {
			models = repository.findAll(queryModel.getText(), queryModel.getPageRequest());
		} else if (existWhere != null) {
			models = repository.findAll(queryModel.getWhere(), queryModel.getPageRequest());
		} else {
			models = repository.findAll(queryModel.getPageRequest());
		}

		return models;
	}

	public TModel convertDtoToModel(TDTO dto) {
		return (TModel) factory.map(dto, typeOfTModel);
	}

	public TDTO convertModelToDto(TModel model) {
		return (TDTO) factory.map(model, typeOfTDTO);
	}

	@Transactional(readOnly = true, rollbackFor = { DBNotFoundException.class })
	public TModel findById(Long id) {
		TModel model = repository.findById(id).get();

		if (model == null) {
			throw new DBNotFoundException("id", String.valueOf(id));
		}

		return model;
	}

	@Transactional(readOnly = true)
	public TModel findOne(Long id) {
		return repository.findById(id).get();
	}

	// TODO Revisar el c�digo, porque el if es igual para ambos casos
	/*
	 * Obtiene el QTable a partir de la ruta del modelo y lo establece en query
	 * model para poder hacer las consultas
	 */
	protected void getQ(QueryModel queryModel) {
		Type packageT = getClass().getGenericSuperclass();
		if (packageT instanceof ParameterizedType) {
			ParameterizedType pt = (ParameterizedType) packageT;
			Type[] fieldsArgTypes = pt.getActualTypeArguments();
			Class<?> result;
			if (fieldsArgTypes.length > 2)
				// result = (Class<?>) fieldsArgTypes[2];
				result = (Class<?>) fieldsArgTypes[0];
			else
				result = (Class<?>) fieldsArgTypes[0];
			// result = (Class<?>) fieldsArgTypes[1];

			String rem = result.getName();
			String[] patht = rem.split("\\.");
			String res = rem.replace(patht[patht.length - 1], "Q" + patht[patht.length - 1]);
			queryModel.setqTable(res);
		}
	}

	public Long count(QueryModel queryModel) {

		getQ(queryModel);

		String existText = queryModel.exitsText();
		String existWhere = queryModel.exitsWhere();

		Long count = null;
		if (existText != null && existWhere != null) {

			BooleanBuilder text = (BooleanBuilder) queryModel.getText();
			BooleanBuilder where = (BooleanBuilder) queryModel.getWhere();
			try {
				count = repository.count(text.and(where));
			} catch (Exception e) {
				throw new DBQueryException(e);
			}
		} else if (existText != null)
			try {
				count = repository.count(queryModel.getText());
			} catch (Exception e) {
				throw new DBQueryException(e);
			}
		else if (existWhere != null)
			try {
				count = repository.count(queryModel.getWhere());
			} catch (Exception e) {
				throw new DBQueryException(e);
			}
		else
			count = repository.count();
		return count;
	}
}
