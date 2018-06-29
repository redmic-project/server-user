package es.redmic.user.common.service;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import es.redmic.databaselib.common.model.LongModel;
import es.redmic.databaselib.common.repository.BaseRepository;
import es.redmic.exception.common.BaseException;
import es.redmic.exception.database.DBConstraintViolationException;
import es.redmic.exception.database.DBPropertyValueException;
import es.redmic.models.es.common.dto.DTO;

public abstract class ServiceRWImpl<TModel extends LongModel, TDTO extends DTO> extends ServiceRImpl<TModel, TDTO>
		implements IServiceRW<TModel, TDTO> {

	protected BaseRepository<TModel, Long> repository;

	@Autowired
	public ServiceRWImpl(BaseRepository<TModel, Long> repository) {
		super(repository);
		this.repository = repository;
	}

	public TDTO save(TDTO dto) {
		TModel model = convertDtoToModel(dto);
		return convertModelToDto(this.saveModel(model));
	}

	public TModel saveModel(TModel model) {
		return persist(model);
	}

	public TDTO update(TDTO dto) {
		findById(dto.getId());
		TModel model = convertDtoToModel(dto);
		return convertModelToDto(updateModel(model));
	}

	@Transactional(rollbackFor = { BaseException.class })
	public TModel updateModel(TModel model) {
		return persist(model);
	}

	@Transactional
	public TModel persist(TModel model) {
		try {
			model = repository.saveAndFlush(model);
			repository.refresh(model);
		} catch (DataIntegrityViolationException e) {
			throw new DBConstraintViolationException(e);
		} catch (ConstraintViolationException e) {
			throw new DBPropertyValueException(e);
		}
		return model;
	}

	public void delete(Long id) {
		deleteModel(findById(id));
	}

	public void deleteModel(TModel model) {
		repository.delete(model);
	}
}
