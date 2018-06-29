package es.redmic.user.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.user.common.controller.ControllerRWImpl;
import es.redmic.user.manager.dto.UserSectorDTO;
import es.redmic.user.manager.model.UserSector;
import es.redmic.user.manager.service.UserSectorService;

@RestController
@RequestMapping(value = "${controller.mapping.USER_SECTOR}")
public class UserSectorController extends ControllerRWImpl<UserSectorDTO, UserSector> {

	@Autowired
	public UserSectorController(UserSectorService service) {
		super(service);
	}
}