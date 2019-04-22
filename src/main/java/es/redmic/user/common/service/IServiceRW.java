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

public interface IServiceRW<TModel extends LongModel, TDTO extends DTO> extends IServiceR<TModel, TDTO> {

	public TDTO save(TDTO dto);

	public TModel saveModel(TModel model);

	public TModel updateModel(TModel model);

	public TDTO update(TDTO dto);

	public TModel persist(TModel model);

	public void deleteModel(TModel model);

	public void delete(Long id);
}
