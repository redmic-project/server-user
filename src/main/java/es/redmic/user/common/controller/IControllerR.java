package es.redmic.user.common.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import es.redmic.databaselib.common.model.SuperModel;
import es.redmic.models.es.common.dto.DTO;
import es.redmic.models.es.common.dto.SuperDTO;
import es.redmic.user.common.query.QueryModel;

public interface IControllerR<TDTO extends DTO, TMod extends SuperModel> {

	public SuperDTO findById(@PathVariable("id") Long id, HttpServletResponse response);

	public SuperDTO findAll(@ModelAttribute("querymodel") QueryModel querymodel,
			@RequestHeader(value = "Range", required = false) String rangeHeader, HttpServletResponse response);
}
