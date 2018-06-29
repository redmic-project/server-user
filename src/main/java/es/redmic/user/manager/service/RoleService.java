package es.redmic.user.manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.redmic.databaselib.user.model.Role;
import es.redmic.models.es.maintenance.administrative.dto.RoleDTO;
import es.redmic.user.common.service.ServiceRWImpl;
import es.redmic.user.manager.repository.RoleRepository;

@Service
public class RoleService extends ServiceRWImpl<Role, RoleDTO> {

	@Autowired
	public RoleService(RoleRepository repository) {
		super(repository);
	}
}
