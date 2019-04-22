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

import es.redmic.databaselib.common.model.LongModel;
import es.redmic.models.es.common.dto.DTO;
import es.redmic.user.common.query.QueryModel;

public interface IServiceR<TModel extends LongModel, TDTO extends DTO> {

	public TModel findById(Long id);

	public Long count(QueryModel queryModel);

	public Iterable<TModel> findAll(QueryModel queryModel);
	
	public TDTO convertModelToDto(TModel model);
}
