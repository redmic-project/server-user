package es.redmic.user.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.databaselib.user.model.Role;
import es.redmic.models.es.maintenance.administrative.dto.RoleDTO;
import es.redmic.user.common.controller.ControllerRWImpl;
import es.redmic.user.manager.service.RoleService;

@RestController
@RequestMapping(value = "${controller.mapping.ROLE}")
public class RoleController extends ControllerRWImpl<RoleDTO, Role> {

	@Autowired
	public RoleController(RoleService service) {
		super(service);
	}
}
