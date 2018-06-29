package es.redmic.user.manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.redmic.user.common.service.ServiceRWImpl;
import es.redmic.user.manager.dto.UserSectorDTO;
import es.redmic.user.manager.model.UserSector;
import es.redmic.user.manager.repository.UserSectorRepository;

@Service
public class UserSectorService extends ServiceRWImpl<UserSector, UserSectorDTO> {

	@Autowired
	public UserSectorService(UserSectorRepository repository) {
		super(repository);
	}
}