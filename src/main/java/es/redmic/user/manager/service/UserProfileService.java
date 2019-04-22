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

import java.util.ArrayList;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import es.redmic.exception.user.PasswordNotMatchException;
import es.redmic.exception.user.ResetPasswordTimeOutException;
import es.redmic.user.config.EntityManagerWrapper;
import es.redmic.user.manager.dto.UserChangeEmailDTO;
import es.redmic.user.manager.dto.UserChangeImageDTO;
import es.redmic.user.manager.dto.UserChangePasswordDTO;
import es.redmic.user.manager.dto.UserChangeSectorDTO;
import es.redmic.user.manager.dto.UserDTO;
import es.redmic.user.manager.dto.UserNameDTO;
import es.redmic.user.manager.dto.UserResettingPasswordDTO;
import es.redmic.user.manager.model.User;
import es.redmic.user.manager.model.UserSector;
import es.redmic.user.manager.repository.AccessRepository;
import es.redmic.user.manager.repository.UserRepository;
import es.redmic.user.manager.repository.UserSectorRepository;
import es.redmic.user.manager.utils.PasswordManager;

@Service
public class UserProfileService extends UserBaseService<User, UserDTO> {

	@Value("${property.PASSWORD_TIME_OUT}")
	int passwordTimeOut; // horas para hacer resetting o activar cuenta

	@Autowired
	EntityManagerWrapper emw;

	@Autowired
	AccessRepository accessRepository;

	@Autowired
	UserSectorRepository userSectorRepository;

	UserRepository repository;

	@Autowired
	public UserProfileService(UserRepository repository) {
		super(repository);
		this.repository = repository;
	}

	public UserChangePasswordDTO changePassword(UserChangePasswordDTO dto) {
		User model = findById(dto.getId());
		if (PasswordManager.match(dto.getOldPassword(), model.getPassword())) {
			model.setPassword(PasswordManager.encrypt(dto.getPassword()));
			model = persist(model);
		} else
			throw new PasswordNotMatchException();

		// TODO Quitar este DTO
		return new UserChangePasswordDTO();
	}

	public User changeName(UserNameDTO dto) {
		User model = repository.findById(dto.getId()).get();
		factory.map(dto, model);
		return persist(model);
	}

	public User changeSector(UserChangeSectorDTO dto) {
		User model = repository.findById(dto.getId()).get();
		UserSector userSectorModel = null;
		if (dto.getSector() != null)
			userSectorModel = userSectorRepository.findById(dto.getSector().getId()).get();
		model.setSector(userSectorModel);
		return persist(model);
	}

	public User changeEmail(UserChangeEmailDTO dto) {
		User model = repository.findById(dto.getId()).get();
		factory.map(dto, model);
		return persist(model);
	}

	public User changeImage(UserChangeImageDTO dto) {
		User model = repository.findById(dto.getId()).get();
		factory.map(dto, model);
		return persist(model);
	}

	public UserDTO resetPassword(UserResettingPasswordDTO dto) {

		User user = findProfileByToken(dto.getToken());
		DateTime dateToken = user.getDatetokenconfirmation();
		user.setDatetokenconfirmation(null);
		user.setTokenConfirmation(null);

		if (dateToken != null && dateToken.isAfter(new DateTime().minusHours(passwordTimeOut))) {
			user.setPassword(PasswordManager.encrypt(dto.getPassword()));
			user = persist(user);
			return factory.map(user, UserDTO.class);
		} else {
			user = persist(user);
			throw new ResetPasswordTimeOutException();
		}
	}

	public void resetPasswordRequest(String email) {

		String queryString = "SELECT * from app.resetting_password(?)";

		ArrayList<Object> parameters = new ArrayList<Object>();
		parameters.add(0, email);

		emw.setQueryString(queryString);
		emw.setParameters(parameters);
		emw.execute();
	}
}
