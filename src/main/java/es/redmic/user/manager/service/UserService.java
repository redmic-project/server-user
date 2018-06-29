package es.redmic.user.manager.service;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import es.redmic.exception.database.DBConstraintViolationException;
import es.redmic.exception.database.DBPropertyValueException;
import es.redmic.exception.user.ActivateAccountTimeOutException;
import es.redmic.user.common.dto.TokenDTO;
import es.redmic.user.config.EntityManagerWrapper;
import es.redmic.user.manager.dto.AccessDTO;
import es.redmic.user.manager.dto.UserBaseDTO;
import es.redmic.user.manager.dto.UserDTO;
import es.redmic.user.manager.dto.UserRegisterDTO;
import es.redmic.user.manager.model.Access;
import es.redmic.user.manager.model.AccessPK;
import es.redmic.user.manager.model.Module;
import es.redmic.user.manager.model.User;
import es.redmic.user.manager.repository.AccessRepository;
import es.redmic.user.manager.repository.UserRepository;
import es.redmic.user.manager.repository.UserSectorRepository;
import es.redmic.user.manager.utils.PasswordManager;

@Service
public class UserService extends UserBaseService<User, UserDTO> {

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
	public UserService(UserRepository repository) {
		super(repository);
		this.repository = repository;
	}

	public void save(UserRegisterDTO dto) {
		// count(*) es necesario cuando la funci�n no devuelve nada
		String queryString = "SELECT count(*) from app.register(?, ?, ?, ?)";
		ArrayList<Object> parameters = new ArrayList<Object>();

		parameters.add(0, dto.getEmail());
		parameters.add(1, dto.getFirstName());
		parameters.add(2, dto.getLastName());
		parameters.add(3, PasswordManager.encrypt(dto.getPassword()));

		emw.setQueryString(queryString);
		emw.setParameters(parameters);

		emw.execute();
	}

	@Override
	public User updateModel(User model) {
		repository.findById(model.getId()).get();

		model = persist(model);

		User user = new User();
		user.setId(model.getId());

		// Eliminar del modelo los que no se enviam
		List<Access> modelAccessList = accessRepository.findByUser(user);
		for (int i = 0; i < modelAccessList.size(); i++) {

			Access item = modelAccessList.get(i);
			List<Access> accessList = model.getAccesses();

			AccessDTO accessDto = new AccessDTO();
			accessDto = factory.map(item, AccessDTO.class);

			if (accessList.indexOf(accessDto) == -1) {
				try {
					accessRepository.delete(item);
				} catch (DataIntegrityViolationException e) {
					throw new DBConstraintViolationException(e);
				} catch (ConstraintViolationException e) {
					throw new DBPropertyValueException(e);
				}
			}
		}

		// A�adir nuevos o modificados
		List<Access> accessList = model.getAccesses();
		for (int i = 0; i < accessList.size(); i++) {

			Access item = accessList.get(i);
			List<Access> modelAccessListAdd = accessRepository.findByUser(user);

			Module module = new Module();
			module.setId(item.getModule().getId());

			if (modelAccessListAdd.indexOf(item) == -1) {
				// nuevo o modificado
				AccessPK accessPk = new AccessPK();
				accessPk.setModule(item.getModule().getId());
				accessPk.setUserid(model.getId());

				Access access = new Access();
				access.setId(accessPk);
				access.setModule(module);
				access.setPerms(item.getPerms());
				access.setUser(user);
				try {
					accessRepository.saveAndFlush(access);
				} catch (DataIntegrityViolationException e) {
					throw new DBConstraintViolationException(e);
				} catch (ConstraintViolationException e) {
					throw new DBPropertyValueException(e);
				}
			}
		}

		model.setAccesses(accessRepository.findByUser(user));
		return model;
	}

	public User updateProfile(UserBaseDTO dto) {
		User model = repository.findById(dto.getId()).get();
		factory.map(dto, model);
		return persist(model);
	}

	public void activateAccount(TokenDTO tokenDTO) {
		User user = findProfileByToken(tokenDTO.getToken());

		DateTime dateToken = user.getDatetokenconfirmation();
		user.setDatetokenconfirmation(null);
		user.setTokenConfirmation(null);

		if (dateToken != null && dateToken.isAfter(new DateTime().minusHours(passwordTimeOut))) {
			user.setEnabled(true);
			persist(user);
		} else {
			persist(user);
			throw new ActivateAccountTimeOutException();
		}
	}

}
