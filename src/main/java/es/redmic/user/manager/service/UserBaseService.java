package es.redmic.user.manager.service;

/*-
 * #%L
 * User
 * %%
 * Copyright (C) 2019 REDMIC Project / Server
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import es.redmic.exception.database.DBNotFoundException;
import es.redmic.exception.user.TokenNotFoundException;
import es.redmic.user.common.service.ServiceRWImpl;
import es.redmic.user.manager.dto.UserDTO;
import es.redmic.user.manager.model.User;
import es.redmic.user.manager.repository.UserRepository;

@Service
public class UserBaseService<TModel extends User, TDTO extends UserDTO> extends ServiceRWImpl<User, UserDTO> {

	@Value("${property.GUEST_USERNAME}")
	private String GUEST_USERNAME;
	@Value("${property.GUEST_USERNAME_OAUTH}")
	private String GUEST_USERNAME_OAUTH;
	
	UserRepository repository;
	
	@Autowired
	public UserBaseService(UserRepository repository) {
		super(repository);
		this.repository = repository;
	}

	public User findProfileByUsername(String username) {

		User model = repository.findByEmail(username);
		if (model == null)
			throw new DBNotFoundException("username", username);
		return model;
	}

	public User findProfileByToken(String token) {
		User model = repository.findByTokenConfirmation(token);

		if (model == null)
			throw new TokenNotFoundException();

		return model;
	}
	
	public String getUsername() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication.getPrincipal().toString().equalsIgnoreCase(GUEST_USERNAME_OAUTH))
			return GUEST_USERNAME;
		Object principal = authentication.getPrincipal();
		
		if (principal instanceof String)
			return principal.toString();
		return ((UserDetails) principal).getUsername();
	}
}
