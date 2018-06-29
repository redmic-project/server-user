package es.redmic.user.common.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import es.redmic.databaselib.common.model.LongModel;
import es.redmic.exception.databinding.DTONotValidException;
import es.redmic.models.es.common.dto.BodyItemDTO;
import es.redmic.models.es.common.dto.DTO;
import es.redmic.models.es.common.dto.SuperDTO;
import es.redmic.user.common.service.IServiceRW;

public abstract class ControllerRWImpl<TDTO extends DTO, TModel extends LongModel> extends ControllerRImpl<TDTO, TModel> implements
		IControllerRW<TDTO, TModel> {

	private final IServiceRW<TModel, TDTO> service;

	public ControllerRWImpl(IServiceRW<TModel, TDTO> service) {
		super(service);
		this.service = service;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public SuperDTO update(@Valid @RequestBody TDTO dto, BindingResult errorDto, @PathVariable("id") Long id,
			HttpServletResponse response) {
		if (errorDto.hasErrors())
			throw new DTONotValidException(errorDto);
		dto.setId(id);
		return new BodyItemDTO<TDTO>(service.update(dto));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public SuperDTO delete(@PathVariable("id") Long id) {
		service.delete(id);
		return new SuperDTO(true);
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	@ResponseBody
	public SuperDTO add(@Valid @RequestBody TDTO dto, BindingResult errorDto) {
		if (errorDto.hasErrors())
			throw new DTONotValidException(errorDto);

		return new BodyItemDTO<TDTO>(service.save(dto));
	}
}
