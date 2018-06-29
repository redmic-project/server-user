package es.redmic.user.common.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import es.redmic.databaselib.common.model.LongModel;
import es.redmic.models.es.common.dto.DTO;
import es.redmic.models.es.common.dto.SuperDTO;

public interface IControllerRW<TDTO extends DTO, TModel extends LongModel>
		extends IControllerR<TDTO, TModel> {

	public SuperDTO update(@Valid @RequestBody TDTO dto, BindingResult bindingResult, @PathVariable("id") Long id,
			HttpServletResponse response);

	public SuperDTO delete(@PathVariable("id") Long id);

	public SuperDTO add(@Valid @RequestBody TDTO dto, BindingResult bindingResult);

}
