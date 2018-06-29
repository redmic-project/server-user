package es.redmic.user.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.user.common.controller.ControllerRWImpl;
import es.redmic.user.manager.dto.ModuleDTO;
import es.redmic.user.manager.model.Module;
import es.redmic.user.manager.service.ModuleService;

@RestController
@RequestMapping(value = "${controller.mapping.MODULE}")
public class ModuleController extends ControllerRWImpl<ModuleDTO, Module> {

	ModuleService service;
	
	@Autowired
	public ModuleController(ModuleService service) {
		super(service);
		
		this.service = service;
	}
	
	/*
	 * Obtener solo los modulos a los que puede acceder un usuario invitado
	 */

	@RequestMapping(value = "/openmodules/", method = RequestMethod.GET)
	public Object findModules() {
		
		return service.getOpenModules();
	}
}
