package es.redmic.user.common.service;

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
