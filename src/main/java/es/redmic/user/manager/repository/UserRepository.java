package es.redmic.user.manager.repository;

import es.redmic.databaselib.common.repository.BaseRepository;
import es.redmic.user.manager.model.User;

public interface UserRepository extends BaseRepository<User, Long> {
	
	User findByEmail(String email);
	
	User findByTokenConfirmation(String tokenconfirmation);
}
