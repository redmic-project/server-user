package es.redmic.user.common.service;

import es.redmic.databaselib.common.model.LongModel;
import es.redmic.models.es.common.dto.DTO;
import es.redmic.user.common.query.QueryModel;

public interface IServiceR<TModel extends LongModel, TDTO extends DTO> {

	public TModel findById(Long id);

	public Long count(QueryModel queryModel);

	public Iterable<TModel> findAll(QueryModel queryModel);
	
	public TDTO convertModelToDto(TModel model);
}
